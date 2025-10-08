package com.weightloss.weightloss_backend.config;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.weightloss.weightloss_backend.entities.User;

public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Set<GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String email, String password, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails fromUserEntity(User user) {
        Set<GrantedAuthority> auth = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.name()))
                .collect(Collectors.toSet());
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), auth);
    }

    public Long getId() { return id; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}