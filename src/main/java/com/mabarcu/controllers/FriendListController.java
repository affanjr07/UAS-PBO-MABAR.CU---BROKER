package com.mabarcu.controllers;

import com.mabarcu.models.User;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FriendListController extends BaseNav {

    @FXML private TextField searchField;
    @FXML private ListView<String> friendList;
    @FXML private ListView<String> requestList;
    @FXML private ListView<String> blockedList;
    @FXML private ListView<User> playerResultList;
    @FXML private Label actionLabel;

    @FXML
    public void initialize() {
        refresh();
    }

    private void refresh() {
        AppData.reloadAll();

        friendList.setItems(AppData.friends);
        requestList.setItems(AppData.requests);
        blockedList.setItems(AppData.blocked);

        if (playerResultList != null) {
            playerResultList.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    public void search() {
        String q = searchField.getText().toLowerCase().trim();

        if (q.isEmpty()) {
            actionLabel.setText("Masukkan nama player dulu.");
            return;
        }

        AppData.reloadAll();

        var results = AppData.players.filtered(player ->
                player.getId() != AppData.currentUser.getId()
                        && (player.getUsername().toLowerCase().contains(q)
                                || player.getDisplayName().toLowerCase().contains(q)
                                || player.getFavoriteGame().toLowerCase().contains(q)
                                || player.getGameRank().toLowerCase().contains(q))
        );

        playerResultList.setItems(results);

        if (results.isEmpty()) {
            actionLabel.setText("Player tidak ditemukan.");
        } else {
            actionLabel.setText("Player ditemukan. Pilih lalu klik Follow Player.");
        }
    }

    @FXML
    public void followUser() {
        User selectedPlayer = playerResultList.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            actionLabel.setText("Pilih player dulu dari hasil pencarian.");
            return;
        }

        try {
            Database.followUser(
                    AppData.currentUser.getId(),
                    selectedPlayer.getId()
            );

            refresh();
            actionLabel.setText("Berhasil follow " + selectedPlayer.getDisplayName() + ".");
        } catch (Exception e) {
            actionLabel.setText(e.getMessage() != null ? e.getMessage() : "Gagal follow player.");
        }
    }

    @FXML
    public void viewProfileFromSearch() {
        User selectedPlayer = playerResultList.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            actionLabel.setText("Pilih player dulu dari hasil pencarian.");
            return;
        }

        AppData.selectedUser = Database.findUserById(selectedPlayer.getId());
        goUserProfile();
    }

    @FXML
    public void accept() {
        String r = requestList.getSelectionModel().getSelectedItem();

        if (r == null) {
            actionLabel.setText("Pilih request yang ingin diterima.");
            return;
        }

        String name = r.split(" ")[0];

        Database.addFriend(
                AppData.currentUser.getId(),
                name,
                "Online",
                "Mobile Legends"
        );

        Database.removeRequest(AppData.currentUser.getId(), r);

        refresh();
        actionLabel.setText("Request diterima. Sekarang sudah jadi teman.");
    }

    @FXML
    public void removeFriend() {
        String f = friendList.getSelectionModel().getSelectedItem();

        if (f == null) {
            actionLabel.setText("Pilih teman dulu.");
            return;
        }

        Database.removeFriend(AppData.currentUser.getId(), f);

        refresh();
        actionLabel.setText("Teman berhasil dihapus.");
    }

    @FXML
    public void blockFriend() {
        String f = friendList.getSelectionModel().getSelectedItem();

        if (f == null) {
            actionLabel.setText("Pilih teman yang akan diblokir.");
            return;
        }

        String name = f.split(" • ")[0];

        Database.addBlocked(AppData.currentUser.getId(), name);
        Database.removeFriend(AppData.currentUser.getId(), f);

        refresh();
        actionLabel.setText("User berhasil diblokir.");
    }
}