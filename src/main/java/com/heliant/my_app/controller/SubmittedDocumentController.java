package com.heliant.my_app.controller;

import com.heliant.my_app.dto.SubmittedDocumentRequest;
import com.heliant.my_app.dto.SubmittedDocumentResponse;
import com.heliant.my_app.service.SubmittedDocumentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/submittedDocuments", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Authorization")
public class SubmittedDocumentController {

    private final SubmittedDocumentService submittedDocumentService;

    public SubmittedDocumentController(final SubmittedDocumentService submittedDocumentService) {
        this.submittedDocumentService = submittedDocumentService;
    }

    @GetMapping
    public ResponseEntity<List<SubmittedDocumentResponse>> getAllSubmittedDocuments() {
        return ResponseEntity.ok(submittedDocumentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmittedDocumentResponse> getSubmittedDocument(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(submittedDocumentService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<SubmittedDocumentResponse> createSubmittedDocument(
            @RequestBody @Valid final SubmittedDocumentRequest submittedDocumentRequest) {
        final SubmittedDocumentResponse createdSubmittedDocument = submittedDocumentService.create(submittedDocumentRequest);
        return new ResponseEntity<>(createdSubmittedDocument, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSubmittedDocument(@PathVariable(name = "id") final Integer id) {
        submittedDocumentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
