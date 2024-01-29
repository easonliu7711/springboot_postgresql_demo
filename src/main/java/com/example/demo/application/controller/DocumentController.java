package com.example.demo.application.controller;

import com.example.demo.application.controller.request.CreateDocumentInfoRequest;
import com.example.demo.application.controller.request.UpdateDocumentInfoRequest;
import com.example.demo.domain.service.DocumentService;
import com.example.demo.domain.service.dto.DocumentInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${server.api-base-path}")
public class DocumentController extends BaseController {

    @Resource
    private DocumentService documentService;

    @Operation(summary = "新增文件", description = "新增文件")
    @PostMapping("/v1/auth/document")
    public String createDocument(@RequestBody CreateDocumentInfoRequest createDocumentInfoRequest) {
        documentService.createDocument(getActionUserId(), new DocumentInfoDto(createDocumentInfoRequest));
        return "Success";
    }

    @Operation(summary = "刪除文件", description = "刪除文件")
    @DeleteMapping("/v1/auth/document")
    public String deleteDocument(@RequestParam String documentId) {
        documentService.deleteDocument(getActionUserId(), documentId);
        return "Success";
    }

    @Operation(summary = "獲取文件", description = "獲取文件")
    @GetMapping("/v1/auth/document")
    public DocumentInfoDto getDocument(@RequestParam String documentId) {
        return documentService.getDocument(getActionUserId(), documentId);
    }

    @Operation(summary = "獲取所有文件", description = "獲取所有文件")
    @GetMapping("/v1/auth/document/list")
    public Page<DocumentInfoDto> getDocuments(@RequestParam int page, @RequestParam int pageSize) {
        return documentService.getDocuments(getActionUserId(), page, pageSize);
    }

    @Operation(summary = "修改文件", description = "修改文件")
    @PutMapping("/v1/auth/document")
    public String updateDocument(@RequestBody UpdateDocumentInfoRequest updateDocumentInfoRequest) {
        documentService.updateDocument(getActionUserId(), new DocumentInfoDto(updateDocumentInfoRequest));
        return "Success";
    }

}
