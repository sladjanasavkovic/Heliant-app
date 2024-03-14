package com.heliant.my_app.dto;

import com.heliant.my_app.model.enums.FieldType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DocumentFieldResponse {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Integer orderNumber;

    @NotNull
    private FieldType type;

    @NotNull
    private Integer document;

}
