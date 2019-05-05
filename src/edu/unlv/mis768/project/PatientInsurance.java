package edu.unlv.mis768.project;

public class PatientInsurance {
	// fields
	private int patientID;
	private String insuranceName;
	private String groupNumber;
	private String memberNumber;
	private String type;
	
	public PatientInsurance() {}
	
	public PatientInsurance(int patientID, String insuranceName, String groupNumber,
						String memberNumber, String type) {
		this.patientID = patientID;
		this.insuranceName = insuranceName;
		this.groupNumber = groupNumber;
		this.memberNumber = memberNumber;
		this.type = type;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
