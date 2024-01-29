package com.cs.sigm.controller.v1;

import com.cs.sigm.api.v1.CSUserResourceV1;
import com.cs.sigm.dto.*;
import com.cs.sigm.entity.CSUser;
import com.cs.sigm.mapper.CSUserMapper;
import com.cs.sigm.service.CSUserService;
import com.cs.sigm.util.CSUserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CSUserControllerV1 implements CSUserResourceV1 {

    @Autowired
    private CSUserService userService;

    @Autowired
    private CSUserMapper userMapper;

    @Autowired
    private CSUserSettings settings;

    @Override
    public ResponseEntity deleteById(Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CSUserDTO> findByUsername(String username, UserDetails userDetails) {
        return ResponseEntity.ok(userMapper.map(userService.findByUsername(username, userDetails)));
    }

    @Override
    public ResponseEntity<List<CSUserDTO>> findAll() {
        return ResponseEntity.ok(userMapper.map(userService.findAll()));
    }

    @Override
    public ResponseEntity<?> create(CSUserDTO userDTO) {
        return ResponseEntity.ok(userMapper.map(userService.create(userMapper.map(userDTO))));
    }

    @Override
    public ResponseEntity<?> update(CSUserUpdateDTO userUpdateDTO, UserDetails userDetails) {
        final CSUser csUser = userMapper.map(userUpdateDTO);
        userService.update(csUser.getId(), csUser.getUsername(), csUser.getRole(), csUser.getLanguage(), csUser.getPerson(), userDetails);
        return ResponseEntity.ok(SuccessResponseDTO.builder().message(settings.getMessage("success.user.update")).build());
    }

    @Override
    public ResponseEntity<?> changePassword(PasswordChangeDTO passwordUpdate, UserDetails userDetails) {
        userService.changePassword(passwordUpdate.getUserId(), passwordUpdate.getUsername(), passwordUpdate.getOldPassword(), passwordUpdate.getNewPassword(), userDetails);
        return ResponseEntity.ok(SuccessResponseDTO.builder().message(settings.getMessage("success.password.change")).build());
    }

    @Override
    public ResponseEntity<?> requestCode(String email) {
        userService.requestCodeAuthorization(email);
        return ResponseEntity.ok(SuccessResponseDTO.builder().message(settings.getMessage("success.password.request")).build());
    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordResetDTO passwordReset) {
        userService.resetPassword(passwordReset.getCode(), passwordReset.getEmail(), passwordReset.getPassword());
        return ResponseEntity.ok(SuccessResponseDTO.builder().message(settings.getMessage("success.password.reset")).build());
    }

}
