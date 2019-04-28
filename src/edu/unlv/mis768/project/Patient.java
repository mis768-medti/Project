package edu.unlv.mis768.project;

public class Patient {
	
	private int patientID;
	private String firstName;
	private String lastName;
	
	public Patient() {}
	
	public Patient(int id, String patientFirstName, String patientLastName) {
		this.patientID = id;
		this.firstName = patientFirstName;
		this.lastName = patientLastName;
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
	
	
	

}
