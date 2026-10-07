package com.bwango.harvestsyncbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "audit_logs", indexes = {@Index(name = "idx_audit_updated_at", columnList = "updatedAt")})
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientLocalId;

    @Column(nullable = false)
    private Long timestamp;

    @Column(nullable = false)
    private String userId;

    private String userName;
    private String userRole;
    private String action;
    private String targetId;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(nullable = false)
    private Long updatedAt;

    public AuditLogEntity() {}

    public AuditLogEntity(Long id, Long clientLocalId, Long timestamp, String userId, String userName, String userRole, String action, String targetId, String details, Long updatedAt) {
        this.id = id;
        this.clientLocalId = clientLocalId;
        this.timestamp = timestamp;
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.action = action;
        this.targetId = targetId;
        this.details = details;
        this.updatedAt = updatedAt;
    }

    public static AuditLogEntityBuilder builder() {
        return new AuditLogEntityBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClientLocalId() { return clientLocalId; }
    public void setClientLocalId(Long clientLocalId) { this.clientLocalId = clientLocalId; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    public static class AuditLogEntityBuilder {
        private Long id;
        private Long clientLocalId;
        private Long timestamp;
        private String userId;
        private String userName;
        private String userRole;
        private String action;
        private String targetId;
        private String details;
        private Long updatedAt;

        public AuditLogEntityBuilder id(Long id) { this.id = id; return this; }
        public AuditLogEntityBuilder clientLocalId(Long clientLocalId) { this.clientLocalId = clientLocalId; return this; }
        public AuditLogEntityBuilder timestamp(Long timestamp) { this.timestamp = timestamp; return this; }
        public AuditLogEntityBuilder userId(String userId) { this.userId = userId; return this; }
        public AuditLogEntityBuilder userName(String userName) { this.userName = userName; return this; }
        public AuditLogEntityBuilder userRole(String userRole) { this.userRole = userRole; return this; }
        public AuditLogEntityBuilder action(String action) { this.action = action; return this; }
        public AuditLogEntityBuilder targetId(String targetId) { this.targetId = targetId; return this; }
        public AuditLogEntityBuilder details(String details) { this.details = details; return this; }
        public AuditLogEntityBuilder updatedAt(Long updatedAt) { this.updatedAt = updatedAt; return this; }

        public AuditLogEntity build() {
            return new AuditLogEntity(id, clientLocalId, timestamp, userId, userName, userRole, action, targetId, details, updatedAt);
        }
    }
}
