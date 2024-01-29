package com.example.demo.infra.gateway.persistence.model.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class DocumentPermissionEntityPK implements Serializable {


    @Column(name = "document_id", nullable = false, length = 50)
    private String documentId;
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    public DocumentPermissionEntityPK() {
        super();
    }

    public DocumentPermissionEntityPK(String documentId, String userId) {
        super();
        this.documentId = documentId;
        this.userId = userId;
    }
}
