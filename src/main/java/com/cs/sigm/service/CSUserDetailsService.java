package com.cs.sigm.service;

import com.cs.sigm.dto.CSUserDetailsDTO;
import com.cs.sigm.entity.CSUser;
import com.cs.sigm.exception.EntryNotFoundException;
import com.cs.sigm.repository.CSUserRepository;
import com.cs.sigm.util.CSUserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CSUserDetailsService implements UserDetailsService {

    @Autowired
    private CSUserRepository csUserRepository;

    @Autowired
    private CSUserSettings settings;

    @Override
    public CSUserDetailsDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        final CSUser csUser = csUserRepository.findByUsername(username).orElseThrow(() ->
                new EntryNotFoundException(settings.getMessage("exception.badCredentials")));
        // @formatter:off
        return CSUserDetailsDTO.builder()
                .userCredentials(new User(
                        csUser.getUsername(),
                        csUser.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + csUser.getRole().name()))))
                .userId(csUser.getId())
                .displayName(csUser.getPerson().getDisplayName())
                .country(csUser.getPerson().getAddress().getCountry().name())
                .language(csUser.getLanguage().name())
                .locked(csUser.getLocked())
                .build();
        // @formatter:on
    }

}
