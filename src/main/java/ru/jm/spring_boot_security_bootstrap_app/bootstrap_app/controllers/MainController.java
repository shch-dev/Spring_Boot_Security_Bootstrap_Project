package ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.Role;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.models.User;
import ru.jm.spring_boot_security_bootstrap_app.bootstrap_app.service.UserService;


import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "service/login";
    }

    @GetMapping("/admin")
    public String adminPage(@ModelAttribute(name = "user") User user, Model model) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);

        List<Role> allRoles = userService.getRoles();
        model.addAttribute("allRoles", allRoles);

        model.addAttribute("allUsers", userService.getAllUsers());

        return "administrator/adminPage";
    }

    @PostMapping("/admin/")
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/";
    }


    @PostMapping(value = "/admin/update/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/";
        }
        List<Role> allRoles = userService.getRoles();
        model.addAttribute("allRoles", allRoles);
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @RequestMapping("/admin/delete/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        userService.removeUser(id);
        return "redirect:/admin/";
    }

    //user controller
    @GetMapping(value = "/user/{id}")
    public String helloUser(Model model, @PathVariable("id") int id) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        User currentUser = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (currentUser.getId() != id && currentUser.getRoles().stream().noneMatch((x -> x.getName().contains("ROLE_ADMIN")))) {
            return "redirect:/user/" + currentUser.getId();
        }
        return "user/user";
    }

    //moderator controller
    @GetMapping("/moderator/{id}")
    public String helloModerator(Model model, @PathVariable("id") int id) {
        model.addAttribute("AllUsers", userService.getAllUsers());

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);

        return "moderator/ModeratorPage";
    }
}