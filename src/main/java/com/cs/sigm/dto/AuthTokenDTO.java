package com.cs.sigm.dto;

import com.cs.sigm.entity.definition.AddressCountry;
import com.cs.sigm.entity.definition.LanguageCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthTokenDTO {

    private String token;

    private Integer minutesToExpire;

    private Integer userId;

    private String username;

    private String userRole;

    private String userDisplayName;

    @Builder.Default
    private String userLanguage = LanguageCode.PT_BR.name();

    @Builder.Default
    private String userCountry = AddressCountry.BR.name();

}
