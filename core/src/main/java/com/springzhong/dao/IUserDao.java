package com.springzhong.dao;

import com.springzhong.model.User;
import com.springzhong.service.UserExistsException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * User Data Access Object (GenericDao) interface.
 *
 * @author <a href="mailto:davidmahorse@gmail.com">Spring Zhong</a>
 */
public interface IUserDao extends IGenericDao<User> {

	/**
	 * 
	 * @param id as id of user
	 * @return
	 */
	public User get(final long id);
    /**
     * Gets users information based on login name.
     * @param username the user's username
     * @return userDetails populated userDetails object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException thrown when user not found in database
     */
    @Transactional
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Gets a list of users ordered by the uppercase version of their username.
     *
     * @return List populated list of users
     */
    List<User> getUsers();

    /**
     * Saves a user's information.
     * @param user the object to be saved
     * @return the persisted User object
     */
    User saveUser(User user)  throws UserExistsException;

    /**
     * Retrieves the password in DB for a user
     * @param username the user's username
     * @return the password in DB, if the user is already persisted
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    String getUserPassword(String username);
    
    /**
     * 
     * @param id
     * @return
     */
    public boolean exists(long id);
    
    /**
     * 
     * @param id
     */
    public void remove(long id);
    
}
