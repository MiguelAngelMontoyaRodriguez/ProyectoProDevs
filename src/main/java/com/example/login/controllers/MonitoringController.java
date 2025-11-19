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


public class MonitoringController {
    @FXML
    private ComboBox<Client> cbClients;
    @FXML
    private ComboBox<String> cbOperation;
    @FXML
    private javafx.scene.control.Label lblStatus;
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
    @FXML
    private TableColumn<Movements, String> colUserType;


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
        colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
        colId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getClientId()));
        colAmount.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAmount()));
        colUserType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUserType()));

        cargarMovimientos();
        applyRowStyle();
    }
    private void applyRowStyle(){
        var movements = tblMovements.getItems();
        tblMovements.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Movements mov, boolean empty) {
                super.updateItem(mov, empty);

                if (mov == null || empty) {
                    setStyle("");
                    setTooltip(null);
                    return;
                }
                    int index = getIndex();
                    Movements previous = index > 0 ? movements.get(index - 1) : null;
                    String alert = evaluateFraud(mov, previous);

                 switch (alert) {
                    case "Suspicious" -> {
                        setStyle("-fx-background-color: #ffe6e6;");
                        setTooltip(new Tooltip("Transacción de monto elevado"));
                    }
                    case "Review" -> {
                        setStyle("-fx-background-color: #fff5cc;");
                        setTooltip(new Tooltip("Transacción consecutiva del mismo cliente"));
                    }
                    default -> {
                        setStyle("-fx-background-color: #e6ffe6;");
                        setTooltip(null);
                    }
                }

            }
        });

    }
    private String evaluateFraud(Movements mov, Movements previous) {
        if (mov.getAmount() > 10000) return "Suspicious";
        if (mov.getType().contains("Transferencia") && mov.getAmount() > 5000) return "Suspicious";
        if (previous != null && mov.getClientId().equals(previous.getClientId()))return "Review";
        return "Normal";
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




}

