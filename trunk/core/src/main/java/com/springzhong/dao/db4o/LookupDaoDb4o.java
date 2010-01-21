package com.springzhong.dao.db4o;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springmodules.db4o.Db4oTemplate;

import com.db4o.ObjectContainer;
import com.springzhong.dao.IDaoInterface;
import com.springzhong.model.Role;

/**
 * Db4o implementation of LookupDao.
 *
 * @author <a href="mailto:davidmahorse@gmail.com">Spring Zhong</a>
 */
@Repository
public class LookupDaoDb4o extends GenericDaoDb4o<Role> implements IDaoInterface {
    private Log log = LogFactory.getLog(LookupDaoDb4o.class);
    private Db4oTemplate db4oTemplate;
    
    @Autowired
    public void setLookupDaoDb4o(ObjectContainer objectContainer) {
        this.db4oTemplate = new Db4oTemplate(objectContainer);
        setDb4oTemplate(db4oTemplate);
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");
        return db4oTemplate.get(Role.class);
    }
}
