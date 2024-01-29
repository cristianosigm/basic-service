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
@Table(name = "login_event", schema = "basicservice")
public class LoginEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "csuser_id", nullable = false)
    private Integer csuserId;

    @NotNull
    @Builder.Default
    private Boolean failed = Boolean.FALSE;

    @NotNull
    @Builder.Default
    private Date created = new Date();

}
