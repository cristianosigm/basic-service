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
public class EquipmentDTO {

    private Integer id;

    @NotNull(message = "{validation.notNull}")
    private Integer companyId;

    @NotBlank(message = "{validation.notBlank}")
    private String name;

    private String identifier;

    private String notes;

    @NotNull(message = "{validation.notNull}")
    @Builder.Default
    private Boolean active = Boolean.TRUE;

}
