module com.example.interfaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens com.example.interfaz to javafx.fxml;
    exports com.example.interfaz;
    exports com.example.interfaz.controladores;
    opens com.example.interfaz.controladores to javafx.fxml;
}