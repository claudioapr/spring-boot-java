package br.com.claudio.calendar.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

  @Id
  private Integer id;

  @NotBlank
  @Email
  private String username;

  @NotBlank
  private String name;

  @NotBlank
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  //
  public User(
      @NotBlank @Email final String username, @NotBlank final String name,
      @NotBlank final String password) {
    this.username = username;
    this.name = name;
    this.password = password;
    this.roles = new HashSet<>();
  }

  public User() {}

  public Integer getId() {
    return id;
  }
  public void addRole(Role role){
      roles.add(role);
  }

  public String getUsername() {
    return username;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public Set<Role> getRoles() {
    return roles;
  }
}
