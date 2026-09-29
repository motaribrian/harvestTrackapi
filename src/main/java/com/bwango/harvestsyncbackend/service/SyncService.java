package com.bwango.harvestsyncbackend.service;


import com.bwango.harvestsyncbackend.dto.SyncPayload;
import com.bwango.harvestsyncbackend.entity.ProductivityEntryEntity;
import com.bwango.harvestsyncbackend.entity.ShiftDayEntity;
import com.bwango.harvestsyncbackend.entity.WorkerEntity;
import com.bwango.harvestsyncbackend.repository.ProductivityEntryRepository;
import com.bwango.harvestsyncbackend.repository.ShiftDayRepository;
import com.bwango.harvestsyncbackend.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SyncService {

    private final WorkerRepository workerRepository;
    private final ShiftDayRepository shiftDayRepository;
    private final ProductivityEntryRepository entryRepository;

    @Transactional
    public SyncPayload processSync(SyncPayload clientPayload) {
        long currentServerTime = Instant.now().toEpochMilli();
        long clientLastSync = clientPayload.getLastSyncTimestamp() != null
                ? clientPayload.getLastSyncTimestamp()
                : 0L;

        // 1. Ingest & Merge Workers
        if (clientPayload.getWorkers() != null) {
            for (SyncPayload.WorkerDto dto : clientPayload.getWorkers()) {
                Optional<WorkerEntity> existingOpt = workerRepository.findById(dto.getId());
                if (existingOpt.isEmpty()) {
                    workerRepository.save(WorkerEntity.builder()
                            .id(dto.getId())
                            .name(dto.getName())
                            .isActive(dto.getIsActive())
                            .isDeleted(dto.getIsDeleted())
                            .createdAt(dto.getCreatedAt())
                            .updatedAt(currentServerTime)
                            .build());
                } else {
                    WorkerEntity existing = existingOpt.get();
                    if (dto.getCreatedAt() != null && dto.getCreatedAt() >= existing.getCreatedAt()) {
                        existing.setName(dto.getName());
                        existing.setIsActive(dto.getIsActive());
                        existing.setIsDeleted(dto.getIsDeleted());
                        existing.setUpdatedAt(currentServerTime);
                        workerRepository.save(existing);
                    }
                }
            }
        }

        // 2. Ingest & Merge Shift Days
        if (clientPayload.getShiftDays() != null) {
            for (SyncPayload.ShiftDayDto dto : clientPayload.getShiftDays()) {
                Optional<ShiftDayEntity> existingOpt = shiftDayRepository.findById(dto.getDate());
                if (existingOpt.isEmpty()) {
                    shiftDayRepository.save(ShiftDayEntity.builder()
                            .date(dto.getDate())
                            .notes(dto.getNotes())
                            .createdAt(dto.getCreatedAt())
                            .isClosed(dto.getIsClosed())
                            .updatedAt(currentServerTime)
                            .build());
                } else {
                    ShiftDayEntity existing = existingOpt.get();
                    if (dto.getCreatedAt() != null && dto.getCreatedAt() >= existing.getCreatedAt()) {
                        existing.setNotes(dto.getNotes());
                        existing.setIsClosed(dto.getIsClosed());
                        existing.setUpdatedAt(currentServerTime);
                        shiftDayRepository.save(existing);
                    }
                }
            }
        }

        // 3. Ingest & Merge Productivity Entries (Deduplication via workerId + timestamp)
        if (clientPayload.getEntries() != null) {
            for (SyncPayload.ProductivityEntryDto dto : clientPayload.getEntries()) {
                Optional<ProductivityEntryEntity> existingOpt =
                        entryRepository.findByWorkerIdAndTimestamp(dto.getWorkerId(), dto.getTimestamp());

                if (existingOpt.isEmpty()) {
                    entryRepository.save(ProductivityEntryEntity.builder()
                            .clientLocalId(dto.getId())
                            .date(dto.getDate())
                            .workerId(dto.getWorkerId())
                            .kg(dto.getKg())
                            .hourString(dto.getHourString())
                            .timestamp(dto.getTimestamp())
                            .updatedAt(currentServerTime)
                            .build());
                } else {
                    ProductivityEntryEntity existing = existingOpt.get();
                    existing.setDate(dto.getDate());
                    existing.setKg(dto.getKg());
                    existing.setHourString(dto.getHourString());
                    existing.setUpdatedAt(currentServerTime);
                    entryRepository.save(existing);
                }
            }
        }

        // Flush updates before querying to ensure dirty writes are accounted for
        workerRepository.flush();
        shiftDayRepository.flush();
        entryRepository.flush();

        // 4. Query changes that occurred after client's lastSyncTimestamp
        List<SyncPayload.WorkerDto> changedWorkers = workerRepository
                .findByUpdatedAtGreaterThan(clientLastSync)
                .stream()
                .map(w -> SyncPayload.WorkerDto.builder()
                        .id(w.getId())
                        .name(w.getName())
                        .isActive(w.getIsActive())
                        .isDeleted(w.getIsDeleted())
                        .createdAt(w.getCreatedAt())
                        .build())
                .toList();

        List<SyncPayload.ShiftDayDto> changedShiftDays = shiftDayRepository
                .findByUpdatedAtGreaterThan(clientLastSync)
                .stream()
                .map(s -> SyncPayload.ShiftDayDto.builder()
                        .date(s.getDate())
                        .notes(s.getNotes())
                        .createdAt(s.getCreatedAt())
                        .isClosed(s.getIsClosed())
                        .build())
                .toList();

        List<SyncPayload.ProductivityEntryDto> changedEntries = entryRepository
                .findByUpdatedAtGreaterThan(clientLastSync)
                .stream()
                .map(e -> SyncPayload.ProductivityEntryDto.builder()
                        .id(e.getClientLocalId())
                        .date(e.getDate())
                        .workerId(e.getWorkerId())
                        .kg(e.getKg())
                        .hourString(e.getHourString())
                        .timestamp(e.getTimestamp())
                        .build())
                .toList();

        // 5. Construct Server Payload
        return SyncPayload.builder()
                .lastSyncTimestamp(currentServerTime)
                .workers(changedWorkers)
                .shiftDays(changedShiftDays)
                .entries(changedEntries)
                .build();
    }
}
