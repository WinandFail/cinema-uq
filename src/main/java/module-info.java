module org.example.proyectofinalprogramacion2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.example.proyectofinalprogramacion2;


    opens org.example.proyectofinalprogramacion2 to javafx.fxml;
    exports org.example.proyectofinalprogramacion2;
}