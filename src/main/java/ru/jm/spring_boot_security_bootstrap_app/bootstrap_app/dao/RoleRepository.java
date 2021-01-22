package ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.dao;

import org.springframework.data.repository.CrudRepository;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
