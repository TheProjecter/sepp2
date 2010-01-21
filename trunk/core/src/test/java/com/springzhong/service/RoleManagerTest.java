package com.springzhong.service;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.springzhong.model.Role;

public class RoleManagerTest extends BaseManagerTestCase {
	private Log log = LogFactory.getLog(RoleManagerTest.class);

	@Autowired
	private RoleManager roleManager;
	
	@Test
	public void testGetRoles()
	{
		List<Role> roles = roleManager.getRoles();
		assertTrue(roles.size() > 0);
	}
}
