package org.gs.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.gs.db.SingletonConnection;


public class CommonUtils {
	
	
	public static String date2String(Date d) {
		if(d==null)
			return "";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		return format.format(d);
	}
	
	public static String date2String(Date date, String pattern) {
		if(date==null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		
		return format.format(date);
	}
	/*
	 * N.B For this method to work the date format must be of this pattern
	 * yyyy-MM-dd
	 */
	public static Date string2Date(String d) {
		if(d==null || d.isEmpty())
			return null;
		String dateString[] = d.split("-");
		if(dateString.length<3)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dateString[0]),Integer.parseInt(dateString[1])-1, Integer.parseInt(dateString[2]));
		return cal.getTime();
	}

	public static Date string2Date(String d, String pattern) {
		if(d==null || d.isEmpty())
			return null;
		String dateString[] = d.split(pattern);
		if(dateString.length<3)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dateString[0]),Integer.parseInt(dateString[1])-1, Integer.parseInt(dateString[2]));
		return cal.getTime();
	}
	
	public static int getLastId(String tableName, String pk) {
        
        String requete ="SELECT "+pk+" FROM "+tableName+" order by "+pk+" DESC LIMIT 1";
       
        int rep = 0;
        
        Connection con=SingletonConnection.getInstance();
		ResultSet res=null;
		
		Statement statement = null;
		
        try {
        	statement=con.createStatement();
			res=statement.executeQuery(requete);
            while(res.next()) {
                rep = res.getInt(pk);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	try {
				res.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
        return rep;
    }
	
	
}
