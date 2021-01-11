package br.com.claudio.calendar.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  private RoleEnum name;

  public Role(RoleEnum name) {
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public RoleEnum getName() {
    return name;
  }

}
