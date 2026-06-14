package com.mabarcu.controllers;

import com.mabarcu.models.User;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class ProfileController extends BaseNav {

    @FXML private TextField displayNameField;
    @FXML private TextField usernameField;
    @FXML private TextField gameField;
    @FXML private TextField rankField;
    @FXML private TextField roleField;
    @FXML private TextField regionField;
    @FXML private TextField genderField;
    @FXML private TextArea bioArea;

    @FXML private Label profileHeader;
    @FXML private Label saveMsg;
    @FXML private Label lblStatus;

    @FXML private Label gameValue;
    @FXML private Label rankValue;
    @FXML private Label roleValue;
    @FXML private Label regionValue;

    @FXML private ListView<String> activityList;

    @FXML
    public void initialize() {
        refreshProfile();
    }

    @FXML
    public void refreshProfile() {
        AppData.reloadAll();

        if (AppData.currentUser == null) {
            return;
        }

        User latestUser = Database.findUser(AppData.currentUser.getUsername());

        if (latestUser != null) {
            AppData.currentUser = latestUser;
        }

        loadUserData();
        loadActivitiesRealtime();
    }

    private void loadUserData() {
        User u = AppData.currentUser;

        if (u == null) {
            return;
        }

        displayNameField.setText(safe(u.getDisplayName()));
        usernameField.setText(safe(u.getUsername()));
        gameField.setText(safe(u.getFavoriteGame()));
        rankField.setText(safe(u.getGameRank()));
        roleField.setText(safe(u.getPreferredRole()));
        regionField.setText(safe(u.getRegion()));
        genderField.setText(safe(u.getGender()));
        bioArea.setText(safe(u.getBio()));

        profileHeader.setText(
                safe(u.getDisplayName()) + "  •  @" + safe(u.getUsername())
        );

        String status = u.getOnlineStatus();

        if (status == null || status.isBlank()) {
            status = u.getStatus();
        }

        if (status == null || status.isBlank()) {
            status = "Offline";
        }

        lblStatus.setText(status);

        setLabel(gameValue, safe(u.getFavoriteGame()));
        setLabel(rankValue, safe(u.getGameRank()));
        setLabel(roleValue, safe(u.getPreferredRole()));
        setLabel(regionValue, safe(u.getRegion()));
    }

    private void loadActivitiesRealtime() {
        if (activityList == null || AppData.currentUser == null) {
            return;
        }

        activityList.getItems().clear();

        List<String> activities = Database.getActivities(AppData.currentUser.getId());

        if (activities.isEmpty()) {
            activityList.getItems().add("Belum ada aktivitas terbaru.");
            return;
        }

        activityList.getItems().addAll(activities);
    }

    @FXML
    public void saveProfile() {
        try {
            User u = AppData.currentUser;

            if (u == null) {
                return;
            }

            u.setDisplayName(displayNameField.getText());
            u.setUsername(usernameField.getText());
            u.setFavoriteGame(gameField.getText());
            u.setGameRank(rankField.getText());
            u.setPreferredRole(roleField.getText());
            u.setRegion(regionField.getText());
            u.setGender(genderField.getText());
            u.setBio(bioArea.getText());

            User updated = Database.updateProfile(u);
            AppData.currentUser = updated;

            Database.addActivity(
                    updated.getId(),
                    "Memperbarui profil"
            );

            saveMsg.setText("Profile berhasil disimpan.");
            saveMsg.getStyleClass().removeAll("error-text");
            if (!saveMsg.getStyleClass().contains("success-text")) {
                saveMsg.getStyleClass().add("success-text");
            }

            refreshProfile();

        } catch (Exception e) {
            saveMsg.setText(e.getMessage());
            saveMsg.getStyleClass().removeAll("success-text");
            if (!saveMsg.getStyleClass().contains("error-text")) {
                saveMsg.getStyleClass().add("error-text");
            }
        }
    }

    private void setLabel(Label label, String value) {
        if (label != null) {
            label.setText(value);
        }
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}