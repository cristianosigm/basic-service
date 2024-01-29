package com.cs.sigm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "part", schema = "basicservice")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "event_id")
    private Integer eventId;

    @NotEmpty
    private String name;

    @NotNull
    @Column(name = "purchase_date")
    private Date purchaseDate;

    @NotNull
    @Builder.Default
    @Column(name = "purchase_value")
    private Long purchaseValue = 0L;

    @NotNull
    @Builder.Default
    private Boolean available = Boolean.TRUE;

}
