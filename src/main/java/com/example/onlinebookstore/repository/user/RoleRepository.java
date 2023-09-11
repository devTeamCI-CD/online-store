package com.example.onlinebookstore.repository.user;

import com.example.onlinebookstore.enums.RoleName;
import com.example.onlinebookstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(RoleName roleName);
}
