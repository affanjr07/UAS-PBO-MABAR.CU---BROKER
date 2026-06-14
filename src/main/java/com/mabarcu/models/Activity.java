package com.mabarcu.models;
import jakarta.persistence.*;
@Entity @Table(name="activities") public class Activity extends BaseEntity { private int userId; private String text; public Activity(){} public Activity(int userId,String text){this.userId=userId;this.text=text;} public int getUserId(){return userId;} public void setUserId(int userId){this.userId=userId;} public String getText(){return text;} public void setText(String text){this.text=text;} }
