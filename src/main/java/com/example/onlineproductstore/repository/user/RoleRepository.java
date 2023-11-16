package com.example.onlineproductstore.repository.user;

import com.example.onlineproductstore.enums.RoleName;
import com.example.onlineproductstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(RoleName roleName);
}
