package org.example.proyectofinalprogramacion2.View;

import javafx.stage.Stage;

public class WindowManager {
    private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}