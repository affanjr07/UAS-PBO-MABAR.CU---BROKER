package com.mabarcu.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User extends Account implements Matchable {
    @NotBlank private String password = "demo";
    @NotBlank private String favoriteGame = "Mobile Legends";
    @NotBlank private String gameRank = "Epic";
    @NotBlank private String preferredRole = "Jungler";
    private String region = "Indonesia";
    private String onlineStatus = "Online";
    @DecimalMin("0.0") @DecimalMax("5.0") private double rating = 4.0;
    @Column(length=700) private String bio = "Siap mabar, no toxic, main objektif.";
    private String gender = "Optional";
    private int followers = 0;
    private int following = 0;
    private int friends = 0;

    public User(){}
    public User(String username,String displayName,String favoriteGame,String gameRank,String preferredRole,String region,String onlineStatus,String role,double rating){
        setUsername(username); setDisplayName(displayName); this.favoriteGame=favoriteGame; this.gameRank=gameRank; this.preferredRole=preferredRole; this.region=region; this.onlineStatus=onlineStatus; setRole(role); this.rating=rating;
        this.followers=120; this.following=88; this.friends=24;
    }
    public User(int id,String username,String displayName,String favoriteGame,String gameRank,String preferredRole,String region,String onlineStatus,String role,double rating,String bio,String gender,int followers,int following,int friends){
        this(username,displayName,favoriteGame,gameRank,preferredRole,region,onlineStatus,role,rating); setId(id); this.bio=bio; this.gender=gender; this.followers=followers; this.following=following; this.friends=friends;
    }
    public String getPassword(){ return password; } public void setPassword(String password){ this.password=password; }
    public String getFavoriteGame(){ return favoriteGame; } public void setFavoriteGame(String favoriteGame){ this.favoriteGame=favoriteGame; }
    public String getGameRank(){ return gameRank; } public void setGameRank(String gameRank){ this.gameRank=gameRank; }
    public String getPreferredRole(){ return preferredRole; } public void setPreferredRole(String preferredRole){ this.preferredRole=preferredRole; }
    public String getRegion(){ return region; } public void setRegion(String region){ this.region=region; }
    public String getOnlineStatus(){ return onlineStatus; } public String getStatus(){ return onlineStatus; } public void setStatus(String s){ this.onlineStatus=s; } public void setOnlineStatus(String s){ this.onlineStatus=s; }
    public double getRating(){ return rating; } public void setRating(double rating){ this.rating=rating; }
    public String getBio(){ return bio; } public void setBio(String bio){ this.bio=bio; }
    public String getGender(){ return gender; } public void setGender(String gender){ this.gender=gender; }
    public int getFollowers(){ return followers; } public void setFollowers(int followers){ this.followers=followers; }
    public int getFollowing(){ return following; } public void setFollowing(int following){ this.following=following; }
    public int getFriends(){ return friends; } public void setFriends(int friends){ this.friends=friends; }
    @Override public String summary(){ return getDisplayName()+" • "+favoriteGame+" • "+gameRank+" • "+preferredRole+" • "+onlineStatus+" • ⭐ "+rating; }
    @Override public String toString(){ return summary(); }
}
