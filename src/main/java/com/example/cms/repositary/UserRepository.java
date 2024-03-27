package com.example.cms.repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);
}
