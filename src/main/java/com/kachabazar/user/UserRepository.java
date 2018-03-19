package com.kachabazar.user;


import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<WebUser, String> {






    WebUser findByUsername(String username);


}