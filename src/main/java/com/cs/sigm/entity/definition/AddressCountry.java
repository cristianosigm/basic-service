package com.cs.sigm.entity.definition;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Getter
public enum AddressCountry {
    BR("Brasil"),
    US("United States of America");

    private final String displayName;

    AddressCountry(String displayName) {
        this.displayName = displayName;
    }

    public static List<AddressCountry> asList() {
        return new ArrayList<>(EnumSet.allOf(AddressCountry.class));
    }

}
