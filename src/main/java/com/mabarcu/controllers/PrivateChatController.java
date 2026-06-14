package com.mabarcu.controllers;

import com.mabarcu.models.User;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class PrivateChatController extends BaseNav {

    @FXML private ListView<String> contactList;
    @FXML private ListView<String> chatList;
    @FXML private TextField messageField;
    @FXML private Label statusLabel;
    @FXML private Label chatPartnerLabel;

    private User selectedContact;

    @FXML
    public void initialize() {
        loadContacts();
        
        contactList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String contactName = newVal.split(" • ")[0];
                selectContact(contactName);
            }
        });
        
        // Auto select if navigated from user profile
        if (AppData.selectedUser != null) {
            selectContact(AppData.selectedUser.getDisplayName());
            // Pre-select in list
            for (String item : contactList.getItems()) {
                if (item.startsWith(AppData.selectedUser.getDisplayName() + " •")) {
                    contactList.getSelectionModel().select(item);
                    break;
                }
            }
        }
    }

    private void loadContacts() {
        if (AppData.currentUser == null) return;
        
        contactList.getItems().clear();
        List<User> mutuals = Database.getMutualFriends(AppData.currentUser.getId());
        
        if (mutuals.isEmpty()) {
            contactList.getItems().add("Belum ada mutual friend.");
            return;
        }
        
        for (User u : mutuals) {
            String status = u.getOnlineStatus();
            if (status == null || status.isBlank()) status = "Offline";
            contactList.getItems().add(u.getDisplayName() + " • " + status);
        }
    }

    private void selectContact(String displayName) {
        selectedContact = null;
        List<User> mutuals = Database.getMutualFriends(AppData.currentUser.getId());
        for (User u : mutuals) {
            if (u.getDisplayName().equalsIgnoreCase(displayName)) {
                selectedContact = u;
                break;
            }
        }

        if (selectedContact != null) {
            chatPartnerLabel.setText("Chatting with " + selectedContact.getDisplayName());
            statusLabel.setText("Connected to " + selectedContact.getDisplayName());
            refreshChat();
        } else {
            chatPartnerLabel.setText("Private Message");
            statusLabel.setText("Pilih teman untuk mulai chat");
            chatList.getItems().clear();
        }
    }

    @FXML
    public void sendMessage() {
        if (AppData.currentUser == null || selectedContact == null) {
            new Alert(Alert.AlertType.WARNING, "Pilih kontak terlebih dahulu!").showAndWait();
            return;
        }

        String msg = messageField.getText();
        if (msg == null || msg.trim().isEmpty()) {
            return;
        }

        try {
            Database.sendPrivateMessage(AppData.currentUser.getId(), selectedContact.getId(), msg);
            messageField.clear();
            refreshChat();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void refreshChat() {
        if (AppData.currentUser == null || selectedContact == null) return;
        
        chatList.getItems().clear();
        List<String> messages = Database.getPrivateMessages(AppData.currentUser.getId(), selectedContact.getId());
        
        if (messages.isEmpty()) {
            chatList.getItems().add("Belum ada pesan. Mulai obrolan sekarang!");
        } else {
            chatList.getItems().addAll(messages);
            Platform.runLater(() -> chatList.scrollTo(chatList.getItems().size() - 1));
        }
    }

    @FXML
    public void sendEmoji() {
        messageField.appendText("😊 ");
    }

    @FXML
    public void sendSticker() {
        messageField.appendText("[Sticker GG] ");
    }
}
