package com.bwango.harvestsyncbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncPayload {

    private Long lastSyncTimestamp;

    @Builder.Default
    private List<WorkerDto> workers = new ArrayList<>();

    @Builder.Default
    private List<ShiftDayDto> shiftDays = new ArrayList<>();

    @Builder.Default
    private List<ProductivityEntryDto> entries = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WorkerDto {
        private String id;
        private String name;
        @JsonProperty("isActive")
        private Boolean isActive;
        @JsonProperty("isDeleted")
        private Boolean isDeleted;
        private Long createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShiftDayDto {
        private String date;
        private String notes;
        private Long createdAt;
        @JsonProperty("isClosed")
        private Boolean isClosed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductivityEntryDto {
        private Long id;
        private String date;
        private String workerId;
        private Double kg;
        private String hourString;
        private Long timestamp;
    }
}
