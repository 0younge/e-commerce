package com.ecommerce.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.users.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
