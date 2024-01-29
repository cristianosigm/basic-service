package com.cs.sigm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "equipment", schema = "basicservice")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @NotBlank
    private String name;

    private String identifier;

    private String notes;

    @NotNull
    @Builder.Default
    private Boolean active = Boolean.TRUE;

}
