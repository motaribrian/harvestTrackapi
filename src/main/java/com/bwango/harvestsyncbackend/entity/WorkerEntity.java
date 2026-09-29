package com.bwango.harvestsyncbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workers", indexes = {@Index(name = "idx_worker_updated_at", columnList = "updatedAt")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
