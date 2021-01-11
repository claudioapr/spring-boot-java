package br.com.claudio.calendar.repository;

import br.com.claudio.calendar.model.Role;
import br.com.claudio.calendar.model.RoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByName(RoleEnum name);

}
