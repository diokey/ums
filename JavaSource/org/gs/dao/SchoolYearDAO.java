package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gs.db.SingletonConnection;
import org.gs.model.SchoolYear;

public class SchoolYearDAO extends DAO<SchoolYear>{

	public SchoolYearDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public SchoolYearDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(SchoolYear object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(SchoolYear object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(SchoolYear object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SchoolYear find(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM school_year where school_year_id=?";
		SchoolYear sy = null;
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			
			if(res.next()) {
				sy = new SchoolYear();
				sy.setSchoolYearId(res.getInt("school_year_id"));
				sy.setSchoolId(res.getInt("school_id"));
				sy.setSchoolYearTitle(res.getString("school_year"));
				sy.setStartDate(res.getDate("start_date"));
				sy.setEndDate(res.getDate("end_date"));
				sy.setSchoolPeriods(new SchoolPeriodDAO().findBySchoolYear(sy.getSchoolYearId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return sy;
	}
	
	public List<SchoolYear> findBySchool(int schoolId) {
		List<SchoolYear> schoolYears = new ArrayList<SchoolYear>();
		
		String sql = "SELECT school_year_id FROM school_year where school_id = ?";
		
		ResultSet res = null;
		PreparedStatement pst = null;
		
		try {
			pst = this.con.prepareStatement(sql);
			pst.setInt(1, schoolId);
			
			res = pst.executeQuery();
			
			while(res.next()) {
				schoolYears.add(this.find(res.getInt("school_year_id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				res.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return schoolYears;
	}

}
