package ro.ubbcluj.map.monitangaj.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ro.ubbcluj.map.monitangaj.domain.Angajat;
import ro.ubbcluj.map.monitangaj.domain.Sarcina;
import ro.ubbcluj.map.monitangaj.domain.StatusSarcina;

import java.net.URL;
import java.util.ResourceBundle;

public class TrimiteSarcinaController {

    private Sarcina toAdd = new Sarcina("", StatusSarcina.INCOMPLETA, 0L);
    private Long idAngajat;
    private Long idSarcina = 0L;

    public void setIdSarcina(Long id_sarcina) {
        this.idSarcina = id_sarcina;
    }

    public void setIdAngajat(Long id_angajat) {
        this.idAngajat = id_angajat;
    }

    public void setText(String text) {
        textSus.setText(text + " Sarcina");
        trimiteButton.setText(text);
    }

    public Sarcina getToAdd() {
        return toAdd;
    }

    @FXML
    TextArea descriere;
    @FXML
    Button trimiteButton;
    @FXML
    Button inapoiButton;
    @FXML
    Text textSus;

    public void trimiteSarcina(ActionEvent actionEvent) {
        Stage stage = (Stage) trimiteButton.getScene().getWindow();
        toAdd.setIdAngajat(idAngajat);
        toAdd.setDescriere(descriere.getText());
        if(idSarcina != 0L)
            toAdd.setId(idSarcina);
        stage.close();
    }

    public void inapoi(ActionEvent actionEvent) {
        Stage stage = (Stage) inapoiButton.getScene().getWindow();
        stage.close();
    }
}
