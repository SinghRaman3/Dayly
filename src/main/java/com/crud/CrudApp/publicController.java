package com.crud.CrudApp;

import com.crud.CrudApp.User.User;
import com.crud.CrudApp.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class publicController {

    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public void createUser(@RequestBody User newUser) {
        userService.saveNewUser(newUser);
    }
}
