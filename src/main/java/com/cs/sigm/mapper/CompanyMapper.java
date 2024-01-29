package com.cs.sigm.mapper;

import com.cs.sigm.dto.CompanyDTO;
import com.cs.sigm.entity.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    private final AddressMapper addressMapper;

    public CompanyMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Company map(CompanyDTO from) {
        if (from == null) {
            return null;
        }
        // @formatter:off
        return Company.builder()
                .id(from.getId())
                .address(addressMapper.map(from.getAddress()))
                .name(from.getName())
                .onboarding(from.getOnboarding())
                .active(from.getActive())
                .build();
        // @formatter:on
    }

    public CompanyDTO map(Company from) {
        if (from == null) {
            return null;
        }
        // @formatter:off
        return CompanyDTO.builder()
                .id(from.getId())
                .address(addressMapper.map(from.getAddress()))
                .name(from.getName())
                .onboarding(from.getOnboarding())
                .active(from.getActive())
                .build();
        // @formatter:on
    }

    public List<CompanyDTO> map(List<Company> from) {
        return from.parallelStream().map(cur -> map(cur)).collect(Collectors.toList());
    }

}
