package com.mabarcu.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "party_rooms")
public class PartyRoom extends BaseEntity {

    @NotBlank(message = "Nama room wajib diisi")
    private String title;

    @NotBlank(message = "Game wajib dipilih")
    private String game;

    private String rank;

    private String neededRole;

    private String roomType;

    @NotBlank(message = "Owner wajib ada")
    private String owner;

    @Min(value = 2, message = "Minimal 2 player")
    @Max(value = 10, message = "Maksimal 10 player")
    private int maxPlayers;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "party_members",
            joinColumns = @JoinColumn(name = "room_id")
    )
    @Column(name = "member_name")
    private List<String> members = new ArrayList<>();

    public PartyRoom() {
    }

    public PartyRoom(
            String title,
            String game,
            String rank,
            String neededRole,
            String roomType,
            String owner,
            int maxPlayers
    ) {
        this.title = title;
        this.game = game;
        this.rank = rank;
        this.neededRole = neededRole;
        this.roomType = roomType;
        this.owner = owner;
        this.maxPlayers = maxPlayers;

        addMember(owner);
    }

    public PartyRoom(
            String game,
            String rank,
            String neededRole,
            String roomType,
            String owner,
            int maxPlayers
    ) {
        this(
                "Room " + game,
                game,
                rank,
                neededRole,
                roomType,
                owner,
                maxPlayers
        );
    }

    public void syncData() {
        if (title == null || title.isBlank()) {
            title = "Room " + safe(game, "Game");
        }

        if (rank == null || rank.isBlank()) {
            rank = "Bebas";
        }

        if (neededRole == null || neededRole.isBlank()) {
            neededRole = "Bebas";
        }

        if (roomType == null || roomType.isBlank()) {
            roomType = "Public";
        }

        if (members == null) {
            members = new ArrayList<>();
        }

        if (owner != null && !owner.isBlank() && members.stream().noneMatch(m -> m.equalsIgnoreCase(owner))) {
            members.add(owner);
        }
    }

    public boolean hasSlot() {
        syncData();
        return members.size() < maxPlayers;
    }

    public void addMember(String name) {
        if (name == null || name.isBlank()) {
            return;
        }

        if (members == null) {
            members = new ArrayList<>();
        }

        boolean exists = members.stream()
                .anyMatch(m -> m.equalsIgnoreCase(name));

        if (!exists && members.size() < maxPlayers) {
            members.add(name);
        }
    }

    public void removeMember(String name) {
        if (members == null || name == null) {
            return;
        }

        members.removeIf(m -> m.equalsIgnoreCase(name));
    }

    public String summary() {
        syncData();

        return title
                + " • "
                + game
                + " • "
                + rank
                + " • Need: "
                + neededRole
                + " • "
                + members.size()
                + "/"
                + maxPlayers
                + " • "
                + roomType
                + " • Owner: "
                + owner;
    }

    private String safe(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getRank() {
        return rank;
    }

    public String getTargetRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setTargetRank(String rank) {
        this.rank = rank;
    }

    public String getNeededRole() {
        return neededRole;
    }

    public String getMissingRole() {
        return neededRole;
    }

    public void setNeededRole(String neededRole) {
        this.neededRole = neededRole;
    }

    public void setMissingRole(String neededRole) {
        this.neededRole = neededRole;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setType(String type) {
        this.roomType = type;
    }

    public String getOwner() {
        return owner;
    }

    public String getOwnerName() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOwnerName(String owner) {
        this.owner = owner;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getMaxUser() {
        return maxPlayers;
    }

    public int getMaxUsers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setMaxUser(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setMaxUsers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getCurrentPlayers() {
        syncData();
        return members.size();
    }

    public List<String> getMembers() {
        syncData();
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
        syncData();
    }
}