package com.bwango.harvestsyncbackend.dto;

import com.bwango.harvestsyncbackend.model.Permission;
import com.bwango.harvestsyncbackend.model.Role;

import java.util.List;

public class AuthResponse {
    private String token;
    private Long expiresAt;
    private String userId;
    private String name;
    private Role role;
    private List<Permission> permissions;

    public AuthResponse() {}

    public AuthResponse(String token, Long expiresAt, String userId, String name, Role role, List<Permission> permissions) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.permissions = permissions;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Long getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Long expiresAt) { this.expiresAt = expiresAt; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public List<Permission> getPermissions() { return permissions; }
    public void setPermissions(List<Permission> permissions) { this.permissions = permissions; }

    public static class AuthResponseBuilder {
        private String token;
        private Long expiresAt;
        private String userId;
        private String name;
        private Role role;
        private List<Permission> permissions;

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder expiresAt(Long expiresAt) { this.expiresAt = expiresAt; return this; }
        public AuthResponseBuilder userId(String userId) { this.userId = userId; return this; }
        public AuthResponseBuilder name(String name) { this.name = name; return this; }
        public AuthResponseBuilder role(Role role) { this.role = role; return this; }
        public AuthResponseBuilder permissions(List<Permission> permissions) { this.permissions = permissions; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, expiresAt, userId, name, role, permissions);
        }
    }
}
