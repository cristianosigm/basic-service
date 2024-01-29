package com.cs.sigm.service;

import com.cs.sigm.entity.Equipment;
import com.cs.sigm.exception.EntryNotFoundException;
import com.cs.sigm.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public void deleteById(Integer id) {
        equipmentRepository.deleteById(id);
    }

    public Equipment findById(Integer id) {
        return equipmentRepository.findById(id).orElseThrow(() -> new EntryNotFoundException());
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

}
