package com.springzhong.dao;

import com.springzhong.model.Role;

import java.util.List;

/**
 * Lookup Data Access Object (GenericDao) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface ILookupDao {
    //~ Methods ================================================================

    /**
     * Returns all Roles ordered by name
     * @return populated list of roles
     */
    List<Role> getRoles();
}
