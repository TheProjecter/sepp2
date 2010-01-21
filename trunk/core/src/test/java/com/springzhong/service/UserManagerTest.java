package com.springzhong.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.springzhong.Constants;
import com.springzhong.model.User;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManagerTest extends BaseManagerTestCase {
    private Log log = LogFactory.getLog(UserManagerTest.class);
    @Autowired
    private UserManager userManager;
    //@Autowired
    //private RoleManager roleManager;
    private User user;

    @Test
    public void testGetUser() throws Exception {
        user = userManager.getUserByUsername("user");
        assertNotNull(user);
        
        log.debug(user);
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void testSaveUser() throws Exception {
        user = userManager.getUserByUsername("user");
        user.setPhoneNumber("303-555-1212");

        log.debug("saving user with updated phone number: " + user);

        user = userManager.saveUser(user);
        assertEquals("303-555-1212", user.getPhoneNumber());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void testAddAndRemoveUser() throws Exception {
        user = new User();

        user.setUsername("john");
        
        //user.addRole(roleManager.getRole(Constants.USER_ROLE));

        user = userManager.saveUser(user);
        assertEquals("john", user.getUsername());
        //assertEquals(1, user.getRoles().size());

        log.debug("removing user...");

        userManager.removeUser(user.getId().toString());

        try {
            user = userManager.getUserByUsername("john");
            fail("Expected 'Exception' not thrown");
        } catch (Exception e) {
            log.debug(e);
            assertNotNull(e);
        }
    }
}
