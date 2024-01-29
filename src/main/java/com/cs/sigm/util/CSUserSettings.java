package com.cs.sigm.util;

import com.cs.sigm.dto.CSUserDetailsDTO;
import com.cs.sigm.entity.definition.LanguageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Locale;

@Slf4j
@Component
@SessionScope
public class CSUserSettings {

    @Autowired
    private MessageSource translator;

    private Locale locale;

    public Locale getLocale() {
        if (ObjectUtils.isEmpty(locale)) {
            try {
                final CSUserDetailsDTO user = (CSUserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                final LanguageCode lc = LanguageCode.valueOf(user.getLanguage());
                locale = new Locale(lc.getCode(), lc.getCountry());
            } catch (Exception e) {
                // TODO: get language from Accept-Language header instead.
                log.warn("Failed to get current user language; using default.");
                locale = Locale.FRENCH;
            }
        }
        return locale;
    }

    public String getMessage(String key) {
        return this.getMessage(key, null);
    }

    public String getMessage(String key, Object[] args) {
        return translator.getMessage(key, args, getLocale());
    }

}
