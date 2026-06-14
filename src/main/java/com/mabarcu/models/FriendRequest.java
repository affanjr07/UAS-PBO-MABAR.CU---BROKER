package com.mabarcu.models;
import jakarta.persistence.*;
@Entity @Table(name="friend_requests") public class FriendRequest extends BaseEntity { private int userId; private String requester; public FriendRequest(){} public FriendRequest(int userId,String requester){this.userId=userId;this.requester=requester;} public int getUserId(){return userId;} public void setUserId(int userId){this.userId=userId;} public String getRequester(){return requester;} public void setRequester(String requester){this.requester=requester;} public String display(){return requester+" mengirim request";} }
