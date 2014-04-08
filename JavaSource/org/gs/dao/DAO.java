package org.gs.dao;

import java.sql.Connection;

public abstract class DAO<T> {

	protected Connection con = null;
	
	public DAO(Connection con) {
		// TODO Auto-generated constructor stub
		this.con = con;
	}

	//create method
	public abstract boolean create(T object);
	
	//update method
	public abstract boolean update(T object);
	
	//delete method
	public abstract boolean delete(T object);
	
	//find method
	public abstract T find(int id);
	
}
