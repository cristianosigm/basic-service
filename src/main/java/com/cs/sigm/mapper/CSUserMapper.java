package com.cs.sigm.mapper;

import com.cs.sigm.dto.CSUserDTO;
import com.cs.sigm.dto.CSUserUpdateDTO;
import com.cs.sigm.entity.CSUser;
import com.cs.sigm.entity.definition.CSUserRole;
import com.cs.sigm.entity.definition.LanguageCode;
import com.cs.sigm.util.ValidationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CSUserMapper {

    private final PersonMapper personMapper;
    private final ValidationUtils validator;

    public CSUserMapper(PersonMapper personMapper, ValidationUtils validator) {
        this.personMapper = personMapper;
        this.validator = validator;
    }

    public CSUser map(CSUserUpdateDTO from) {
        if (from == null) {
            return null;
        }
        validator.validateUser(from);
        // @formatter:off
        final CSUser user = CSUser.builder()
                .id(from.getId())
                .role(CSUserRole.valueOf(from.getRole()))
                .language(LanguageCode.valueOf(from.getLanguage()))
                .username(from.getUsername())
                .active(from.getActive())
                .person(personMapper.map(from.getPerson()))
                .build();
        // @formatter:on
        return user;
    }

    public CSUser map(CSUserDTO from) {
        if (from == null) {
            return null;
        }
        validator.validateUser(from);
        // @formatter:off
        final CSUser user = CSUser.builder()
                .id(from.getId())
                .role(CSUserRole.valueOf(from.getRole()))
                .language(LanguageCode.valueOf(from.getLanguage()))
                .username(from.getPerson().getEmail())
                .password(from.getPassword())
                .person(personMapper.map(from.getPerson()))
                .build();
        // @formatter:on
        return user;
    }

    public CSUserDTO map(CSUser from) {
        if (from == null) {
            return null;
        }
        // @formatter:off
        return CSUserDTO.builder()
                .id(from.getId())
                .role(from.getRole().name())
                .language(from.getLanguage().name())
                .username(from.getUsername())
                .active(from.getActive())
                .person(personMapper.map(from.getPerson()))
                .build();
        // @formatter:on
    }

    public List<CSUserDTO> map(List<CSUser> from) {
        return from.parallelStream().map(cur -> map(cur)).collect(Collectors.toList());
    }

}
