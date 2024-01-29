package com.example.demo.infra.gateway.persistence.repository;

import com.example.demo.infra.gateway.persistence.model.DocumentInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentInfoRepository extends JpaRepository<DocumentInfoEntity, String> {

    @Query(value = "SELECT l.* FROM demo.document_info l left join demo.document_permission r on l.document_id = r.document_id WHERE r.user_id = ?1 AND r.can_view = true ", nativeQuery = true)
    Page<DocumentInfoEntity> findAllByUserId(String actionUserId, PageRequest pageRequest);

}
