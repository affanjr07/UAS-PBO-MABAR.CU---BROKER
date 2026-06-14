package com.mabarcu.backend.services;

import com.mabarcu.backend.repositories.*;
import com.mabarcu.models.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AppService {

    private final UserRepository users;
    private final FriendRepository friends;
    private final FriendRequestRepository requests;
    private final BlockedUserRepository blocked;
    private final PartyRoomRepository rooms;
    private final ChatMessageRepository chats;
    private final RoomChatMessageRepository roomChats;
    private final NotificationRepository notifications;
    private final ActivityRepository activities;
    private final PasswordEncoder encoder;

    public AppService(
            UserRepository users,
            FriendRepository friends,
            FriendRequestRepository requests,
            BlockedUserRepository blocked,
            PartyRoomRepository rooms,
            ChatMessageRepository chats,
            RoomChatMessageRepository roomChats,
            NotificationRepository notifications,
            ActivityRepository activities,
            PasswordEncoder encoder
    ) {
        this.users = users;
        this.friends = friends;
        this.requests = requests;
        this.blocked = blocked;
        this.rooms = rooms;
        this.chats = chats;
        this.roomChats = roomChats;
        this.notifications = notifications;
        this.activities = activities;
        this.encoder = encoder;
    }

    @Transactional
    public User register(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username wajib diisi.");
        }

        String clean = username.trim().toLowerCase();

        if (users.findByUsernameIgnoreCase(clean).isPresent()) {
            throw new IllegalArgumentException("Username sudah dipakai.");
        }

        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password minimal 4 karakter.");
        }

        User u = new User(
                clean,
                clean.substring(0, 1).toUpperCase() + clean.substring(1),
                "Mobile Legends",
                "Epic",
                "Jungler",
                "Indonesia",
                "Offline",
                "USER",
                4.0
        );

        u.setPassword(encoder.encode(password));
        u.setOnlineStatus("Offline");

        User saved = users.save(u);

        addNotification(saved.getId(), "Akun berhasil dibuat.");
        addActivity(saved.getId(), "Bergabung ke MABAR.CU");

        return saved;
    }

    @Transactional
    public User login(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username wajib diisi.");
        }

        User u = users.findByUsernameIgnoreCase(username.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Username tidak ditemukan."));

        if (!encoder.matches(password, u.getPassword())) {
            throw new IllegalArgumentException("Password salah.");
        }

        u.setOnlineStatus("Online");
        return users.save(u);
    }

    @Transactional
    public User loginOrCreate(String username, String password) {
        return login(username, password);
    }

    @Transactional
    public void setOnline(int userId) {
        User u = users.findById(userId).orElseThrow();
        u.setOnlineStatus("Online");
        users.save(u);
    }

    @Transactional
    public void setOffline(int userId) {
        User u = users.findById(userId).orElseThrow();
        u.setOnlineStatus("Offline");
        users.save(u);
    }

    public List<User> getPlayers() {
        return users.findAll()
                .stream()
                .filter(u -> !"ADMIN".equalsIgnoreCase(u.getRole()))
                .sorted(
                        Comparator
                                .comparing((User u) -> !"Online".equalsIgnoreCase(u.getOnlineStatus()))
                                .thenComparing(User::getRating, Comparator.reverseOrder())
                )
                .toList();
    }

    public List<User> searchPlayers(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getPlayers();
        }

        String q = keyword.trim();

        return users.findByUsernameContainingIgnoreCaseOrDisplayNameContainingIgnoreCase(q, q)
                .stream()
                .filter(u -> !"ADMIN".equalsIgnoreCase(u.getRole()))
                .toList();
    }

    public User findUser(String username) {
        return users.findByUsernameIgnoreCase(username).orElse(null);
    }

    @Transactional
    public User updateProfile(@Valid User u) {
        User db = users.findById(u.getId()).orElseThrow();

        db.setUsername(u.getUsername());
        db.setDisplayName(u.getDisplayName());
        db.setFavoriteGame(u.getFavoriteGame());
        db.setGameRank(u.getGameRank());
        db.setPreferredRole(u.getPreferredRole());
        db.setRegion(u.getRegion());
        db.setGender(u.getGender());
        db.setBio(u.getBio());

        return users.save(db);
    }

    @Transactional
    public void followUser(int followerId, int targetId) {
        if (followerId == targetId) {
            throw new IllegalArgumentException("Tidak bisa follow diri sendiri.");
        }

        User follower = users.findById(followerId).orElseThrow();
        User target = users.findById(targetId).orElseThrow();

        boolean alreadyFollow = friends.findByUserId(followerId)
                .stream()
                .anyMatch(f -> f.getFriendName().equalsIgnoreCase(target.getDisplayName()));

        if (alreadyFollow) {
            return;
        }

        friends.save(new Friend(
                followerId,
                target.getDisplayName(),
                target.getOnlineStatus(),
                target.getFavoriteGame()
        ));

        follower.setFollowing(follower.getFollowing() + 1);
        target.setFollowers(target.getFollowers() + 1);

        users.save(follower);
        users.save(target);

        addNotification(targetId, follower.getDisplayName() + " mulai mengikuti kamu.");
        addActivity(followerId, "Follow " + target.getDisplayName());
    }

    @Transactional
    public void unfollowUser(int followerId, int targetId) {
        User target = users.findById(targetId).orElseThrow();

        friends.deleteByUserIdAndFriendNameContainingIgnoreCase(
                followerId,
                target.getDisplayName()
        );

        User follower = users.findById(followerId).orElseThrow();

        if (follower.getFollowing() > 0) {
            follower.setFollowing(follower.getFollowing() - 1);
        }

        if (target.getFollowers() > 0) {
            target.setFollowers(target.getFollowers() - 1);
        }

        users.save(follower);
        users.save(target);

        addActivity(followerId, "Unfollow " + target.getDisplayName());
    }

    public List<String> getFriends(int userId) {
        return friends.findByUserId(userId)
                .stream()
                .map(Friend::display)
                .toList();
    }

    @Transactional
    public void addFriend(int userId, String name, String status, String game) {
        friends.save(new Friend(userId, name, status, game));
        addNotification(userId, "Berhasil follow " + name);
        addActivity(userId, "Follow player " + name);
    }

    @Transactional
    public void removeFriend(int userId, String label) {
        String name = label.split(" • ")[0];
        friends.deleteByUserIdAndFriendNameContainingIgnoreCase(userId, name);
        addActivity(userId, "Menghapus teman " + name);
    }

    public List<String> getRequests(int userId) {
        return requests.findByUserId(userId)
                .stream()
                .map(FriendRequest::display)
                .toList();
    }

    @Transactional
    public void addRequest(int userId, String requester) {
        requests.save(new FriendRequest(userId, requester));
    }

    @Transactional
    public void removeRequest(int userId, String label) {
        String name = label.split(" ")[0];
        requests.deleteByUserIdAndRequesterContainingIgnoreCase(userId, name);
    }

    public List<String> getBlocked(int userId) {
        return blocked.findByUserId(userId)
                .stream()
                .map(BlockedUser::getBlockedName)
                .toList();
    }

    @Transactional
    public void addBlocked(int userId, String name) {
        blocked.save(new BlockedUser(userId, name));
        addActivity(userId, "Memblokir " + name);
    }

    public List<PartyRoom> getRooms() {
        return rooms.findAll()
                .stream()
                .sorted(Comparator.comparing(PartyRoom::getId).reversed())
                .toList();
    }

    @Transactional
    public PartyRoom insertRoom(@Valid PartyRoom r) {
        r.syncData();

        PartyRoom saved = rooms.save(r);

        addActivity(
                findUserIdByDisplay(r.getOwner()),
                "Membuat party " + r.getGame()
        );

        return saved;
    }

    @Transactional
    public PartyRoom createRoom(
            String game,
            String rank,
            String neededRole,
            String roomType,
            String owner,
            int maxPlayers
    ) {
        if (game == null || game.isBlank()) {
            throw new IllegalArgumentException("Game wajib dipilih.");
        }

        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner tidak valid.");
        }

        if (maxPlayers < 2) {
            throw new IllegalArgumentException("Max player minimal 2.");
        }

        if (maxPlayers > 10) {
            throw new IllegalArgumentException("Max player maksimal 10.");
        }

        PartyRoom room = new PartyRoom(
                "Room " + game,
                game,
                rank == null || rank.isBlank() ? "Bebas" : rank,
                neededRole == null || neededRole.isBlank() ? "Bebas" : neededRole,
                roomType == null || roomType.isBlank() ? "Public" : roomType,
                owner,
                maxPlayers
        );

        room.syncData();

        PartyRoom saved = rooms.save(room);

        addActivity(
                findUserIdByDisplay(owner),
                "Membuat room " + game
        );

        return saved;
    }

    @Transactional
    public void addRoomMember(int roomId, String name) {
        PartyRoom r = rooms.findById(roomId).orElseThrow();

        r.syncData();

        if (r.getMembers().stream().anyMatch(m -> m.equalsIgnoreCase(name))) {
            return;
        }

        if (!r.hasSlot()) {
            throw new IllegalStateException("Room penuh");
        }

        r.addMember(name);
        rooms.save(r);
    }

    @Transactional
    public void removeRoomMember(int roomId, String name) {
        PartyRoom r = rooms.findById(roomId).orElseThrow();
        r.removeMember(name);
        rooms.save(r);
    }

    @Transactional
    public void inviteUser(int senderId, int targetId, int roomId) {
        User sender = users.findById(senderId).orElseThrow();
        User target = users.findById(targetId).orElseThrow();
        PartyRoom room = rooms.findById(roomId).orElseThrow();

        String roomName = room.getGame() + " - " + room.getRank();

        addNotification(
                target.getId(),
                sender.getDisplayName() + " mengundang kamu ke room " + roomName
        );

        addActivity(
                sender.getId(),
                "Invite " + target.getDisplayName() + " ke room " + roomName
        );
    }

    public List<String> getRoomChat(int roomId) {
        return roomChats.findByRoomIdOrderByIdAsc(roomId)
                .stream()
                .map(RoomChatMessage::display)
                .toList();
    }

    @Transactional
    public void addRoomChat(int roomId, String sender, String message) {
        if (sender == null || sender.isBlank()) {
            throw new IllegalArgumentException("Sender tidak valid.");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Pesan tidak boleh kosong.");
        }

        PartyRoom room = rooms.findById(roomId).orElseThrow();
        room.syncData();

        boolean isMember = room.getMembers()
                .stream()
                .anyMatch(m -> m.equalsIgnoreCase(sender));

        if (!isMember) {
            throw new IllegalArgumentException("Kamu harus join room dulu sebelum chat.");
        }

        roomChats.save(new RoomChatMessage(roomId, sender, message.trim()));
    }

    public List<String> getChat() {
        return chats.findAll()
                .stream()
                .sorted(Comparator.comparing(ChatMessage::getId))
                .map(ChatMessage::display)
                .toList();
    }

    public List<String> getChat(String channel) {
        return chats.findByChannelIgnoreCaseOrderByIdAsc(channel)
                .stream()
                .map(ChatMessage::display)
                .toList();
    }

    @Transactional
    public void addChat(String sender, String msg) {
        addChat(sender, "Mobile Legends", msg);
    }

    @Transactional
    public void addChat(String sender, String channel, String msg) {
        if (msg == null || msg.isBlank()) {
            throw new IllegalArgumentException("Pesan tidak boleh kosong");
        }

        chats.save(new ChatMessage(sender, channel, msg.trim()));
    }

    @Transactional
    public void addNotification(int userId, String text) {
        notifications.save(new Notification(userId, text));
    }

    @Transactional
    public void addActivity(int userId, String text) {
        activities.save(new Activity(userId, text));
    }

    public List<String> getNotifications(int userId) {
        return notifications.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(Notification::getText)
                .toList();
    }

    public List<String> getActivities(int userId) {
        return activities.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(Activity::getText)
                .toList();
    }

    private int findUserIdByDisplay(String display) {
        return users.findAll()
                .stream()
                .filter(u ->
                        u.getDisplayName().equalsIgnoreCase(display)
                                || u.getUsername().equalsIgnoreCase(display)
                )
                .map(User::getId)
                .findFirst()
                .orElse(1);
    }

    @Transactional
    public void seed() {
        // Demo/fake user dimatikan.
        // User asli hanya berasal dari register.
    }
}