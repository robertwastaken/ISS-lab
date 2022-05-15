package ro.ubbcluj.map.monitangaj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.map.monitangaj.authentication.PasswordRepository;
import ro.ubbcluj.map.monitangaj.controllers.LoginController;
import ro.ubbcluj.map.monitangaj.repository.SarciniRepository;
import ro.ubbcluj.map.monitangaj.repository.AngajatiRepository;
import ro.ubbcluj.map.monitangaj.service.*;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/iss";
        String username = "postgres";
        String password = "admin";

        AngajatiRepository angajatiRepository = new AngajatiRepository(url, username, password);
        AngajatiService angajatiService = new AngajatiService(angajatiRepository);

        SarciniRepository sarciniRepository = new SarciniRepository(url, username, password);
        SarciniService sarciniService = new SarciniService(sarciniRepository);

        PasswordRepository passwordRepository = new PasswordRepository(url, username, password);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene home = new Scene(fxmlLoader.load(), 346, 211);
        stage.setTitle("Login");
        stage.setScene(home);
        LoginController loginController = fxmlLoader.getController();
        loginController.setAngajatiService(angajatiService);
        loginController.setSarciniService(sarciniService);
        loginController.setPasswordRepository(passwordRepository);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}