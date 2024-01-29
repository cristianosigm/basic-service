package com.cs.sigm.mapper;

import com.cs.sigm.dto.PersonDTO;
import com.cs.sigm.entity.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    private final AddressMapper addressMapper;

    public PersonMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Person map(PersonDTO from) {
        if (from == null) {
            return null;
        }
        // @formatter:off
        return Person.builder()
                .id(from.getId())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .displayName(from.getDisplayName())
                .email(from.getEmail())
                .phoneMobile(from.getPhoneMobile())
                .phoneHome(from.getPhoneHome())
                .phoneContact(from.getPhoneContact())
                .address(addressMapper.map(from.getAddress()))
                .build();
        // @formatter:on
    }

    public PersonDTO map(Person from) {
        if (from == null) {
            return null;
        }
        // @formatter:off
        return PersonDTO.builder()
                .id(from.getId())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .displayName(from.getDisplayName())
                .email(from.getEmail())
                .phoneMobile(from.getPhoneMobile())
                .phoneHome(from.getPhoneHome())
                .phoneContact(from.getPhoneContact())
                .address(addressMapper.map(from.getAddress()))
                .build();
        // @formatter:on
    }

    public List<PersonDTO> map(List<Person> from) {
        return from.parallelStream().map(cur -> map(cur)).collect(Collectors.toList());
    }

}
