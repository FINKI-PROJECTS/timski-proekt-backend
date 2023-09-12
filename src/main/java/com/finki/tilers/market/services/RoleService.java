package com.finki.tilers.market.services;

import com.finki.tilers.market.exception.CustomBadRequestException;
import com.finki.tilers.market.model.entity.Role;
import com.finki.tilers.market.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Initializes the roles by checking if certain roles exist and creates them if not.
     * This method is annotated with @PostConstruct to ensure it is executed after the bean is constructed.
     */
    @PostConstruct
    public void init() {
        if (getByNameOrNull("ADMIN") == null) {
            roleRepository.save(Role.builder().roleName("ADMIN").build());
        }
        if (getByNameOrNull("USER") == null) {
            roleRepository.save(Role.builder().roleName("USER").build());
        }
        if (getByNameOrNull("MODERATOR") == null) {
            roleRepository.save(Role.builder().roleName("MODERATOR").build());
        }

    }

    /**
     * Retrieves a role by its name.
     *
     * @param name The name of the role.
     * @return The Role object with the specified name.
     * @throws CustomBadRequestException if the role doesn't exist.
     */
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByRoleName(name)
                .orElseThrow(() -> new CustomBadRequestException("Role with name " + name + "doesn't exist"));
    }


    /**
     * Retrieves all roles.
     *
     * @return A list of all roles.
     */
    public List<Role> getAll() {
        return roleRepository.findAll();
    }



    /**
     * Retrieves all roles as a list of maps, where each map contains the "id" and "value" of a role.
     *
     * @return A list of maps representing the roles.
     */
    public List<Map<String, String>> getAllForList() {
        return roleRepository.findAll().stream().map(
                (role) -> {
                    Map<String, String> res = new HashMap<>();
                    res.put("id", role.getId().toString());
                    res.put("value", role.getRoleName());
                    return res;
                }
        ).collect(Collectors.toList());
    }

    /**
     * Retrieves a role by its name, or returns null if the role doesn't exist.
     *
     * @param name The name of the role.
     * @return The Role object with the specified name, or null if it doesn't exist.
     */
    public Role getByNameOrNull(String name) {
        return roleRepository.getRoleByRoleName(name).orElse(null);
    }

    /**
     * Retrieves a list of roles based on their IDs.
     *
     * @param ids The IDs of the roles to retrieve.
     * @return A list of roles with the specified IDs.
     * @throws CustomBadRequestException if any of the roles with the given IDs doesn't exist.
     */
    public List<Role> getRoleListByIDs(List<Long> ids) throws CustomBadRequestException {
        return roleRepository.getRoleByIdIn(ids);
    }


}