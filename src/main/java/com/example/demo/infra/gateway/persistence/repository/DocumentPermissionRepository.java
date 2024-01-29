package com.example.demo.infra.gateway.persistence.repository;

import com.example.demo.infra.gateway.persistence.model.DocumentPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentPermissionRepository extends JpaRepository<DocumentPermissionEntity, String> {

    @Query(value = "SELECT COUNT(*) > 0 FROM document_permission WHERE document_id = ?1 AND user_id = ?2 AND can_view = true ", nativeQuery = true)
    Boolean canView(String documentId, String userId);

    @Query(value = "SELECT COUNT(*) > 0 FROM document_permission WHERE document_id = ?1 AND user_id = ?2 AND can_edit = true ", nativeQuery = true)
    Boolean canEdit(String documentId, String userId);
}
