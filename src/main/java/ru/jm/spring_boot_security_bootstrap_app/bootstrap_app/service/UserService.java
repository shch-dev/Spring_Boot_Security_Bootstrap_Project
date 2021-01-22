package ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.service;

import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.Role;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public void removeUser(int id);

    public User getUser(int id);

    public List<Role> getRoles();
}
