package com.springzhong.model;

/**
 * Because db4o doesn't support static variable storage, so this class is created.
 *
 * @author <a href="mailto:davidmahorse@gmail.com">Spring Zhong</a>
 */
public class ObjectIdSequenceNo {
	private static final ObjectIdSequenceNo m_instance = new ObjectIdSequenceNo();
	
	private ObjectIdSequenceNo(){}
	
	public static ObjectIdSequenceNo getInstance()
	{
		return m_instance;
	}
	/**
	 * The sequence no of role objects' id
	 */
	public long roleIdSequenceNo;
	
	/**
	 * The sequence no of user objects' id
	 */
	public long userIdSequenceNo;
}
