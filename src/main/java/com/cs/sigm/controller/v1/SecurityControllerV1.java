package com.cs.sigm.controller.v1;

import com.cs.sigm.api.v1.SecurityResourceV1;
import com.cs.sigm.dto.AuthTokenDTO;
import com.cs.sigm.dto.LoginDTO;
import com.cs.sigm.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityControllerV1 implements SecurityResourceV1 {

    @Autowired
    private SecurityService securityService;

    @Override
    public ResponseEntity<AuthTokenDTO> login(LoginDTO login) {
        return ResponseEntity.ok(securityService.login(login.getUsername(), login.getPassword()));
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        securityService.logout(request);
        return ResponseEntity.ok().build();
    }

}
