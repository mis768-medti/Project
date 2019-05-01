package edu.unlv.mis768.project;

public class PatientDependent extends Patient {
	//fields
	private boolean minorInd;
	
	public PatientDependent() {
		
	}
	
	public boolean getMinorInd() {
		return minorInd;
	}
	
	public String dependentToString() {
		return toString() + "\n"+
			"Minor: " + String.valueOf(minorInd);
	}
	

}
