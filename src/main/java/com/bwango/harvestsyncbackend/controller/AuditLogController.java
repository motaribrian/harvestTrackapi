package com.bwango.harvestsyncbackend.controller;

import com.bwango.harvestsyncbackend.dto.SyncPayload;
import com.bwango.harvestsyncbackend.repository.AuditLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/audit-logs")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    public AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<List<SyncPayload.AuditLogDto>> getAuditLogs() {
        List<SyncPayload.AuditLogDto> logs = auditLogRepository.findAll().stream()
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
        return ResponseEntity.ok(logs);
    }
}
