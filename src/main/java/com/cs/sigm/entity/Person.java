package com.cs.sigm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "person", schema = "basicservice")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(unique = true, nullable = false)
    private Integer id;

    @NotEmpty
    private String firstName;

    private String lastName;

    @NotEmpty
    private String displayName;

    @NotEmpty
    @Column(unique = true)
    private String email;

    private String phoneMobile;

    private String phoneHome;

    private String phoneContact;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

}
