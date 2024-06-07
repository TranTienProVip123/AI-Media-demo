package com.bezkoder.spring.thymeleaf.image.upload.repository;

import com.bezkoder.spring.thymeleaf.image.upload.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
