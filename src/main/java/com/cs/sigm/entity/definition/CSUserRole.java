package com.cs.sigm.entity.definition;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Getter
public enum CSUserRole {
    TECHNICIAN("Technician"),
    ADMINISTRATOR("Administrator");

    private final String displayName;

    CSUserRole(String displayName) {
        this.displayName = displayName;
    }

    public static List<CSUserRole> asList() {
        return new ArrayList<>(EnumSet.allOf(CSUserRole.class));
    }

}
