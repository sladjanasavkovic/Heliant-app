package com.heliant.my_app.service;

import com.heliant.my_app.dto.DocumentRequest;
import com.heliant.my_app.dto.DocumentResponse;
import com.heliant.my_app.mapper.DocumentMapper;
import com.heliant.my_app.model.Document;
import com.heliant.my_app.model.DocumentField;
import com.heliant.my_app.model.SubmittedDocument;
import com.heliant.my_app.repository.DocumentFieldRepository;
import com.heliant.my_app.repository.DocumentRepository;
import com.heliant.my_app.repository.SubmittedDocumentRepository;
import com.heliant.my_app.util.NotFoundException;
import com.heliant.my_app.util.ReferencedException;
import com.heliant.my_app.util.ReferencedWarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final SubmittedDocumentRepository submittedDocumentRepository;
    private final DocumentFieldRepository documentFieldRepository;
    private final DocumentMapper documentMapper = new DocumentMapper();

    public DocumentService(final DocumentRepository documentRepository,
            final SubmittedDocumentRepository submittedDocumentRepository,
            final DocumentFieldRepository documentFieldRepository) {
        this.documentRepository = documentRepository;
        this.submittedDocumentRepository = submittedDocumentRepository;
        this.documentFieldRepository = documentFieldRepository;
    }

    public Page<DocumentResponse> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final Page<Document> documents = documentRepository.findAll(paging);
        return documents.map(documentMapper::mapToDTO);
    }

    public DocumentResponse get(final Integer id) {
        return documentRepository.findById(id)
                .map(document -> documentMapper.mapToDTO(document))
                .orElseThrow(NotFoundException::new);
    }

    public DocumentResponse create(final DocumentRequest documentRequest) {
        final Document document = documentMapper.mapToEntity(documentRequest);
        return documentMapper.mapToDTO(documentRepository.save(document));
    }

    public DocumentResponse update(final Integer id, final DocumentRequest documentRequest) {
        final Document document = documentRepository.findById(id).orElseThrow(() -> new NotFoundException("Document not found"));
        document.setName(documentRequest.getName());
        return documentMapper.mapToDTO(documentRepository.save(document));
    }

    public void delete(final Integer id) {
        final ReferencedWarning referencedWarning = getReferencedWarning(id);
        if (referencedWarning != null) throw new ReferencedException(referencedWarning);
        documentRepository.deleteById(id);
    }

    private ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Document document = documentRepository.findById(id).orElseThrow(() -> new NotFoundException("Document not found"));
        final SubmittedDocument documentSubmittedDocument = submittedDocumentRepository.findFirstByDocument(document);
        if (documentSubmittedDocument != null) {
            referencedWarning.setKey("document.submittedDocument.document.referenced");
            referencedWarning.addParam(documentSubmittedDocument.getId());
            return referencedWarning;
        }
        final DocumentField documentDocumentField = documentFieldRepository.findFirstByDocument(document);
        if (documentDocumentField != null) {
            referencedWarning.setKey("document.documentField.document.referenced");
            referencedWarning.addParam(documentDocumentField.getId());
            return referencedWarning;
        }
        return null;
    }

}
