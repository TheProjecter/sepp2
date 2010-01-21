package com.springzhong.dao.db4o;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springmodules.db4o.Db4oTemplate;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.springzhong.dao.IRoleDao;
import com.springzhong.model.ObjectIdSequenceNo;
import com.springzhong.model.Role;
import com.springzhong.model.User;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 * 
 * @author <a href="mailto:davidmahorse@gmail.com">Spring Zhong</a>
 */
@Repository
public class RoleDaoDb4o extends GenericDaoDb4o<Role> implements IRoleDao {

	@Autowired
	public void setObjectContainer(ObjectContainer objectContainer) {
		Db4oTemplate db4oTemplate = new Db4oTemplate(objectContainer);
		setDb4oTemplate(db4oTemplate);
	}

	/**
	 * Constructor to create a Generics-based version using Role as the entity
	 */
	public RoleDaoDb4o() {
		super(Role.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getRoles() {
		Query query = getDb4oTemplate().query();
		query.constrain(Role.class);
		query.descend("name");
		ObjectSet result = query.execute();
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Role getRoleByName(final String rolename) {
		List<Role> roles = getDb4oTemplate().query(new Predicate<Role>() {
			private static final long serialVersionUID = -1387148454736958183L;

			public boolean match(Role role) {
				return role.getName().equals(rolename);
			}
		});

		if (roles.isEmpty()) {
			return null;
		} else {
			return roles.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role save(Role object){
		String s = "oi";
		synchronized (s) {
			if (object.getId() == null) {
				ObjectIdSequenceNo oi = getObjectIdSequenceNo();
				oi.roleIdSequenceNo++;
				object.setId(oi.roleIdSequenceNo);
				saveObjectIdSequenceNo();
			}
		}
		super.save(object);
		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeRole(String rolename) {
		Object role = getRoleByName(rolename);
		getDb4oTemplate().delete(role);
	}
}
