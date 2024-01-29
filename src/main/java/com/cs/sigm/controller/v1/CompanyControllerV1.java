package com.cs.sigm.controller.v1;

import com.cs.sigm.api.v1.CompanyResourceV1;
import com.cs.sigm.dto.CompanyDTO;
import com.cs.sigm.mapper.CompanyMapper;
import com.cs.sigm.service.CompanyService;
import com.cs.sigm.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyControllerV1 implements CompanyResourceV1 {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final ValidationUtils validator;

    @Autowired
    public CompanyControllerV1(CompanyService companyService, CompanyMapper companyMapper, ValidationUtils validator) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<CompanyDTO> save(CompanyDTO companyDTO) {
        validator.validateAddress(companyDTO.getAddress());
        return ResponseEntity.ok(companyMapper.map(companyService.save(companyMapper.map(companyDTO))));
    }

    @Override
    public ResponseEntity deleteById(Integer id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CompanyDTO> findById(Integer id) {
        return ResponseEntity.ok(companyMapper.map(companyService.findById(id)));
    }

    @Override
    public ResponseEntity<List<CompanyDTO>> findAll() {
        return ResponseEntity.ok(companyMapper.map(companyService.findAll()));
    }

}
