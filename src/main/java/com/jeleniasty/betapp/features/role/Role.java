package com.jeleniasty.betapp.features.role;

import com.jeleniasty.betapp.features.user.BetappUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Table(schema = "betapp")
@Entity(name = "role")
@Getter
public class Role {

  @Id
  @SequenceGenerator(
    schema = "betapp",
    name = "role_id_seq",
    sequenceName = "role_id_seq",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  private RoleName name;

  @ManyToMany(mappedBy = "roles")
  private Set<BetappUser> users = new HashSet<>();
}
