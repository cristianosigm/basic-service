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
@Table(name = "password_reset_event", schema = "basicservice")
public class PasswordResetEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    private Integer userId;

    @NotEmpty
    private String code;

    @NotNull
    @Builder.Default
    private Date requested = new Date();

    private Date completed;

    @NotNull
    @Builder.Default
    private Boolean successful = Boolean.FALSE;

}
