package com.example.rediscachespring.repository;


import com.example.rediscachespring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
