package com.heliant.my_app.mapper;

import com.heliant.my_app.dto.DocumentFieldRequest;
import com.heliant.my_app.dto.DocumentFieldResponse;
import com.heliant.my_app.model.Document;
import com.heliant.my_app.model.DocumentField;
import com.heliant.my_app.repository.DocumentRepository;
import com.heliant.my_app.util.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DocumentFieldMapper {

    private DocumentRepository documentRepository;

    public DocumentFieldMapper(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentFieldResponse mapToDTO(final DocumentField documentField) {
        DocumentFieldResponse documentFieldResponse = new DocumentFieldResponse();
        documentFieldResponse.setId(documentField.getId());
        documentFieldResponse.setName(documentField.getName());
        documentFieldResponse.setOrderNumber(documentField.getOrderNumber());
        documentFieldResponse.setType(documentField.getType());
        documentFieldResponse.setDocument(documentField.getDocument() == null ? null : documentField.getDocument().getId());
        return documentFieldResponse;
    }

    public DocumentField mapToEntity(final DocumentFieldRequest documentFieldRequest) {
        DocumentField documentField = new DocumentField();
        documentField.setName(documentFieldRequest.getName());
        documentField.setOrderNumber(documentFieldRequest.getOrderNumber());
        documentField.setType(documentFieldRequest.getType());
        final Document document = documentFieldRequest.getDocument() == null ? null : documentRepository.findById(documentFieldRequest.getDocument())
                .orElseThrow(() -> new NotFoundException("Document not found"));
        documentField.setDocument(document);
        return documentField;
    }
}