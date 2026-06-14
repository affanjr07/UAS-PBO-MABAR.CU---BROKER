package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import com.mabarcu.models.User;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserProfileController extends BaseNav {

    @FXML private Label profileHeader;
    @FXML private Label lblStatus;

    @FXML private Label followersLabel;
    @FXML private Label followingLabel;
    @FXML private Label friendsLabel;

    @FXML private Label gameValue;
    @FXML private Label rankValue;
    @FXML private Label roleValue;
    @FXML private Label regionValue;

    @FXML private Label bioLabel;
    
    @FXML private Button followButton;
    @FXML private Button messageButton;

    @FXML
    public void initialize() {
        refreshProfile();
    }

    @FXML
    public void refreshProfile() {
        User target = AppData.selectedUser;
        if (target == null) {
            goDashboard();
            return;
        }
        
        // Refresh target user to get latest stats
        target = Database.findUserById(target.getId());
        if (target == null) {
            goDashboard();
            return;
        }
        AppData.selectedUser = target;

        loadUserData();
        updateButtons();
    }

    private void loadUserData() {
        User u = AppData.selectedUser;

        profileHeader.setText(safe(u.getDisplayName()) + "  •  @" + safe(u.getUsername()));

        String status = u.getOnlineStatus();
        if (status == null || status.isBlank()) status = u.getStatus();
        if (status == null || status.isBlank()) status = "Offline";

        lblStatus.setText(status);

        setLabel(gameValue, safe(u.getFavoriteGame()));
        setLabel(rankValue, safe(u.getGameRank()));
        setLabel(roleValue, safe(u.getPreferredRole()));
        setLabel(regionValue, safe(u.getRegion()));
        
        setLabel(bioLabel, safe(u.getBio()));

        setLabel(followersLabel, String.valueOf(u.getFollowers()));
        setLabel(followingLabel, String.valueOf(u.getFollowing()));
        setLabel(friendsLabel, String.valueOf(u.getFriends()));
    }

    private void updateButtons() {
        if (AppData.currentUser == null || AppData.selectedUser == null) return;
        
        int myId = AppData.currentUser.getId();
        int targetId = AppData.selectedUser.getId();
        
        if (myId == targetId) {
            followButton.setDisable(true);
            messageButton.setDisable(true);
            return;
        }

        boolean isMutual = Database.isMutualFollow(myId, targetId);
        messageButton.setDisable(!isMutual);
        
        // Check if I already follow them
        boolean iFollowThem = Database.getFriends(myId).stream()
                .anyMatch(f -> f.startsWith(AppData.selectedUser.getDisplayName() + " •"));
                
        if (iFollowThem) {
            followButton.setText("✖ Unfollow");
            followButton.getStyleClass().setAll("secondary-flat-btn");
        } else {
            followButton.setText("➕ Follow");
            followButton.getStyleClass().setAll("primary-green-btn");
        }
    }

    @FXML
    public void toggleFollow() {
        if (AppData.currentUser == null || AppData.selectedUser == null) return;
        
        int myId = AppData.currentUser.getId();
        int targetId = AppData.selectedUser.getId();
        
        boolean iFollowThem = Database.getFriends(myId).stream()
                .anyMatch(f -> f.startsWith(AppData.selectedUser.getDisplayName() + " •"));
                
        try {
            if (iFollowThem) {
                Database.unfollowUser(myId, targetId);
            } else {
                Database.followUser(myId, targetId);
            }
            refreshProfile();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void sendMessage() {
        if (AppData.currentUser == null || AppData.selectedUser == null) return;
        
        if (!Database.isMutualFollow(AppData.currentUser.getId(), AppData.selectedUser.getId())) {
            new Alert(Alert.AlertType.WARNING, "Kalian harus saling follow untuk bisa mengirim pesan!").showAndWait();
            return;
        }
        
        goPrivateChat();
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
