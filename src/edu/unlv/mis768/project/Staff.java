package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Staff extends Employee {
	//fields 
	private String userType = "admin";
	
	//Constructor
	public Staff(String firstName, String lastName, int id) {
		
		super(firstName, lastName, id);
	}
	
	public String getUserType() {
		return userType;
	}
	
	@Override
	/**
	 * Deletes Staff and associated information from database
	 */
	public void remove() {
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Get staff's username
			String sqlSelect = "SELECT Username FROM " + AppointmentDBConstants.ADMIN_TABLE_NAME
					+ " WHERE AdminID = " + this.getId();
			ResultSet result = stmt.executeQuery(sqlSelect);
			result.first();
			String username = result.getString("Username");
			
			// Remove staff from Admin table
			String sqlDelete = "DELETE FROM " + AppointmentDBConstants.ADMIN_TABLE_NAME
					+ " WHERE AdminID = " + this.getId();
			
			stmt.executeUpdate(sqlDelete);
			
			// Remove username from User table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.USER_TABLE_NAME
						+ " WHERE Username = '" + username + "'";
			
			stmt.executeUpdate(sqlDelete);
			
			AppointmentDBUtil.closeDBConnection(conn);
        	
	        }
	              	
 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Delete Staff Error");
        	System.out.println(ex.getMessage());
		}	
	}
	
	public String toString() {
		return "Name: " + getFirstName() + " " + getLastName() +"\n"
				+"ID: " + getId();
	}
	
	
	
	



}
