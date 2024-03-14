package com.heliant.my_app.service;

import com.heliant.my_app.dto.SubmittedDocumentFieldRequest;
import com.heliant.my_app.dto.SubmittedDocumentFieldResponse;
import com.heliant.my_app.mapper.SubmittedDocumentFieldMapper;
import com.heliant.my_app.model.DocumentField;
import com.heliant.my_app.model.SubmittedDocument;
import com.heliant.my_app.model.SubmittedDocumentField;
import com.heliant.my_app.repository.DocumentFieldRepository;
import com.heliant.my_app.repository.SubmittedDocumentFieldRepository;
import com.heliant.my_app.repository.SubmittedDocumentRepository;
import com.heliant.my_app.util.CreationNotAllowedException;
import com.heliant.my_app.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class SubmittedDocumentFieldService {

    private final SubmittedDocumentFieldRepository submittedDocumentFieldRepository;
    private final DocumentFieldRepository documentFieldRepository;
    private final SubmittedDocumentFieldMapper submittedDocumentFieldMapper;

    public SubmittedDocumentFieldService(
            final SubmittedDocumentFieldRepository submittedDocumentFieldRepository,
            final DocumentFieldRepository documentFieldRepository,
            final SubmittedDocumentFieldMapper submittedDocumentFieldMapper) {
        this.submittedDocumentFieldRepository = submittedDocumentFieldRepository;
        this.documentFieldRepository = documentFieldRepository;
        this.submittedDocumentFieldMapper = submittedDocumentFieldMapper;
    }

    public List<SubmittedDocumentFieldResponse> findAll() {
        final List<SubmittedDocumentField> submittedDocumentFields = submittedDocumentFieldRepository.findAll();
        return submittedDocumentFields.stream()
                .map(submittedDocumentFieldMapper::mapToDTO)
                .toList();
    }

    public SubmittedDocumentFieldResponse get(final Integer id) {
        SubmittedDocumentField submittedDocumentField = submittedDocumentFieldRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Submitted document field not found."));
        return submittedDocumentFieldMapper.mapToDTO(submittedDocumentField);
    }

    public Set<SubmittedDocumentField> createAll(final List<SubmittedDocumentFieldRequest> submittedDocumentFieldRequests,
                                                 final SubmittedDocument savedSubmittedDocument){
        Set<SubmittedDocumentField> responseList = new HashSet<>();
        for(SubmittedDocumentFieldRequest request:submittedDocumentFieldRequests){
            responseList.add(create(request, savedSubmittedDocument));
        }
        return responseList;
    }

    public SubmittedDocumentField create(final SubmittedDocumentFieldRequest submittedDocumentFieldRequest,
                                         final SubmittedDocument savedSubmittedDocument) {
        DocumentField documentField = documentFieldRepository.findById(submittedDocumentFieldRequest.getDocumentField())
                                                             .orElseThrow(() -> new NotFoundException("Document field not found."));
        if(savedSubmittedDocument.getDocument().getId() != documentField.getDocument().getId())
            throw new CreationNotAllowedException(String.format("Document id:%d does not contain requested document field id:%d.",
                                                    savedSubmittedDocument.getDocument().getId(), documentField.getDocument().getId()));
        final SubmittedDocumentField submittedDocumentField = submittedDocumentFieldMapper.mapToEntity(submittedDocumentFieldRequest, savedSubmittedDocument);
        return submittedDocumentFieldRepository.save(submittedDocumentField);
    }

    public void deleteAll(final Set<SubmittedDocumentField> submittedDocumentFields) {
        submittedDocumentFieldRepository.deleteAll(submittedDocumentFields);
    }

}
