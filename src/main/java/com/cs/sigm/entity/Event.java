package com.cs.sigm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "event", schema = "basicservice")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "equipment_id", nullable = false)
    private Integer equipmentId;

    @NotNull
    @Column(name = "technician_id", nullable = false)
    private Integer technicianId;

    @NotNull
    private Date scheduled;

    @NotNull
    @Builder.Default
    private Boolean executed = Boolean.FALSE;

    private Date executionDate;

    private String notes;

}
