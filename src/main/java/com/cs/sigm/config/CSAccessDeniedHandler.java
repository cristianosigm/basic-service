package com.cs.sigm.config;

import com.cs.sigm.dto.ErrorResponseDTO;
import com.cs.sigm.util.CSUserSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class CSAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CSUserSettings settings;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        log.error("Exception caught by CSAccessDeniedHandler: " + exception.getMessage(), exception);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // @formatter:off
        response.getOutputStream().println(objectMapper.
            writeValueAsString(ErrorResponseDTO.builder()
                .httpStatus(HttpStatus.FORBIDDEN.name())
                .message(settings.getMessage("exception.permission"))
                .errors(Arrays.asList(exception.getMessage()))
                .build()));
        // @formatter:on
    }

}
