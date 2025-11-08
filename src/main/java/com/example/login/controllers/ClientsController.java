package com.example.login.controllers;

import com.example.login.models.Client;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ClientsController {
    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtDocument;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<String> cbAccountType;

    @FXML
    private Button btnGuardar;

    private UserRepository userRepository;
    private DashboardController dashboardController;



    @FXML
    public void initialize() {
        userRepository = UserRepository.getInstancia();
    }

    /**
     * Establece el controlador del dashboard para poder regresar
     */
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    /**
     * Maneja el evento de guardar producto
     */
    @FXML
    private void OnRegisterClient() {
        if (!validarCampos()) {
            return;
        }

        try {
            String firstNames = txtFirstName.getText().trim();
            String lastNames = txtLastName.getText().trim();
            String document = txtDocument.getText().trim();
            String email = txtEmail.getText().trim();
            String password = txtPassword.getText().trim();
            String phone = txtPhone.getText().trim();
            String accountType = cbAccountType.getValue();



            // Verificar si el E-mail ya existe
            if (userRepository.searchForEmail(email) != null) {
                mostrarAlerta("Error", "Ya existe un Usuario con ese Email", Alert.AlertType.ERROR);
                return;
            }

            // Crear y guardar el Cliente
            User newUser = new Client(email, password, firstNames, lastNames, document, phone, accountType);
            userRepository.addUser(newUser);

            mostrarAlerta("Éxito", "Cliente creado correctamente", Alert.AlertType.INFORMATION);


        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Algo salio mal?", Alert.AlertType.ERROR);
        }
    }

    /**
     * Valida que los campos del formulario estén completos
     */
    private boolean validarCampos() {
        if (txtFirstName.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "Los nombres son obligatorios", Alert.AlertType.WARNING);
            return false;
        }
        if (txtLastName.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "Los apellidos son obligatorios", Alert.AlertType.WARNING);
            return false;
        }
        if (txtDocument.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El documento es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPhone.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El telefono es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El Email es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPassword.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "La contraseña es obligatoria", Alert.AlertType.WARNING);
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
