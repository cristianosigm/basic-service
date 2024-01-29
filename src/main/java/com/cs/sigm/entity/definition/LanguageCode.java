package com.cs.sigm.entity.definition;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Getter
public enum LanguageCode {
    PT_BR("Português do Brasil", "pt", "BR"),
    EN_US("English", "en", "US");

    private final String displayName;
    private final String code;
    private final String country;

    LanguageCode(String displayName, String code, String country) {
        this.displayName = displayName;
        this.code = code;
        this.country = country;
    }

    public static List<LanguageCode> asList() {
        return new ArrayList<>(EnumSet.allOf(LanguageCode.class));
    }

}
