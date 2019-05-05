package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Patient {
	
	// fields 	
	String patientFirstName;
	String patientLastName;
	ArrayList<PatientInsurance> insurance;
	Date dateOfBirth;
	Doctor doctor;
	int patientID;
	
	
	public Patient() {}
	
	public Patient(int patientID, String firstName, String lastName, Date dateOfBirth) {
		
		this.patientID = patientID;
		this.patientFirstName = firstName;
		this.patientLastName = lastName;
		this.dateOfBirth = dateOfBirth;
		
		this.insurance = new ArrayList<PatientInsurance>();
		
		this.pullInsuranceInformation();
		
	}
	
	/**
	 * Queries the patient insurance table and populates
	 * the insurance ArrayList with the patient's 
	 * insurance information
	 */
	public void pullInsuranceInformation() {

    	// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Query patient insurance table
	        String sqlSelect = "SELECT * FROM " + AppointmentDBConstants.INSURANCE_TABLE_NAME
	        		+ " WHERE PatientID = '" + this.patientID + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        // Clear insurance ArrayList
	        if (insurance.size() > 0)
	        	insurance.clear();
	        
	        // Populate ArrayList with PatientInsurance objects
	        //	created from SQL query results
	        result.first();
	        do {
	        	insurance.add(new PatientInsurance(result.getInt("PatientID"), 
	        			result.getString("InsuranceName"), 
	        			result.getString("GroupNo"),
						result.getString("MemberNo"),
						result.getString("InsuranceType")));
	        	} while(!result.next());
		} 
		catch (SQLException ex) {
        	System.out.println(ex.getMessage());
		}	
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public int getPatientID() {
		return patientID;
	}
	
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	
	public String getPatientFirstName() {
		return patientFirstName;
	}
	
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	
	public String getPatientLastName() {
		return patientLastName;
	}
	
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	
	public ArrayList<PatientInsurance> getInsurance() {
		return insurance;
	}
	
	public String getDoctor() {
		return (doctor.getFirstName() + " " + doctor.getLastName());
	}
	
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public String toString() {
		return "Name: " + getPatientFirstName()+"\n"
				+ "Doctor: " + doctor.getFirstName() + " " + doctor.getLastName();
	}
	
	

}
