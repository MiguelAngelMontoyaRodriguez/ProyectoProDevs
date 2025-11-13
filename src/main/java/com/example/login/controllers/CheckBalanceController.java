package com.example.login.controllers;

import com.example.login.App;
import com.example.login.models.Client;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CheckBalanceController {

    @FXML
    private ComboBox<Client> cbClients;

    @FXML
    private TextField txtBalance;

    private UserRepository userRepository;

    private User loggedUser;

    public void setUser(User user) {
        this.loggedUser = user;
    }

    @FXML
    public void initialize() {
        userRepository = UserRepository.getInstancia();
        cargarClientes();
    }

    public void cargarClientes(){
            ArrayList<User> user = userRepository.getByRole(3);
            ArrayList<Client> clients = new ArrayList<>();

            for (User users : user) {
                    clients.add((Client) users);
            }

            cbClients.setItems(FXCollections.observableArrayList(clients));

    }

    public void OnGoCheckBalance(){
        Client selectedClient = cbClients.getValue();

        if (selectedClient == null) {
            mostrarAlerta("Consulta de saldo", "Por favor, selecciona un cliente antes de continuar.");
            return;
        }

        double saldo = selectedClient.getBalance();
        txtBalance.setText("$ " + String.format("%,.2f", saldo));
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
