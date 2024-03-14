package com.heliant.my_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;
}
