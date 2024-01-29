package com.cs.sigm.api.v1;

import com.cs.sigm.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/security/")
public interface SecurityResourceV1 {

    @PostMapping(value = "/login")
    ResponseEntity<?> login(@RequestBody @Valid LoginDTO login);

    @PostMapping(value = "/logout")
    ResponseEntity<?> logout(HttpServletRequest request);

}
