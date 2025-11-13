package com.example.login.controllers;

import com.example.login.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class BalanceController {

    @FXML
    private TextField txtBalance;

    private Client currentClient;

    /**
     * recibe el cliente actual desde el DashboardController
     */
    public void setClient(Client client) {
        this.currentClient = client;
        showBalance();
    }

    /**
     * Evento del botón "Actualizar saldo".
     * Refresca el saldo actual mostrado en pantalla.
     */
    @FXML
    private void OnRefreshBalance() {
        if (currentClient == null) {
            mostrarAlerta("Error", "No se encontró información del cliente.");
            return;
        }
        showBalance();
    }

    /**
     * Muestra el saldo del cliente actual en el TextField.
     */
    private void showBalance() {
        if (currentClient != null) {
            txtBalance.setText("$ " + String.format("%,.2f", currentClient.getBalance()));
        } else {
            txtBalance.setText("Cliente no disponible");
        }
    }

    /**
     * Muestra una alerta sencilla (para errores o mensajes informativos).
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}