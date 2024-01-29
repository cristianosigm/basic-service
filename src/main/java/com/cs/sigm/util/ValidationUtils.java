package com.cs.sigm.util;

import com.cs.sigm.dto.AddressDTO;
import com.cs.sigm.dto.CSUserDTO;
import com.cs.sigm.dto.CSUserUpdateDTO;
import com.cs.sigm.entity.definition.AddressCountry;
import com.cs.sigm.entity.definition.AddressState;
import com.cs.sigm.entity.definition.CSUserRole;
import com.cs.sigm.entity.definition.LanguageCode;
import com.cs.sigm.exception.EntityValidationException;
import com.cs.sigm.exception.PasswordValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Slf4j
@Component
public class ValidationUtils {

    private static final Pattern NUMBERS = Pattern.compile("(.*\\d.*)");
    private static final Pattern LOWERCASE = Pattern.compile("(.*[a-z].*)");
    private static final Pattern UPPERCASE = Pattern.compile("(.*[A-Z].*)");
    private static final Pattern SPECIAL = Pattern.compile("(.*[@#$%^&+=].*)");

    @Value("${climate-service.rules.password.min-length}")
    private int minLength;

    @Value("${climate-service.rules.password.max-length}")
    private int maxLength;

    @Autowired
    private CSUserSettings settings;

    public void validateUser(CSUserDTO userDTO) {
        validateUser(CSUserUpdateDTO.builder().role(userDTO.getRole()).language(userDTO.getLanguage()).build());
    }

    public void validateUser(CSUserUpdateDTO userDTO) {
        if (!ObjectUtils.isEmpty(userDTO)) {
            int errors = 0;
            final StringBuilder bfr = new StringBuilder(512);
            try {
                CSUserRole.valueOf(userDTO.getRole());
            } catch (Exception e) {
                bfr.append("#").append(settings.getMessage("validation.definition.role"));
                errors++;
            }
            try {
                LanguageCode.valueOf(userDTO.getLanguage());
            } catch (Exception e) {
                bfr.append("#").append(settings.getMessage("validation.definition.language"));
                errors++;
            }
            if (errors > 0) {
                throw new EntityValidationException(settings.getMessage("validation.user.invalid") + bfr);
            }
        }
    }

    public void validateAddress(AddressDTO addressDTO) {
        if (!ObjectUtils.isEmpty(addressDTO)) {
            int errors = 0;
            final StringBuilder bfr = new StringBuilder(512);
            try {
                AddressState.valueOf(addressDTO.getState());
            } catch (Exception e) {
                bfr.append("#").append(settings.getMessage("validation.definition.state"));
                errors++;
            }
            try {
                AddressCountry.valueOf(addressDTO.getCountry());
            } catch (Exception e) {
                bfr.append("#").append(settings.getMessage("validation.definition.country"));
                errors++;
            }
            if (errors > 0) {
                throw new EntityValidationException(settings.getMessage("validation.address.invalid") + bfr);
            }
        }
    }

    public void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new PasswordValidationException(settings.getMessage("validation.password.empty"));
        } else {
            int errors = 0;
            final StringBuilder bfr = new StringBuilder(512);
            if (!(password.length() >= minLength)) {
                bfr.append("#").append(settings.getMessage("validation.password.min", ArrayUtils.toArray(minLength)));
                errors++;
            }
            if (!(password.length() <= maxLength)) {
                bfr.append("#").append(settings.getMessage("validation.password.max", ArrayUtils.toArray(minLength)));
                errors++;
            }
            if (password.contains(" ")) {
                bfr.append("#").append(settings.getMessage("validation.password.space"));
                errors++;
            }
            if (!NUMBERS.matcher(password).matches()) {
                bfr.append("#").append(settings.getMessage("validation.password.number"));
                errors++;
            }
            if (!LOWERCASE.matcher(password).matches()) {
                bfr.append("#").append(settings.getMessage("validation.password.lowercase"));
                errors++;
            }
            if (!UPPERCASE.matcher(password).matches()) {
                bfr.append("#").append(settings.getMessage("validation.password.uppercase"));
                errors++;
            }
            if (!SPECIAL.matcher(password).matches()) {
                bfr.append("#").append(settings.getMessage("validation.password.special"));
                errors++;
            }
            if (errors > 0) {
                throw new PasswordValidationException(settings.getMessage("validation.password.invalid") + bfr);
            }
        }
    }

}
