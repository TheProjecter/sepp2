package com.springzhong.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class tests the current LookupDao implementation class
 * @author spring zhong
 */
public class Db4oLookupDaoTest extends Db4oBaseDaoTestCase {
	@Autowired
    IDaoInterface lookupDaoDb4o;
    
    @Test
    public void testGetRoles() {
    	List roles = lookupDaoDb4o.getRoles();
        log.debug(roles);
        assertTrue(roles.size() > 0);
    }
}
