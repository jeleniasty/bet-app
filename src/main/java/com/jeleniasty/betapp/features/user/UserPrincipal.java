package com.jeleniasty.betapp.features.user;

import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode
public class UserPrincipal implements UserDetails {

  private Long id;
  private String username;
  private String password;

  @Getter
  private String email;

  private Set<? extends GrantedAuthority> authorities;

  public UserPrincipal(
    Long id,
    String username,
    String password,
    String email,
    Set<? extends GrantedAuthority> authorities
  ) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.authorities = authorities;
  }

  public static UserPrincipal create(BetappUser betappUser) {
    return new UserPrincipal(
      betappUser.getId(),
      betappUser.getUsername(),
      betappUser.getPassword(),
      betappUser.getEmail(),
      new HashSet<>()
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  @NotNull
  public String getPassword() {
    return this.password;
  }

  @Override
  @NotNull
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
