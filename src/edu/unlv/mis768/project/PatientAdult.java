package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	        	} while(result.next());
	        
	        AppointmentDBUtil.closeDBConnection(conn);
		} 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
        	System.out.println(ex.getMessage());
		}	
	}
	
	public ArrayList<PatientDependent> getDependents(){
		return dependentList;
	}
	
	/**
	 * Determines if patient has any dependents
	 * @return true if patient has dependents, false otherwise
	 */
	public boolean hasDependents() {
		boolean hasDependents = false;
		
		if (dependentList.size() > 0)
			hasDependents = true;
		
		return hasDependents;
	}
	
	/**
	 * Determines if patient has the specified dependent
	 * @param dependent A dependent object
	 * @return true if patient has dependent, false otherwise
	 */
	public boolean hasDependent(PatientDependent dependent) {
		boolean hasDependent = false;
		
		for (int i = 0; i < dependentList.size(); i++) {
			if (dependent.equals(dependentList.get(i)))
				hasDependent = true;
		}
		
		return hasDependent;
	}
	
	public void addDependent(PatientDependent dependent) {
		
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Create a calendar object
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			String birthDateString = format.format(dependent.dateOfBirth);

			
			// Check if dependent is in the Patient table
			String sqlSelect = "SELECT PatientID FROM " +  AppointmentDBConstants.PATIENT_TABLE_NAME 
					+ " WHERE FirstName ='" +  dependent.patientFirstName + "' AND "
					+ " LastName = '" + dependent.patientLastName + "' AND " 
					+ " DateOfBirth = '" + birthDateString + "'";
			
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        if (!result.last()) {
	        	// dependent does not exist in the Patient table
	        	// insert dependent into Patient table
	        	String sqlInsert = "INSERT INTO " + AppointmentDBConstants.PATIENT_TABLE_NAME
	        			+ "(FirstName, LastName, DateOfBirth) VALUES ('"
	        			+ dependent.patientFirstName + "','" 
	        			+ dependent.patientLastName + "','" 
	        			+ birthDateString + "')";
	        	
	        	stmt.executeUpdate(sqlInsert);
	        	
	        	// Execute select statement again
	        	result = stmt.executeQuery(sqlSelect);
	        
	        }
	        
	        result.first();
	        
	        // Get dependentID from SQL Select results
        	int dependentID = result.getInt("PatientID");
	        
	    	// Insert a record into the Patient table
        	String sqlInsert = "INSERT INTO " + AppointmentDBConstants.DEPENDENT_TABLE_NAME
        			+ " VALUES (" + this.patientID + ", " + dependentID + ")";
        	
        	// TO DO: Add dependent's insurance information
        	
        	stmt.executeUpdate(sqlInsert);
        	
        	AppointmentDBUtil.closeDBConnection(conn);
        	
        	// Refresh Dependent ArrayList
        	this.pullDependentInformation();
		} 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
        	System.out.println(ex.getMessage());
		}	
	}
	
	public void removeDependent(PatientDependent dependent) {
		
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Remove dependent from PatientDependent table
			String sqlDelete = "DELETE FROM " + AppointmentDBConstants.DEPENDENT_TABLE_NAME
					+ " WHERE DependentID = " + dependent.patientID;
			
			System.out.println(sqlDelete);
			stmt.executeUpdate(sqlDelete);
			
			// Remove dependent from Patient table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
					+ " WHERE PatientID = " + dependent.patientID;
			
			System.out.println(sqlDelete);
			stmt.executeUpdate(sqlDelete);
			
			// To do: Remove dependent from Appointment table
			
			// To do: Remove dependent from Insurance table

			
			AppointmentDBUtil.closeDBConnection(conn);
			
	       	// Refresh Dependent ArrayList
        	this.pullDependentInformation();
        	
	        }
	              	
 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Delete Dependent Error");
        	System.out.println(ex.getMessage());
		}	
	}
	
	/**
	 * Cascades any insurance removals to patient's dependents
	 */
	public void removeInsurance(PatientInsurance insurance) {
		// Check if patient has dependents
		if (dependentList.size() > 0) {
			// Patient has dependents
			// Loop over dependents
			for (int i = 0; i < dependentList.size(); i++) {
				// Remove insurance
				dependentList.get(i).removeInsurance(insurance);
			}
		}
		
		// Remove insurance for patient
		super.removeInsurance(insurance);
	}
	
	/**
	 * Returns an ArrayList containing the patient's and
	 * 	patient's dependents' appointments
	 */
	public ArrayList<Appointment> getAppointments(){
		// Create an ArrayList to return
		ArrayList<Appointment> appList = new ArrayList<Appointment>();
		// Copy over patient's appointments
		appList.addAll(appointmentList);
		
		// Check if patient has dependents
		if (dependentList.size() > 0) {
			// Patient has dependents
			// Loop over dependents
			for (int i = 0; i < dependentList.size(); i++) {
				// Add appointments to appList
				appList.addAll(dependentList.get(i).getAppointments());
			}
		}
		return appList;
	}
	
	/**
	 * Returns an ArrayList containing the patient's and
	 * 	patient's dependents' future appointments
	 */
	public ArrayList<Appointment> getFutureAppointments(){
		// Create an ArrayList to return
		ArrayList<Appointment> appList = new ArrayList<Appointment>();
		// Copy over patient's future appointments
		appList.addAll(super.getFutureAppointments());
		
		// Check if patient has dependents
		if (dependentList.size() > 0) {
			// Patient has dependents
			// Loop over dependents
			for (int i = 0; i < dependentList.size(); i++) {
				// Add future appointments to appList
				appList.addAll(dependentList.get(i).getFutureAppointments());
			}
		}
		return appList;
	}
	
	public void removeAppointment(Appointment appointment) {
		// Determine if appointment is for patient or a dependent
		Patient appointmentPatient = appointment.getPatient();
		
		if (appointmentPatient.getPatientID() == this.patientID) {
			// Appointment is for this patient
			super.removeAppointment(appointment);
		}
		else {
			// Appointment is for a dependent
			// Loop over dependents
			for (int i = 0; i < dependentList.size(); i++) {
				// Check if appointment is for dependent
				Patient dependent = dependentList.get(i);
				if(appointmentPatient.getPatientID() == dependent.getPatientID()) {
					// Appointment is for this patient
					dependent.removeAppointment(appointment);
				}
			}
		}
	}
}
