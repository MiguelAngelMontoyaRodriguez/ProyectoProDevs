package com.example.login.controllers;

import com.example.login.models.Admin;
import com.example.login.models.Cashier;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Optional;

public class EmployeeManagementController {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtShift;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> cbRole;
    @FXML
    private Label lblStatus;
    @FXML
    private TableView<User> tblEmployees;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colRole;

    private UserRepository userRepository;

    @FXML
    public void initialize() {
        userRepository = UserRepository.getInstancia();

        // Configurar columnas
        colName.setCellValueFactory(c -> new SimpleStringProperty(getName(c.getValue())));
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colRole.setCellValueFactory(c -> new SimpleStringProperty(getRoleName(c.getValue().getRole())));

        // Cargar roles y empleados
        cbRole.setItems(FXCollections.observableArrayList("Administrador", "Cajero"));
        cargarEmpleados();

        // Evento de selección
        tblEmployees.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarEmpleadoSeleccionado(newSel);
            }
        });
    }

    private String getName(User users) {
        if (users instanceof Admin a) return a.getName();
        if (users instanceof Cashier c) return c.getName();
        return "";
    }

    private String getRoleName(int role) {
        return switch (role) {
            case 1 -> "Administrador";
            case 2 -> "Cajero";
            case 3 -> "Cliente";
            default -> "Desconocido";
        };
    }

    private int getRoleValue(String roleName) {
        return switch (roleName) {
            case "Administrador" -> 1;
            case "Cajero" -> 2;
            default -> 3;
        };
    }

    private void cargarEmpleadoSeleccionado(User empleado) {
        txtEmail.setText(empleado.getEmail());
        txtPassword.setText(empleado.getPassword());
        cbRole.setValue(getRoleName(empleado.getRole()));

        if (empleado instanceof Admin a) {
            txtFirstName.setText(a.getName());
        }
        else if (empleado instanceof Cashier c) {
            txtFirstName.setText(c.getName());
        }
    }

    private void limpiarCampos() {
        txtFirstName.clear();
        txtEmail.clear();
        txtPassword.clear();
        cbRole.getSelectionModel().clearSelection();
        tblEmployees.getSelectionModel().clearSelection();
    }

    private void cargarEmpleados() {
        ArrayList<User> empleados = new ArrayList<>();
        for (User user : userRepository.getAll()) {
            if (user.getRole() == 1 || user.getRole() == 2) {
                empleados.add(user);
            }
        }
        tblEmployees.setItems(FXCollections.observableArrayList(empleados));
    }

    // AGREGAR EMPLEADO
    @FXML
    private void OnAddEmployee() {
        String name = txtFirstName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String id = txtId.getText().trim();
        String Shift = txtShift.getText().trim();
        String password = txtPassword.getText().trim();
        String roleName = cbRole.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || roleName == null) {
            lblStatus.setText("⚠️ Todos los campos son obligatorios.");
            return;
        }

        for (User user : userRepository.getAll()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                lblStatus.setText("⚠️ Ese correo ya existe.");
                return;
            }
        }

        int role = getRoleValue(roleName);
        User empoyee;

        if (role == 1) {
            empoyee = new Admin(email, password, name, phone, id);
        } else {
            empoyee = new Cashier(email, password, name, id,Shift);
        }

        userRepository.addUser(empoyee);
        cargarEmpleados();
        limpiarCampos();
        lblStatus.setText("✅ Empleado agregado correctamente.");
    }

    // MODIFICAR
    @FXML
    private void OnEditEmployee() {
        User seleccionado = tblEmployees.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un empleado para modificar.", Alert.AlertType.WARNING);
            return;
        }

        seleccionado.setEmail(txtEmail.getText().trim());
        seleccionado.setPassword(txtPassword.getText().trim());
        seleccionado.setRole(getRoleValue(cbRole.getValue()));

        if (seleccionado instanceof Admin a) {
            a.setName(txtFirstName.getText().trim());
        } else if (seleccionado instanceof Cashier c) {
            c.setName(txtFirstName.getText().trim());
        }

        userRepository.updateUser(seleccionado);
        cargarEmpleados();
        limpiarCampos();
        lblStatus.setText("✅ Empleado modificado correctamente.");
    }

    // ELIMINAR
    @FXML
    private void OnDeleteEmployee() {
        User seleccionado = tblEmployees.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un empleado para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Eliminar empleado?");
        confirmacion.setContentText("Email: " + seleccionado.getEmail());

        Optional<ButtonType> result = confirmacion.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            userRepository.eliminateUser(seleccionado);
            cargarEmpleados();
            limpiarCampos();
            mostrarAlerta("Éxito", "Empleado eliminado correctamente.", Alert.AlertType.INFORMATION);
        }
    }

    // Reutilizable para alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}