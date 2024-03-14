package com.heliant.my_app.service;

import com.heliant.my_app.dto.SubmittedDocumentRequest;
import com.heliant.my_app.dto.SubmittedDocumentResponse;
import com.heliant.my_app.mapper.SubmittedDocumentMapper;
import com.heliant.my_app.model.SubmittedDocument;
import com.heliant.my_app.model.SubmittedDocumentField;
import com.heliant.my_app.repository.SubmittedDocumentRepository;
import com.heliant.my_app.util.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
public class SubmittedDocumentService {

    private final SubmittedDocumentRepository submittedDocumentRepository;
    private final SubmittedDocumentFieldService submittedDocumentFieldService;
    private final SubmittedDocumentMapper submittedDocumentMapper;


    public SubmittedDocumentService(final SubmittedDocumentRepository submittedDocumentRepository,
                                    final SubmittedDocumentFieldService submittedDocumentFieldService,
                                    final SubmittedDocumentMapper submittedDocumentMapper) {
        this.submittedDocumentRepository = submittedDocumentRepository;
        this.submittedDocumentFieldService = submittedDocumentFieldService;
        this.submittedDocumentMapper = submittedDocumentMapper;
    }

    public List<SubmittedDocumentResponse> findAll() {
        final List<SubmittedDocument> submittedDocuments = submittedDocumentRepository.findAll();
        return submittedDocuments.stream()
                .map(submittedDocumentMapper::mapToDTO)
                .toList();
    }

    public SubmittedDocumentResponse get(final Integer id) {
        return submittedDocumentRepository.findById(id)
                .map(submittedDocumentMapper::mapToDTO)
                .orElseThrow(() -> new NotFoundException("Submitted document not found."));
    }

    @Transactional
    public SubmittedDocumentResponse create(final @Valid SubmittedDocumentRequest submittedDocumentRequest) {
        final SubmittedDocument submittedDocument = submittedDocumentMapper.mapToEntity(submittedDocumentRequest);
        final SubmittedDocument savedSubmittedDocument = submittedDocumentRepository.save(submittedDocument);

        Set<SubmittedDocumentField> response = submittedDocumentFieldService.createAll(submittedDocumentRequest.getSubmittedDocumentFields(), savedSubmittedDocument);
        savedSubmittedDocument.setSubmittedDocumentFields(response);

        return submittedDocumentMapper.mapToDTO(savedSubmittedDocument);
    }

    public void delete(final Integer id) {
        final SubmittedDocument submittedDocument = submittedDocumentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Submitted document not found."));
        submittedDocumentFieldService.deleteAll(submittedDocument.getSubmittedDocumentFields());
        submittedDocumentRepository.deleteById(id);
    }


}
