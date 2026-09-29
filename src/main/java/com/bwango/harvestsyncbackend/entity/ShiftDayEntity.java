package com.bwango.harvestsyncbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "shift_days")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
