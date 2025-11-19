package com.example.login.controllers;


import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SecurityAuthenticationController {

        @FXML
        private TableView<User> tblUsers;
        @FXML
        private TableColumn<User, String> colEmail;
        @FXML
        private TableColumn<User, String> colRole;
        @FXML
        private TableColumn<User, String> colStatus;
        @FXML
        private Label lblStatus;

        private UserRepository userRepository;

        @FXML
        public void initialize() {
            userRepository = UserRepository.getInstancia();

            colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
            colRole.setCellValueFactory(c -> new SimpleStringProperty(getRoleName(c.getValue().getRole())));
            colStatus.setCellValueFactory(c -> new SimpleStringProperty(getStatus(c.getValue())));

            cargarUsuarios();
        }

        private void cargarUsuarios() {
            tblUsers.setItems(FXCollections.observableArrayList(userRepository.getAll()));
        }

        private String getRoleName(int role) {
            return switch (role) {
                case 1 -> "Administrador";
                case 2 -> "Cajero";
                case 3 -> "Cliente";
                default -> "Desconocido";
            };
        }

        private String getStatus(User user) {
            return user.isBlocked() ? "Bloqueado" : "Activo";
        }

        @FXML
        private void OnBlockUser() {
            User selected = tblUsers.getSelectionModel().getSelectedItem();
            if (selected == null) {
                lblStatus.setText("⚠️ Seleccione un usuario para bloquear.");
                return;
            }
            selected.setBlocked(true);
            lblStatus.setText("🔒 Usuario bloqueado correctamente.");
            tblUsers.refresh();
        }

        @FXML
        private void OnUnblockUser() {
            User selected = tblUsers.getSelectionModel().getSelectedItem();
            if (selected == null) {
                lblStatus.setText("⚠️ Seleccione un usuario para desbloquear.");
                return;
            }
            selected.setBlocked(false);
            lblStatus.setText("✅ Usuario desbloqueado correctamente.");
            tblUsers.refresh();
        }

        @FXML
        private void OnResetPassword() {
            User selected = tblUsers.getSelectionModel().getSelectedItem();
            if (selected == null) {
                lblStatus.setText("⚠️ Seleccione un usuario para restablecer la contraseña.");
                return;
            }
            selected.setPassword("12345"); // valor temporal
            lblStatus.setText("🔄 Contraseña restablecida a: 12345");
            tblUsers.refresh();
        }

}
