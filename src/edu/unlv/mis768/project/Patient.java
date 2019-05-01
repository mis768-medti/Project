package edu.unlv.mis768.project;

public class Patient {
	//FIELDS 	
	String patientName;
	String insurance;
	Doctor doctor;
	
	public Patient() {
		
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getDoctor() {
		return doctor.getName();
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public String toString() {
		return "Name: " + getPatientName()+"\n"
				+ "Doctor: " + doctor.getName();
	}
	
	

}
