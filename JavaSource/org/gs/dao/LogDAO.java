package org.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.gs.db.SingletonConnection;
import org.gs.model.Log;

public class LogDAO extends DAO<Log>{

	public LogDAO() {
		// TODO Auto-generated constructor stub
		super(SingletonConnection.getInstance());
	}
	
	public LogDAO(Connection con) {
		super(con);
	}

	@Override
	public boolean create(Log object) {
		// TODO Auto-generated method stub
		String req = "";
		
		if(object.getUserId()!=0)
			req = "INSERT INTO logs (log_severity,log_description,user,ip_adresse,log_time) values (?,?,?,?,NOW())";
		else
			req = "INSERT INTO logs (log_severity,log_description,ip_adresse,log_time) values (?,?,?,NOW())";
		
		
		PreparedStatement st = null;
		boolean res = false;
		
		try {
			
			if(object.getUserId()!=0) {
				st = this.con.prepareStatement(req);
				st.setString(1, object.getLogSeverity());
				st.setString(2, object.getDescription());
				st.setInt(3, object.getUserId());
				st.setString(4, object.getUserIp());
			}else{
				st = this.con.prepareStatement(req);
				st.setString(1, object.getLogSeverity());
				st.setString(2, object.getDescription());
				st.setString(3, object.getUserIp());
			}
			
			res = st.executeUpdate()>0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return res;
	}

	@Override
	public boolean update(Log object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Log object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Log find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
