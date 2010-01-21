package com.springzhong.webapp.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.subethamail.wiser.Wiser;

import com.springzhong.service.MailEngine;
import com.springzhong.service.UserManager;

public class PasswordHintTest extends BasePageTestCase {
    private PasswordHint bean;

    @Autowired
    UserManager userManager;
    
    @Autowired
    SimpleMailMessage mailMessage;
    
    @Autowired
    MailEngine mailEngine;
        
    @Before
    public void before() throws Exception{
    	super.onSetUp();
    	bean = new PasswordHint();
        bean.setUserManager(userManager);
        bean.setMessage(mailMessage);
        bean.setMailEngine(mailEngine);
        bean.setTemplateName("accountCreated.vm");
    }
    
    @Test
    public void testExecute() throws Exception {
        // start SMTP Server
        Wiser wiser = new Wiser();
        wiser.setPort(getSmtpPort());
        wiser.start();
        
        bean.setUsername("user");
        //assertTrue(bean.execute().equals("success"));
        //assertFalse(bean.hasErrors());
        
        // verify an account information e-mail was sent
        wiser.stop();
        //assertTrue(wiser.getMessages().size() == 1);

        // verify that success messages are in the request
        //assertNotNull(bean.getSession().getAttribute("messages"));
    }
}
