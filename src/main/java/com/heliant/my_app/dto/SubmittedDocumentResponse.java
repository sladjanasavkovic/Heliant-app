package com.heliant.my_app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SubmittedDocumentResponse {

    private Integer id;

    @NotNull
    private Integer document;

    @NotNull
    @NotEmpty
    private List<SubmittedDocumentFieldResponse> submittedDocumentFields;

}
