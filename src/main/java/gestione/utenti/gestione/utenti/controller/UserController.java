package gestione.utenti.gestione.utenti.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;

import gestione.utenti.gestione.utenti.model.UserModel;

import gestione.utenti.gestione.utenti.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/users") //tutti gli endpoint avranno base (/users)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public UserModel createUser(@RequestBody UserModel user) {
        return userService.createUser(user);
    }

    @GetMapping("/findById")
    public Optional<UserModel> findUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("deleteAllUsers")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @DeleteMapping("deleteUser")
    public void deleteUser(UserModel userModel) {
        userService.deleteUser(userModel);
    }
}
