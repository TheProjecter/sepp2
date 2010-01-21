package com.springzhong.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springmodules.db4o.Db4oTemplate;
import org.springmodules.db4o.ObjectContainerFactoryBean;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.springzhong.model.Address;
import com.springzhong.model.Role;
import com.springzhong.model.User;

@Repository
public class ObjectContainerMaintenance implements ObjectContainerMaintenanceInterface{
	private Log log = LogFactory.getLog(ObjectContainerMaintenance.class);
	private Db4oTemplate db4oTemplate;

	@Autowired
	public ObjectContainerMaintenance(ObjectContainer objectContainer) {
		this.db4oTemplate = new Db4oTemplate(objectContainer);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void ClearAllObjects()
	{
		ObjectContainer oc = db4oTemplate.getObjectContainer();
		ObjectSet result = oc.queryByExample(new Object());
		while (result.hasNext()) {
			oc.delete(result.next());
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void PopulateInitObjects()
	{
		User user = new User();
		user.setId(1L);
		user.setUsername("user");
		user.setPassword("12dea96fec20593566ab75692c9949596833adc9");
		user.setFirstName("Tomcat456");
		user.setLastName("User");
		Address address = new Address();
		address.setCity("Denver");
		address.setCountry("US");
		address.setPostalCode("80210");
		address.setProvince("CO");
		//assign address
		user.setAddress(address);
		user.setWebsite("http://tomcat.apache.org");
		user.setPasswordHint("<![CDATA[The same as your username.]]>");
		user.setVersion(1);
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setAccountLocked(false);
		user.setCredentialsExpired(false);
		//assign role
		Role role = new Role();
		role.setId(2L);
		role.setName("ROLE_USER");
		role.setDescription("<![CDATA[Default role for all Users]]>");
		ObjectContainer oc = db4oTemplate.getObjectContainer();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);
		oc.store(user);//store to object container
		user = new User();
		user.setId(2L);
		user.setUsername("admin");
		user.setPassword("d033e22ae348aeb5660fc2140aec35850c4da997");
		user.setFirstName("Mattdb4o");
		user.setLastName("Raible");
		address = new Address();
		address.setCity("Denver");
		address.setCountry("US");
		address.setPostalCode("80210");
		address.setProvince("CO");
		//assign address
		user.setAddress(address);
		user.setEmail("matt@raibledesigns.com");
		user.setWebsite("http://raibledesigns.com");
		user.setPasswordHint("<![CDATA[The same as your username.]]>");
		user.setVersion(1);
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setAccountLocked(false);
		user.setCredentialsExpired(false);
		//assign role
		roles = new HashSet<Role>();
		role = new Role(); 
		role.setId(1L);
		role.setName("ROLE_ADMIN");
		role.setDescription("<![CDATA[Administrator role (can edit Users)]]>");
		roles.add(role);
		user.setRoles(roles);
		oc.store(user);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ObjectContainer getObjectContainer()
	{
		/*
		ResourceBean res;
		Resource resource;
		resource = new Resource("file:///home/springzhong/formula1.db4o");
		ObjectContainerFactoryBean objectContainerFactoryBean = new ObjectContainerFactoryBean();
		objectContainerFactoryBean.setDatabaseFile(""); 
		*/
		return null;	
	}
}
