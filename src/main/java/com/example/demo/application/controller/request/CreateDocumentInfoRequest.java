package com.example.demo.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema
@Data
@ToString
public class CreateDocumentInfoRequest {

        @Schema(description = "文件標題")
        private String title;
        @Schema(description = "文件內容")
        private String content;
}
