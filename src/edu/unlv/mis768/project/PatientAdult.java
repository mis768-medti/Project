package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class PatientAdult extends Patient {
	
	private ArrayList<PatientDependent> dependentList;
	
	public PatientAdult(int patientID, String firstName, String lastName, Date dateOfBirth) {
		super(patientID, firstName, lastName, dateOfBirth);
		
		// Instantiate dependentList
		this.dependentList = new ArrayList<PatientDependent>();
		this.pullDependentInformation();
	}
	
	public PatientAdult(int patientID, String firstName, String lastName, String dateOfBirth) 
			throws Exception {
		super(patientID, firstName, lastName, dateOfBirth);
		
		// Instantiate dependentList
		this.dependentList = new ArrayList<PatientDependent>();
		this.pullDependentInformation();
	}
	
	/**
	 * Queries the patient dependent table and populates
	 * the dependent ArrayList with the patient's
	 * dependent information
	 */
	public void pullDependentInformation() {
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Query patient dependent table
			String sqlSelect = "SELECT * FROM " +  AppointmentDBConstants.PATIENT_TABLE_NAME 
					+ " WHERE PatientID in (SELECT DependentID FROM " 
					+ AppointmentDBConstants.DEPENDENT_TABLE_NAME + " WHERE PatientID = " 
					+ this.patientID + ")";
			
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        // Clear dependent ArrayList
	        if (dependentList.size() > 0)
	        	dependentList.clear();
	        
	        // Populate ArrayList with PatientInsurance objects
	        //	created from SQL query results
	        result.first();
	        do {
	        	dependentList.add(new PatientDependent(result.getInt("PatientID"), 
	        			result.getString("FirstName"), 
	        			result.getString("LastName"),
						result.getDate("DateOfBirth")));
	        	} while(!result.next());
		} 
		catch (SQLException ex) {
        	System.out.println(ex.getMessage());
		}	
	}
	
	public ArrayList<PatientDependent> getDependents(){
		return dependentList;
	}
	
	public boolean hasDependent(PatientDependent dependent) {
		boolean hasDependent = false;
		
		for (int i = 0; i < dependentList.size(); i++) {
			if (dependent.equals(dependentList.get(i)))
				hasDependent = true;
		}
		
		return hasDependent;
	}
}
