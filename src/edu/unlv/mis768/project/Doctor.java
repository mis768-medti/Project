package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Doctor extends Employee {
	//fields 
	private String userType = "provider";
	private String specialty;
	private ArrayList<String> treatableReasonsList;

	public Doctor(String firstName, String lastName, int id, String specialty) {
		super(firstName, lastName, id);
		this.specialty = specialty;
		
		// Instantiate ArrayLists
		treatableReasonsList = new ArrayList<String>();
		
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
        try {
        	// Create a statement object
			Statement stmt = conn.createStatement();
			
			// Populate treatable conditions list
			// Query specialty table
	        String sqlSelect = "SELECT DISTINCT VisitReason FROM " 
	        		+ AppointmentDBConstants.SPECIALTY_TABLE_NAME
	        		+ " WHERE Specialty = '" + specialty + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        if (result.next()) {
	        	result.first();
	        	do {
		        	treatableReasonsList.add(result.getString("VisitReason"));
		        } while(result.next());
	        }
	        
	        AppointmentDBUtil.closeDBConnection(conn);
	        
        }  catch (SQLException ex) {
        	AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("SQL Error");
			System.out.println(ex.getMessage());
		}
	}
	
	public boolean canTreat(String visitReason) {
		boolean canTreat = false;
		
		for (int i = 0; i < treatableReasonsList.size(); i++) {
			if (visitReason.equalsIgnoreCase(treatableReasonsList.get(i)))
				canTreat = true;
		}
		
		return canTreat;
	}
	
	public ArrayList<Slot> getSlots(String date) throws Exception {
		ArrayList<Slot> slotList = new ArrayList<Slot>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date appointmentDate = formatter.parse(date);
		
		// Default available slots for that day
		for (int i = 8; i < 18; i++) {
			slotList.add(new Slot(appointmentDate, i));
		}
		
		
		
		// Get appointments for that day
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
        try {
        	// Create a statement object
			Statement stmt = conn.createStatement();
			
			// Query appointment table
	        String sqlSelect = "SELECT cast(AppointmentDateTime as char) as AppointmentDateTime FROM " 
	        		+ AppointmentDBConstants.APPOINTMENT_TABLE_NAME
	        		+ " WHERE PhysicianID = " + this.getId() + " AND"
	        		+ " cast(AppointmentDateTime as date) = '" + date + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        // Mark unavailable slots as booked
	        if (result.next()) {
		        result.first();
		        do {
		        	// Get hour
		        	int hour = Integer.parseInt(result.getString("AppointmentDateTime").substring(11,13));
		        	// Loop over slots
		        	for (int i = 0; i < slotList.size(); i++) {
		        		Slot slot = slotList.get(i);
		        		if (slot.getHour() == hour)
		        			slot.setBookedIndicator(true);
		        	}
		        } while(result.next());
	        }
	        
	        AppointmentDBUtil.closeDBConnection(conn);
	        
        }  catch (SQLException ex) {
        	AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("SQL Error");
			System.out.println(ex.getMessage());
		}
        
        return slotList;
	}
	
	

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
		treatableReasonsList.clear();
		
		// Populate treatable conditions list
		// Create a connection to the database.
		Connection conn =
		         AppointmentDBUtil.getDBConnection();
		        
		try {
		        // Create a statement object
				Statement stmt = conn.createStatement();
					
				// Query specialty table
			    String sqlSelect = "SELECT DISTINCT VisitReason FROM " 
			        		+ AppointmentDBConstants.SPECIALTY_TABLE_NAME
			        		+ " WHERE Specialty = '" + specialty + "'";
			    ResultSet result = stmt.executeQuery(sqlSelect);
			        
			    result.first();
			    do {
			       	treatableReasonsList.add(result.getString("VisitReason"));
			    } while(result.next());
			    
			    AppointmentDBUtil.closeDBConnection(conn);
			        
		 }  catch (SQLException ex) {
			 	AppointmentDBUtil.closeDBConnection(conn);
				System.out.println("SQL Error");
				System.out.println(ex.getMessage());
		 }
	}
	
	@Override
	/**
	 * Deletes Doctor and associated information from database
	 */
	public void remove() {
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Remove doctor from Appointment table
			String sqlDelete = "DELETE FROM " + AppointmentDBConstants.APPOINTMENT_TABLE_NAME
					+ " WHERE PhysicianID = " + this.getId();
			
			stmt.executeUpdate(sqlDelete);
			
			// Get doctor's username
			String sqlSelect = "SELECT Username FROM " + AppointmentDBConstants.PROVIDER_TABLE_NAME
					+ " WHERE ProviderID = " + this.getId();
			ResultSet result = stmt.executeQuery(sqlSelect);
			result.first();
			String username = result.getString("Username");
			
			// Remove doctor from Provider table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.PROVIDER_TABLE_NAME
					+ " WHERE ProviderID = " + this.getId();
			
			stmt.executeUpdate(sqlDelete);
			
			// Remove username from User table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.USER_TABLE_NAME
						+ " WHERE Username = '" + username + "'";
			
			stmt.executeUpdate(sqlDelete);
			
			AppointmentDBUtil.closeDBConnection(conn);
        	
	        }
	              	
 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Delete Doctor Error");
        	System.out.println(ex.getMessage());
		}	
	}

	public String getUserType() {
		return userType;
	}
	
	public String toString() {
		return getFirstName() + " " + getLastName();
	}
	
	
	

}
