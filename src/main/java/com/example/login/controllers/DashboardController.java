package com.example.login.controllers;

import com.example.login.App;
import com.example.login.models.Client;
import com.example.login.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class DashboardController {
    @FXML
    private Button btnRegisterClient;
    @FXML
    private Button btnDepositsWithdrawals;
    @FXML
    private Button btnCheckBalance;
    @FXML
    private Button btnTransfers;
    @FXML
    private Button btnGenerateReport;
    @FXML
    private Button btnEmployeeManagement;
    @FXML
    private Button btnSecurityAuthentication;
    @FXML
    private Button btnTransactionMonitoring;
    @FXML
    private Button btnAdvancedReports;
    @FXML
    private Button btnBalance;

    @FXML
    private StackPane MainContainer;

    private User loggedUser;

    public void setUser(User user) {
        this.loggedUser = user;
        interfaceForRole();
    }

    private void interfaceForRole() {
        int role = loggedUser.getRole();

        switch (role) {
            case 1:
                btnBalance.setVisible(false);
                btnBalance.setManaged(false);

                break;

            case 2:
                btnEmployeeManagement.setVisible(false);
                btnSecurityAuthentication.setVisible(false);
                btnTransactionMonitoring.setVisible(false);
                btnAdvancedReports.setVisible(false);
                btnBalance.setVisible(false);

                btnEmployeeManagement.setManaged(false);
                btnSecurityAuthentication.setManaged(false);
                btnTransactionMonitoring.setManaged(false);
                btnAdvancedReports.setManaged(false);
                btnBalance.setManaged(false);
                break;

            case 3:
                btnEmployeeManagement.setVisible(false);
                btnGenerateReport.setVisible(false);
                btnTransactionMonitoring.setVisible(false);
                btnAdvancedReports.setVisible(false);
                btnRegisterClient.setVisible(false);
                btnSecurityAuthentication.setVisible(false);
                btnCheckBalance.setVisible(false);

                btnEmployeeManagement.setManaged(false);
                btnGenerateReport.setManaged(false);
                btnTransactionMonitoring.setManaged(false);
                btnAdvancedReports.setManaged(false);
                btnRegisterClient.setManaged(false);
                btnSecurityAuthentication.setManaged(false);
                btnCheckBalance.setManaged(false);



                break;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void OnGoClients() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/login/clients.fxml"));
            Parent clients = loader.load();

            // Obtener el controlador de Clientes
            ClientsController controller = loader.getController();
            controller.setDashboardController(this);

            // Reemplazar el contenido del contenedor principal
            MainContainer.getChildren().clear();
            MainContainer.getChildren().add(clients);
            VBox.setVgrow(clients, Priority.ALWAYS);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el formulario de Clientes", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void OnGoLogout(ActionEvent event) {
        try {
            // Cargar el login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/login.fxml"));
            Parent root = loader.load();

            // 2️⃣ Crear la nueva escena
            Scene scene = new Scene(root);

            // 3️⃣ Obtener el Stage actual y reemplazar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

            System.out.println("Sesión cerrada correctamente");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Error al volver al cerrar sesion: " + e.getMessage());
        }
    }
    @FXML
    private void OnGoDeposits() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/login/deposits.fxml"));
            Parent deposits = loader.load();

            // Obtener el controlador de Clientes
            DepositsController controller = loader.getController();
            controller.setUser(App.loggedUser);



            // Reemplazar el contenido del contenedor principal
            MainContainer.getChildren().clear();
            MainContainer.getChildren().add(deposits);
            VBox.setVgrow(deposits, Priority.ALWAYS);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la lista", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void OnGoCheckBalance() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/login/checkBalance.fxml"));
            Parent checkBalance = loader.load();

            // Obtener el controlador de Clientes
            CheckBalanceController controller = loader.getController();
            controller.setUser(App.loggedUser);


            // Reemplazar el contenido del contenedor principal
            MainContainer.getChildren().clear();
            MainContainer.getChildren().add(checkBalance);
            VBox.setVgrow(checkBalance, Priority.ALWAYS);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la lista", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void OnGoTransfer() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/login/transfer.fxml"));
            Parent transfer = loader.load();

            // Obtener el controlador de Clientes
            TransferController controller = loader.getController();


            // Reemplazar el contenido del contenedor principal
            MainContainer.getChildren().clear();
            MainContainer.getChildren().add(transfer);
            VBox.setVgrow(transfer, Priority.ALWAYS);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el apartado de transferencia", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void OnGoReport() {}

    @FXML
    private void OnGoSecurity() {}

    @FXML
    private void OnGoEmployees() {}

    @FXML
    private void OnGoMonitor() {}

    @FXML
    private void OnGoAdvancedReports() {}

    @FXML
    private void OnGoBalance() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/login/balance.fxml"));
            Parent balance = loader.load();

            // Obtener el controlador de Clientes
            BalanceController controller = loader.getController();
            controller.setClient((Client) loggedUser);

            // Reemplazar el contenido del contenedor principal
            MainContainer.getChildren().clear();
            MainContainer.getChildren().add(balance);
            VBox.setVgrow(balance, Priority.ALWAYS);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar su saldo", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
