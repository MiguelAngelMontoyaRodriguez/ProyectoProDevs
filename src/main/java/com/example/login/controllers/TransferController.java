package com.example.login.controllers;

import com.example.login.models.Client;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class TransferController {
    @FXML
    private ComboBox<Client> cbOrigin;
    @FXML
    private ComboBox<Client> cbDestination;
    @FXML
    private TextField txtAmount;
    @FXML
    private Label lblStatus;

    private UserRepository userRepository;


    @FXML
    public void initialize() {
        userRepository = UserRepository.getInstancia();
        cargarClientes();
    }

    private void cargarClientes() {
        ArrayList<User> users =userRepository.getByRole(3);
        ArrayList<Client> clients = new ArrayList<>();

        for (User u : users) {
            clients.add((Client) u);
        }

        cbOrigin.setItems(FXCollections.observableArrayList(clients));
        cbDestination.setItems(FXCollections.observableArrayList(clients));
    }

    @FXML
    private void OnConfirmTransfer() {
        Client origen = cbOrigin.getValue();
        Client destiny = cbDestination.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        if (origen == null || destiny == null) {
            lblStatus.setText("Seleccione las cuentas de origen y destino .");
            return;
        }
        if (origen == destiny) {
            lblStatus.setText("No puede transferirse a usted mismo, bobo.");
            return;
        }
        if (origen.getBalance() < amount) {
            lblStatus.setText("Saldo insuficiente.");
            return;
        }

        origen.setBalance(origen.getBalance() - amount);
        destiny.setBalance(destiny.getBalance() + amount);
        lblStatus.setText("✅ Transferencia exitosa.");
    }
}
