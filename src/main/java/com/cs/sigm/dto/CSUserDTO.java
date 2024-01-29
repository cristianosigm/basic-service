package com.cs.sigm.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CSUserDTO {

    private Integer id;

    @NotBlank(message = "{validation.notBlank}")
    private String role;

    @NotBlank(message = "{validation.notBlank}")
    private String language;

    private String username;

    @NotBlank(message = "{validation.notBlank}")
    private String password;

    private Boolean active;

    @Valid
    @NotNull(message = "{validation.notNull}")
    private PersonDTO person;

}
