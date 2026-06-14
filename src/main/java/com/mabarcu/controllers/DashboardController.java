package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import com.mabarcu.models.User;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label onlineCountLabel;
    @FXML private Label offlineCountLabel;

    @FXML private ListView<String> onlineList;
    @FXML private ListView<String> offlineList;
    @FXML private ListView<String> notificationList;
    @FXML private ListView<String> activityList;

    @FXML
    public void initialize() {
        refresh();
    }

    @FXML
    public void refresh() {
        if (AppData.currentUser == null) {
            MainApp.setRoot("LoginView");
            return;
        }

        User current = AppData.currentUser;

        if (welcomeLabel != null) {
            welcomeLabel.setText("Halo, " + current.getDisplayName());
        }

        loadPlayers();
        loadNotifications();
        loadActivities();
    }

    private void loadPlayers() {
        if (onlineList == null || offlineList == null) {
            return;
        }

        onlineList.getItems().clear();
        offlineList.getItems().clear();

        List<User> players = Database.getPlayers();

        for (User u : players) {
            if (AppData.currentUser != null && u.getId() == AppData.currentUser.getId()) {
                continue;
            }

            String label = formatPlayer(u);

            if ("Online".equalsIgnoreCase(u.getOnlineStatus())) {
                onlineList.getItems().add(label);
            } else {
                offlineList.getItems().add(label);
            }
        }

        if (onlineCountLabel != null) {
            onlineCountLabel.setText(String.valueOf(onlineList.getItems().size()));
        }

        if (offlineCountLabel != null) {
            offlineCountLabel.setText(String.valueOf(offlineList.getItems().size()));
        }
    }

    private void loadNotifications() {
        if (notificationList == null || AppData.currentUser == null) {
            return;
        }

        notificationList.getItems().clear();
        notificationList.getItems().addAll(
                Database.getNotifications(AppData.currentUser.getId())
        );
    }

    private void loadActivities() {
        if (activityList == null || AppData.currentUser == null) {
            return;
        }

        activityList.getItems().clear();
        activityList.getItems().addAll(
                Database.getActivities(AppData.currentUser.getId())
        );
    }

    private String formatPlayer(User u) {
        return "@"
                + safe(u.getUsername())
                + " • "
                + safe(u.getDisplayName())
                + " • "
                + safe(u.getFavoriteGame())
                + " • "
                + safe(u.getGameRank())
                + " • "
                + safe(u.getPreferredRole())
                + " • "
                + safe(u.getOnlineStatus());
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}