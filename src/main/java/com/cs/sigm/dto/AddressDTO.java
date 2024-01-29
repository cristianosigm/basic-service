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
public class AddressDTO {

    private Integer id;

    @NotBlank(message = "{validation.notBlank}")
    private String street;

    private String number;

    private String complement;

    private String district;

    @NotBlank(message = "{validation.notBlank}")
    private String city;

    @NotBlank(message = "{validation.notBlank}")
    private String state;

    @NotBlank(message = "{validation.notBlank}")
    private String country;

    private String postalCode;

}
