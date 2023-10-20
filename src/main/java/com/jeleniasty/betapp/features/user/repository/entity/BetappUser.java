package com.jeleniasty.betapp.features.user.repository.entity;

import com.jeleniasty.betapp.features.bet.Bet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "betapp_user")
@Table(schema = "betapp")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BetappUser implements UserDetails {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "betapp_user_id_seq",
    sequenceName = "betapp_user_id_seq"
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "betapp_user_id_seq"
  )
  @Column(name = "id", updatable = false)
  private Long id;

  @NotNull
  @Column(name = "username")
  private String username;

  @NotNull
  @Column(name = "email")
  private String email;

  @NotNull
  @Column(name = "password")
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private BetappUserRole betappUserRole;

  @NotNull
  @Column(name = "points")
  private Integer points;

  @NotNull
  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  @OneToMany(
    mappedBy = "player",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Bet> playerBets = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(betappUserRole.name()));
  }

  @Override
  @NotNull
  public String getPassword() {
    return password;
  }

  @Override
  @NotNull
  public String getUsername() {
    return username;
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
