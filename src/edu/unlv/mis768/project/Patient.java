package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Patient extends User {
	
	// fields 	
	String userType = "patient";
	String firstName;
	String lastName;
	ArrayList<PatientInsurance> insuranceList;
	ArrayList<Patient> dependentList;
	ArrayList<Appointment> appointmentList;
	Date dateOfBirth;
	int patientID;
	
	
	public Patient() {}
	
	public Patient(int patientID, String firstName, String lastName, Date dateOfBirth){
		
		// Set fields
		this.patientID = patientID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		
		// Instantiate ArrayLists
		this.insuranceList = new ArrayList<PatientInsurance>();
		this.appointmentList = new ArrayList<Appointment>();
		
		this.pullInsuranceInformation();
		this.pullAppointmentInformation();
		
	}
	
	public Patient(int patientID, String firstName, String lastName, String dateOfBirth) 
			throws Exception {
		
		// Set fields
		this.patientID = patientID;
		this.firstName = firstName;
		this.lastName = lastName;
		
		SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-yyyy");
		this.dateOfBirth = formatter.parse(dateOfBirth);
				
		// Instantiate ArrayLists
		this.insuranceList = new ArrayList<PatientInsurance>();
		this.appointmentList = new ArrayList<Appointment>();
				
		this.pullInsuranceInformation();
		this.pullAppointmentInformation();
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
	        if (insuranceList.size() > 0)
	        	insuranceList.clear();
	        
	        if (result.next()) {
	        	// Populate ArrayList with PatientInsurance objects
		        //	created from SQL query results
		        result.first();
		        do {
		        	insuranceList.add(new PatientInsurance(result.getInt("PatientID"), 
		        			result.getString("InsuranceName"), 
		        			result.getString("GroupNo"),
							result.getString("MemberNo"),
							result.getString("InsuranceType")));
		        	} while(result.next());
	        }
	        
	        AppointmentDBUtil.closeDBConnection(conn);
		} 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Patient Insurance Error");
        	System.out.println(ex.getMessage());
		}	
	}
	
	/**
	 * Queries the appointment table and populates
	 * the appointment ArrayList with the patient's 
	 * appointment information
	 */
	public void pullAppointmentInformation() {
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Query patient appointment table
	        String sqlSelect = "SELECT cast(a.AppointmentDateTime as char) as AppointmentDateTime,"
	        		+"a.VisitReason, a.PhysicianID,"
	        		+ " p.FirstName, p.LastName, p.Specialty FROM " 
	        		+ AppointmentDBConstants.APPOINTMENT_TABLE_NAME
	        		+ " a INNER JOIN " + AppointmentDBConstants.PROVIDER_TABLE_NAME + " p"
	        		+ " on a.PhysicianID = p.ProviderID "
	        		+ " WHERE PatientID = '" + this.patientID + "'";
	        
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        // Clear appointment ArrayList
	        if (appointmentList.size() > 0)
	        	appointmentList.clear();
	        
	        if (result.next()) {
	        	// Populate ArrayList with Appointment objects
		        //	created from SQL query results
		        result.first();
		        do {
		        	 
		        	Doctor appointmentDoctor = new Doctor(result.getString("FirstName"),
		        									result.getString("LastName"),
		        									result.getInt("PhysicianID"),
		        									result.getString("Specialty"));

		        	String appointmentDate = result.getString("AppointmentDateTime");
		        	Slot appointmentSlot = new Slot(appointmentDate);
		        	
		        	
		        	appointmentList.add(new Appointment(this, 
		        			appointmentDoctor, 
		        			appointmentSlot,
							result.getString("VisitReason")));
		        	} while(result.next());
	        }
	        
	        AppointmentDBUtil.closeDBConnection(conn);
		} 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Patient Appointment SQL Error");
        	System.out.println(ex.getMessage());
		} catch (Exception e) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Patient Appointment Slot Error");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
	
	public ArrayList<Appointment> getFutureAppointments(){
		// Array List to house future appointments
		ArrayList<Appointment> futureAppointments = new ArrayList<Appointment>();
		
		// Loop over appointments
		for (int i = 0; i < appointmentList.size(); i++) {
			Appointment appointment = appointmentList.get(i);
			
			// If appointment is in the future
			// add to future appointments ArrayList
			if (appointment.isFutureAppointment())
				futureAppointments.add(appointment);
		}
		
		return futureAppointments;
	}
	
	public void removeInsurance(PatientInsurance insurance) {
		
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Remove insurance from PatientInsurance table
			String sqlDelete = "DELETE FROM " + AppointmentDBConstants.INSURANCE_TABLE_NAME
					+ " WHERE PatientID = " + this.patientID + " AND  "
					+ " InsuranceName = '" + insurance.getInsuranceName() + "' AND "
					+ " GroupNo = '" + insurance.getGroupNumber() + "'" ;
			
			System.out.println(sqlDelete);
			stmt.executeUpdate(sqlDelete);
			
			AppointmentDBUtil.closeDBConnection(conn);
	
	       	// Refresh Insurance ArrayList
        	this.pullInsuranceInformation();
	        }
	              	
 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Delete Insurance Error");
        	System.out.println(ex.getMessage());
		}	
	}
	
	public void removeAppointment(Appointment appointment) {
		
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Remove appointment from Appointment table
			int patientID = appointment.getPatient().getPatientID();
			int physicianID = appointment.getDoctor().getId();
			String appointmentDateTime = appointment.getSlot();
			
			String sqlDelete = "DELETE FROM " + AppointmentDBConstants.APPOINTMENT_TABLE_NAME
					+ " WHERE PatientID = " + patientID + " AND  "
					+ " PhysicianID = " + physicianID + " AND "
					+ " AppointmentDateTime = '" + appointmentDateTime + "'" ;
			
			stmt.executeUpdate(sqlDelete);
			
			AppointmentDBUtil.closeDBConnection(conn);
	
	       	// Refresh Appointment ArrayList
        	this.pullAppointmentInformation();
	        }
	              	
 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Delete Insurance Error");
        	System.out.println(ex.getMessage());
		}	
	}
	
	@Override
	/**
	 * Deletes Patient and associated information from database
	 */
	public void remove() {
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Remove patient from Appointment table
			String sqlDelete = "DELETE FROM " + AppointmentDBConstants.APPOINTMENT_TABLE_NAME
					+ " WHERE PatientID = " + this.patientID;
			
			stmt.executeUpdate(sqlDelete);
			
			// Remove patient from Insurance table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.INSURANCE_TABLE_NAME
					+ " WHERE PatientID = " + this.patientID;
			
			stmt.executeUpdate(sqlDelete);

			// Remove patient from Dependent table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.DEPENDENT_TABLE_NAME
					+ " WHERE PatientID = " + this.patientID + " OR"
					+ " DependentID = " + this.patientID;
			
			stmt.executeUpdate(sqlDelete);
			
			// Get patient's username
			String sqlSelect = "SELECT Username FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
					+ " WHERE PatientID = " + this.patientID;
			ResultSet result = stmt.executeQuery(sqlSelect);
			result.first();
			String username = result.getString("Username");
			
			// Remove patient from Patient table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
					+ " WHERE PatientID = " + this.patientID;
			
			stmt.executeUpdate(sqlDelete);
			
			// Remove username from User table
			sqlDelete = "DELETE FROM " + AppointmentDBConstants.USER_TABLE_NAME
						+ " WHERE Username = '" + username + "'";
			
			stmt.executeUpdate(sqlDelete);
			
			AppointmentDBUtil.closeDBConnection(conn);
        	
	        }
	              	
 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Delete Patient Error");
        	System.out.println(ex.getMessage());
		}	
	}
	
	public String getUserType() {
		return userType;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public void setDateOfBirth(String dateOfBirth) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-yyyy");
		this.dateOfBirth = formatter.parse(dateOfBirth);
	}
	
	public int getPatientID() {
		return patientID;
	}
	
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public ArrayList<PatientInsurance> getInsurance() {
		return insuranceList;
	}
	
	public ArrayList<Appointment> getAppointments() {
		return appointmentList;
	}
	
	public String toString() {
		return getFirstName()+" " + getLastName();
	}
	
	

}
