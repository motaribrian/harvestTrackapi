package com.bwango.harvestsyncbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shift_days", indexes = {@Index(name = "idx_shift_updated_at", columnList = "updatedAt")})
public class ShiftDayEntity {

    @Id
    @Column(nullable = false, length = 10) // e.g., "YYYY-MM-DD"
    private String date;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Boolean isClosed;

    @Column(nullable = false)
    private Long updatedAt;

    public ShiftDayEntity() {}

    public ShiftDayEntity(String date, String notes, Long createdAt, Boolean isClosed, Long updatedAt) {
        this.date = date;
        this.notes = notes;
        this.createdAt = createdAt;
        this.isClosed = isClosed;
        this.updatedAt = updatedAt;
    }

    public static ShiftDayEntityBuilder builder() {
        return new ShiftDayEntityBuilder();
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Long getCreatedAt() { return createdAt; }
    public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
    public Boolean getIsClosed() { return isClosed; }
    public void setIsClosed(Boolean isClosed) { this.isClosed = isClosed; }
    public Long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Long updatedAt) { this.updatedAt = updatedAt; }

    public static class ShiftDayEntityBuilder {
        private String date;
        private String notes;
        private Long createdAt;
        private Boolean isClosed;
        private Long updatedAt;

        public ShiftDayEntityBuilder date(String date) { this.date = date; return this; }
        public ShiftDayEntityBuilder notes(String notes) { this.notes = notes; return this; }
        public ShiftDayEntityBuilder createdAt(Long createdAt) { this.createdAt = createdAt; return this; }
        public ShiftDayEntityBuilder isClosed(Boolean isClosed) { this.isClosed = isClosed; return this; }
        public ShiftDayEntityBuilder updatedAt(Long updatedAt) { this.updatedAt = updatedAt; return this; }

        public ShiftDayEntity build() {
            return new ShiftDayEntity(date, notes, createdAt, isClosed, updatedAt);
        }
    }
}
