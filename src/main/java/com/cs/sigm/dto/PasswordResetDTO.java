package com.cs.sigm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetDTO {

    private String code;

    @NotBlank(message = "{validation.notBlank}")
    private String email;

    private String password;

}
