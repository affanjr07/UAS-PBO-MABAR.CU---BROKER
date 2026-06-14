package com.mabarcu.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "private_messages")
public class PrivateMessage extends BaseEntity {

    private int senderId;
    private int receiverId;

    @NotBlank(message = "Sender name tidak boleh kosong")
    private String senderName;

    @NotBlank(message = "Receiver name tidak boleh kosong")
    private String receiverName;

    @NotBlank(message = "Pesan tidak boleh kosong")
    @Column(length = 1000)
    private String message;

    public PrivateMessage() {
    }

    public PrivateMessage(int senderId, int receiverId, String senderName, String receiverName, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.message = message;
    }

    public String display() {
        return senderName + ": " + message;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
