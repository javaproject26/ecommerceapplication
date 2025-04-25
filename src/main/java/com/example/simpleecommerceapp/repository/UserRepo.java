package com.example.simpleecommerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simpleecommerceapp.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
	public User findByEmailIgnoreCase(String Email);
}
