package org.example.proyectofinalprogramacion2.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainView extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MainView.class.getResource("/org/example/proyectofinalprogramacion2/View/main-view.fxml")
        );
        Scene scene = new Scene(loader.load(), 900, 600);
        stage.setTitle("CinemaUQ");
        stage.setScene(scene);
        stage.show();
    }
}