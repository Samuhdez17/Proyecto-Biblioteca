package com.example.interfaz.controladores.menus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MenuPrincipal {
    @FXML
    private Button btMenuEjem;

    @FXML
    private Button btMenuLibros;

    @FXML
    private Button btMenuPrest;

    @FXML
    private Button btMenuUsuarios;

    @FXML
    private Button btVerInfo;

    @FXML
    private ImageView fotoUsuario;

    @FXML
    private Label nombreRolUsuario;

    @FXML
    void irA(ActionEvent event) {
        Button menu = (Button) event.getSource();

        if (menu == btMenuLibros) {

        } else if (menu == btMenuEjem) {

        } else if (menu == btMenuPrest) {

        } else if (menu == btMenuUsuarios) {

        } else if (menu == btVerInfo) {

        } else {
            throw new IllegalStateException("Valor inesperado: " + menu);
        }
    }
}
