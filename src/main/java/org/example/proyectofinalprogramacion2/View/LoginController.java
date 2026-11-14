package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.Admin;
import org.example.proyectofinalprogramacion2.Model.User;
import org.example.proyectofinalprogramacion2.Services.DataStore;
import org.example.proyectofinalprogramacion2.Services.SessionManager;

import java.io.IOException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void onLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = DataStore.authenticate(email, password);

        if (user == null) {
            errorLabel.setText("Correo o contraseña incorrectos.");
            return;
        }

        SessionManager.getInstance().setCurrentUser(user);

        String fxmlPath = (user instanceof Admin)
                ? "/org/example/proyectofinalprogramacion2/View/admin-dashboard-view.fxml"
                : "/org/example/proyectofinalprogramacion2/View/customer-dashboard-view.fxml";

        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            WindowManager.setMainStage(stage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), 900, 650);
            StylesManager.applyStyles(scene);

            stage.setTitle("CinemaUQ - " + (user instanceof Admin ? "Administrador" : "Cliente"));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error al cargar la ventana.");
        }
    }
}