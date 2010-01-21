package com.springzhong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.springzhong.dao.IRoleDao;
import com.springzhong.model.Role;
import com.springzhong.service.RoleManager;

/**
 * Implementation of RoleManager interface.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service("roleManager")
@Repository
public class RoleManagerImpl extends GenericManagerImpl<Role> implements RoleManager {
    IRoleDao roleDao;

    @Autowired
    public void setRoleDao(IRoleDao roleDao) {
        this.dao = roleDao;
        this.roleDao =  roleDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Role> getRoles() {
    	return roleDao.getRoles();
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole(String rolename) {
        return roleDao.getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    public Role saveRole(Role role) throws Exception{
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        roleDao.removeRole(rolename);
    }
}