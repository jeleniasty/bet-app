package com.jeleniasty.betapp.features.user;

import com.jeleniasty.betapp.features.bet.Bet;
import com.jeleniasty.betapp.features.user.role.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "betapp_user")
@Table(schema = "betapp")
@NoArgsConstructor
@Getter
@Setter
public class BetappUser {

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
  @Column(name = "points")
  private Integer points;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", insertable = false)
  private LocalDateTime updatedAt;

  @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
  @JoinTable(
    schema = "betapp",
    name = "user_role",
    joinColumns = @JoinColumn(name = "betapp_user"),
    inverseJoinColumns = @JoinColumn(name = "role")
  )
  private Set<Role> roles;

  @OneToMany(
    mappedBy = "player",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<Bet> playerBets = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BetappUser that = (BetappUser) o;
    return (
      Objects.equals(id, that.id) &&
      Objects.equals(username, that.username) &&
      Objects.equals(email, that.email) &&
      Objects.equals(password, that.password)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, email, password);
  }

  public BetappUser(
    @NotNull String username,
    @NotNull String email,
    @NotNull String password,
    @NotNull Integer points,
    Set<Role> roles
  ) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.points = points;
    this.roles = roles;
  }
}
