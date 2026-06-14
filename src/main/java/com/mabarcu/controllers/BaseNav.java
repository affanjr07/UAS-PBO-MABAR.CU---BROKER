package com.mabarcu.controllers;

import com.mabarcu.MainApp;
import javafx.fxml.FXML;

public abstract class BaseNav {

    @FXML
    public void goDashboard() {
        MainApp.setRoot("DashboardView");
    }

    @FXML
    public void goMatchmaking() {
        MainApp.setRoot("MatchmakingView");
    }

    @FXML
    public void goProfile() {
        MainApp.setRoot("ProfileView");
    }

    @FXML
    public void goChat() {
        MainApp.setRoot("ChatView");
    }

    @FXML
    public void goParty() {
        MainApp.setRoot("PartyRoomView");
    }

    @FXML
    public void goFriends() {
        MainApp.setRoot("FriendListView");
    }

    @FXML
    public void goPrivateChat() {
        MainApp.setRoot("PrivateChatView");
    }

    @FXML
    public void goUserProfile() {
        MainApp.setRoot("UserProfileView");
    }

    @FXML
    public void logout() {
        MainApp.setRoot("LoginView");
    }
}