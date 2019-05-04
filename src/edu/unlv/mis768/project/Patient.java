package edu.unlv.mis768.project;

public class Patient {
	//FIELDS 	
	String patientFirstName;
	String patientLastName;
	String insurance;
	Doctor doctor;
	int patientID;
	
	public Patient() {
		
	}
	public Patient(int patientID, String firstName, String lastName) {
		this.patientID=patientID;
		this.patientFirstName = firstName;
		this.patientLastName = lastName;
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
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
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
