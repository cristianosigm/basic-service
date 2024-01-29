package com.cs.sigm.entity;

import com.cs.sigm.entity.definition.CSUserRole;
import com.cs.sigm.entity.definition.LanguageCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "csuser", schema = "basicservice")
public class CSUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "csuser_role", nullable = false)
    @Builder.Default
    private CSUserRole role = CSUserRole.TECHNICIAN;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language_code", nullable = false)
    @Builder.Default
    private LanguageCode language = LanguageCode.EN_US;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    @Builder.Default
    @Column(name = "failed_attempts", nullable = false)
    private Integer failedAttempts = 0;

    @NotNull
    @Builder.Default
    private Boolean active = Boolean.TRUE;

    @NotNull
    @Builder.Default
    private Boolean locked = Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

}
