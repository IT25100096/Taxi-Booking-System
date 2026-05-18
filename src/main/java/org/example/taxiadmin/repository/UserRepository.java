package org.example.taxiadmin.repository;

import org.example.taxiadmin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Find a user by their email
    Optional<User> findByEmail(String email);

    // Check if an email already exists
    boolean existsByEmail(String email);

}
