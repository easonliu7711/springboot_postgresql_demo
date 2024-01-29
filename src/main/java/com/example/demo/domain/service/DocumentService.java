package com.example.demo.domain.service;

import com.example.demo.domain.service.dto.DocumentInfoDto;
import org.springframework.data.domain.Page;

public interface DocumentService {
    void createDocument(String actionUserId, DocumentInfoDto documentInfoDto);

    void deleteDocument(String actionUserId, String documentId);

    DocumentInfoDto getDocument(String actionUserId, String documentId);

    Page<DocumentInfoDto> getDocuments(String actionUserId, int page, int pageSize);

    void updateDocument(String actionUserId, DocumentInfoDto documentInfoDto);
}
