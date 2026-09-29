package com.bwango.harvestsyncbackend.controller;

import com.bwango.harvestsyncbackend.dto.SyncPayload;
import com.bwango.harvestsyncbackend.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SyncController {

    private final SyncService syncService;

    @PostMapping(
            value = "/sync",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SyncPayload> syncData(@RequestBody SyncPayload payload) {
        SyncPayload response = syncService.processSync(payload);
        return ResponseEntity.ok(response);
    }
}
