package com.springzhong.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.db4o.ObjectSet;
import com.springzhong.Constants;
import com.springzhong.model.Role;
import com.springzhong.util.IObjectContainerMaintenance;

public class Db4oRoleDaoTest extends Db4oBaseDaoTestCase {
	
    @Autowired
    private IRoleDao roleDaoDb4o;
    
    @Autowired
    private ILookupDao lookupDaoDb4o;
       
    @Autowired
	private IObjectContainerMaintenance oDatabaseMaintenance;
	
    @Test
    public void testGetRoleInvalid() throws Exception {
        Role role = roleDaoDb4o.getRoleByName("badrolename");
        assertNull(role);
    }

    @Test
    public void testGetRole() throws Exception {
        Role role = roleDaoDb4o.getRoleByName(Constants.USER_ROLE);
        assertNotNull(role);
    }

    @Test
    public void testUpdateRole() throws Exception {
        Role role = roleDaoDb4o.getRoleByName("ROLE_USER");
        role.setDescription("test descr");
        roleDaoDb4o.save(role);
        role = roleDaoDb4o.getRoleByName("ROLE_USER");
        assertEquals("test descr", role.getDescription());
    }

    public static void listResult(ObjectSet result) {
        System.out.println(result.size());
        while(result.hasNext()) {
            System.out.println(result.next());
        }
    }
    
    @Test
    public void testAddAndRemoveRole() throws Exception {
    	//oDatabaseMaintenance.ClearAllObjects();
    	//oDatabaseMaintenance.PopulateInitObjects();
    	
    	
    	Role role = new Role("testrole88");
    	role.setDescription("new role descr999");
    	roleDaoDb4o.save(role);
    	role.setDescription("new role descr9999999999");
    	roleDaoDb4o.save(role);
    	
    	role = roleDaoDb4o.getRoleByName("testrole88");
        assertNotNull(role.getDescription());

        roleDaoDb4o.removeRole("testrole88");

        role = roleDaoDb4o.getRoleByName("testrole88");
        assertNull(role);
        
    }

    //@Test
    public void testFindByNamedQuery() {
        HashMap<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("name", Constants.USER_ROLE);
        //List<Role> roles = roleDaoDb4o.findByNamedQuery("findRoleByName", queryParams);
        //assertNull(roles);
        //assertTrue(roles.size() > 0);
    }
}
