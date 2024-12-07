package web.controller;

import web.model.User;
import web.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UsersController {
    private final UserServiceImpl userService;
    @Autowired
    public UsersController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping("/")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("list", users);
        return "users";
    }
}
