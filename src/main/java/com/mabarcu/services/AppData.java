package com.mabarcu.services;
import com.mabarcu.models.*; import javafx.collections.*;

public class AppData {
    public static User currentUser;
    public static ObservableList<User> players = FXCollections.observableArrayList();
    public static ObservableList<String> friends = FXCollections.observableArrayList();
    public static ObservableList<String> requests = FXCollections.observableArrayList();
    public static ObservableList<String> blocked = FXCollections.observableArrayList();
    public static ObservableList<PartyRoom> rooms = FXCollections.observableArrayList();
    public static ObservableList<String> chat = FXCollections.observableArrayList();
    public static ObservableList<String> notifications = FXCollections.observableArrayList();
    public static ObservableList<String> activities = FXCollections.observableArrayList();

    public static void register(String username, String password) {
        Database.register(username, password);
    }

    public static void logout() {
        if (currentUser != null) {
            Database.setOffline(currentUser.getId());
        }
        currentUser = null;
    }

    public static void init() {
        Database.init();
    }
    public static void login(String username, String password){ currentUser = Database.loginOrCreate(username, password); reloadAll(); }
    public static void reloadAll(){
        if(currentUser == null) return;
        players.setAll(Database.getPlayers()); friends.setAll(Database.getFriends(currentUser.getId())); requests.setAll(Database.getRequests(currentUser.getId())); blocked.setAll(Database.getBlocked(currentUser.getId())); rooms.setAll(Database.getRooms()); chat.setAll(Database.getChat()); notifications.setAll(Database.getNotifications(currentUser.getId())); activities.setAll(Database.getActivities(currentUser.getId()));
    }
}
