package com.bwango.harvestsyncbackend.service;

import com.bwango.harvestsyncbackend.dto.SyncPayload;
import com.bwango.harvestsyncbackend.entity.AuditLogEntity;
import com.bwango.harvestsyncbackend.entity.ProductivityEntryEntity;
import com.bwango.harvestsyncbackend.entity.ShiftDayEntity;
import com.bwango.harvestsyncbackend.entity.WorkerEntity;
import com.bwango.harvestsyncbackend.repository.AuditLogRepository;
import com.bwango.harvestsyncbackend.repository.ProductivityEntryRepository;
import com.bwango.harvestsyncbackend.repository.ShiftDayRepository;
import com.bwango.harvestsyncbackend.repository.WorkerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SyncService {

    private final WorkerRepository workerRepository;
    private final ShiftDayRepository shiftDayRepository;
    private final ProductivityEntryRepository entryRepository;
    private final AuditLogRepository auditLogRepository;

    public SyncService(WorkerRepository workerRepository, ShiftDayRepository shiftDayRepository, ProductivityEntryRepository entryRepository, AuditLogRepository auditLogRepository) {
        this.workerRepository = workerRepository;
        this.shiftDayRepository = shiftDayRepository;
        this.entryRepository = entryRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public SyncPayload processSync(SyncPayload clientPayload) {
        long currentServerTime = Instant.now().toEpochMilli();
        long clientLastSync = (clientPayload.getLastSyncTimestamp() != null)
                ? clientPayload.getLastSyncTimestamp()
                : 0L;

        // Track incoming primary keys to prevent echo-back to the caller
        Set<String> pushedWorkerIds = new HashSet<>();
        Set<String> pushedShiftDayDates = new HashSet<>();
        Set<String> pushedEntryCompositeKeys = new HashSet<>();
        Set<String> pushedAuditLogCompositeKeys = new HashSet<>();

        // ========================================================
        // 1. PUSH PHASE: Process & Merge Client Data
        // ========================================================

        // 1.1 Merge Workers (Key: id)
        if (clientPayload.getWorkers() != null) {
            for (SyncPayload.WorkerDto dto : clientPayload.getWorkers()) {
                if (dto.getId() == null) continue;
                pushedWorkerIds.add(dto.getId());

                Optional<WorkerEntity> existingOpt = workerRepository.findById(dto.getId());
                if (existingOpt.isEmpty()) {
                    workerRepository.save(WorkerEntity.builder()
                            .id(dto.getId())
                            .name(dto.getName())
                            .isActive(Boolean.TRUE.equals(dto.getIsActive()))
                            .isDeleted(Boolean.TRUE.equals(dto.getIsDeleted()))
                            .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : currentServerTime)
                            .updatedAt(currentServerTime)
                            .build());
                } else {
                    WorkerEntity existing = existingOpt.get();
                    // Merge rule: Overwrite if client createdAt is equal or newer
                    if (dto.getCreatedAt() == null || dto.getCreatedAt() >= existing.getCreatedAt()) {
                        existing.setName(dto.getName());
                        existing.setIsActive(Boolean.TRUE.equals(dto.getIsActive()));
                        existing.setIsDeleted(Boolean.TRUE.equals(dto.getIsDeleted()));
                        existing.setUpdatedAt(currentServerTime);
                        workerRepository.save(existing);
                    }
                }
            }
        }

        // 1.2 Merge Shift Days (Key: date)
        if (clientPayload.getShiftDays() != null) {
            for (SyncPayload.ShiftDayDto dto : clientPayload.getShiftDays()) {
                if (dto.getDate() == null) continue;
                pushedShiftDayDates.add(dto.getDate());

                Optional<ShiftDayEntity> existingOpt = shiftDayRepository.findById(dto.getDate());
                if (existingOpt.isEmpty()) {
                    shiftDayRepository.save(ShiftDayEntity.builder()
                            .date(dto.getDate())
                            .notes(dto.getNotes())
                            .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : currentServerTime)
                            .isClosed(Boolean.TRUE.equals(dto.getIsClosed()))
                            .updatedAt(currentServerTime)
                            .build());
                } else {
                    ShiftDayEntity existing = existingOpt.get();
                    if (dto.getCreatedAt() == null || dto.getCreatedAt() >= existing.getCreatedAt()) {
                        existing.setNotes(dto.getNotes());
                        existing.setIsClosed(Boolean.TRUE.equals(dto.getIsClosed()));
                        existing.setUpdatedAt(currentServerTime);
                        shiftDayRepository.save(existing);
                    }
                }
            }
        }

        // 1.3 Merge Productivity Entries (Key: workerId + timestamp to prevent ID collisions)
        if (clientPayload.getEntries() != null) {
            for (SyncPayload.ProductivityEntryDto dto : clientPayload.getEntries()) {
                if (dto.getWorkerId() == null || dto.getTimestamp() == null) continue;
                String compositeKey = dto.getWorkerId() + "#" + dto.getTimestamp();
                pushedEntryCompositeKeys.add(compositeKey);

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

        // 1.4 Merge Audit Logs (Key: userId + timestamp)
        if (clientPayload.getAuditLogs() != null) {
            for (SyncPayload.AuditLogDto dto : clientPayload.getAuditLogs()) {
                if (dto.getUserId() == null || dto.getTimestamp() == null) continue;
                String compositeKey = dto.getUserId() + "#" + dto.getTimestamp();
                pushedAuditLogCompositeKeys.add(compositeKey);

                Optional<AuditLogEntity> existingOpt =
                        auditLogRepository.findByUserIdAndTimestamp(dto.getUserId(), dto.getTimestamp());

                if (existingOpt.isEmpty()) {
                    auditLogRepository.save(AuditLogEntity.builder()
                            .clientLocalId(dto.getId())
                            .timestamp(dto.getTimestamp())
                            .userId(dto.getUserId())
                            .userName(dto.getUserName())
                            .userRole(dto.getUserRole())
                            .action(dto.getAction())
                            .targetId(dto.getTargetId())
                            .details(dto.getDetails())
                            .updatedAt(currentServerTime)
                            .build());
                } else {
                    AuditLogEntity existing = existingOpt.get();
                    existing.setUserName(dto.getUserName());
                    existing.setUserRole(dto.getUserRole());
                    existing.setAction(dto.getAction());
                    existing.setTargetId(dto.getTargetId());
                    existing.setDetails(dto.getDetails());
                    existing.setUpdatedAt(currentServerTime);
                    auditLogRepository.save(existing);
                }
            }
        }

        // Flush dirty writes to MariaDB before running query
        workerRepository.flush();
        shiftDayRepository.flush();
        entryRepository.flush();
        auditLogRepository.flush();

        // ========================================================
        // 2. PULL PHASE: Query updates made after clientLastSync
        //    (excluding entities pushed by client in this batch)
        // ========================================================

        List<SyncPayload.WorkerDto> pullWorkers = workerRepository
                .findByUpdatedAtGreaterThan(clientLastSync)
                .stream()
                .filter(w -> !pushedWorkerIds.contains(w.getId()))
                .map(w -> SyncPayload.WorkerDto.builder()
                        .id(w.getId())
                        .name(w.getName())
                        .isActive(w.getIsActive())
                        .isDeleted(w.getIsDeleted())
                        .createdAt(w.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        List<SyncPayload.ShiftDayDto> pullShiftDays = shiftDayRepository
                .findByUpdatedAtGreaterThan(clientLastSync)
                .stream()
                .filter(s -> !pushedShiftDayDates.contains(s.getDate()))
                .map(s -> SyncPayload.ShiftDayDto.builder()
                        .date(s.getDate())
                        .notes(s.getNotes())
                        .createdAt(s.getCreatedAt())
                        .isClosed(s.getIsClosed())
                        .build())
                .collect(Collectors.toList());

        List<SyncPayload.ProductivityEntryDto> pullEntries = entryRepository
                .findByUpdatedAtGreaterThan(clientLastSync)
                .stream()
                .filter(e -> !pushedEntryCompositeKeys.contains(e.getWorkerId() + "#" + e.getTimestamp()))
                .map(e -> SyncPayload.ProductivityEntryDto.builder()
                        .id(e.getClientLocalId())
                        .date(e.getDate())
                        .workerId(e.getWorkerId())
                        .kg(e.getKg())
                        .hourString(e.getHourString())
                        .timestamp(e.getTimestamp())
                        .build())
                .collect(Collectors.toList());

        List<SyncPayload.AuditLogDto> pullAuditLogs = auditLogRepository
                .findByUpdatedAtGreaterThan(clientLastSync)
                .stream()
                .filter(a -> !pushedAuditLogCompositeKeys.contains(a.getUserId() + "#" + a.getTimestamp()))
                .map(a -> SyncPayload.AuditLogDto.builder()
                        .id(a.getClientLocalId())
                        .timestamp(a.getTimestamp())
                        .userId(a.getUserId())
                        .userName(a.getUserName())
                        .userRole(a.getUserRole())
                        .action(a.getAction())
                        .targetId(a.getTargetId())
                        .details(a.getDetails())
                        .build())
                .collect(Collectors.toList());

        // ========================================================
        // 3. RETURN RESPONSE
        // ========================================================
        return SyncPayload.builder()
                .lastSyncTimestamp(currentServerTime)
                .workers(pullWorkers)
                .shiftDays(pullShiftDays)
                .entries(pullEntries)
                .auditLogs(pullAuditLogs)
                .build();
    }
}
