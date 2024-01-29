package com.cs.sigm.service;

import com.cs.sigm.entity.CSUser;
import com.cs.sigm.entity.PasswordResetEvent;
import com.cs.sigm.entity.Person;
import com.cs.sigm.entity.definition.CSUserRole;
import com.cs.sigm.entity.definition.LanguageCode;
import com.cs.sigm.exception.CSAuthenticationException;
import com.cs.sigm.exception.CSAuthorizationException;
import com.cs.sigm.exception.EntryNotFoundException;
import com.cs.sigm.exception.PasswordValidationException;
import com.cs.sigm.repository.CSUserRepository;
import com.cs.sigm.repository.PasswordResetEventRepository;
import com.cs.sigm.repository.PersonRepository;
import com.cs.sigm.util.CSUserSettings;
import com.cs.sigm.util.ValidationUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class CSUserService {

    @Value("${climate-service.security.code-size}")
    private Integer defaultCodeSize;

    @Value("${climate-service.security.maxFailedAttempts}")
    private Integer maxFailedAttempts;

    @Autowired
    private CSUserRepository csUserRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordResetEventRepository passwordResetEventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationUtils validator;

    @Autowired
    private CSEmailService emailService;

    @Autowired
    private CSUserSettings settings;

    public void deleteById(Integer id) {
        csUserRepository.deleteById(id);
    }

    public List<CSUser> findAll() {
        return csUserRepository.findAll();
    }

    public CSUser findByUsername(String username, UserDetails userDetails) {
        if (isAdmin(userDetails) || (userDetails.getUsername().equals(username))) {
            return csUserRepository.findByUsername(username).orElseThrow(() -> new EntryNotFoundException());
        }
        throw new CSAuthorizationException(settings.getMessage("exception.permission"));
    }

    public CSUser findByUsername(String username) {
        return csUserRepository.findByUsername(username).orElse(null);
    }

    public Boolean update(Integer userId, String username, CSUserRole userRole, LanguageCode languageCode, Person person, UserDetails userDetails) {
        if (isAdmin(userDetails) || (userDetails.getUsername().equals(username))) {
            csUserRepository.updateProfile(userId, userRole, languageCode);
            return ObjectUtils.isEmpty(personRepository.save(person));
        }
        throw new CSAuthorizationException(settings.getMessage("exception.permission"));
    }

    public CSUser create(CSUser user) {
        user.setPassword(passwordEncoder.encode(this.createRandomCode(10) + "0@"));
        user.setLocked(Boolean.TRUE);
        return csUserRepository.save(user);
    }

    public void changePassword(Integer userId, String username, String oldPassword, String newPassword, UserDetails userDetails) {
        if (isAdmin(userDetails) || (userDetails.getUsername().equals(username))) {
            validator.validatePassword(newPassword);
            final CSUser user = csUserRepository.findById(userId).orElseThrow(() -> new EntryNotFoundException("User not found."));
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                increaseFailedAttempts(user);
                throw new PasswordValidationException(settings.getMessage("exception.currentPassword"));
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            csUserRepository.save(user);
        } else {
            throw new CSAuthorizationException(settings.getMessage("exception.permission"));
        }
    }

    // TODO: create a job to remove expired codes
    public void requestCodeAuthorization(String email) {
        final CSUser user = csUserRepository.findByUsername(email).orElse(null);
        if (user != null) {
            PasswordResetEvent resetEvent = passwordResetEventRepository.findPendingEventsByUserId(user.getId()).orElse(null);
            if (ObjectUtils.isEmpty(resetEvent)) {
                resetEvent = PasswordResetEvent.builder().code(createRandomCode()).userId(user.getId()).build();
                passwordResetEventRepository.save(resetEvent);
            }
            // @formatter:off
            emailService.sendEmail(email,
                    "Password Reset Requested",
                    "<h2>Hello, " + user.getPerson().getDisplayName() + "!</h2>" +
                            "<p>You requested to change your password. To proceed, please copy and paste the code below in the reset form: <p>" +
                            "<br/><p>" + resetEvent.getCode() + "</p><br/>" +
                            "<p>If you do not proceed, this token will expire in one hour, and a new token need to be requested.</p>");
            // @formatter:on
        }
    }

    public void resetPassword(String code, String email, String password) {
        validator.validatePassword(password);
        final CSUser user = csUserRepository.findByUsername(email).orElseThrow(() -> new EntryNotFoundException(settings.getMessage("exception.notFound")));
        final PasswordResetEvent resetEvent = passwordResetEventRepository.findPendingEventsByUserId(user.getId()).orElseThrow(() -> new CSAuthenticationException(settings.getMessage("exception.codeExpired")));
        if (!StringUtils.hasText(code) || !code.equals(resetEvent.getCode())) {
            throw new CSAuthenticationException(settings.getMessage("exception.codeInvalid"));
        }
        resetEvent.setCompleted(new Date());
        resetEvent.setSuccessful(true);
        passwordResetEventRepository.save(resetEvent);
        user.setPassword(passwordEncoder.encode(password));
        user.setLocked(Boolean.FALSE);
        user.setFailedAttempts(0);
        csUserRepository.save(user);
    }

    public void increaseFailedAttempts(CSUser user) {
        user.setFailedAttempts(user.getFailedAttempts() + 1);
        if (user.getFailedAttempts() >= maxFailedAttempts) {
            user.setLocked(Boolean.TRUE);
        }
        csUserRepository.save(user);
    }

    public void cleanFailedAttempts(CSUser user) {
        user.setFailedAttempts(0);
        csUserRepository.save(user);
    }

    private String createRandomCode() {
        return this.createRandomCode(defaultCodeSize);
    }

    private String createRandomCode(Integer codeSize) {
        return RandomStringUtils.random(codeSize, true, true);
    }

    private Boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + CSUserRole.ADMINISTRATOR.name()));
    }

}
