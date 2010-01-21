package com.springzhong.webapp.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springzhong.service.UserManager;

public class UserListTest extends BasePageTestCase {
    private UserList bean;
    
    @Autowired
    private UserManager userManager;

    @Before
	public void onSetUp() throws Exception {    
        super.onSetUp();
        bean = new UserList();
        bean.setSortColumn("username");
        bean.setUserManager(userManager);
    }
    
    @Test
    public void testSearch() throws Exception {
        assertTrue(bean.getUsers().size() >= 1);
        assertFalse(bean.hasErrors());
    }

}
