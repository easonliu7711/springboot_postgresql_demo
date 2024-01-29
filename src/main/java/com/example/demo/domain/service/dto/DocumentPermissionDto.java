package com.example.demo.domain.service.dto;

import com.example.demo.application.controller.request.DocumentPermissionRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema
@Data
@ToString
public class DocumentPermissionDto {

    @Schema(description = "文件ID")
    private String documentId;
    @Schema(description = "使用者ID")
    private String userId;
    @Schema(description = "是否可瀏覽")
    private boolean canView;
    @Schema(description = "是否可編輯")
    private boolean canEdit;

    public DocumentPermissionDto(DocumentPermissionRequest documentPermissionRequest) {
        this.documentId = documentPermissionRequest.getDocumentId();
        this.userId = documentPermissionRequest.getUserId();
        this.canView = documentPermissionRequest.isCanView();
        this.canEdit = documentPermissionRequest.isCanEdit();
    }
}
