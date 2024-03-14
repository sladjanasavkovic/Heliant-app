package com.heliant.my_app.mapper;

import com.heliant.my_app.dto.SubmittedDocumentFieldRequest;
import com.heliant.my_app.dto.SubmittedDocumentFieldResponse;
import com.heliant.my_app.model.DocumentField;
import com.heliant.my_app.model.enums.FieldType;
import com.heliant.my_app.model.SubmittedDocument;
import com.heliant.my_app.model.SubmittedDocumentField;
import com.heliant.my_app.repository.DocumentFieldRepository;
import com.heliant.my_app.repository.SubmittedDocumentRepository;
import com.heliant.my_app.util.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SubmittedDocumentFieldMapper {

    private final SubmittedDocumentRepository submittedDocumentRepository;
    private final DocumentFieldRepository documentFieldRepository;

    public SubmittedDocumentFieldMapper(SubmittedDocumentRepository submittedDocumentRepository, DocumentFieldRepository documentFieldRepository) {
        this.submittedDocumentRepository = submittedDocumentRepository;
        this.documentFieldRepository = documentFieldRepository;
    }

    public SubmittedDocumentFieldResponse mapToDTO(final SubmittedDocumentField submittedDocumentField) {
        SubmittedDocumentFieldResponse submittedDocumentFieldResponse = new SubmittedDocumentFieldResponse();
        submittedDocumentFieldResponse.setId(submittedDocumentField.getId());
        switch (submittedDocumentField.getDocumentField().getType()){
            case FieldType.NUMERIC ->  submittedDocumentFieldResponse.setNumericValue(submittedDocumentField.getNumericValue());
            case FieldType.TEXT -> submittedDocumentFieldResponse.setTextValue(submittedDocumentField.getTextValue());
        }
        submittedDocumentFieldResponse.setSubmittedDocument(submittedDocumentField.getSubmittedDocument() == null ? null : submittedDocumentField.getSubmittedDocument().getId());
        submittedDocumentFieldResponse.setDocumentField(submittedDocumentField.getDocumentField() == null ? null : submittedDocumentField.getDocumentField().getId());
        return submittedDocumentFieldResponse;
    }

    public SubmittedDocumentField mapToEntity(final SubmittedDocumentFieldRequest submittedDocumentFieldRequest, SubmittedDocument savedSubmittedDocument) {
        SubmittedDocumentField submittedDocumentField = new SubmittedDocumentField();
        DocumentField documentField = documentFieldRepository.findById(submittedDocumentFieldRequest.getDocumentField()).orElseThrow(() -> new NotFoundException("Document field not found."));
        switch (documentField.getType()){
            case FieldType.NUMERIC ->  submittedDocumentField.setNumericValue(submittedDocumentFieldRequest.getNumericValue());
            case FieldType.TEXT -> submittedDocumentField.setTextValue(submittedDocumentFieldRequest.getTextValue());
        }
        submittedDocumentField.setSubmittedDocument(savedSubmittedDocument);
        submittedDocumentField.setDocumentField(documentField);
        return submittedDocumentField;
    }
}