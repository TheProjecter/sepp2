package com.springzhong.service;

import com.springzhong.model.Role;

import java.util.List;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface RoleManager extends GenericManager<Role> {
    /**
     * {@inheritDoc}
     */
    List getRoles();

    /**
     * {@inheritDoc}
     */
    Role getRole(String rolename);

    /**
     * {@inheritDoc}
     * @throws Exception 
     */
    Role saveRole(Role role) throws Exception;

    /**
     * {@inheritDoc}
     */
    void removeRole(String rolename);
}
