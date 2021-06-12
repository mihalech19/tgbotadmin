package com.mihalech19.tgbotadmin.Services;

import com.mihalech19.tgbotadmin.Configs.UserDetailsImpl;
import com.mihalech19.tgbotadmin.Interfaces.UserRepository;
import com.mihalech19.tgbotadmin.Entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findById(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }

        log.info("loadUserByUsername() : " + username);

        return new UserDetailsImpl(user.get());
    }
}