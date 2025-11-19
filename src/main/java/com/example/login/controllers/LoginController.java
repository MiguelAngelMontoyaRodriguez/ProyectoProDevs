package com.example.login.controllers;

import com.example.login.App;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;



public class LoginController {
    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Button btnIngresar;

    private UserRepository userRepository;

    @FXML
    private AnchorPane rootPane;



    @FXML
    public void initialize() {
        userRepository = UserRepository.getInstancia();
        userRepository.cargarDatosEjemplo();
    }

    /**
     * Maneja el evento de inciar sesion
     */
    @FXML
    private void onIngresar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        String email = txtCorreo.getText().trim();
        String password = txtContraseña.getText().trim();

        // Buscar el usuario en el repositorio
        User user = userRepository.login(email, password);

        if (user == null) {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
            return;
        }

        // Validar si está bloqueado
        if (user.isBlocked()) {
            mostrarAlerta("Acceso Denegado", "Su cuenta ha sido bloqueada. Contacta al administrador.", Alert.AlertType.ERROR);
            return;
        }

        // Guardar el usuario logueado globalmente
        App.loggedUser = user;

        try {
            // Cargar el Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();

            // Cerrar ventana de login
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el Dashboard.", Alert.AlertType.ERROR);
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
