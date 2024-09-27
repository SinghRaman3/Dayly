package com.crud.CrudApp.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

//    @GetMapping("/getAll")
//    public Map<Integer, User> getUsers(){
//        return users;
//    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable int id) {
        return null;
    }

    @PostMapping("/create")
    public User addUser(@RequestBody User user) {
        return null;
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User user) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public User deleteUser(@PathVariable int id) {
        return null;
    }
}
