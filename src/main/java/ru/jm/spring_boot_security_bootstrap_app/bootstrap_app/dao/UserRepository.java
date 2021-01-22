package ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User getUserByUsername(String username);
}
