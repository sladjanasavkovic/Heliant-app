package com.heliant.my_app.service;

import com.heliant.my_app.dto.DocumentFieldRequest;
import com.heliant.my_app.dto.DocumentFieldResponse;
import com.heliant.my_app.mapper.DocumentFieldMapper;
import com.heliant.my_app.model.Document;
import com.heliant.my_app.model.DocumentField;
import com.heliant.my_app.model.SubmittedDocumentField;
import com.heliant.my_app.repository.DocumentFieldRepository;
import com.heliant.my_app.repository.DocumentRepository;
import com.heliant.my_app.repository.SubmittedDocumentFieldRepository;
import com.heliant.my_app.util.NotFoundException;
import com.heliant.my_app.util.ReferencedException;
import com.heliant.my_app.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DocumentFieldService {

    private final DocumentFieldRepository documentFieldRepository;
    private final DocumentRepository documentRepository;
    private final SubmittedDocumentFieldRepository submittedDocumentFieldRepository;
    private final DocumentFieldMapper documentFieldMapper;

    public DocumentFieldService(final DocumentFieldRepository documentFieldRepository,
                                final DocumentRepository documentRepository,
                                final SubmittedDocumentFieldRepository submittedDocumentFieldRepository, DocumentFieldMapper documentFieldMapper) {
        this.documentFieldRepository = documentFieldRepository;
        this.documentRepository = documentRepository;
        this.submittedDocumentFieldRepository = submittedDocumentFieldRepository;
        this.documentFieldMapper = documentFieldMapper;
    }

    public List<DocumentFieldResponse> findAll() {
        final List<DocumentField> documentFields = documentFieldRepository.findAll(Sort.by("orderNumber"));
        return documentFields.stream()
                .map(documentFieldMapper::mapToDTO)
                .toList();
    }

    public DocumentFieldResponse get(final Integer id) {
        return documentFieldRepository.findById(id)
                .map(documentFieldMapper::mapToDTO)
                .orElseThrow(() -> new NotFoundException("Document field not found."));
    }

    public DocumentFieldResponse create(final DocumentFieldRequest documentFieldRequest) {
        final DocumentField documentField = documentFieldMapper.mapToEntity(documentFieldRequest);
        return documentFieldMapper.mapToDTO(documentFieldRepository.save(documentField));
    }

    public DocumentFieldResponse update(final Integer id, final DocumentFieldRequest documentFieldRequest) {
        final DocumentField documentField = documentFieldRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document field not found."));
        final Document document = documentRepository.findById(documentFieldRequest.getDocument())
                .orElseThrow(() -> new NotFoundException("Document not found."));

        documentField.setName(documentFieldRequest.getName());
        documentField.setType(documentFieldRequest.getType());
        documentField.setDocument(document);
        documentField.setOrderNumber(documentFieldRequest.getOrderNumber());
        return documentFieldMapper.mapToDTO(documentFieldRepository.save(documentField));
    }

    public void delete(final Integer id) {
        final ReferencedWarning referencedWarning = getReferencedWarning(id);
        if (referencedWarning != null) throw new ReferencedException(referencedWarning);
        documentFieldRepository.deleteById(id);
    }

    private ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final DocumentField documentField = documentFieldRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document field not found."));
        final SubmittedDocumentField documentFieldSubmittedDocumentField = submittedDocumentFieldRepository.findFirstByDocumentField(documentField);
        if (documentFieldSubmittedDocumentField != null) {
            referencedWarning.setKey("documentField.submittedDocumentField.documentField.referenced");
            referencedWarning.addParam(documentFieldSubmittedDocumentField.getId());
            return referencedWarning;
        }
        return null;
    }

}
