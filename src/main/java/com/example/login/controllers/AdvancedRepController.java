package com.example.login.controllers;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



public class AdvancedRepController {

    @FXML private ComboBox<Client> cbClients;
    @FXML private ComboBox<String> cbType;
    @FXML private TableView<Movements> tblReporte;
    @FXML private TableColumn<Movements, String> colDate;
    @FXML private TableColumn<Movements, String> colTipo;
    @FXML private TableColumn<Movements, Number> colMonto;
    @FXML private Label lblResumenTotal;
    @FXML private Label lblResumenAlertas;
    @FXML
    private DatePicker dpInicial;
    @FXML
    private DatePicker dpFinal;

    private MovementsRepository movementsRepository;
    private UserRepository userRepository;

    @FXML
    private void initialize() {
        movementsRepository = MovementsRepository.getInstancia();
        userRepository = UserRepository.getInstancia();
        //cargarMovementsEjemplo();
        cbType.setItems(FXCollections.observableArrayList("Todos", "Depósito", "Retiro"));
        cbType.setValue("Todos");



        // Configurar columnas
        colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFormattedDate()));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
        colMonto.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getAmount()));

        cargarClientes();
        // Estilo de filas según alerta
        applyRowStyle();

    }



    private String evaluateFraud(Movements mov, Movements previous) {
        if (mov.getAmount() > 10000) return "Suspicious";
        if (previous != null && mov.getClientId().equals(previous.getClientId())) return "Review";
        return "Normal";
    }

    @FXML
    private void generarReporteGeneral() {
        String tipoSeleccionado = cbType.getValue();
        Client cliente = cbClients.getValue();
        if (cliente == null) {
            lblResumenTotal.setText("⚠️ Seleccione un cliente.");
            lblResumenAlertas.setText("");
            return;
        }
        LocalDate desde = dpInicial.getValue();
        LocalDate hasta = dpFinal.getValue();

        List<Movements> todos = movementsRepository.getAll();
        List<Movements> delCliente = todos.stream()
                .filter(m -> m.getClientId().equals(cliente.getId()))
                .filter(m -> {
                    LocalDate fecha = m.getDate().toLocalDate(); // extrae solo la fecha
                    if (desde != null && fecha.isBefore(desde)) return false;
                    if (hasta != null && fecha.isAfter(hasta)) return false;
                    return true;
                }).filter(m -> tipoSeleccionado == null || tipoSeleccionado.equals("Todos") || m.getType().equalsIgnoreCase(tipoSeleccionado))
                .collect(Collectors.toList());



        tblReporte.setItems(FXCollections.observableArrayList(delCliente));

        double total = 0;
        int alertas = 0;
        Movements previous = null;

        for (Movements mov : delCliente) {
            total += mov.getAmount();
            String alerta = evaluateFraud(mov, previous);
            if (!alerta.equals("Normal")) alertas++;
            previous = mov;
        }

        lblResumenTotal.setText("Total movido: $" + total);
        lblResumenAlertas.setText("Alertas detectadas: " + alertas);
    }
    @FXML
    private void generarReporteUsuarios() {
        List<Movements> porUsuario = movementsRepository.getAll().stream()
                .sorted(Comparator.comparing(Movements::getUserType))
                .collect(Collectors.toList());

        tblReporte.setItems(FXCollections.observableArrayList(porUsuario));
        tblReporte.refresh();
    }
    @FXML
    private void generarReporteSospechosas() {
        List<Movements> sospechosos = new ArrayList<>();
        Movements previous = null;
        List<Movements> todos = movementsRepository.getAll().stream()
                .sorted(Comparator.comparing(Movements::getDate))
                .collect(Collectors.toList());


        for (Movements mov : todos) {
            String alerta = evaluateFraud(mov, previous);
            if (!alerta.equals("Normal")) sospechosos.add(mov);
            previous = mov;
        }


        tblReporte.setItems(FXCollections.observableArrayList(sospechosos));
        tblReporte.refresh();
    }


    private void cargarClientes() {
        List<User> usuarios = userRepository.getByRole(3); // 3 = Cliente
        List<Client> clientes = usuarios.stream()
                .filter(u -> u instanceof Client)
                .map(u -> (Client) u)
                .collect(Collectors.toList());

        cbClients.setItems(FXCollections.observableArrayList(clientes));
    }


    private void applyRowStyle() {
        tblReporte.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Movements mov, boolean empty) {
                super.updateItem(mov, empty);

                if (mov == null || empty) {
                    setStyle("");
                    setTooltip(null);
                    return;
                }

                int index = getIndex();
                Movements previous = index > 0 ? tblReporte.getItems().get(index - 1) : null;
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
}
