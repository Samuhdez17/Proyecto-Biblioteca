package com.example.interfaz.controladores.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

public class MenuLibros {
    @FXML
    private Button btAtras;

    @FXML
    private Button btRegistrarLibro;

    @FXML
    void initialize() {
        if (btAtras != null && btRegistrarLibro != null) {
            btAtras.setContentDisplay(ContentDisplay.TOP);
            btRegistrarLibro.setContentDisplay(ContentDisplay.TOP);
        }
    }

    @FXML
    void irMenuPrincipal(ActionEvent event) {

    }

    @FXML
    void registrarLibro(ActionEvent event) {

    }
}
