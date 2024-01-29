package com.cs.sigm.mapper;

import com.cs.sigm.dto.EquipmentDTO;
import com.cs.sigm.entity.Equipment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipmentMapper {

    public Equipment map(EquipmentDTO from) {
        // @formatter:off
        return Equipment.builder()
                .id(from.getId())
                .companyId(from.getCompanyId())
                .name(from.getName())
                .identifier(from.getIdentifier())
                .notes(from.getNotes())
                .active(from.getActive())
                .build();
        // @formatter:on
    }

    public EquipmentDTO map(Equipment from) {
        // @formatter:off
        return EquipmentDTO.builder()
                .id(from.getId())
                .companyId(from.getCompanyId())
                .name(from.getName())
                .identifier(from.getIdentifier())
                .notes(from.getNotes())
                .active(from.getActive())
                .build();
        // @formatter:on
    }

    public List<EquipmentDTO> map(List<Equipment> from) {
        return from.parallelStream().map(cur -> map(cur)).collect(Collectors.toList());
    }

}
