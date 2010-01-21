package com.springzhong.service;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;

import com.springzhong.model.User;

public class UserExistsExceptionTest extends BaseManagerTestCase {
    @Autowired
    private UserManager manager;
    private Log log = LogFactory.getLog(UserExistsExceptionTest.class);

    @Test
    @ExpectedException(UserExistsException.class)
    public void testAddExistingUser() throws Exception {
        log.debug("entered 'testAddExistingUser' method");
        assertNotNull(manager);

        User user = manager.getUser("1");
        
        User user2 = new User();
        BeanUtils.copyProperties(user, user2);
        user2.setId(1L);
        user2.setVersion(null);
        user2.setRoles(null);
        
        // try saving as new user, this should fail UserExistsException b/c of unique keys
        manager.saveUser(user2);
    }    
}
