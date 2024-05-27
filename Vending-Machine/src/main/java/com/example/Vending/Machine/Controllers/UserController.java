package com.example.Vending.Machine.Controllers;

import com.example.Vending.Machine.DAOs.UserDao;
import com.example.Vending.Machine.Models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    private UserDao userDao;

    private UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userDao.getALlUsers();
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userDao.getUser(username);
    }

    @PostMapping("")
    public void createUser(@RequestBody User user) {
        userDao.createUser(user);
    }

    @PutMapping("/{username}")
    public void updateUser(@PathVariable String username, @RequestBody User user) {
        userDao.updateUser(user, false);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userDao.deleteUser(username);
    }

    @GetMapping("/{username}/roles")
    public List<String> getUserRoles(@PathVariable String username) {
        return userDao.getRolesForUser(username);
    }

    @PostMapping("/{username}/roles/{role}")
    public void removeUserRole(@PathVariable String username, @PathVariable String role) {
        userDao.removeRoleFromUser(username, role);
    }
}
