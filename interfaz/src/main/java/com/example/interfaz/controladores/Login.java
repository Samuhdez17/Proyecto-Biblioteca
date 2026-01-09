package com.example.interfaz.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class Login {
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

    @FXML
    void initialize() {
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
}