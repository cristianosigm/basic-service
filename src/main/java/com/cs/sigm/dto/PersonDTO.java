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
public class PersonDTO {

    private Integer id;

    @NotBlank(message = "{validation.notBlank}")
    private String firstName;

    private String lastName;

    @NotBlank(message = "{validation.notBlank}")
    private String displayName;

    @NotBlank(message = "{validation.notBlank}")
    private String email;

    private String phoneMobile;

    private String phoneHome;

    private String phoneContact;

    @Valid
    @NotNull(message = "{validation.notNull}")
    private AddressDTO address;

}
