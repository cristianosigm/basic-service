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
public class CompanyDTO {

    private Integer id;

    @NotBlank(message = "{validation.notBlank}")
    private String name;

    @NotBlank(message = "{validation.notBlank}")
    private String onboarding;

    @Builder.Default
    private Boolean active = Boolean.TRUE;

    @Valid
    @NotNull(message = "{validation.notNull}")
    private AddressDTO address;

}
