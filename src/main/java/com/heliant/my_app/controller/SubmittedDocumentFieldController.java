package com.heliant.my_app.controller;

import com.heliant.my_app.dto.SubmittedDocumentFieldResponse;
import com.heliant.my_app.service.SubmittedDocumentFieldService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api/submittedDocumentFields", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Authorization")
public class SubmittedDocumentFieldController {

    private final SubmittedDocumentFieldService submittedDocumentFieldService;

    public SubmittedDocumentFieldController(
            final SubmittedDocumentFieldService submittedDocumentFieldService) {
        this.submittedDocumentFieldService = submittedDocumentFieldService;
    }

    @GetMapping
    public ResponseEntity<List<SubmittedDocumentFieldResponse>> getAllSubmittedDocumentFields() {
        return ResponseEntity.ok(submittedDocumentFieldService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmittedDocumentFieldResponse> getSubmittedDocumentField(
            @PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(submittedDocumentFieldService.get(id));
    }

}
