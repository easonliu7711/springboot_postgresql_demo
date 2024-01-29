package com.example.demo.infra.gateway.persistence.model;

import com.example.demo.domain.service.dto.DocumentInfoDto;
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
@Table(name = "document_info")
public class DocumentInfoEntity {
    @Id
    @Column(name = "document_id", nullable = false, length = 50)
    private String documentId;
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Column(name = "content")
    private String content;
    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    public DocumentInfoEntity(DocumentInfoDto documentInfoDto) {
        this.documentId = documentInfoDto.getDocumentId();
        this.title = documentInfoDto.getTitle();
        this.content = documentInfoDto.getContent();
    }

    public DocumentInfoEntity() {

    }
}
