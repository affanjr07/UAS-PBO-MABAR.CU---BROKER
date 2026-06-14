package com.mabarcu.models;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class Account extends BaseEntity {
    @NotBlank private String username;
    @NotBlank private String displayName;
    private String role = "USER";
    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }
    public String getDisplayName(){ return displayName; }
    public void setDisplayName(String displayName){ this.displayName = displayName; }
    public String getRole(){ return role; }
    public void setRole(String role){ this.role = role; }
    public boolean isAdmin(){ return "ADMIN".equalsIgnoreCase(role); }
}
