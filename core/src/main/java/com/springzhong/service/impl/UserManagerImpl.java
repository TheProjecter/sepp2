package com.springzhong.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.springzhong.dao.IUserDao;
import com.springzhong.model.User;
import com.springzhong.service.UserExistsException;
import com.springzhong.service.UserManager;
import com.springzhong.service.UserService;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "com.springzhong.service.UserService")
@Repository
public class UserManagerImpl extends GenericManagerImpl<User> implements
		UserManager, UserService {
	private PasswordEncoder passwordEncoder;
	private IUserDao userDao;

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserManagerImpl()
	{
	}
	
	@Autowired
	public void setUserDaoDb4orImpl(IUserDao userDaoDb4o) {
		this.dao = userDaoDb4o;
		this.userDao = userDaoDb4o;
	}

	/**
	 * {@inheritDoc}
	 */
	public User getUser(String userId) {
		return userDao.get(new Long(userId));
	}

	/**
	 * {@inheritDoc}
	 */
	public List<User> getUsers() {
		return userDao.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public User saveUser(User user) throws UserExistsException {

		if (user.getVersion() == null) {
			// if new user, lowercase userId
			user.setUsername(user.getUsername().toLowerCase());
		}

		// Get and prepare password management-related artifacts
		boolean passwordChanged = false;
		if (passwordEncoder != null) {
			// Check whether we have to encrypt (or re-encrypt) the password
			if (user.getVersion() == null) {
				// New user, always encrypt
				passwordChanged = true;
			} else {
				// Existing user, check password in DB
				String currentPassword = userDao.getUserPassword(user
						.getUsername());
				if (currentPassword == null) {
					passwordChanged = true;
				} else {
					if (!currentPassword.equals(user.getPassword())) {
						passwordChanged = true;
					}
				}
			}

			// If password was changed (or new user), encrypt it
			if (passwordChanged) {
				user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
			}
		} else {
			log.warn("PasswordEncoder not set, skipping password encryption...");
		}

		try {
			return userDao.saveUser(user);
		} catch (UserExistsException e) {
			// e.printStackTrace();
			log.warn(e.getMessage());
			throw new UserExistsException("User '" + user.getUsername()
					+ "' already exists!");
		} catch (JpaSystemException e) { // needed for JPA
			// e.printStackTrace();
			log.warn(e.getMessage());
			throw new UserExistsException("User '" + user.getUsername()
					+ "' already exists!");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeUser(String userId) {
		log.debug("removing user: " + userId);
		userDao.remove(new Long(userId));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param username
	 *            the login name of the human
	 * @return User the populated user object
	 * @throws UsernameNotFoundException
	 *             thrown when username not found
	 */
	public User getUserByUsername(String username)
			throws UsernameNotFoundException {
		return (User) userDao.loadUserByUsername(username);
	}
}
