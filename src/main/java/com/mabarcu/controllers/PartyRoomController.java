package com.mabarcu.controllers;


import com.mabarcu.models.PartyRoom;
import com.mabarcu.services.AppData;
import com.mabarcu.services.Database;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PartyRoomController {

    @FXML private ComboBox<String> gameBox;
    @FXML private ComboBox<String> rankBox;
    @FXML private ComboBox<String> roleBox;
    @FXML private ComboBox<String> typeBox;
    @FXML private Spinner<Integer> maxPlayerSpinner;

    @FXML private ListView<String> roomList;
    @FXML private ListView<String> memberList;
    @FXML private ListView<String> roomChatList;

    @FXML private TextField roomMessageField;
    @FXML private Label infoLabel;

    private PartyRoom selectedRoom;

    @FXML
    public void initialize() {
        setupForm();
        setupRoomSelection();
        refreshRooms();
    }

    private void setupForm() {
        gameBox.getItems().setAll(
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

        rankBox.getItems().setAll(
                "Bebas",
                "Warrior",
                "Elite",
                "Master",
                "Grandmaster",
                "Epic",
                "Legend",
                "Mythic",
                "Iron",
                "Bronze",
                "Silver",
                "Gold",
                "Platinum",
                "Diamond",
                "Ascendant",
                "Immortal",
                "Radiant",
                "Crown",
                "Ace",
                "Conqueror"
        );

        roleBox.getItems().setAll(
                "Bebas",
                "Jungler",
                "Roamer",
                "Mid Lane",
                "Gold Laner",
                "EXP Lane",
                "Duelist",
                "Initiator",
                "Controller",
                "Sentinel",
                "Rusher",
                "Support",
                "Sniper",
                "IGL"
        );

        typeBox.getItems().setAll("Public", "Private");

        gameBox.setValue("Mobile Legends");
        rankBox.setValue("Bebas");
        roleBox.setValue("Bebas");
        typeBox.setValue("Public");

        maxPlayerSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 5)
        );
    }

    private void setupRoomSelection() {
        roomList.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> selectRoom());
    }

    @FXML
    public void createRoom() {
        try {
            if (AppData.currentUser == null) {
                setInfo("Login dulu sebelum membuat room.");
                return;
            }

            PartyRoom room = Database.createRoom(
                    gameBox.getValue(),
                    rankBox.getValue(),
                    roleBox.getValue(),
                    typeBox.getValue(),
                    AppData.currentUser.getDisplayName(),
                    maxPlayerSpinner.getValue()
            );

            selectedRoom = room;

            refreshRooms();
            selectRoomById(room.getId());

            setInfo("Room berhasil dibuat untuk " + room.getGame() + ".");

        } catch (Exception e) {
            setInfo(e.getMessage());
        }
    }

    @FXML
    public void joinRoom() {
        try {
            if (selectedRoom == null) {
                setInfo("Pilih room dulu.");
                return;
            }

            if (AppData.currentUser == null) {
                setInfo("Login dulu.");
                return;
            }

            Database.addRoomMember(
                    selectedRoom.getId(),
                    AppData.currentUser.getDisplayName()
            );

            int selectedId = selectedRoom.getId();

            refreshRooms();
            selectRoomById(selectedId);

            setInfo("Berhasil join room.");

        } catch (Exception e) {
            setInfo(e.getMessage());
        }
    }

    @FXML
    public void leaveRoom() {
        try {
            if (selectedRoom == null) {
                setInfo("Pilih room dulu.");
                return;
            }

            if (AppData.currentUser == null) {
                setInfo("Login dulu.");
                return;
            }

            Database.removeRoomMember(
                    selectedRoom.getId(),
                    AppData.currentUser.getDisplayName()
            );

            refreshRooms();
            clearRoomDetail();

            setInfo("Berhasil keluar room.");

        } catch (Exception e) {
            setInfo(e.getMessage());
        }
    }

    @FXML
    public void sendRoomMessage() {
        try {
            if (selectedRoom == null) {
                setInfo("Pilih room dulu.");
                return;
            }

            if (AppData.currentUser == null) {
                setInfo("Login dulu.");
                return;
            }

            String msg = roomMessageField.getText();

            if (msg == null || msg.isBlank()) {
                setInfo("Pesan tidak boleh kosong.");
                return;
            }

            Database.addRoomChat(
                    selectedRoom.getId(),
                    AppData.currentUser.getDisplayName(),
                    msg.trim()
            );

            roomMessageField.clear();
            loadRoomChat();

            setInfo("Pesan terkirim.");

        } catch (Exception e) {
            setInfo(e.getMessage());
        }
    }

    @FXML
    public void refreshRooms() {
        roomList.getItems().clear();

        for (PartyRoom r : Database.getRooms()) {
            roomList.getItems().add(formatRoom(r));
        }
    }

    private void selectRoom() {
        String selected = roomList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        int id = Integer.parseInt(selected.split(" \\| ")[0]);

        selectedRoom = Database.getRooms()
                .stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);

        if (selectedRoom == null) {
            clearRoomDetail();
            return;
        }

        memberList.getItems().clear();
        memberList.getItems().addAll(selectedRoom.getMembers());

        loadRoomChat();

        setInfo("Room dipilih: " + selectedRoom.getGame());
    }

    private void selectRoomById(int id) {
        for (String item : roomList.getItems()) {
            if (item.startsWith(id + " | ")) {
                roomList.getSelectionModel().select(item);
                selectRoom();
                return;
            }
        }
    }

    private void loadRoomChat() {
        roomChatList.getItems().clear();

        if (selectedRoom == null) {
            return;
        }

        roomChatList.getItems().addAll(
                Database.getRoomChat(selectedRoom.getId())
        );
    }

    private void clearRoomDetail() {
        selectedRoom = null;

        if (memberList != null) {
            memberList.getItems().clear();
        }

        if (roomChatList != null) {
            roomChatList.getItems().clear();
        }
    }

    private String formatRoom(PartyRoom r) {
        return r.getId()
                + " | "
                + r.getGame()
                + " • "
                + r.getRank()
                + " • Need: "
                + r.getNeededRole()
                + " • "
                + r.getMembers().size()
                + "/"
                + r.getMaxPlayers()
                + " • "
                + r.getRoomType()
                + " • Owner: "
                + r.getOwner();
    }

    private void setInfo(String text) {
        if (infoLabel != null) {
            infoLabel.setText(text);
        }
    }
}