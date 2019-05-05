package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Doctor extends Employee {
	//fields 
	private String specialty;
	private ArrayList<Slot> slotList;
	private ArrayList<Appointment> appointmentList;
	private ArrayList<String> treatableReasonsList;

	public Doctor(String firstName, String lastName, int id, String specialty) {
		super(firstName, lastName, id);
		this.specialty = specialty;
		
		// Instantiate ArrayLists
		slotList = new ArrayList<Slot>();
		appointmentList = new ArrayList<Appointment>();
		treatableReasonsList = new ArrayList<String>();
		
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
	        
        }  catch (SQLException ex) {
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
			        
		 }  catch (SQLException ex) {
				System.out.println("SQL Error");
				System.out.println(ex.getMessage());
		 }
	}

	public ArrayList<Slot> getSlotList() {
		return slotList;
	}

	public void setCalendarList(ArrayList<Slot> slotList) {
		this.slotList = slotList;
	}

	public ArrayList<Appointment> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(ArrayList<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}
	
	public String toString() {
		return getFirstName() + " " + getLastName();
	}
	
	
	

}
