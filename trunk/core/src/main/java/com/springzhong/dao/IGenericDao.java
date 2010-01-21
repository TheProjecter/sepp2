package com.springzhong.dao;

import java.util.List;


/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 * 
 * <p>
 * Extend this interface if you want typesafe (no casting necessary) DAO's for
 * your domain objects.
 * 
 * @author <a href="mailto:davidmahorse@gmail.com">spring zhong</a>
 * @param <T>
 *            a type variable
 */
public interface IGenericDao<T> {

	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @return List of populated objects
	 */
	List<T> getAll();

	/**
	 * Generic method to save an object - handles both update and insert.
	 */
	T save(T object);

}
