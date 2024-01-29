package com.cs.sigm.api.v1;

import com.cs.sigm.dto.CSUserDTO;
import com.cs.sigm.dto.CSUserUpdateDTO;
import com.cs.sigm.dto.PasswordChangeDTO;
import com.cs.sigm.dto.PasswordResetDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/user")
public interface CSUserResourceV1 {

    @PostMapping(value = "/password/change")
    ResponseEntity<?> changePassword(@RequestBody @Valid PasswordChangeDTO passwordUpdate, @AuthenticationPrincipal UserDetails user);

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@RequestBody @Valid CSUserDTO userDTO);

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> deleteById(@PathVariable(value = "id") Integer id);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAll();

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findByUsername(@PathVariable(value = "username") String username, @AuthenticationPrincipal UserDetails user);

    @PostMapping(value = "/password/code/{email}")
    ResponseEntity<?> requestCode(@PathVariable String email);

    @PostMapping(value = "/password/reset")
    ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetDTO passwordReset);

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> update(@RequestBody @Valid CSUserUpdateDTO userUpdateDTO, @AuthenticationPrincipal UserDetails user);

}
