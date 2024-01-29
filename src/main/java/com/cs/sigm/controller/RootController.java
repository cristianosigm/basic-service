package com.cs.sigm.controller;

import com.cs.sigm.dto.AddressCountryDTO;
import com.cs.sigm.dto.AddressStateDTO;
import com.cs.sigm.dto.CSUserRoleDTO;
import com.cs.sigm.dto.LanguageCodeDTO;
import com.cs.sigm.service.RootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/utils")
public class RootController {

    private final RootService rootService;

    @Autowired
    public RootController(RootService rootService) {
        this.rootService = rootService;
    }

    @GetMapping("/version")
    public String getVersion() {
        return "0.0.1-SNAPSHOT";
    }

    @GetMapping("/states/{country}")
    public List<AddressStateDTO> getStates(@PathVariable(value = "country") String country) {
        return rootService.getStatesByCountry(country);
    }

    @GetMapping("/countries")
    public List<AddressCountryDTO> getCountries() {
        return rootService.getCountries();
    }

    @GetMapping("/roles")
    public List<CSUserRoleDTO> getRoles() {
        return rootService.getRoles();
    }

    @GetMapping("/languages")
    public List<LanguageCodeDTO> getLanguageCodes() {
        return rootService.getLanguageCodes();
    }

}
