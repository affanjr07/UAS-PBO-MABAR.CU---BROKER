package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import com.mabarcu.services.AppData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        usernameField.clear();
        passwordField.clear();
        showFeedback("", false);
    }

    @FXML
    public void login() {
        String username = usernameField.getText() == null
                ? ""
                : usernameField.getText().trim();

        String password = passwordField.getText() == null
                ? ""
                : passwordField.getText();

        if (username.isEmpty()) {
            showFeedback("Username wajib diisi.", true);
            return;
        }

        if (password.isEmpty()) {
            showFeedback("Password wajib diisi.", true);
            return;
        }

        try {
            AppData.login(username, password);
            MainApp.setRoot("DashboardView");
        } catch (Exception e) {
            showFeedback(
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Login gagal. Periksa username/password.",
                    true
            );
        }
    }

    @FXML
    public void register() {
        String username = usernameField.getText() == null
                ? ""
                : usernameField.getText().trim();

        String password = passwordField.getText() == null
                ? ""
                : passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showFeedback("Username dan password wajib diisi.", true);
            return;
        }

        try {
            AppData.register(username, password);
            showFeedback("Akun berhasil dibuat. Silakan login.", false);
        } catch (Exception e) {
            showFeedback(
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Registrasi gagal.",
                    true
            );
        }
    }

    @FXML
    public void forgotPassword() {
        showFeedback("Fitur reset password belum tersedia.", false);
    }

    private void showFeedback(String text, boolean isError) {
        if (messageLabel == null) {
            return;
        }

        messageLabel.setText(text == null ? "" : text);
        messageLabel.getStyleClass().removeAll("success-text", "error-text");

        if (text == null || text.isBlank()) {
            return;
        }

        if (isError) {
            messageLabel.getStyleClass().add("error-text");
        } else {
            messageLabel.getStyleClass().add("success-text");
        }
    }
}