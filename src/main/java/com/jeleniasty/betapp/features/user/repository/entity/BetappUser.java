package com.jeleniasty.betapp.features.user.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeleniasty.betapp.features.bet.Bet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "betapp_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(schema = "betapp")
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
  private String username;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private BetappUserRole betappUserRole;

  @NotNull
  private Integer score;

  @NotNull
  @Column(name = "total_bets")
  private Integer totalBets;

  @JsonIgnore
  @OneToMany(mappedBy = "betPlayer", cascade = CascadeType.ALL)
  private Set<Bet> playerBets;

  @NotNull
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @NotNull
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

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
