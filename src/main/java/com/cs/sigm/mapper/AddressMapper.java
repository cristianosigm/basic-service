package com.cs.sigm.mapper;

import com.cs.sigm.dto.AddressDTO;
import com.cs.sigm.entity.Address;
import com.cs.sigm.entity.definition.AddressCountry;
import com.cs.sigm.entity.definition.AddressState;
import com.cs.sigm.util.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressMapper {

    private final ValidationUtils validator;

    public AddressMapper(ValidationUtils validator) {
        this.validator = validator;
    }

    public Address map(AddressDTO from) {
        if (from == null) {
            return null;
        }
        validator.validateAddress(from);
        // @formatter:off
        return Address.builder()
                .id(from.getId())
                .addressNumber(from.getNumber())
                .state(AddressState.valueOf(from.getState()))
                .country(AddressCountry.valueOf(from.getCountry()))
                .city(from.getCity())
                .complement(from.getComplement())
                .district(from.getDistrict())
                .postalCode(from.getPostalCode())
                .street(from.getStreet())
                .build();
        // @formatter:on
    }

    public AddressDTO map(Address from) {
        if (from == null) {
            return null;
        }
        // @formatter:off
        return AddressDTO.builder()
                .id(from.getId())
                .country(from.getCountry().name())
                .number(from.getAddressNumber())
                .state(from.getState().name())
                .city(from.getCity())
                .complement(from.getComplement())
                .district(from.getDistrict())
                .postalCode(from.getPostalCode())
                .street(from.getStreet())
                .build();
        // @formatter:on
    }

    public List<AddressDTO> map(List<Address> from) {
        return from.parallelStream().map(cur -> map(cur)).collect(Collectors.toList());
    }

}
