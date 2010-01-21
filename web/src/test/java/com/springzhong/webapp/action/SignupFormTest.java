package com.springzhong.webapp.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.subethamail.wiser.Wiser;

import com.springzhong.Constants;
import com.springzhong.model.Address;
import com.springzhong.model.User;
import com.springzhong.service.MailEngine;
import com.springzhong.service.RoleManager;
import com.springzhong.service.UserManager;

public class SignupFormTest extends BasePageTestCase {
    private SignupForm bean;

    @Autowired
    UserManager userManager;
    
    @Autowired
    RoleManager roleManager;
    
    @Autowired
    SimpleMailMessage mailMessage;
    
    @Autowired
    MailEngine mailEngine;
    
    @Before
    public void onSetUp() throws Exception {
    	super.onSetUp();
        bean = new SignupForm();
        bean.setUserManager(userManager);
        bean.setRoleManager(roleManager);
        bean.setMessage(mailMessage);
        bean.setMailEngine(mailEngine);
        bean.setTemplateName("accountCreated.vm");
    }

    @Test
    public void testExecute() throws Exception {
        User user = new User("self-registered");
        user.setPassword("Password1");
        user.setConfirmPassword("Password1");
        user.setFirstName("First");
        user.setLastName("Last");

        Address address = new Address();
        address.setCity("Denver");
        address.setProvince("CO");
        address.setCountry("USA");
        address.setPostalCode("80210");
        user.setAddress(address);

        user.setEmail("self-registered@raibledesigns.com");
        user.setWebsite("http://raibledesigns.com");
        user.setPasswordHint("Password is one with you.");
        bean.setUser(user);

       // start SMTP Server
        Wiser wiser = new Wiser();
        wiser.setPort(getSmtpPort());
        wiser.start();

        assertTrue(bean.save().equals("mainMenu"));
        assertFalse(bean.hasErrors());

        // verify an account information e-mail was sent
        wiser.stop();
        assertTrue(wiser.getMessages().size() == 1);

        // verify that success messages are in the session
        assertNotNull(bean.getSession().getAttribute(Constants.REGISTERED));

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
