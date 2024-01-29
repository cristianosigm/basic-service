package com.cs.sigm.service;

import com.cs.sigm.dto.AuthTokenDTO;
import com.cs.sigm.entity.CSUser;
import com.cs.sigm.entity.LoginEvent;
import com.cs.sigm.repository.LoginEventRepository;
import com.cs.sigm.util.CSUserSettings;
import com.cs.sigm.util.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Service
public class SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CSUserService csUserService;

    @Autowired
    private LoginEventRepository loginEventRepository;

    @Autowired
    private TokenBlacklistService blacklistService;

    @Autowired
    private CSUserSettings settings;

    public AuthTokenDTO login(String username, String password) {
        final CSUser csUser = csUserService.findByUsername(username);
        if (!ObjectUtils.isEmpty(csUser)) {
            Authentication auth;
            try {
                auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            } catch (BadCredentialsException | LockedException ex) {
                saveLoginFailed(csUser);
                throw ex;
            }
            if (!ObjectUtils.isEmpty(auth) && auth.isAuthenticated()) {
                if (csUser.getFailedAttempts() > 0) {
                    clearFailedEventsCounter(csUser);
                } else {
                    saveLoginEvent(csUser.getId(), Boolean.FALSE);
                }
                // @formatter:off
                return AuthTokenDTO.builder()
                        .userId(csUser.getId())
                        .username(csUser.getUsername())
                        .userRole(csUser.getRole().name())
                        .userDisplayName(csUser.getPerson().getDisplayName())
                        .userCountry(csUser.getPerson().getAddress().getCountry().name())
                        .userLanguage(csUser.getLanguage().name())
                        .token(jwtService.generateToken(csUser.getUsername()))
                        .minutesToExpire(jwtService.getMinutesToExpire())
                        .build();
                // @formatter:on
            } else {
                saveLoginFailed(csUser);
                throw new BadCredentialsException(settings.getMessage("exception.badCredentials"));
            }
        }
        throw new UsernameNotFoundException(settings.getMessage("exception.badCredentials"));
    }

    public void logout(HttpServletRequest request) {
        final String token = jwtService.extractToken(request);
        if (StringUtils.hasText(token)) {
            blacklistService.blacklistToken(token);
        }
    }

    private void clearFailedEventsCounter(CSUser user) {
        saveLoginEvent(user.getId(), Boolean.FALSE);
        csUserService.cleanFailedAttempts(user);
    }

    private void saveLoginFailed(CSUser user) {
        saveLoginEvent(user.getId(), Boolean.TRUE);
        user.setFailedAttempts(user.getFailedAttempts() + 1);
        csUserService.increaseFailedAttempts(user);
    }

    private void saveLoginEvent(Integer userId, Boolean failed) {
        loginEventRepository.save(LoginEvent.builder().csuserId(userId).created(new Date()).failed(failed).build());
    }

}
