package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Appointment {
	//field
	private Patient patient;
	private Doctor doctor;
	private Slot slot; 
	private String visitType;
	private String comments = "";
	
	public Appointment(Patient p, Doctor d, Slot s, String visitType) {
		this.patient = p;
		this.doctor = d;
		this.slot = s;
		this.visitType = visitType;
	}
	
	public Appointment(Doctor d, Slot s) {
		this.doctor = d;
		this.slot = s;
		
		// Determine Patient and Visit Type
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
        try {
        	// Create a statement object
			Statement stmt = conn.createStatement();
			
			// Query appointment table
	        String sqlSelect = "SELECT VisitReason, a.PatientID, p.firstName,"
	        		+ "p.lastName, cast(p.DateOfBirth as char) as DateOfBirth FROM " 
	        		+ AppointmentDBConstants.APPOINTMENT_TABLE_NAME + " a INNER JOIN "
	        		+ AppointmentDBConstants.PATIENT_TABLE_NAME + " p on a.PatientID = p.PatientID"
	        		+ " WHERE PhysicianID = " + doctor.getId() + " AND"
	        		+ " AppointmentDateTime = '" + slot + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.first();
	        this.visitType = result.getString("VisitReason");
	        this.patient = new Patient(result.getInt("PatientID"),
	        						result.getString("firstName"),
	        						result.getString("lastName"),
	        						result.getString("DateOfBirth"));
	        
	        AppointmentDBUtil.closeDBConnection(conn);
	        
        }  catch (SQLException ex) {
        	AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("SQL Error");
			System.out.println(ex.getMessage());
		} catch (Exception e) {
			System.out.println("Appointment Patient Error");
			System.out.println(e.getMessage());
		}
	}
	

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public String getSlot() {
		return slot.toString();
	}

	public void changeSlot(Slot newSlot) {
		this.slot = newSlot;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	
	public String getComments() {
		return this.comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * Compares appointment date to today
	 * @return true if appointment is in the future, false otherwise
	 */
	public boolean isFutureAppointment() {
		// Today's date for comparison
		Date today = new Date();
		if (this.slot.getDate().compareTo(today) >= 0)
			return true;
		else
			return false;
	}
	
	public void scheduleAppointment() {
		
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
		// Create a statement object
		stmt = conn.createStatement();
			
		// insert appointment into Appointment table
		String sqlInsert;
		if (!comments.isEmpty()) {
			// Appointment has comments
			sqlInsert = "INSERT INTO " + AppointmentDBConstants.APPOINTMENT_TABLE_NAME
        			+ " (PatientID, PhysicianID, AppointmentDateTime, VisitReason) VALUES (" 
					+ patient.getPatientID() + "," + doctor.getId() + ",'"
        			+ slot.toString() + "','" + visitType + "','" 
					+ comments + "')";	
		}
		else {
			// Appointment does not have comments
			sqlInsert = "INSERT INTO " + AppointmentDBConstants.APPOINTMENT_TABLE_NAME
        			+ " (PatientID, PhysicianID, AppointmentDateTime, VisitReason) VALUES (" 
					+ patient.getPatientID() + "," + doctor.getId() + ",'"
        			+ slot.toString() + "','" + visitType + "')";	
		}
        
        stmt.executeUpdate(sqlInsert);
		
		AppointmentDBUtil.closeDBConnection(conn);
		} catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
        	System.out.println(ex.getMessage());
		}
        
	}
	
	/**
	 * Deletes Appointment from database
	 */
	public void remove() {
		// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Remove appointment from Appointment table
			String sqlDelete = "DELETE FROM " + AppointmentDBConstants.APPOINTMENT_TABLE_NAME
					+ " WHERE PhysicianID = " + doctor.getId() + " AND"
					+ " PatientID = " + patient.getPatientID() + " AND"
					+ " AppointmentDateTime = '" + slot + "'";
			
			stmt.executeUpdate(sqlDelete);
		
			AppointmentDBUtil.closeDBConnection(conn);
        	
	        }
	              	
 
		catch (SQLException ex) {
			AppointmentDBUtil.closeDBConnection(conn);
			System.out.println("Delete Appointment Error");
        	System.out.println(ex.getMessage());
		}	
	}
	
//	public String toString() {
//		return "string";
//	}
	
	

}
