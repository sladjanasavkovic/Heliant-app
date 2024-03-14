package com.heliant.my_app.controller;

import com.heliant.my_app.dto.DocumentRequest;
import com.heliant.my_app.dto.DocumentResponse;
import com.heliant.my_app.service.DocumentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/documents", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Authorization")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping()
    public ResponseEntity<Page<DocumentResponse>> getAllDocuments(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                                  @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(documentService.findAll(pageNo, pageSize, sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(documentService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<DocumentResponse> createDocument(
            @RequestBody @Valid final DocumentRequest documentRequest) {
        final DocumentResponse savedDocument = documentService.create(documentRequest);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponse> updateDocument(@PathVariable(name = "id") final Integer id,
                                                           @RequestBody @Valid final DocumentRequest documentRequest) {
        final DocumentResponse updatedDocument = documentService.update(id, documentRequest);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDocument(@PathVariable(name = "id") final Integer id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
