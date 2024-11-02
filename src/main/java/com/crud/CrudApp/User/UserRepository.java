package com.crud.CrudApp.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(String username);
    void deleteByUserName(String username);
}
