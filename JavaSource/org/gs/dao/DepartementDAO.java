package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.gs.model.Department;

public class DepartementDAO extends DAO<Department>{

	public DepartementDAO(Connection con) {
		// TODO Auto-generated constructor stub
		super(con);
	}

	@Override
	public boolean create(Department object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Department object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Department object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Department find(int id) {
		// TODO Auto-generated method stub
		String requete = "SELECT * FROM WHRERE department_id=?";
		
		try {
			PreparedStatement statement = this.con.prepareStatement(requete);
			ResultSet result = statement.executeQuery();
			
			if(result.first()) {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		
		return null;
	}


}
