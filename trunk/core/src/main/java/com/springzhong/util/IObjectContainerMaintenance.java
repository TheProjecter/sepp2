package com.springzhong.util;

public interface IObjectContainerMaintenance {
	/**
	 * clear all objects in database
	 */
	public void ClearAllObjects();

	/**
	 * populate data into databases
	 */
	public void PopulateInitObjects();
}
