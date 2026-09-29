package com.bwango.harvestsyncbackend.repository;

import com.bwango.harvestsyncbackend.entity.ShiftDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShiftDayRepository extends JpaRepository<ShiftDayEntity, String> {
    List<ShiftDayEntity> findByUpdatedAtGreaterThan(Long lastSyncTimestamp);
}
