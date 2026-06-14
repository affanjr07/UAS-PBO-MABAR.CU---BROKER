package com.mabarcu.models;
import jakarta.persistence.*;
@Entity @Table(name="blocked_users") public class BlockedUser extends BaseEntity { private int userId; private String blockedName; public BlockedUser(){} public BlockedUser(int userId,String blockedName){this.userId=userId;this.blockedName=blockedName;} public int getUserId(){return userId;} public void setUserId(int userId){this.userId=userId;} public String getBlockedName(){return blockedName;} public void setBlockedName(String blockedName){this.blockedName=blockedName;} }
