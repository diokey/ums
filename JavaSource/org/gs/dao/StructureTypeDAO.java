package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.StructureType;

public class StructureTypeDAO extends DAO<StructureType>{

	public StructureTypeDAO(Connection con) {
		// TODO Auto-generated constructor stub
		super(con);
	}
	
	public StructureTypeDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}

	@Override
	public boolean create(StructureType object) {
		// TODO Auto-generated method stub
		
		
		return false;
	}

	@Override
	public boolean update(StructureType object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(StructureType object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StructureType find(int id) {
		// TODO Auto-generated method stub
		String req = "SELECT * FROM structure_type where structure_type_id=?";
		ResultSet res = null;
		
		StructureType sType = null;
		PreparedStatement statement = null;
		try {
			statement = this.con.prepareStatement(req);
			
			statement.setInt(1, id);
			
			res = statement.executeQuery();
			
			if(res.next()) {
				sType = new StructureType();
				
				sType.setStructureTypeId(res.getInt("structure_type_id"));
				sType.setTitle(res.getString("title"));
				sType.setDescription(res.getString("description"));
				sType.setLevel(res.getInt("level"));
				sType.setCreatedOn(res.getDate("created_on"));
				sType.setCreatedBy(res.getInt("created_by"));
				sType.setDeleted(res.getBoolean("deleted"));				
				
			}
			
		}catch(Exception e) {
			
		}finally {
			
			try {
				
				res.close();
				statement.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return sType;
	}
	
	public List<StructureType> readAll() {
		String req = "SELECT * FROM structure_type";
		ResultSet res = null;
		List<StructureType> allTypes = new ArrayList<StructureType>();
		StructureType sType = null;
		
		try {
			Statement statement = this.con.createStatement();
			
			res = statement.executeQuery(req);
			
			while(res.next()) {
				sType = new StructureType();
				
				sType.setStructureTypeId(res.getInt("structure_type_id"));
				sType.setTitle(res.getString("title"));
				sType.setDescription(res.getString("description"));
				sType.setLevel(res.getInt("level"));
				sType.setCreatedOn(res.getDate("created_on"));
				sType.setCreatedBy(res.getInt("created_by"));
				sType.setDeleted(res.getBoolean("deleted"));
				
				allTypes.add(sType);
			}
			
			statement.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return allTypes;
	}
	
	public StructureType findSubLevel(int id) {
		
		StructureType selected = this.find(id);
		
		String req = "SELECT * FROM structure_type where level=?";
		ResultSet res = null;
		
		StructureType sType = null;
		PreparedStatement statement = null;
		try {
			statement = this.con.prepareStatement(req);
			
			statement.setInt(1, selected.getLevel()+1);
			
			res = statement.executeQuery();
			
			if(res.next()) {
				sType = new StructureType();
				
				sType.setStructureTypeId(res.getInt("structure_type_id"));
				sType.setTitle(res.getString("title"));
				sType.setDescription(res.getString("description"));
				sType.setLevel(res.getInt("level"));
				sType.setCreatedOn(res.getDate("created_on"));
				sType.setCreatedBy(res.getInt("created_by"));
				sType.setDeleted(res.getBoolean("deleted"));				
				
			}
			
		}catch(Exception e) {
			
		}finally {
			
			try {
				
				res.close();
				statement.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return sType;
	}

}
