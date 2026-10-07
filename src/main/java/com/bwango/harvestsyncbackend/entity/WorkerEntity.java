package com.bwango.harvestsyncbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "workers", indexes = {@Index(name = "idx_worker_updated_at", columnList = "updatedAt")})
public class WorkerEntity {

    @Id
    @Column(nullable = false, length = 64)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    public WorkerEntity() {}

    public WorkerEntity(String id, String name, Boolean isActive, Boolean isDeleted, Long createdAt, Long updatedAt) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static WorkerEntityBuilder builder() {
        return new WorkerEntityBuilder();
    }

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
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    public static class WorkerEntityBuilder {
        private String id;
        private String name;
        private Boolean isActive;
        private Boolean isDeleted;
        private Long createdAt;
        private Long updatedAt;

        public WorkerEntityBuilder id(String id) { this.id = id; return this; }
        public WorkerEntityBuilder name(String name) { this.name = name; return this; }
        public WorkerEntityBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public WorkerEntityBuilder isDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; return this; }
        public WorkerEntityBuilder createdAt(Long createdAt) { this.createdAt = createdAt; return this; }
        public WorkerEntityBuilder updatedAt(Long updatedAt) { this.updatedAt = updatedAt; return this; }

        public WorkerEntity build() {
            return new WorkerEntity(id, name, isActive, isDeleted, createdAt, updatedAt);
        }
    }
}
