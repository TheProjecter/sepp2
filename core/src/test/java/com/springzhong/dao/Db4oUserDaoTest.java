package com.springzhong.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.compass.core.CompassCallbackWithoutResult;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.gps.CompassGps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.ExpectedException;
import com.springzhong.Constants;
import com.springzhong.model.Address;
import com.springzhong.model.Role;
import com.springzhong.model.User;

public class Db4oUserDaoTest extends Db4oBaseDaoTestCase {
    @Autowired
    private IUserDao dao;
    @Autowired
    private IRoleDao rdao;
    //@Autowired
    private CompassTemplate compassTemplate;
    //@Autowired
    private CompassGps compassGps;

    @Test
    @ExpectedException(UsernameNotFoundException.class)
    public void testGetUserInvalid() throws Exception {
        // should throw UsernameNotFoundException
        dao.get(1000L);
    }

    @Test
    public void testGetUser() throws Exception {
        User user = dao.get(1L);

        assertNotNull(user);
        assertEquals(1, user.getRoles().size());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testGetUserPassword() throws Exception {
        User user = dao.get(1L);
        String password = dao.getUserPassword(user.getUsername());
        assertNotNull(password);
        log.debug("password: " + password);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = dao.get(1L);

        Address address = user.getAddress();
        address.setAddress("new address");

        dao.saveUser(user);
        //flush();

        user = dao.get(1L);
        assertEquals(address, user.getAddress());
        assertEquals("new address", user.getAddress().getAddress());

        // verify that violation occurs when adding new user with same username
        //user.setId(null);

        // should throw DataIntegrityViolationException
        dao.saveUser(user);
    }

    @Test
    public void testAddUserRole() throws Exception {
        User user = dao.get(1L);
        assertEquals(1, user.getRoles().size());

        Role role = rdao.getRoleByName(Constants.ADMIN_ROLE);
        user.addRole(role);
        dao.saveUser(user);
        //flush();

        user = dao.get(1L);
        assertEquals(2, user.getRoles().size());

        //add the same role twice - should result in no additional role
        user.addRole(role);
        dao.saveUser(user);
        //flush();

        user = dao.get(1L);
        assertEquals("more than 2 roles", 2, user.getRoles().size());

        user.getRoles().remove(role);
        dao.saveUser(user);
        //flush();

        user = dao.get(1L);
        assertEquals(1, user.getRoles().size());
    }

    @Test
    @ExpectedException(UsernameNotFoundException.class)
    public void testAddAndRemoveUser() throws Exception {
        User user = new User("testuser");
        user.setPassword("testpass");
        user.setFirstName("Test");
        user.setLastName("Last");
        Address address = new Address();
        address.setCity("Denver");
        address.setProvince("CO");
        address.setCountry("USA");
        address.setPostalCode("80210");
        user.setAddress(address);
        user.setEmail("testuser@appfuse.org");
        user.setWebsite("http://raibledesigns.com");

        Role role = rdao.getRoleByName(Constants.USER_ROLE);
        assertNotNull(role.getId());
        user.addRole(role);

        user = dao.saveUser(user);
        //flush();

        assertNotNull(user.getId());
        user = dao.get(user.getId());
        assertEquals("testpass", user.getPassword());

        dao.remove(user.getId());
        //flush();

        // should throw UsernameNotFoundException
        dao.get(user.getId());
    }

    @Test
    public void testUserExists() throws Exception {
        boolean b = dao.exists(1L);
        assertTrue(b);
    }

    @Test
    @ExpectedException(UsernameNotFoundException.class)
    public void testUserNotExists() throws Exception {
        boolean b = dao.exists(111L);
        assertFalse(b);
    }

    //@Test
    public void testUserSearch() throws Exception {
        // reindex all the data
        compassGps.index();

        User user = compassTemplate.get(User.class, -2);
        assertNotNull(user);
        assertEquals("Matt", user.getFirstName());

        compassTemplate.execute(new CompassCallbackWithoutResult() {
            @Override
            protected void doInCompassWithoutResult(CompassSession compassSession) throws CompassException {
                CompassHits hits = compassSession.find("Matt");
                assertEquals(1, hits.length());
                assertEquals("Matt", ((User) hits.data(0)).getFirstName());
                assertEquals("Matt", hits.resource(0).getValue("firstName"));
            }
        });

        // test mirroring
        user = dao.get(-2L);
        user.setFirstName("MattX");
        dao.saveUser(user);
        flush();

        // now verify it is reflected in the index
        user = compassTemplate.get(User.class, -2);
        assertNotNull(user);
        assertEquals("MattX", user.getFirstName());

        compassTemplate.execute(new CompassCallbackWithoutResult() {
            @Override
            protected void doInCompassWithoutResult(CompassSession compassSession) throws CompassException {
                CompassHits hits = compassSession.find("MattX");
                assertEquals(1, hits.length());
                assertEquals("MattX", ((User) hits.data(0)).getFirstName());
            }
        });
    }
}
