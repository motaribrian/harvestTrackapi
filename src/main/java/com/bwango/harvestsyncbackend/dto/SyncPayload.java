package com.bwango.harvestsyncbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class SyncPayload {

    private Long lastSyncTimestamp;
    private List<WorkerDto> workers = new ArrayList<>();
    private List<ShiftDayDto> shiftDays = new ArrayList<>();
    private List<ProductivityEntryDto> entries = new ArrayList<>();
    private List<AuditLogDto> auditLogs = new ArrayList<>();

    public SyncPayload() {}

    public SyncPayload(Long lastSyncTimestamp, List<WorkerDto> workers, List<ShiftDayDto> shiftDays, List<ProductivityEntryDto> entries, List<AuditLogDto> auditLogs) {
        this.lastSyncTimestamp = lastSyncTimestamp;
        this.workers = workers != null ? workers : new ArrayList<>();
        this.shiftDays = shiftDays != null ? shiftDays : new ArrayList<>();
        this.entries = entries != null ? entries : new ArrayList<>();
        this.auditLogs = auditLogs != null ? auditLogs : new ArrayList<>();
    }

    public static SyncPayloadBuilder builder() {
        return new SyncPayloadBuilder();
    }

    public Long getLastSyncTimestamp() { return lastSyncTimestamp; }
    public void setLastSyncTimestamp(Long lastSyncTimestamp) { this.lastSyncTimestamp = lastSyncTimestamp; }
    public List<WorkerDto> getWorkers() { return workers; }
    public void setWorkers(List<WorkerDto> workers) { this.workers = workers; }
    public List<ShiftDayDto> getShiftDays() { return shiftDays; }
    public void setShiftDays(List<ShiftDayDto> shiftDays) { this.shiftDays = shiftDays; }
    public List<ProductivityEntryDto> getEntries() { return entries; }
    public void setEntries(List<ProductivityEntryDto> entries) { this.entries = entries; }
    public List<AuditLogDto> getAuditLogs() { return auditLogs; }
    public void setAuditLogs(List<AuditLogDto> auditLogs) { this.auditLogs = auditLogs; }

    public static class SyncPayloadBuilder {
        private Long lastSyncTimestamp;
        private List<WorkerDto> workers = new ArrayList<>();
        private List<ShiftDayDto> shiftDays = new ArrayList<>();
        private List<ProductivityEntryDto> entries = new ArrayList<>();
        private List<AuditLogDto> auditLogs = new ArrayList<>();

        public SyncPayloadBuilder lastSyncTimestamp(Long lastSyncTimestamp) { this.lastSyncTimestamp = lastSyncTimestamp; return this; }
        public SyncPayloadBuilder workers(List<WorkerDto> workers) { this.workers = workers; return this; }
        public SyncPayloadBuilder shiftDays(List<ShiftDayDto> shiftDays) { this.shiftDays = shiftDays; return this; }
        public SyncPayloadBuilder entries(List<ProductivityEntryDto> entries) { this.entries = entries; return this; }
        public SyncPayloadBuilder auditLogs(List<AuditLogDto> auditLogs) { this.auditLogs = auditLogs; return this; }

        public SyncPayload build() {
            return new SyncPayload(lastSyncTimestamp, workers, shiftDays, entries, auditLogs);
        }
    }

    public static class WorkerDto {
        private String id;
        private String name;
        @JsonProperty("isActive")
        private Boolean isActive;
        @JsonProperty("isDeleted")
        private Boolean isDeleted;
        private Long createdAt;

        public WorkerDto() {}
        public WorkerDto(String id, String name, Boolean isActive, Boolean isDeleted, Long createdAt) {
            this.id = id;
            this.name = name;
            this.isActive = isActive;
            this.isDeleted = isDeleted;
            this.createdAt = createdAt;
        }
        public static WorkerDtoBuilder builder() { return new WorkerDtoBuilder(); }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }
        public Boolean getIsDeleted() { return isDeleted; }
        public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
        public Long getCreatedAt() { return createdAt; }
        public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }

        public static class WorkerDtoBuilder {
            private String id;
            private String name;
            private Boolean isActive;
            private Boolean isDeleted;
            private Long createdAt;
            public WorkerDtoBuilder id(String id) { this.id = id; return this; }
            public WorkerDtoBuilder name(String name) { this.name = name; return this; }
            public WorkerDtoBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
            public WorkerDtoBuilder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }
            public WorkerDtoBuilder createdAt(Long createdAt) { this.createdAt = createdAt; return this; }
            public WorkerDto build() { return new WorkerDto(id, name, isActive, isDeleted, createdAt); }
        }
    }

    public static class ShiftDayDto {
        private String date;
        private String notes;
        private Long createdAt;
        @JsonProperty("isClosed")
        private Boolean isClosed;

        public ShiftDayDto() {}
        public ShiftDayDto(String date, String notes, Long createdAt, Boolean isClosed) {
            this.date = date;
            this.notes = notes;
            this.createdAt = createdAt;
            this.isClosed = isClosed;
        }
        public static ShiftDayDtoBuilder builder() { return new ShiftDayDtoBuilder(); }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
        public Long getCreatedAt() { return createdAt; }
        public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
        public Boolean getIsClosed() { return isClosed; }
        public void setIsClosed(Boolean isClosed) { this.isClosed = isClosed; }

        public static class ShiftDayDtoBuilder {
            private String date;
            private String notes;
            private Long createdAt;
            private Boolean isClosed;
            public ShiftDayDtoBuilder date(String date) { this.date = date; return this; }
            public ShiftDayDtoBuilder notes(String notes) { this.notes = notes; return this; }
            public ShiftDayDtoBuilder createdAt(Long createdAt) { this.createdAt = createdAt; return this; }
            public ShiftDayDtoBuilder isClosed(Boolean isClosed) { this.isClosed = isClosed; return this; }
            public ShiftDayDto build() { return new ShiftDayDto(date, notes, createdAt, isClosed); }
        }
    }

    public static class ProductivityEntryDto {
        private Long id;
        private String date;
        private String workerId;
        private Double kg;
        private String hourString;
        private Long timestamp;

        public ProductivityEntryDto() {}
        public ProductivityEntryDto(Long id, String date, String workerId, Double kg, String hourString, Long timestamp) {
            this.id = id;
            this.date = date;
            this.workerId = workerId;
            this.kg = kg;
            this.hourString = hourString;
            this.timestamp = timestamp;
        }
        public static ProductivityEntryDtoBuilder builder() { return new ProductivityEntryDtoBuilder(); }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
        public String getWorkerId() { return workerId; }
        public void setWorkerId(String workerId) { this.workerId = workerId; }
        public Double getKg() { return kg; }
        public void setKg(Double kg) { this.kg = kg; }
        public String getHourString() { return hourString; }
        public void setHourString(String hourString) { this.hourString = hourString; }
        public Long getTimestamp() { return timestamp; }
        public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

        public static class ProductivityEntryDtoBuilder {
            private Long id;
            private String date;
            private String workerId;
            private Double kg;
            private String hourString;
            private Long timestamp;
            public ProductivityEntryDtoBuilder id(Long id) { this.id = id; return this; }
            public ProductivityEntryDtoBuilder date(String date) { this.date = date; return this; }
            public ProductivityEntryDtoBuilder workerId(String workerId) { this.workerId = workerId; return this; }
            public ProductivityEntryDtoBuilder kg(Double kg) { this.kg = kg; return this; }
            public ProductivityEntryDtoBuilder hourString(String hourString) { this.hourString = hourString; return this; }
            public ProductivityEntryDtoBuilder timestamp(Long timestamp) { this.timestamp = timestamp; return this; }
            public ProductivityEntryDto build() { return new ProductivityEntryDto(id, date, workerId, kg, hourString, timestamp); }
        }
    }

    public static class AuditLogDto {
        private Long id;
        private Long timestamp;
        private String userId;
        private String userName;
        private String userRole;
        private String action;
        private String targetId;
        private String details;

        public AuditLogDto() {}
        public AuditLogDto(Long id, Long timestamp, String userId, String userName, String userRole, String action, String targetId, String details) {
            this.id = id;
            this.timestamp = timestamp;
            this.userId = userId;
            this.userName = userName;
            this.userRole = userRole;
            this.action = action;
            this.targetId = targetId;
            this.details = details;
        }
        public static AuditLogDtoBuilder builder() { return new AuditLogDtoBuilder(); }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
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

        public static class AuditLogDtoBuilder {
            private Long id;
            private Long timestamp;
            private String userId;
            private String userName;
            private String userRole;
            private String action;
            private String targetId;
            private String details;
            public AuditLogDtoBuilder id(Long id) { this.id = id; return this; }
            public AuditLogDtoBuilder timestamp(Long timestamp) { this.timestamp = timestamp; return this; }
            public AuditLogDtoBuilder userId(String userId) { this.userId = userId; return this; }
            public AuditLogDtoBuilder userName(String userName) { this.userName = userName; return this; }
            public AuditLogDtoBuilder userRole(String userRole) { this.userRole = userRole; return this; }
            public AuditLogDtoBuilder action(String action) { this.action = action; return this; }
            public AuditLogDtoBuilder targetId(String targetId) { this.targetId = targetId; return this; }
            public AuditLogDtoBuilder details(String details) { this.details = details; return this; }
            public AuditLogDto build() { return new AuditLogDto(id, timestamp, userId, userName, userRole, action, targetId, details); }
        }
    }
}
