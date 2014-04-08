package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.Program;

public class ProgramDAO extends DAO<Program>{

	public ProgramDAO() {
		super(SingletonConnection.getInstance());
	}
	
	public ProgramDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Program object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Program object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Program object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Program find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Program> findAll() {
		List<Program> programs = new ArrayList<Program>();
		String sql = "SELECT * FROM school_program";
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				
				Program p = new Program();
				p.setProgramId(res.getInt("program_id"));
				p.setProgramName(res.getString("program_name"));
				p.setProgramDescription(res.getString("program_description"));
				
				programs.add(p);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return programs;
	}

}
