package com.example.demo.domain.service;

import com.example.demo.common.error.ErrorCode;
import com.example.demo.common.exception.ActionRuntimeException;
import com.example.demo.domain.service.dto.DocumentInfoDto;
import com.example.demo.infra.gateway.persistence.model.DocumentInfoEntity;
import com.example.demo.infra.gateway.persistence.model.DocumentPermissionEntity;
import com.example.demo.infra.gateway.persistence.repository.DocumentInfoRepository;
import com.example.demo.infra.gateway.persistence.repository.DocumentPermissionRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Resource
    private DocumentInfoRepository documentInfoRepository;
    @Resource
    private DocumentPermissionRepository documentPermissionRepository;

    @Override
    public void createDocument(String actionUserId, DocumentInfoDto documentInfoDto) {
        documentPermissionRepository.save(new DocumentPermissionEntity(actionUserId, documentInfoDto.getDocumentId(), true, true));
        documentInfoRepository.save(new DocumentInfoEntity(documentInfoDto));
    }

    @Override
    public void deleteDocument(String actionUserId, String documentId) {
        if (!documentPermissionRepository.canEdit(documentId, actionUserId)) {
            throw new ActionRuntimeException(ErrorCode.DOCUMENT_PERMISSION_DENIED, "You don't have permission to delete this document");
        }
        documentInfoRepository.deleteById(documentId);
    }

    @Override
    public DocumentInfoDto getDocument(String actionUserId, String documentId) {
        if (!documentPermissionRepository.canView(documentId, actionUserId)) {
            throw new ActionRuntimeException(ErrorCode.DOCUMENT_PERMISSION_DENIED, "You don't have permission to view this document");
        }
        return documentInfoRepository.findById(documentId).map(DocumentInfoDto::new).orElse(null);
    }


    @Override
    public Page<DocumentInfoDto> getDocuments(String actionUserId, int page, int pageSize) {
        return documentInfoRepository.findAllByUserId(actionUserId, PageRequest.of(page, pageSize)).map(DocumentInfoDto::new);
    }

    @Override
    public void updateDocument(String actionUserId, DocumentInfoDto documentInfoDto) {
        if (!documentPermissionRepository.canEdit(documentInfoDto.getDocumentId(), actionUserId)) {
            throw new ActionRuntimeException(ErrorCode.DOCUMENT_PERMISSION_DENIED, "You don't have permission to update this document");
        }
        documentInfoRepository.save(new DocumentInfoEntity(documentInfoDto));
    }
}
