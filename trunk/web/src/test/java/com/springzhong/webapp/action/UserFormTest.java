package com.springzhong.webapp.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springzhong.model.User;
import com.springzhong.service.UserManager;

public class UserFormTest extends BasePageTestCase {
    private UserForm bean;
    
    @Autowired
    private UserManager userManager;

    @Before
    public void onSetUp() throws Exception {
        super.onSetUp();
        bean = new UserForm();
        bean.setUserManager(userManager);
        assertNotNull(bean);
    }

    @After
    public void onTearDown() throws Exception {
        super.onTearDown();
        bean = null;
    }
    
    @Test
    public void testEdit() throws Exception {
        bean.setId("1");
        assertTrue(bean.edit().equals("editProfile"));
        assertNotNull(bean.getUser().getUsername());
        assertFalse(bean.hasErrors());
    }

    @Test
    public void testSave() throws Exception {
        User user = userManager.getUser("1");
        user.setPassword("user");
        user.setConfirmPassword("user");
        bean.setUser(user);

        assertTrue(bean.save().equals("mainMenu"));
        assertNotNull(bean.getUser());
        assertFalse(bean.hasErrors());
    }
 
    @Test
    public void testRemove() throws Exception {
        User user2Delete = new User();
        user2Delete.setId(2L);
        bean.setUser(user2Delete);
        assertTrue(bean.delete().equals("list"));
        assertFalse(bean.hasErrors());
    }
}
