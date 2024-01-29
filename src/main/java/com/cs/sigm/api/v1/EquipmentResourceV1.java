package com.cs.sigm.api.v1;

import com.cs.sigm.api.v1.shared.PersistentResource;
import com.cs.sigm.dto.EquipmentDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/equipment")
public interface EquipmentResourceV1 extends PersistentResource {

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> save(@RequestBody @Valid EquipmentDTO equipmentDTO);

}
