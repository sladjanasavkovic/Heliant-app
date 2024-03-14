package com.heliant.my_app.controller;

import com.heliant.my_app.dto.DocumentFieldRequest;
import com.heliant.my_app.dto.DocumentFieldResponse;
import com.heliant.my_app.service.DocumentFieldService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/documentFields", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Authorization")
public class DocumentFieldController {

    private final DocumentFieldService documentFieldService;

    public DocumentFieldController(final DocumentFieldService documentFieldService) {
        this.documentFieldService = documentFieldService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentFieldResponse>> getAllDocumentFields() {
        return ResponseEntity.ok(documentFieldService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentFieldResponse> getDocumentField(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(documentFieldService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<DocumentFieldResponse> createDocumentField(
            @RequestBody @Valid final DocumentFieldRequest documentFieldRequest) {
        final DocumentFieldResponse createdDocumentField = documentFieldService.create(documentFieldRequest);
        return new ResponseEntity<>(createdDocumentField, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentFieldResponse> updateDocumentField(@PathVariable(name = "id") final Integer id,
                                                                     @RequestBody @Valid final DocumentFieldRequest documentFieldRequest) {
        final DocumentFieldResponse updatedDocumentField = documentFieldService.update(id, documentFieldRequest);
        return ResponseEntity.ok(updatedDocumentField);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDocumentField(@PathVariable(name = "id") final Integer id) {
        documentFieldService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
