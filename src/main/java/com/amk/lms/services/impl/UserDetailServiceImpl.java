package com.amk.lms.services.impl;

import com.amk.lms.Repositories.UserRepository;
import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.models.entities.User;
import com.amk.lms.securities.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.findByEmail(username));
            user.orElseThrow(() -> new UsernameNotFoundException("User Not Found:" + username));
            return user.map(ApplicationUser::new).get();
        } catch (Exception ex) {
            throw new  EtAuthException(ex.getMessage());
        }
    }
}
