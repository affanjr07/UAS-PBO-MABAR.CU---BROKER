package com.mabarcu.services;

import com.mabarcu.backend.services.AppService;
import com.mabarcu.models.PartyRoom;
import com.mabarcu.models.User;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class Database {

    private static ConfigurableApplicationContext context;

    public static void init(ConfigurableApplicationContext springContext) {
        context = springContext;
        service().seed();
    }

    public static void init() {
        if (context == null) {
            throw new IllegalStateException("Spring context belum aktif. Pastikan MainApp memanggil Database.init(context).");
        }
    }

    private static AppService service() {
        if (context == null) {
            throw new IllegalStateException("Spring context belum aktif.");
        }

        return context.getBean(AppService.class);
    }

    public static User login(String username, String password) {
        return service().login(username, password);
    }

    public static User loginOrCreate(String username, String password) {
        return service().loginOrCreate(username, password);
    }

    public static User register(String username, String password) {
        return service().register(username, password);
    }

    public static void setOnline(int userId) {
        service().setOnline(userId);
    }

    public static void setOffline(int userId) {
        service().setOffline(userId);
    }

    public static List<User> getPlayers() {
        return service().getPlayers();
    }

    public static List<User> searchPlayers(String keyword) {
        return service().searchPlayers(keyword);
    }

    public static User findUser(String username) {
        return service().findUser(username);
    }

    public static User updateProfile(User user) {
        return service().updateProfile(user);
    }

    public static List<String> getFriends(int userId) {
        return service().getFriends(userId);
    }

    public static void addFriend(int userId, String name, String status, String game) {
        service().addFriend(userId, name, status, game);
    }

    public static void removeFriend(int userId, String label) {
        service().removeFriend(userId, label);
    }

    public static void followUser(int followerId, int targetId) {
        service().followUser(followerId, targetId);
    }

    public static void unfollowUser(int followerId, int targetId) {
        service().unfollowUser(followerId, targetId);
    }

    public static List<String> getRequests(int userId) {
        return service().getRequests(userId);
    }

    public static void addRequest(int userId, String requester) {
        service().addRequest(userId, requester);
    }

    public static void removeRequest(int userId, String label) {
        service().removeRequest(userId, label);
    }

    public static List<String> getBlocked(int userId) {
        return service().getBlocked(userId);
    }

    public static void addBlocked(int userId, String name) {
        service().addBlocked(userId, name);
    }

    public static List<PartyRoom> getRooms() {
        return service().getRooms();
    }

    public static PartyRoom insertRoom(PartyRoom room) {
        return service().insertRoom(room);
    }

    public static PartyRoom createRoom(
            String game,
            String rank,
            String neededRole,
            String roomType,
            String owner,
            int maxPlayers
    ) {
        return service().createRoom(game, rank, neededRole, roomType, owner, maxPlayers);
    }

    public static void addRoomMember(int roomId, String name) {
        service().addRoomMember(roomId, name);
    }

    public static void removeRoomMember(int roomId, String name) {
        service().removeRoomMember(roomId, name);
    }

    public static void inviteUser(int senderId, int targetId, int roomId) {
        service().inviteUser(senderId, targetId, roomId);
    }

    public static List<String> getRoomChat(int roomId) {
        return service().getRoomChat(roomId);
    }

    public static void addRoomChat(int roomId, String sender, String message) {
        service().addRoomChat(roomId, sender, message);
    }

    public static List<String> getChat() {
        return service().getChat();
    }

    public static List<String> getChat(String channel) {
        return service().getChat(channel);
    }

    public static void addChat(String sender, String msg) {
        service().addChat(sender, msg);
    }

    public static void addChat(String sender, String channel, String msg) {
        service().addChat(sender, channel, msg);
    }

    public static List<String> getNotifications(int userId) {
        return service().getNotifications(userId);
    }

    public static void addNotification(int userId, String text) {
        service().addNotification(userId, text);
    }

    public static List<String> getActivities(int userId) {
        return service().getActivities(userId);
    }

    public static void addActivity(int userId, String text) {
        service().addActivity(userId, text);
    }

    public static boolean isMutualFollow(int userId1, int userId2) {
        return service().isMutualFollow(userId1, userId2);
    }

    public static List<String> getPrivateMessages(int userId1, int userId2) {
        return service().getPrivateMessages(userId1, userId2);
    }

    public static void sendPrivateMessage(int senderId, int receiverId, String message) {
        service().sendPrivateMessage(senderId, receiverId, message);
    }

    public static List<User> getMutualFriends(int userId) {
        return service().getMutualFriends(userId);
    }

    public static User findUserById(int id) {
        return service().findUserById(id);
    }
}