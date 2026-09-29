package com.bwango.harvestsyncbackend.entity;

import jakarta.persistence.*;
import lombok.*;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
