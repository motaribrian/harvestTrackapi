package com.bwango.harvestsyncbackend.model;

import java.util.Set;

import static com.bwango.harvestsyncbackend.model.Permission.*;

public enum Role {
    SUPER_ADMIN(Set.of(
            LOG_PRODUCTIVITY, EDIT_TODAY_ENTRY, DELETE_ENTRY,
            VIEW_WORKERS, REGISTER_WORKER, DELETE_WORKER,
            VIEW_ANALYTICS, EXPORT_DATA, VIEW_AUDIT_LOGS, MANAGE_USERS
    )),
    ADMIN(Set.of(
            LOG_PRODUCTIVITY, EDIT_TODAY_ENTRY, DELETE_ENTRY,
            VIEW_WORKERS, REGISTER_WORKER, DELETE_WORKER,
            VIEW_ANALYTICS, EXPORT_DATA, VIEW_AUDIT_LOGS
    )),
    SUPERVISOR(Set.of(
            LOG_PRODUCTIVITY, EDIT_TODAY_ENTRY, DELETE_ENTRY,
            VIEW_WORKERS, REGISTER_WORKER
    )),
    DATA_CLERK(Set.of(
            LOG_PRODUCTIVITY, VIEW_WORKERS
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
