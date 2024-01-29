package com.cs.sigm.service;

import com.cs.sigm.entity.Company;
import com.cs.sigm.exception.EntryNotFoundException;
import com.cs.sigm.repository.CompanyRepository;
import com.cs.sigm.util.CSUserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CSUserSettings settings;

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public void deleteById(Integer id) {
        companyRepository.deleteById(id);
    }

    public Company findById(Integer id) {
        return companyRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(settings.getMessage("exception.notFound")));
    }

    public List<Company> findAll() {
        return companyRepository.findAll(Sort.by("id"));
    }

}
