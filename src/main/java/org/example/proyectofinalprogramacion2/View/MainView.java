package org.example.proyectofinalprogramacion2.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 450, 450);
        StylesManager.applyStyles(scene);

        stage.setTitle("CinemaUQ - Login");
        stage.setScene(scene);
        stage.show();
    }
}