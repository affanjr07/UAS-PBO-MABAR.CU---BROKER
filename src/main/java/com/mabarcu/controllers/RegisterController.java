package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    public void register() {
        String u = usernameField.getText().trim();
        String p = passwordField.getText();
        String c = confirmPasswordField.getText();

        if (u.isEmpty() || p.isEmpty() || c.isEmpty()) {
            showFeedback("Semua field wajib diisi.", true);
            return;
        }

        if (!p.equals(c)) {
            showFeedback("Password dan konfirmasi tidak cocok.", true);
            return;
        }

        if (p.length() < 4) {
            showFeedback("Password minimal 4 karakter.", true);
            return;
        }

        try {
            Database.register(u, p);
            Alert success = new Alert(Alert.AlertType.INFORMATION, "Registrasi berhasil! Silakan login.");
            success.showAndWait();
            goLogin();
        } catch (Exception e) {
            showFeedback(e.getMessage(), true);
        }
    }

    @FXML
    public void goLogin() {
        MainApp.setRoot("LoginView");
    }

    private void showFeedback(String text, boolean isError) {
        messageLabel.setText(text);
        messageLabel.getStyleClass().removeAll("success-text", "error-text");

        if (isError) {
            messageLabel.getStyleClass().add("error-text");
        } else {
            messageLabel.getStyleClass().add("success-text");
        }
    }
}
