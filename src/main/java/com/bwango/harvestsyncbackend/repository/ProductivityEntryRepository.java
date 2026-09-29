package com.bwango.harvestsyncbackend.repository;

import com.bwango.harvestsyncbackend.entity.ProductivityEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductivityEntryRepository extends JpaRepository<ProductivityEntryEntity, Long> {
    Optional<ProductivityEntryEntity> findByWorkerIdAndTimestamp(String workerId, Long timestamp);
    List<ProductivityEntryEntity> findByUpdatedAtGreaterThan(Long lastSyncTimestamp);
}
