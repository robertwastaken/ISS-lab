package ro.ubbcluj.map.monitangaj.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ro.ubbcluj.map.monitangaj.HelloApplication;
import ro.ubbcluj.map.monitangaj.domain.Angajat;
import ro.ubbcluj.map.monitangaj.service.SarciniService;
import ro.ubbcluj.map.monitangaj.service.AngajatiService;

import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AngajatiSefController implements Initializable {

    private AngajatiService angajatiService;
    private SarciniService sarciniService;
    ObservableList<Angajat> angajati = FXCollections.observableArrayList();

    @FXML
    TableView<Angajat> angajatiTable;
    @FXML
    TableColumn<Angajat, Long> angajatiTableNume;
    @FXML
    TableColumn<Angajat, String> angajatiTablePrenume;
    @FXML
    TableColumn<Angajat, String> angajatiTablePrezenta;
    @FXML
    Button transmiteButton;
    @FXML
    Button veziButton;
    @FXML
    Button logOutButton;

    public AngajatiSefController() {
    }

    public void setAngajatiService(AngajatiService angajatiService){
        this.angajatiService = angajatiService;
        loadAngajati();
    }
    public void setSarciniService(SarciniService sarciniService){
        this.sarciniService = sarciniService;
    }

    private void loadAngajati() {
        Iterable<Angajat> angajatiIterable = angajatiService.findAll();
        List<Angajat> angajatiList = StreamSupport.stream(angajatiIterable.spliterator(), false)
                .collect(Collectors.toList());
        angajati.setAll(angajatiList);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        angajatiTableNume.setCellValueFactory(new PropertyValueFactory<Angajat, Long>("nume"));
        angajatiTablePrenume.setCellValueFactory(new PropertyValueFactory<Angajat, String>("prenume"));
        angajatiTablePrezenta.setCellValueFactory(new PropertyValueFactory<Angajat, String>("prezenta"));
        angajatiTable.setItems(angajati);
    }

    private void refresh() {
        loadAngajati();
        angajatiTable.setItems(angajati);
    }

    public void transmiteSarcinaDialog(ActionEvent actionEvent) throws IOException {

        if(!checkSelection())
            return;

        Long id = angajatiTable.getSelectionModel().getSelectedItem().getId();

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trimiteSarcina.fxml"));
        Scene trimite = new Scene(fxmlLoader.load(), 500, 200);
        TrimiteSarcinaController trimiteSarcinaController = fxmlLoader.getController();
        window.setTitle("Trimite Sarcina");
        trimiteSarcinaController.setText("Trimite");
        trimiteSarcinaController.setIdAngajat(id);
        window.setScene(trimite);
        window.showAndWait();

        try {
            if(trimiteSarcinaController.getToAdd().getIdAngajat() == 0L)
                return;
            sarciniService.addEntity(trimiteSarcinaController.getToAdd());
        }catch(IllegalArgumentException| InputMismatchException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.getDialogPane().setHeaderText(e.getMessage());
            alert.showAndWait();
        }

        refresh();
    }

    private boolean checkSelection(){
        Angajat selected = angajatiTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.getDialogPane().setHeaderText("N-ai selectat nimic");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void veziSarciniDialog(ActionEvent actionEvent) throws IOException {

        if(!checkSelection())
            return;
        Long id = angajatiTable.getSelectionModel().getSelectedItem().getId();

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sarciniSef.fxml"));
        Scene modifica = new Scene(fxmlLoader.load(), 800, 400);
        SarciniSefController sarciniSefController = fxmlLoader.getController();
        sarciniSefController.setId(id);
        sarciniSefController.setSarciniService(sarciniService);
        window.setTitle("Lista Sarcina");
        window.setScene(modifica);
        window.showAndWait();

        refresh();
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();
    }
}
