package ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.dao.RoleRepository;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.dao.UserRepository;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.Role;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.User;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void removeUser(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public User getUser(int id) {
        User user = null;

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) { //если в optional присутствует User
            user = optionalUser.get(); //назначаю переменной user этого User
        } else {
            System.out.println("Optional does not contain a user"); //если Optional пустой, то вывожу в консоль ошибку
        }

        return user;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roles;
    }
}
