package ro.ubbcluj.map.monitangaj.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ro.ubbcluj.map.monitangaj.HelloApplication;
import ro.ubbcluj.map.monitangaj.authentication.PasswordRepository;
import ro.ubbcluj.map.monitangaj.domain.Angajat;
import ro.ubbcluj.map.monitangaj.service.SarciniService;
import ro.ubbcluj.map.monitangaj.service.AngajatiService;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private AngajatiService angajatiService;
    private SarciniService sarciniService;
    private PasswordRepository passwordRepository;

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Text wrongText;
    @FXML
    Button loginButton;

    public void setAngajatiService(AngajatiService angajatiService){
        this.angajatiService = angajatiService;
    }
    public void setSarciniService(SarciniService sarciniService){
        this.sarciniService = sarciniService;
    }
    public void setPasswordRepository(PasswordRepository passwordRepository){
        this.passwordRepository = passwordRepository;
    }

    public void login(ActionEvent actionEvent) throws IOException {
        if (!usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty())
            if (passwordRepository.authenticate(usernameTextField.getText(), passwordTextField.getText())){
                if(Objects.equals(usernameTextField.getText(), "admin")){
                    Stage window = new Stage();

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("angajatiSef.fxml"));
                    Scene home = new Scene(fxmlLoader.load(), 600, 400);
                    AngajatiSefController homeController = fxmlLoader.getController();
                    homeController.setAngajatiService(angajatiService);
                    homeController.setSarciniService(sarciniService);
                    window.setTitle("Home");
                    window.setScene(home);
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                    window.show();
                }
                else{
                    Angajat angajat = angajatiService.findOne(passwordRepository.getId(usernameTextField.getText()));
                    angajat.setPrezenta("prezent");
                    angajatiService.updateEntity(angajat);

                    Stage window = new Stage();

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sarcini.fxml"));
                    Scene sarcini = new Scene(fxmlLoader.load(), 800, 400);
                    SarciniController sarciniController = fxmlLoader.getController();
                    sarciniController.setId(angajat.getId());
                    sarciniController.setSarciniService(sarciniService);
                    sarciniController.setAngajatiService(angajatiService);
                    window.setTitle(angajat.getNume() + " " + angajat.getPrenume());
                    window.setScene(sarcini);
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            angajat.setPrezenta("absent");
                            angajatiService.updateEntity(angajat);
                        }
                    });
                    window.show();
                }
            }
            else{
                wrongText.setVisible(true);
                passwordTextField.clear();
            }
    }
}
