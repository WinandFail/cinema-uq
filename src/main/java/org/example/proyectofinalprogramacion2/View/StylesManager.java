package org.example.proyectofinalprogramacion2.View;

import javafx.scene.Scene;
import java.net.URL;

public class StylesManager {
    public static void applyStyles(Scene scene) {
        URL cssUrl = StylesManager.class.getResource("/org/example/proyectofinalprogramacion2/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println(" ADVERTENCIA: No se encontró styles.css");
        }
    }
}