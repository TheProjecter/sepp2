package com.springzhong.dao.db4o;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springmodules.db4o.Db4oTemplate;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.springzhong.dao.IUserDao;
import com.springzhong.model.ObjectIdSequenceNo;
import com.springzhong.model.User;
import com.springzhong.service.UserExistsException;

/**
 * This class interacts with SpringModules's Db4oTemplate to save/delete and
 * retrieve User objects.
 * 
 * @author Modified by <a href="mailto:davidmahorse@gmail.com">Spring Zhong</a>
 */
@Repository("userDao")
public class UserDaoDb4o extends GenericDaoDb4o<User> implements
		IUserDao, UserDetailsService {

	/**
	 * 
	 */
	@Autowired
	public void setObjectContainer(ObjectContainer objectContainer) {
		Db4oTemplate db4oTemplate = new Db4oTemplate(objectContainer);
		setDb4oTemplate(db4oTemplate);
	}
	
	/**
	 * Constructor that sets the entity to User.class.
	 */
	public UserDaoDb4o() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	public User get(final long id) {
		List<User> users = getDb4oTemplate().query(new Predicate<User>() {
			private static final long serialVersionUID = -580376693126256351L;

			public boolean match(User user) {
				return user.getId() == id;
			}
		});

		if (users == null || users.isEmpty()) {
			throw new UsernameNotFoundException("user '" + id
					+ "' not found...");
		} else {
			return users.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(long id) {
		User user = get(id);
		return user != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(long id) {
		User user = get(id);
		getDb4oTemplate().delete(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		Query query = getDb4oTemplate().query();
		query.constrain(User.class);
		query.descend("username");
		ObjectSet result = query.execute();
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public User saveUser(User user)  throws UserExistsException{
		if (log.isDebugEnabled())
			log.debug("user's id: " + user.getId());

		if (user.getId() == null) {
			String s = "oi";
			synchronized (s) {
				ObjectIdSequenceNo oi = getObjectIdSequenceNo();
				oi.userIdSequenceNo++;
				user.setId(oi.userIdSequenceNo);
				saveObjectIdSequenceNo();
			}
		}
		else if(!getDb4oTemplate().isStored(user))
		{
			if(exists(user.getId()))
			{
				throw new UserExistsException("UserExistsException");
			}
		}
		
		super.save(user);
		return user;
	}

	/**
	 * Overridden simply to call the saveUser method. This is happenening
	 * because saveUser flushes the session and saveObject of BaseDaoHibernate
	 * does not.
	 * 
	 * @param user
	 *            the user to save
	 * @return the modified user (with a primary key set if they're new)
	 */
	@Override
	public User save(User user) {
		
		try {
			return this.saveUser(user);
		} catch (UserExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		List<User> users = getDb4oTemplate().query(new Predicate<User>() {
			private static final long serialVersionUID = -2664829087651136220L;

			public boolean match(User user) {
				return user.getUsername().equals(username);
			}
		});

		if (users == null || users.isEmpty()) {
			throw new UsernameNotFoundException("user '" + username
					+ "' not found...");
		} else {
			return (UserDetails) users.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public String getUserPassword(final String username) {
		List<User> users = getDb4oTemplate().query(new Predicate<User>() {
			private static final long serialVersionUID = 5088020108996914196L;

			public boolean match(User user) {
				return user.getUsername().equals(username);
			}
		});
		User theUser;
		if (users == null || users.isEmpty()) {
			throw new UsernameNotFoundException("user '" + username
					+ "' not found...");
		} else {
			theUser = users.get(0);
		}
		return theUser.getPassword();
	}

}
