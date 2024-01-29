package com.cs.sigm.service;

import com.cs.sigm.dto.AddressCountryDTO;
import com.cs.sigm.dto.AddressStateDTO;
import com.cs.sigm.dto.CSUserRoleDTO;
import com.cs.sigm.dto.LanguageCodeDTO;
import com.cs.sigm.entity.definition.AddressCountry;
import com.cs.sigm.entity.definition.AddressState;
import com.cs.sigm.entity.definition.CSUserRole;
import com.cs.sigm.entity.definition.LanguageCode;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RootService {

    private List<AddressStateDTO> states;
    private List<AddressCountryDTO> countries;
    private List<CSUserRoleDTO> roles;
    private List<LanguageCodeDTO> languages;

    public List<AddressStateDTO> getStatesByCountry(String country) {
        // @formatter:off
        if (ObjectUtils.isEmpty(states)) {
            states = AddressState.asList().stream()
                    .map(state -> AddressStateDTO.builder()
                        .id(state.toString())
                        .name(state.getName())
                        .acronym(state.getAcronym())
                        .country(state.getCountry())
                        .build())
                    .collect(Collectors.toList());
        }
        return states.stream().filter(state -> state.getCountry().equals(country)).collect(Collectors.toList());
        // @formatter:on
    }

    public List<AddressCountryDTO> getCountries() {
        // @formatter:off
        if (ObjectUtils.isEmpty(countries)) {
            countries = AddressCountry.asList().stream()
                    .map(ac -> AddressCountryDTO.builder()
                        .id(ac.name())
                        .displayName(ac.getDisplayName())
                        .build())
                    .collect(Collectors.toList());
        }
        // @formatter:on
        return countries;
    }

    public List<CSUserRoleDTO> getRoles() {
        // @formatter:off
        if (ObjectUtils.isEmpty(roles)) {
            roles = CSUserRole.asList().stream()
                    .map(ur -> CSUserRoleDTO.builder()
                        .id(ur.name())
                        .displayName(ur.getDisplayName())
                        .build())
                    .collect(Collectors.toList());
        }
        // @formatter:on
        return roles;
    }

    public List<LanguageCodeDTO> getLanguageCodes() {
        // @formatter:off
        if (ObjectUtils.isEmpty(languages)) {
            languages = LanguageCode.asList().stream()
                    .map(ur -> LanguageCodeDTO.builder()
                        .id(ur.name())
                        .displayName(ur.getDisplayName())
                        .build())
                    .collect(Collectors.toList());
        }
        // @formatter:on
        return languages;
    }

}
