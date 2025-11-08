package com.example.login.controllers;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;



public class LoginController {
    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtContraseña;

    @FXML
    private Button btnIngresar;

    private UserRepository userRepository;

    @FXML
    private AnchorPane rootPane;


    @FXML
    public void initialize() {
        userRepository = UserRepository.getInstancia();
        userRepository.cargarDatosEjemplo();

        rootPane.setStyle("""
                -fx-background-radius: 50;
                -fx-border-width: 15;
                -fx-background-color: linear-gradient(from 0% 0% to 0% 100%, #8247b5 0%, #636060 100%)
                """);

        btnIngresar.setStyle(
                "-fx-background-color: linear-gradient(to right, #8247b5, #3a3a3a);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-radius: 25;" +
                        "-fx-border-color: #ffd700;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0.5, 0, 2);"
        );

        btnIngresar.setOnMouseEntered(e -> {
            btnIngresar.setStyle(
                    "-fx-background-color: linear-gradient(to right, #9d5de5, #555);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 25;" +
                            "-fx-border-radius: 25;" +
                            "-fx-border-color: #ffea00;" +
                            "-fx-border-width: 1.5;" +
                            "-fx-cursor: hand;" +
                            "-fx-scale-x: 1.07;" +
                            "-fx-scale-y: 1.07;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 10, 0.5, 0, 3);"
            );
        });

        btnIngresar.setOnMouseExited(e -> {
            btnIngresar.setStyle(
                    "-fx-background-color: linear-gradient(to right, #8247b5, #3a3a3a);" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 25;" +
                            "-fx-border-radius: 25;" +
                            "-fx-border-color: #ffd700;" +
                            "-fx-border-width: 1.5;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0.5, 0, 2);" +
                            "-fx-scale-x: 1.0;" +
                            "-fx-scale-y: 1.0;"
            );
        });

        btnIngresar.setOnMousePressed(e -> btnIngresar.setScaleX(0.95));
        btnIngresar.setOnMousePressed(e -> btnIngresar.setScaleY(0.95));
        btnIngresar.setOnMouseReleased(e -> {
            btnIngresar.setScaleX(1.0);
            btnIngresar.setScaleY(1.0);
        });
    }

    /**
     * Maneja el evento de inciar sesion
     */
    @FXML
    private void onIngresar(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        try {
            String Email = txtCorreo.getText().trim();
            String Password = txtContraseña.getText().trim();

            User user = userRepository.login(Email, Password);


            // Verificar si el correo esta registrado
            if (UserRepository.searchUser(Email, Password) ) {
                try {
                    // Cargar el nuevo FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/dashboard.fxml"));
                    Parent root = loader.load();

                    DashboardController controller = loader.getController();
                    controller.setUser(user);

                    // Crear nueva escena
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Dashboard");
                    stage.show();

                    // Cerrar la ventana de login
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
