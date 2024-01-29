package com.cs.sigm.entity;

import com.cs.sigm.entity.definition.AddressState;
import com.cs.sigm.entity.definition.AddressCountry;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "address", schema = "basicservice")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    private String street;

    private String addressNumber;

    private String complement;

    private String district;

    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_state")
    private AddressState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_country")
    private AddressCountry country;

    private String postalCode;

}
