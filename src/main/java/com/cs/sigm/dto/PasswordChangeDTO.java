package com.cs.sigm.dto;

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
public class PasswordChangeDTO {

    @NotNull(message = "{validation.notNull}")
    private Integer userId;

    @NotBlank(message = "{validation.notBlank}")
    private String username;

    @NotBlank(message = "{validation.notBlank}")
    private String oldPassword;

    @NotBlank(message = "{validation.notBlank}")
    private String newPassword;

}
