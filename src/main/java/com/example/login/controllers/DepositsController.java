package com.example.login.controllers;

import com.example.login.App;
import com.example.login.models.Client;
import com.example.login.models.Movements;
import com.example.login.models.User;
import com.example.login.repositories.MovementsRepository;
import com.example.login.repositories.UserRepository;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class DepositsController {

    @FXML
    private ComboBox<Client> cbClients;
    @FXML
    private ComboBox<String> cbOperation;
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtAmount;
    @FXML
    private TableView<Movements> tblMovements;
    @FXML
    private TableColumn<Movements, String> colDate;
    @FXML
    private TableColumn<Movements, String> colType;
    @FXML
    private TableColumn<Movements, Number> colAmount;
    @FXML
    private TableColumn<Movements, String> colId;

    private MovementsRepository movementsRepository;
    private UserRepository userRepository;
    private User loggedUser;

    public void setUser(User user) {
        App.loggedUser = user;
        interfaceForRole();
    }

    private void interfaceForRole() {
        int role = App.loggedUser.getRole();

        if (role == 3) {
            cbClients.setVisible(false);
        }
    }


    @FXML
    public void initialize() {
        movementsRepository = MovementsRepository.getInstancia();
        userRepository = UserRepository.getInstancia();
        // Configurar columnas
        colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFormattedDate()));
        colId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClientId()));
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
        colAmount.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAmount()));

        cargarClientes();
        cargarTipos();
        cargarMovimientos();

    }

    private void cargarMovimientos() {
        ArrayList<Movements> todo = movementsRepository.getAll();

        if (App.loggedUser.getRole() == 3) {
            // Filtrar solo los del cliente actual
            ArrayList<Movements> mismo = new ArrayList<>();
            for (Movements move : todo) {
                if (move.getClientId().equals(((Client) App.loggedUser).getId())) {
                    mismo.add(move);
                }
            }
            tblMovements.setItems(FXCollections.observableArrayList(mismo));
        } else {
            // Cajero o admin ve todos
            tblMovements.setItems(FXCollections.observableArrayList(todo));
        }
    }

    private void cargarTipos() {
        cbOperation.setItems(FXCollections.observableArrayList("Depósito", "Retiro"));
    }
    public void cargarClientes(){
        ArrayList<User> user = userRepository.getByRole(3);
        ArrayList<Client> clients = new ArrayList<>();

        for (User users : user) {
            clients.add((Client) users);
        }

        cbClients.setItems(FXCollections.observableArrayList(clients));

    }


    @FXML
    private void OnConfirmOperation() {
        Client client;
        if (App.loggedUser.getRole() == 3) {
            // Si es cliente, se usa su propia cuenta
            client = (Client) App.loggedUser;
        } else {
            // Si es cajero, debe seleccionar un cliente
            client = cbClients.getValue();

            if (client == null) {
                lblStatus.setText("⚠️ Seleccione un cliente.");
                return;
            }
        }
        String operation = cbOperation.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        if (operation.equals("Depósito")) {
            client.setBalance(client.getBalance() + amount);
            lblStatus.setText("✅ Depósito exitoso.");
            Movements move = new Movements(client.getId(), "Depósito", amount);
            movementsRepository.add(move);

        } else if (operation.equals("Retiro")) {
            if (client.getBalance() >= amount) {
                client.setBalance(client.getBalance() - amount);
                lblStatus.setText("✅ Retiro realizado.");
                Movements mov = new Movements(client.getId(), "Retiro", amount);
                movementsRepository.add(mov);

            } else {
                lblStatus.setText("❌ Saldo insuficiente.");
            }
        }

        // Actualizar lista y tabla
        tblMovements.setItems(FXCollections.observableArrayList(movementsRepository.getAll()));
        tblMovements.refresh();

        // Actualizar el repositorio para mantener sincronizado
        userRepository.updateClient(client);


    }
}
