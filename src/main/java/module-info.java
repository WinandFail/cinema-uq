module org.example.proyectofinalprogramacion2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyectofinalprogramacion2 to javafx.fxml;
    exports org.example.proyectofinalprogramacion2;
}