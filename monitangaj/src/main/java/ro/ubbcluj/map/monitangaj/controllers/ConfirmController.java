package ro.ubbcluj.map.monitangaj.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmController {

    private boolean confirmation = false;

    public boolean isConfirmation() {
        return confirmation;
    }

    @FXML
    Button yesButton;
    @FXML
    Button noButton;

    public void yes(ActionEvent actionEvent) {
        this.confirmation = true;
        Stage stage = (Stage) yesButton.getScene().getWindow();
        stage.close();
    }

    public void no(ActionEvent actionEvent) {
        this.confirmation = false;
        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();
    }
}
