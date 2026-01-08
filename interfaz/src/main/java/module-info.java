module com.example.interfaz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interfaz to javafx.fxml;
    exports com.example.interfaz;
}