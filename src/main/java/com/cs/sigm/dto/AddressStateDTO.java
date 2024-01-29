package com.cs.sigm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressStateDTO {

    private String id;

    private String country;

    private String name;

    private String acronym;

}
