package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.DriverManager;


public class AppointmentDBUtil {
	/**
	 * This method establishes the DB connection
	 * @return a database connection
	 */
	public static Connection getDBConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(AppointmentDBConstants.DB_URL, 
					AppointmentDBConstants.USER_NAME, AppointmentDBConstants.PASSWORD);
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		
		
		
		return conn;
		
	}

	/**
	 * This method closes the DB connection
	 * @param the connection to be closed
	 */
	public static void closeDBConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ex) {
				System.out.println("ERROR: " + ex.getMessage());
			}
		}
	}
}
