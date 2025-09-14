package com.sinaukoding.library_management_system.repository.managementuser;

import com.sinaukoding.library_management_system.entity.managementuser.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    Boolean existsByEmail(String email);
    Boolean existsByEmailAndIdNot(String email, String id);
    Boolean existsByUsername(String username);
    Boolean existsByUsernameAndIdNot(String username, String id);
    Optional<User> findByUsername(String username);

}
