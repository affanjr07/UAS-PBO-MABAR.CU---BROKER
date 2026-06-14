package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SidebarController extends BaseNav {

    @FXML
    @Override
    public void logout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Yakin ingin logout?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (AppData.currentUser != null) {
                    Database.setOffline(AppData.currentUser.getId());
                }

                AppData.currentUser = null;
                MainApp.setRoot("LoginView");
            }
        });
    }
}
