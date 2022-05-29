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
import ro.ubbcluj.map.monitangaj.HelloApplication;
import ro.ubbcluj.map.monitangaj.authentication.PasswordRepository;
import ro.ubbcluj.map.monitangaj.domain.*;
import ro.ubbcluj.map.monitangaj.service.SarciniService;
import ro.ubbcluj.map.monitangaj.service.AngajatiService;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SarciniController implements Initializable {
    private SarciniService sarciniService;
    private AngajatiService angajatiService;
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
    Button terminaButton;
    @FXML
    Button logOutButton;
    @FXML
    Button reincarcaButton;

    public void setSarciniService(SarciniService sarciniService){
        this.sarciniService = sarciniService;
        loadSarcini();
    }

    public void setAngajatiService(AngajatiService angajatiService){
        this.angajatiService = angajatiService;
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

    public void terminaSarcinaDialog(ActionEvent actionEvent) throws IOException {
        if(!checkSelection())
            return;

        if(sarciniTable.getSelectionModel().getSelectedItem().getStatus() == StatusSarcina.COMPLETA)
            return;

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("confirm.fxml"));
        Scene termina = new Scene(fxmlLoader.load(), 346, 148);
        ConfirmController terminaController = fxmlLoader.getController();
        window.setTitle("Termina Sarcina");
        window.setScene(termina);
        window.showAndWait();
        if (terminaController.isConfirmation())
            terminaSarcina();
        refresh();
    }

    private void terminaSarcina() {
        Sarcina sarcina = sarciniTable.getSelectionModel().getSelectedItem();
        sarcina.setStatus(StatusSarcina.COMPLETA);
        sarciniService.updateEntity(sarcina);
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

    public void logout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        Angajat angajat = angajatiService.findOne(id);
        angajat.setPrezenta("absent");
        angajatiService.updateEntity(angajat);
        stage.close();
    }

    public void refreshButton(ActionEvent actionEvent) {
        refresh();
    }
}
