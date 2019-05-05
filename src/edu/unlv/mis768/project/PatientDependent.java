package edu.unlv.mis768.project;

import java.util.Date;

public class PatientDependent extends Patient {
	
	public PatientDependent() {
		super();
	}

	public PatientDependent(int patientID, String firstName, String lastName, Date dateOfBirth) {
		super(patientID, firstName, lastName, dateOfBirth);
	}
	
	public PatientDependent(int patientID, String firstName, String lastName, String dateOfBirth) 
			throws Exception {
		super(patientID, firstName, lastName, dateOfBirth);
	}
	
	
	public String dependentToString() {
		return toString();
	}
	
	public boolean equals(PatientDependent dependent) {
		if (this.patientFirstName.equals(dependent.patientFirstName) &&
				this.patientLastName.equals(dependent.patientLastName) &&
				this.dateOfBirth.equals(dependent.dateOfBirth))
			return true;
		else
			return false;
	}
	

}
