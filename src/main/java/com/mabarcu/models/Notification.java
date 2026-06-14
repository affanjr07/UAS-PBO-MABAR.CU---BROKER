package com.mabarcu.models;
import jakarta.persistence.*;
@Entity @Table(name="notifications") public class Notification extends BaseEntity { private int userId; private String text; public Notification(){} public Notification(int userId,String text){this.userId=userId;this.text=text;} public int getUserId(){return userId;} public void setUserId(int userId){this.userId=userId;} public String getText(){return text;} public void setText(String text){this.text=text;} }
