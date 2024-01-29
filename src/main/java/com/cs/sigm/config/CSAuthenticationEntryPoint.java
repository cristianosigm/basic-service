package com.cs.sigm.config;

import com.cs.sigm.dto.ErrorResponseDTO;
import com.cs.sigm.util.CSUserSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class CSAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private CSUserSettings settings;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.error("Exception caught by CSAuthenticationEntryPoint: " + exception.getMessage(), exception);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // @formatter:off
        response.getOutputStream().println(objectMapper
            .writeValueAsString(ErrorResponseDTO.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.name())
                .message(settings.getMessage("exception.expired"))
                .errors(Collections.singletonList(exception.getMessage()))
                .build()));
        // @formatter:on
    }

}
