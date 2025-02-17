package com.example.bikash.SpringTest.Repos;


import com.example.bikash.SpringTest.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Optional<User>findByEmail(String email);


    @Query("select  u from User  u where u.username =:username")
    User findByUsernameByQuery( @Param("username") String username);

}


