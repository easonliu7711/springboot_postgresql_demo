package com.example.demo.domain.service.dto;

import com.example.demo.application.controller.request.CreateDocumentInfoRequest;
import com.example.demo.application.controller.request.UpdateDocumentInfoRequest;
import com.example.demo.infra.gateway.persistence.model.DocumentInfoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema
@Data
@ToString
public class DocumentInfoDto {

    @Schema(description = "文件ID")
    private String documentId;
    @Schema(description = "文件標題")
    private String title;
    @Schema(description = "文件內容")
    private String content;
    @Schema(description = "文件建立時間")
    private LocalDateTime createTime;
    @Schema(description = "文件更新時間")
    private LocalDateTime updateTime;

    public DocumentInfoDto(CreateDocumentInfoRequest createDocumentInfoRequest) {
        this.documentId = UUID.randomUUID().toString();
        this.title = createDocumentInfoRequest.getTitle();
        this.content = createDocumentInfoRequest.getContent();
    }

    public DocumentInfoDto(UpdateDocumentInfoRequest updateDocumentInfoRequest) {
        this.documentId = updateDocumentInfoRequest.getDocumentId();
        this.title = updateDocumentInfoRequest.getTitle();
        this.content = updateDocumentInfoRequest.getContent();
    }

    public DocumentInfoDto(DocumentInfoEntity documentInfoEntity) {
        this.documentId = documentInfoEntity.getDocumentId();
        this.title = documentInfoEntity.getTitle();
        this.content = documentInfoEntity.getContent();
        this.createTime = documentInfoEntity.getCreateTime();
        this.updateTime = documentInfoEntity.getUpdateTime();
    }
}
