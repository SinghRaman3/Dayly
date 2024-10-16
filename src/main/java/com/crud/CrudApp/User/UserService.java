package com.crud.CrudApp.User;

import com.crud.CrudApp.User.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id) {
        return userRepository.findById(String.valueOf(id));
    }

    public User getByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(String.valueOf(id));
    }

}

