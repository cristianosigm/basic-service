package com.cs.sigm.controller.v1;

import com.cs.sigm.api.v1.EquipmentResourceV1;
import com.cs.sigm.dto.EquipmentDTO;
import com.cs.sigm.mapper.EquipmentMapper;
import com.cs.sigm.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EquipmentControllerV1 implements EquipmentResourceV1 {

    private final EquipmentService equipmentService;
    private final EquipmentMapper equipmentMapper;

    @Autowired
    public EquipmentControllerV1(EquipmentService equipmentService, EquipmentMapper equipmentMapper) {
        this.equipmentService = equipmentService;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public ResponseEntity save(EquipmentDTO equipmentDTO) {
        return ResponseEntity.ok(equipmentMapper.map(equipmentService.save(equipmentMapper.map(equipmentDTO))));
    }

    @Override
    public ResponseEntity deleteById(Integer id) {
        equipmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<EquipmentDTO> findById(Integer id) {
        return ResponseEntity.ok(equipmentMapper.map(equipmentService.findById(id)));
    }

    @Override
    public ResponseEntity<List<EquipmentDTO>> findAll() {
        return ResponseEntity.ok(equipmentMapper.map(equipmentService.findAll()));
    }

}
