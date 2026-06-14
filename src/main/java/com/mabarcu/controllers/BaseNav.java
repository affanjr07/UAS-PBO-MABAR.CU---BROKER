package com.mabarcu.controllers;

import com.mabarcu.MainApp;

public abstract class BaseNav {

    public void goDashboard() {
        MainApp.setRoot("DashboardView");
    }

    public void goMatchmaking() {
        MainApp.setRoot("MatchmakingView");
    }

    public void goProfile() {
        MainApp.setRoot("ProfileView");
    }

    public void goChat() {
        MainApp.setRoot("ChatView");
    }

    public void goParty() {
        MainApp.setRoot("PartyRoomView");
    }

    public void goFriends() {
        MainApp.setRoot("FriendListView");
    }

    public void logout() {
        MainApp.setRoot("LoginView");
    }
}