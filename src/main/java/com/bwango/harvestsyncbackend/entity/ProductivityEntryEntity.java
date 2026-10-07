package com.bwango.harvestsyncbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "productivity_entries",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_worker_timestamp", columnNames = {"workerId", "timestamp"})
        },
        indexes = {
                @Index(name = "idx_entry_updated_at", columnList = "updatedAt")
        }
)
public class ProductivityEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serverId;

    @Column(nullable = false)
    private Long clientLocalId;

    @Column(nullable = false, length = 10)
    private String date;

    @Column(nullable = false, length = 64)
    private String workerId;

    @Column(nullable = false)
    private Double kg;

    @Column(nullable = false, length = 8)
    private String hourString;

    @Column(nullable = false)
    private Long timestamp;

    @Column(nullable = false)
    private Long updatedAt;

    public ProductivityEntryEntity() {}

    public ProductivityEntryEntity(Long serverId, Long clientLocalId, String date, String workerId, Double kg, String hourString, Long timestamp, Long updatedAt) {
        this.serverId = serverId;
        this.clientLocalId = clientLocalId;
        this.date = date;
        this.workerId = workerId;
        this.kg = kg;
        this.hourString = hourString;
        this.timestamp = timestamp;
        this.updatedAt = updatedAt;
    }

    public static ProductivityEntryEntityBuilder builder() {
        return new ProductivityEntryEntityBuilder();
    }

    public Long getServerId() { return serverId; }
    public void setServerId(Long serverId) { this.serverId = serverId; }
    public Long getClientLocalId() { return clientLocalId; }
    public void setClientLocalId(Long clientLocalId) { this.clientLocalId = clientLocalId; }
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
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    public static class ProductivityEntryEntityBuilder {
        private Long serverId;
        private Long clientLocalId;
        private String date;
        private String workerId;
        private Double kg;
        private String hourString;
        private Long timestamp;
        private Long updatedAt;

        public ProductivityEntryEntityBuilder serverId(Long serverId) { this.serverId = serverId; return this; }
        public ProductivityEntryEntityBuilder clientLocalId(Long clientLocalId) { this.clientLocalId = clientLocalId; return this; }
        public ProductivityEntryEntityBuilder date(String date) { this.date = date; return this; }
        public ProductivityEntryEntityBuilder workerId(String workerId) { this.workerId = workerId; return this; }
        public ProductivityEntryEntityBuilder kg(Double kg) { this.kg = kg; return this; }
        public ProductivityEntryEntityBuilder hourString(String hourString) { this.hourString = hourString; return this; }
        public ProductivityEntryEntityBuilder timestamp(Long timestamp) { this.timestamp = timestamp; return this; }
        public ProductivityEntryEntityBuilder updatedAt(Long updatedAt) { this.updatedAt = updatedAt; return this; }

        public ProductivityEntryEntity build() {
            return new ProductivityEntryEntity(serverId, clientLocalId, date, workerId, kg, hourString, timestamp, updatedAt);
        }
    }
}
