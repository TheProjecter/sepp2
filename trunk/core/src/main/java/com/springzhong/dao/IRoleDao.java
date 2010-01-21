package com.springzhong.dao;

import java.util.List;
import com.springzhong.model.Role;

/**
 * Role Data Access Object (DAO) interface.
 *
 * @author <a href="mailto:davidmahorse@gmail.com">Spring Zhong</a>
 */
public interface IRoleDao extends IGenericDao<Role> {
    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return populated role object
     */
    Role getRoleByName(String rolename);

    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    void removeRole(String rolename);
    
    /**
     * Get all roles from object container
     */
    public List<Role> getRoles();
}
