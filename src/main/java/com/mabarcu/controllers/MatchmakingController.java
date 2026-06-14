package com.mabarcu.controllers;

import com.mabarcu.models.User;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MatchmakingController extends BaseNav {
    @FXML private ComboBox<String> gameFilter;
    @FXML private ComboBox<String> rankFilter;
    @FXML private ComboBox<String> roleFilter;
    @FXML private ComboBox<String> regionFilter;

    @FXML private ListView<String> resultList;
    @FXML private Label infoLabel;

    @FXML
    public void initialize() {
        AppData.reloadAll();

        // Populate Combo Box Items
        gameFilter.getItems().setAll("Semua", "Mobile Legends", "Valorant", "PUBG Mobile");
        rankFilter.getItems().setAll("Semua", "Mythic", "Legend", "Epic", "Gold", "Crown");
        roleFilter.getItems().setAll("Semua", "Jungler", "Roamer", "Mid Lane", "Gold Laner", "Duelist", "Controller", "Rusher");
        regionFilter.getItems().setAll("Semua", "Indonesia", "Singapore");

        // Set Default Selection Value
        gameFilter.setValue("Semua");
        rankFilter.setValue("Semua");
        roleFilter.setValue("Semua");
        regionFilter.setValue("Semua");

        search();
    }

    @FXML
    public void search() {
        resultList.getItems().clear();

        String game = gameFilter.getValue();
        String rank = rankFilter.getValue();
        String role = roleFilter.getValue();
        String region = regionFilter.getValue();

        List<User> players = Database.getPlayers();

        for (User p : players) {
            if (AppData.currentUser != null && p.getId() == AppData.currentUser.getId()) {
                continue;
            }

            boolean cocok =
                    match(game, p.getFavoriteGame()) &&
                            match(rank, p.getGameRank()) &&
                            match(role, p.getPreferredRole()) &&
                            match(region, p.getRegion());

            if (cocok) {
                resultList.getItems().add(
                        p.getId() + " | @" + p.getUsername()
                                + " • " + p.getDisplayName()
                                + " • " + p.getFavoriteGame()
                                + " • " + p.getGameRank()
                                + " • " + p.getPreferredRole()
                                + " • " + p.getOnlineStatus()
                );
            }
        }

        if (resultList.getItems().isEmpty()) {
            infoLabel.setText("Player tidak ditemukan. Coba kosongkan filter atau ubah game/rank/role.");
        } else {
            infoLabel.setText("Ditemukan " + resultList.getItems().size() + " player.");
        }
    }

    private boolean match(String filter, String value) {
        return filter == null || filter.equals("Semua") || filter.equalsIgnoreCase(value);
    }

    @FXML
    public void inviteSelected() {
        String s = resultList.getSelectionModel().getSelectedItem();

        if (s == null) {
            infoLabel.setText("Pilih player terlebih dahulu.");
            return;
        }

        int targetId = Integer.parseInt(s.split(" \\| ")[0]);

        Database.addNotification(
                targetId,
                AppData.currentUser.getDisplayName() + " mengundang kamu untuk mabar."
        );

        infoLabel.setText("Invite berhasil dikirim.");
    }

    @FXML
    public void followSelected() {
        String s = resultList.getSelectionModel().getSelectedItem();

        if (s == null) {
            infoLabel.setText("Pilih player terlebih dahulu.");
            return;
        }

        int targetId = Integer.parseInt(s.split(" \\| ")[0]);

        Database.followUser(AppData.currentUser.getId(), targetId);

        AppData.reloadAll();
        infoLabel.setText("Berhasil follow player.");
    }

    @FXML
    public void viewProfile() {
        String s = resultList.getSelectionModel().getSelectedItem();

        if (s == null) {
            infoLabel.setText("Pilih player terlebih dahulu.");
            return;
        }

        int targetId = Integer.parseInt(s.split(" \\| ")[0]);
        User target = Database.findUserById(targetId);
        
        if (target != null) {
            AppData.selectedUser = target;
            goUserProfile();
        }
    }
}