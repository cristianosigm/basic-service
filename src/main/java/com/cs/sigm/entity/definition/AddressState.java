package com.cs.sigm.entity.definition;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Getter
public enum AddressState {

    BR_AC("BR", "Acre", "AC"),
    BR_AL("BR", "Alagoas", "AL"),
    BR_AP("BR", "Amapá", "AP"),
    BR_AM("BR", "Amazonas", "AM"),
    BR_BA("BR", "Bahia", "BA"),
    BR_CE("BR", "Ceará", "CE"),
    BR_DF("BR", "Distrito Federal", "DF"),
    BR_ES("BR", "Espírito Santo", "ES"),
    BR_GO("BR", "Goiás", "GO"),
    BR_MA("BR", "Maranhão", "MA"),
    BR_MT("BR", "Mato Grosso", "MT"),
    BR_MS("BR", "Mato Grosso do Sul", "MS"),
    BR_MG("BR", "Minas Gerais", "MG"),
    BR_PA("BR", "Pará", "PA"),
    BR_PB("BR", "Paraíba", "PB"),
    BR_PR("BR", "Paraná", "PR"),
    BR_PE("BR", "Pernambuco", "PE"),
    BR_PI("BR", "Piauí", "PI"),
    BR_RJ("BR", "Rio de Janeiro", "RJ"),
    BR_RN("BR", "Rio Grande do Norte", "RN"),
    BR_RS("BR", "Rio Grande do Sul", "RS"),
    BR_RO("BR", "Rondônia", "RO"),
    BR_RR("BR", "Roraima", "RR"),
    BR_SC("BR", "Santa Catarina", "SC"),
    BR_SP("BR", "São Paulo", "SP"),
    BR_SE("BR", "Sergipe", "SE"),
    BR_TO("BR", "Tocantins", "TO"),

    US_AL("US", "Alabama", "AL"),
    US_AK("US", "Alaska", "AK"),
    US_AZ("US", "Arizona", "AZ"),
    US_AR("US", "Arkansas", "AR"),
    US_CA("US", "California", "CA"),
    US_CO("US", "Colorado", "CO"),
    US_CT("US", "Connecticut", "CT"),
    US_DE("US", "Delaware", "DE"),
    US_FL("US", "Florida", "FL"),
    US_GA("US", "Georgia", "GA"),
    US_HI("US", "Hawaii", "HI"),
    US_ID("US", "Idaho", "ID"),
    US_IL("US", "Illinois", "IL"),
    US_IN("US", "Indiana", "IN"),
    US_IA("US", "Iowa", "IA"),
    US_KS("US", "Kansas", "KS"),
    US_KY("US", "Kentucky", "KY"),
    US_LA("US", "Louisiana", "LA"),
    US_ME("US", "Maine", "ME"),
    US_MD("US", "Maryland", "MD"),
    US_MA("US", "Massachusetts", "MA"),
    US_MI("US", "Michigan", "MI"),
    US_MN("US", "Minnesota", "MN"),
    US_MS("US", "Mississippi", "MS"),
    US_MO("US", "Missouri", "MO"),
    US_MT("US", "Montana", "MT"),
    US_NE("US", "Nebraska", "NE"),
    US_NV("US", "Nevada", "NV"),
    US_NH("US", "New Hampshire", "NH"),
    US_NJ("US", "New Jersey", "NJ"),
    US_NM("US", "New Mexico", "NM"),
    US_NY("US", "New York", "NY"),
    US_NC("US", "North Carolina", "NC"),
    US_ND("US", "North Dakota", "ND"),
    US_OH("US", "Ohio", "OH"),
    US_OK("US", "Oklahoma", "OK"),
    US_OR("US", "Oregon", "OR"),
    US_PA("US", "Pennsylvania", "PA"),
    US_RI("US", "Rhode Island", "RI"),
    US_SC("US", "South Carolina", "SC"),
    US_SD("US", "South Dakota", "SD"),
    US_TN("US", "Tennessee", "TN"),
    US_TX("US", "Texas", "TX"),
    US_UT("US", "Utah", "UT"),
    US_VT("US", "Vermont", "VT"),
    US_VA("US", "Virginia", "VA"),
    US_WA("US", "Washington", "WA"),
    US_WV("US", "West Virginia", "WV"),
    US_WI("US", "Wisconsin", "WI"),
    US_WY("US", "Wyoming", "WY"),
    US_DC("US", "District of Columbia", "DC"),
    US_AS("US", "American Samoa", "AS"),
    US_GU("US", "Guam", "GU"),
    US_MP("US", "Northern Mariana Islands", "MP"),
    US_PR("US", "Puerto Rico", "PR"),
    US_UM("US", "United States Minor Outlying Islands", "UM"),
    US_VI("US", "Virgin Islands", "VI");

    private final String country;
    private final String name;
    private final String acronym;

    AddressState(String country, String name, String acronym) {
        this.country = country;
        this.name = name;
        this.acronym = acronym;
    }

    public static List<AddressState> asList() {
        return new ArrayList<>(EnumSet.allOf(AddressState.class));
    }

}
