package com.example.interfaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class Controlador {
    // ESCENA 1 (LOGIN)
    @FXML
    private CheckBox cbCaracteres;

    @FXML
    private Label mensajeErrLogin1;

    @FXML
    private Label mensajeErrLogin2;
    @FXML
    private TextField email;

    @FXML
    private Button logIn;

    @FXML
    private TextField passVisible;

    @FXML
    private PasswordField pssFiled;

    // ESCENA 2 (MENU PRINCIPAL)
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

    // ESCENA 3 (MENU LIBROS)

    // ESCENA 4 (MENU EJEMPLARES)

    // ESCENA 5 (MENU PRESTAMOS)

    // ESCENA 6 (MENU USUARIOS)

    // ESCENA 7 (INFORMACION)

    @FXML
    void initialize() {
        // ESCENA 1
        if (
                mensajeErrLogin1 != null && mensajeErrLogin2 != null &&
                passVisible != null && pssFiled != null
        ) {
            mensajeErrLogin1.setVisible(false);
            mensajeErrLogin2.setVisible(false);
            passVisible.setVisible(false);
            pssFiled.setVisible(true);
        }
    }

    // ESCENA 1
    @FXML
    void iniciarSesion(ActionEvent event) {
        mensajeErrLogin2.setVisible(email.getText().isEmpty() || pssFiled.getText().isEmpty());


    }

    @FXML
    void showPassword(ActionEvent event) {
        if (cbCaracteres.isSelected()) {
            passVisible.setText(pssFiled.getText());
            passVisible.setVisible(true);
            pssFiled.setVisible(false);
        } else {
            pssFiled.setText(passVisible.getText());
            pssFiled.setVisible(true);
            passVisible.setVisible(false);
        }
    }

    // ESCENA 2
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