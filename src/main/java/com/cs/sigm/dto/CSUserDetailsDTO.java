package com.cs.sigm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CSUserDetailsDTO implements UserDetails {

    private User userCredentials;

    private Integer userId;

    private String displayName;

    private String language;

    private String country;

    private Boolean locked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userCredentials.getAuthorities();
    }

    @Override
    public String getPassword() {
        return userCredentials.getPassword();
    }

    @Override
    public String getUsername() {
        return userCredentials.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userCredentials.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userCredentials.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userCredentials.isEnabled();
    }
}
