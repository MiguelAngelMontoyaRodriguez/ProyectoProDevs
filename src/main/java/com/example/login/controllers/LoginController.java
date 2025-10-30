package com.example.login.controllers;

import com.example.login.repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtContraseña;

    @FXML
    private Button btnIngresar;

    private UserRepository userRepository;

    @FXML
    public void initialize() {
        userRepository = UserRepository.getInstancia();
    }

    /**
     * Maneja el evento de guardar producto
     */
    @FXML
    private void onIngresar() {
        if (!validarCampos()) {
            return;
        }

        try {
            String Email = txtCorreo.getText().trim();
            String Password = txtContraseña.getText().trim();


            // Verificar si el correo esta registrado
            if (UserRepository.searchUser(Email, Password) == 1) {
                mostrarAlerta("Verficación Exitosa", "Bienvenido, Usuario", Alert.AlertType.INFORMATION);
                return;
            }
            else{
                mostrarAlerta("Error", "Usuario no existente", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Usuario o Contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    /**
     * Valida que los campos esten completos
     */
    private boolean validarCampos() {
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El Correo es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtContraseña.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "La Contraseña es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    /**
     * Muestra una alerta al usuario
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


}
