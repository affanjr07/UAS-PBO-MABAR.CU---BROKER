package com.mabarcu.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "room_chat_messages")
public class RoomChatMessage extends BaseEntity {

    private int roomId;

    @NotBlank(message = "Sender tidak boleh kosong")
    private String sender;

    @NotBlank(message = "Pesan tidak boleh kosong")
    @Column(length = 1000)
    private String message;

    public RoomChatMessage() {
    }

    public RoomChatMessage(int roomId, String sender, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }

    public String display() {
        return sender + ": " + message;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getMsg() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsg(String message) {
        this.message = message;
    }
}