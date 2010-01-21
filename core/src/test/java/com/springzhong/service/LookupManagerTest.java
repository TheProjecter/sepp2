package com.springzhong.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springzhong.model.LabelValue;

public class LookupManagerTest extends BaseManagerTestCase {
	private Log log = LogFactory.getLog(LookupManagerTest.class);
	
	@Autowired
	LookupManager dao;
	
	@Test
	public void testGetAllRoles()
	{
		List<LabelValue> roles = dao.getAllRoles();
		assertTrue(roles.size() > 0);
	}

}
