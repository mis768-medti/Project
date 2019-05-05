package edu.unlv.mis768.project;

public class Appointment {
	//field
	private Patient patient;
	private Doctor doctor;
	private Slot slot; 
	private String visitType;
	
	public Appointment(Patient p, Doctor d, Slot s, String visitType) {
		this.patient = p;
		this.doctor = d;
		this.slot = s;
		this.visitType = visitType;
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
	
//	public String toString() {
//		return "string";
//	}
	
	

}
