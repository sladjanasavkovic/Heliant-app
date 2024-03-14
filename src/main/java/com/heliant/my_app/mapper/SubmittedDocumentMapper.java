package com.heliant.my_app.mapper;

import com.heliant.my_app.dto.SubmittedDocumentFieldResponse;
import com.heliant.my_app.dto.SubmittedDocumentRequest;
import com.heliant.my_app.dto.SubmittedDocumentResponse;
import com.heliant.my_app.model.Document;
import com.heliant.my_app.model.SubmittedDocument;
import com.heliant.my_app.model.SubmittedDocumentField;
import com.heliant.my_app.repository.DocumentFieldRepository;
import com.heliant.my_app.repository.DocumentRepository;
import com.heliant.my_app.repository.SubmittedDocumentFieldRepository;
import com.heliant.my_app.util.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SubmittedDocumentMapper {

    private final DocumentRepository documentRepository;

    private final SubmittedDocumentFieldRepository submittedDocumentFieldRepository;
    private final DocumentFieldRepository documentFieldRepository;

    private final SubmittedDocumentFieldMapper submittedDocumentFieldMapper;

    public SubmittedDocumentMapper(DocumentRepository documentRepository, SubmittedDocumentFieldRepository submittedDocumentFieldRepository, DocumentFieldRepository documentFieldRepository, SubmittedDocumentFieldMapper submittedDocumentFieldMapper) {
        this.documentRepository = documentRepository;
        this.submittedDocumentFieldRepository = submittedDocumentFieldRepository;
        this.documentFieldRepository = documentFieldRepository;
        this.submittedDocumentFieldMapper = submittedDocumentFieldMapper;
    }

    public SubmittedDocumentResponse mapToDTO(final SubmittedDocument submittedDocument) {
        SubmittedDocumentResponse submittedDocumentResponse = new SubmittedDocumentResponse();
        submittedDocumentResponse.setId(submittedDocument.getId());
        submittedDocumentResponse.setDocument(submittedDocument.getDocument() == null ? null : submittedDocument.getDocument().getId());

        List<SubmittedDocumentFieldResponse> fieldResponses = new ArrayList<>();
        Set<SubmittedDocumentField> fields = submittedDocument.getSubmittedDocumentFields();
        for(SubmittedDocumentField f:fields){
            fieldResponses.add(submittedDocumentFieldMapper.mapToDTO(f));
        }
        submittedDocumentResponse.setSubmittedDocumentFields(fieldResponses);

        return submittedDocumentResponse;
    }

    public SubmittedDocument mapToEntity(final SubmittedDocumentRequest submittedDocumentRequest) {
        SubmittedDocument submittedDocument = new SubmittedDocument();
        final Document document = submittedDocumentRequest.getDocument() == null ? null : documentRepository
                                    .findById(submittedDocumentRequest.getDocument())
                                    .orElseThrow(() -> new NotFoundException("Document not found"));

        submittedDocument.setDocument(document);
        return submittedDocument;
    }
}