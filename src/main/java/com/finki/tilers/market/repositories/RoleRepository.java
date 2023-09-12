package com.finki.tilers.market.repositories;


import com.finki.tilers.market.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByRoleName(String role);

    List<Role> getRoleByIdIn(List<Long> ids);

}
