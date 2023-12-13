package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        List<User> users = userService.index();
        model.addAttribute("users", users);
        return "adminPage";
    }

    @GetMapping("new")
    public String addUser(@ModelAttribute("user") User user) {
        return "adminPage";
    }


    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam("roles") String[] selectedRoles) {
        userService.createUserWithRoles(user, selectedRoles);
        return "redirect:/admin";
    }

    @GetMapping("edit")
    public String editUser(@RequestParam("id") Long userId, Model model) {
        User user = userService.getById(userId);
        model.addAttribute("user", user);
        return "/edit";
    }

    @PostMapping("/edit")
    public String editUserSubmit(@ModelAttribute User editedUser, @RequestParam("roles") String[] selectedRoles) {
        userService.editUserWithRoles(editedUser, selectedRoles);
        return "redirect:/admin";
    }

    @GetMapping("delete")
    public String deleteUser(@RequestParam("id") Long userId, Model model) {
        User user = userService.getById(userId);
        model.addAttribute("user", user);
        return "adminPage";
    }

    @PostMapping("/delete")
    public String deleteUserSubmit(@ModelAttribute User user) {
        userService.delete(user.getId());
        return "redirect:/admin";
    }
}