package com.juandlr.springsecurityproject.service.implementation;

import com.juandlr.springsecurityproject.entity.ApplicationUser;
import com.juandlr.springsecurityproject.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ApplicationUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser userFromDB = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with the given username: " + username +  " does not exists"));
        return new UserDetailsImpl(userFromDB);
    }


}
