package com.heliant.my_app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmittedDocumentRequest {


    @NotNull
    private Integer document;

    @NotNull
    @NotEmpty
    private List<SubmittedDocumentFieldRequest> submittedDocumentFields;

}
