module org.example.proyectofinalprogramacion2 {
    requires javafx.controls;
    requires javafx.fxml;

    // Exporta el paquete View para que JavaFX lo vea
    exports org.example.proyectofinalprogramacion2.View;

    // Permite que FXML inyecte los @FXML
    opens org.example.proyectofinalprogramacion2.View to javafx.fxml;
}