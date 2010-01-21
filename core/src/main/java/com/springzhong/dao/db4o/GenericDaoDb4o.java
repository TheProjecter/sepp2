package com.springzhong.dao.db4o;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springmodules.db4o.Db4oTemplate;
import com.db4o.ObjectSet;
import com.springzhong.Constants;
import com.springzhong.dao.IGenericDao;
import com.springzhong.model.ObjectIdSequenceNo;

public class GenericDaoDb4o<T> implements IGenericDao<T> {
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());
	private Class<T> persistentClass;
	private Db4oTemplate db4oTemplate;
	private ObjectIdSequenceNo objectIdSequenceNo;

	public GenericDaoDb4o()
	{
		
	}
	
	protected void setDb4oTemplate(Db4oTemplate db4oTemplate) {
		this.db4oTemplate = db4oTemplate;
		// load the ObjectIdSequenceNo object from db4o
		ObjectIdSequenceNo proto = ObjectIdSequenceNo.getInstance();
		while (true) {
			ObjectSet result = db4oTemplate.getObjectContainer()
					.queryByExample(proto);
			if (result.size() <= 0) {
				String s = "oi";
				synchronized (s) {
					// if another thread is waiting enter this code?
					result = db4oTemplate.getObjectContainer().queryByExample(
							proto);
					if (result.size() > 0) {
						continue;
					}
					ObjectIdSequenceNo oi = proto;
					oi.roleIdSequenceNo = Constants.ROLESTARTEDNO;
					oi.userIdSequenceNo = Constants.USERSTARTEDNO;
					db4oTemplate.set(oi);
					objectIdSequenceNo = oi;
				}
			} else {
				objectIdSequenceNo = (ObjectIdSequenceNo) result.get(0);
			}
			break;// exit while
		}
	}


	public ObjectIdSequenceNo getObjectIdSequenceNo() {
		return objectIdSequenceNo;
	}

	public void saveObjectIdSequenceNo() {
		db4oTemplate.getObjectContainer().store(objectIdSequenceNo);
	}

	/**
	 * Constructor that takes in a class to see which type of entity to persist.
	 * Use this constructor when subclassing.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericDaoDb4o(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
		
	}

	public Db4oTemplate getDb4oTemplate() {
		return this.db4oTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return db4oTemplate.get(this.persistentClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public T save(T object) {
		db4oTemplate.set(object);
		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		return null;// no use
	}
}
