package com.bwango.harvestsyncbackend.repository;

import com.bwango.harvestsyncbackend.entity.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkerRepository extends JpaRepository<WorkerEntity, String> {
    List<WorkerEntity> findByUpdatedAtGreaterThan(Long lastSyncTimestamp);
}
