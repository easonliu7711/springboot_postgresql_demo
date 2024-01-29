package com.example.demo.infra.gateway.persistence.model;

import com.example.demo.infra.gateway.persistence.model.pk.DocumentPermissionEntityPK;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "document_permission")
public class DocumentPermissionEntity {

    @EmbeddedId
    private DocumentPermissionEntityPK documentPermissionEntityPK;
    @Column(name = "can_view", nullable = false)
    private boolean canView;
    @Column(name = "can_edit", nullable = false)
    private boolean canEdit;
    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    public DocumentPermissionEntity(String actionUserId, String documentId, boolean canView, boolean canEdit) {
        this.documentPermissionEntityPK = new DocumentPermissionEntityPK(documentId, actionUserId);
        this.canView = canView;
        this.canEdit = canEdit;
    }

    public DocumentPermissionEntity() {

    }
}
