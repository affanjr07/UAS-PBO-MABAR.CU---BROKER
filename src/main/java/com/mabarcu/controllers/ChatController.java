package com.mabarcu.controllers;


import com.mabarcu.models.User;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChatController {

    @FXML private ComboBox<String> channelBox;
    @FXML private ListView<String> contactList;
    @FXML private ListView<String> chatList;
    @FXML private TextField messageField;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        setupChannels();
        loadContacts();
        loadChat();
    }

    private void setupChannels() {
        channelBox.getItems().setAll(
                "Mobile Legends",
                "Valorant",
                "Free Fire",
                "Counter Strike 2",
                "Phasmophobia",
                "PUBG Mobile",
                "Roblox",
                "Growtopia",
                "GTA V",
                "Minecraft",
                "Genshin Impact",
                "Honkai Star Rail",
                "Dota 2",
                "League of Legends",
                "Apex Legends",
                "Fortnite",
                "Call of Duty Mobile",
                "Point Blank",
                "eFootball",
                "FC Mobile"
        );

        channelBox.setValue("Mobile Legends");

        channelBox.setOnAction(e -> {
            loadContacts();
            loadChat();
        });
    }

    @FXML
    public void loadContacts() {
        contactList.getItems().clear();

        String game = channelBox.getValue();

        for (User u : Database.getPlayers()) {
            if (AppData.currentUser != null && u.getId() == AppData.currentUser.getId()) {
                continue;
            }

            if (game.equalsIgnoreCase(u.getFavoriteGame())) {
                contactList.getItems().add(
                        "@" + u.getUsername()
                                + " • " + u.getDisplayName()
                                + " • " + u.getGameRank()
                                + " • " + u.getOnlineStatus()
                );
            }
        }

        if (contactList.getItems().isEmpty()) {
            contactList.getItems().add("Belum ada player di komunitas " + game);
        }
    }

    @FXML
    public void loadChat() {
        chatList.getItems().clear();

        String channel = channelBox.getValue();

        chatList.getItems().addAll(Database.getChat(channel));

        setStatus("Channel aktif: " + channel);
    }

    @FXML
    public void sendMessage() {
        if (AppData.currentUser == null) {
            setStatus("Login dulu sebelum chat.");
            return;
        }

        String msg = messageField.getText();

        if (msg == null || msg.isBlank()) {
            setStatus("Pesan tidak boleh kosong.");
            return;
        }

        String channel = channelBox.getValue();

        Database.addChat(
                AppData.currentUser.getDisplayName(),
                channel,
                msg.trim()
        );

        messageField.clear();
        loadChat();

        setStatus("Pesan terkirim ke komunitas " + channel + ".");
    }

    @FXML
    public void sendEmoji() {
        appendText(" 🎮");
    }

    @FXML
    public void sendSticker() {
        appendText(" 🔥");
    }

    private void appendText(String text) {
        String current = messageField.getText();

        if (current == null) {
            current = "";
        }

        messageField.setText(current + text);
        messageField.positionCaret(messageField.getText().length());
    }

    private void setStatus(String text) {
        if (statusLabel != null) {
            statusLabel.setText(text);
        }
    }
}