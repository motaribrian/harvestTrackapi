package com.bwango.harvestsyncbackend.repository;

import com.bwango.harvestsyncbackend.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
    List<AuditLogEntity> findByUpdatedAtGreaterThan(Long updatedAt);
    Optional<AuditLogEntity> findByUserIdAndTimestamp(String userId, Long timestamp);
}
