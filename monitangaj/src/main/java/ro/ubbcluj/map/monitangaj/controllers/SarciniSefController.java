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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ro.ubbcluj.map.monitangaj.HelloApplication;
import ro.ubbcluj.map.monitangaj.domain.Sarcina;
import ro.ubbcluj.map.monitangaj.domain.StatusSarcina;
import ro.ubbcluj.map.monitangaj.service.SarciniService;

import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SarciniSefController implements Initializable {
    private SarciniService sarciniService;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    ObservableList<Sarcina> sarcini = FXCollections.observableArrayList();

    @FXML
    TableView<Sarcina> sarciniTable;
    @FXML
    TableColumn<Sarcina, String> sarciniTableDescriere;
    @FXML
    TableColumn<Sarcina, String> sarciniTableStatus;
    @FXML
    Button modificaButton;
    @FXML
    Button revocaButton;
    @FXML
    Button inapoiButton;

    public void setSarciniService(SarciniService sarciniService){
        this.sarciniService = sarciniService;
        loadSarcini();
    }

    private void loadSarcini() {
        sarcini.clear();
        for (Sarcina sarcina : sarciniService.findAll()) {
            if(Objects.equals(sarcina.getIdAngajat(), this.id))
                sarcini.add(sarcina);
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sarciniTableDescriere.setCellValueFactory(new PropertyValueFactory<Sarcina, String>("descriere"));
        sarciniTableStatus.setCellValueFactory(new PropertyValueFactory<Sarcina, String>("status"));
        sarciniTable.setItems(sarcini);

    }

    private void refresh() {
        sarcini.clear();
        loadSarcini();
        sarciniTable.setItems(sarcini);
    }


    public void modificaSarcinaDialog(ActionEvent actionEvent) throws IOException {
        if(!checkSelection())
            return;

        Long idSarcina = sarciniTable.getSelectionModel().getSelectedItem().getId();

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("trimiteSarcina.fxml"));
        Scene modifica = new Scene(fxmlLoader.load(), 500, 200);
        TrimiteSarcinaController modificaSarcinaController = fxmlLoader.getController();
        window.setTitle("Modifica Sarcina");
        modificaSarcinaController.setText("Modifica");
        modificaSarcinaController.setIdAngajat(id);
        modificaSarcinaController.setIdSarcina(idSarcina);
        window.setScene(modifica);
        window.showAndWait();

        try {
            if(modificaSarcinaController.getToAdd().getIdAngajat() == 0L)
                return;
            sarciniService.updateEntity(modificaSarcinaController.getToAdd());
        }catch(IllegalArgumentException| InputMismatchException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.getDialogPane().setHeaderText(e.getMessage());
            alert.showAndWait();
        }

        refresh();
    }

    public void revocaSarcinaDialog(ActionEvent actionEvent) throws IOException {
        if(!checkSelection())
            return;

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("confirm.fxml"));
        Scene revoca = new Scene(fxmlLoader.load(), 346, 148);
        ConfirmController revocaController = fxmlLoader.getController();
        window.setTitle("Revoca Sarcina");
        window.setScene(revoca);
        window.showAndWait();
        if (revocaController.isConfirmation())
            revocaSarcina();
        refresh();
    }

    private void revocaSarcina() {
        Sarcina sarcina = sarciniTable.getSelectionModel().getSelectedItem();
        sarciniService.deleteEntity(sarcina.getId());
    }

    private boolean checkSelection() {
        if(sarciniTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.getDialogPane().setHeaderText("N-ai selectat nimic");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void inapoi(ActionEvent actionEvent) {
        Stage stage = (Stage) inapoiButton.getScene().getWindow();
        stage.close();
    }
}
