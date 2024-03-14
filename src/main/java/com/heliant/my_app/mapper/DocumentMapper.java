package com.heliant.my_app.mapper;

import com.heliant.my_app.dto.DocumentRequest;
import com.heliant.my_app.dto.DocumentResponse;
import com.heliant.my_app.model.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public DocumentMapper() {
    }

    public DocumentResponse mapToDTO(final Document document) {
        DocumentResponse documentResponse = new DocumentResponse();
        documentResponse.setId(document.getId());
        documentResponse.setName(document.getName());
        return documentResponse;
    }

    public Document mapToEntity(final DocumentRequest documentResponse) {
        Document document = new Document();
        document.setName(documentResponse.getName());
        return document;
    }
}