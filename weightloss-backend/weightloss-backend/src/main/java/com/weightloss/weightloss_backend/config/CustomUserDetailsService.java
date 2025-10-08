package com.weightloss.weightloss_backend.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.weightloss.weightloss_backend.repository.UserRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 private final UserRepository userRepository;
	    public CustomUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        return userRepository.findByEmail(email)
	            .map(CustomUserDetails::fromUserEntity)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
	    }
}

