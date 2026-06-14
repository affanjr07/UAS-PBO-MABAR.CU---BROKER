package com.mabarcu.controllers;

import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import com.mabarcu.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    @FXML private Label profileStats;
    @FXML private Label saveMsg;
    @FXML private Label lblStatus;

    @FXML private ListView<String> activityList;

    @FXML
    public void initialize() {
        AppData.reloadAll();
        load();
        activityList.setItems(AppData.activities);
    }

    private void load() {
        User u = AppData.currentUser;
        if (u == null) return;

        // Populate Input Fields
        displayNameField.setText(u.getDisplayName());
        usernameField.setText(u.getUsername());
        gameField.setText(u.getFavoriteGame());
        rankField.setText(u.getGameRank());
        roleField.setText(u.getPreferredRole());
        regionField.setText(u.getRegion());
        genderField.setText(u.getGender());
        bioArea.setText(u.getBio());

        // Populating Header Banner Info
        profileHeader.setText(u.getDisplayName() + "  •  @" + u.getUsername());
        if (lblStatus != null) {
            lblStatus.setText(u.getStatus() != null ? u.getStatus() : "Offline");
        }

        profileStats.setText("Followers " + u.getFollowers() + "   |   Following " + u.getFollowing() + "   |   Friends " + AppData.friends.size() + "   |   Rating " + u.getRating() + " ★");
    }

    @FXML
    public void saveProfile() {
        User u = AppData.currentUser;
        if (u == null) return;

        // Data Mapping
        u.setDisplayName(displayNameField.getText());
        u.setUsername(usernameField.getText());
        u.setFavoriteGame(gameField.getText());
        u.setGameRank(rankField.getText());
        u.setPreferredRole(roleField.getText());
        u.setRegion(regionField.getText());
        u.setGender(genderField.getText());
        u.setBio(bioArea.getText());

        // Database Execution
        Database.updateProfile(u);
        AppData.reloadAll();

        // Response Feedback Animation-state
        saveMsg.setText("Profile successfully saved.");
        saveMsg.getStyleClass().removeAll("error-text");
        saveMsg.getStyleClass().add("success-text");

        load();
    }
}