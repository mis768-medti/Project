package edu.unlv.mis768.project;

import java.util.ArrayList;

public class Doctor extends Employee {
	//fields 
	private String specialty;
	private ArrayList<Slot> slotList;
	private ArrayList<Appointment> appointmentList;

	public Doctor(String firstName, String lastName, int id, String title, String specialty) {
		super(firstName, lastName, id);
		this.specialty = specialty;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
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
		return "Name: " + getFirstName() + " " + getLastName() +"\n"
				+"ID: " + getId() + "\n"
				+ "Specialty: " + getSpecialty();
	}
	
	
	

}
