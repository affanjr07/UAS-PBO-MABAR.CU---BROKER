package com.mabarcu.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "chat_messages")
public class ChatMessage extends BaseEntity {

    @NotBlank(message = "Sender tidak boleh kosong")
    private String sender;

    @NotBlank(message = "Channel tidak boleh kosong")
    private String channel;

    @NotBlank(message = "Pesan tidak boleh kosong")
    @Column(length = 1000)
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.channel = "Mobile Legends";
        this.message = message;
    }

    public ChatMessage(String sender, String channel, String message) {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
    }

    public String display() {
        return "[" + channel + "] " + sender + ": " + message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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