package com.heliant.my_app.model.listeners;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class EntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        if (entity instanceof Auditable) {
            Auditable auditable = (Auditable) entity;
            auditable.setCreatedBy(UserContextHolder.getUser());
            auditable.setModifiedBy(UserContextHolder.getUser());
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Auditable) {
            Auditable auditable = (Auditable) entity;
            auditable.setModifiedBy(UserContextHolder.getUser());
        }
    }
}
