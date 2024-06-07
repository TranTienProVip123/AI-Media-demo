package com.bezkoder.spring.thymeleaf.image.upload.repository;

import com.bezkoder.spring.thymeleaf.image.upload.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
