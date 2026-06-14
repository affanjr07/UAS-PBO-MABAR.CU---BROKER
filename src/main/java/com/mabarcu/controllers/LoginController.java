package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import com.mabarcu.services.AppData;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        usernameField.clear();
        passwordField.clear();
    }

    @FXML
    public void login() {
        String u = usernameField.getText().trim();
        if (u.isEmpty()) {
            showFeedback("Username wajib diisi.", true);
            return;
        }

        AppData.login(u, passwordField.getText());
        MainApp.setRoot("DashboardView");
    }

    @FXML
    public void register() {
        String u = usernameField.getText().trim();
        String p = passwordField.getText();

        if (u.isEmpty() || p.isEmpty()) {
            showFeedback("Username dan password wajib diisi.", true);
            return;
        }

        AppData.register(u, p);
        showFeedback("Akun berhasil dibuat. Silakan login.", false);
    }

    @FXML
    public void forgotPassword() {
        showFeedback("Reset password demo aktif. Database lokal tetap aman.", false);
    }

    /**
     * Helper Method untuk mengatur warna pesan feedback secara dinamis
     */
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