package com.example.demo.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema
@Data
@ToString
public class DocumentPermissionRequest {

    @Schema(description = "文件ID")
    private String documentId;
    @Schema(description = "使用者ID")
    private String userId;
    @Schema(description = "是否可瀏覽")
    private boolean canView;
    @Schema(description = "是否可編輯")
    private boolean canEdit;
}
