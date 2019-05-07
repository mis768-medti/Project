package edu.unlv.mis768.project;

/**
 * PatientInsurance Class Definition
 */
public class PatientInsurance {
	// fields
	private int patientID;
	private String insuranceName;
	private String groupNumber;
	private String memberNumber;
	private String type;
	
	/**
	 * No-arg constructor for the PatientInsurance object
	 */
	public PatientInsurance() {}
	
	/**
	 * PatientInsurance object constructor
	 * @param patientID integer patient's id
	 * @param insuranceName String insurance name
	 * @param groupNumber String insurance group number
	 * @param memberNumber String insurance member number
	 * @param type String type of insurance
	 */
	public PatientInsurance(int patientID, String insuranceName, String groupNumber,
						String memberNumber, String type) {
		this.patientID = patientID;
		this.insuranceName = insuranceName;
		this.groupNumber = groupNumber;
		this.memberNumber = memberNumber;
		this.type = type;
	}

	/**
	 * @return integer patient's id
	 */
	public int getPatientID() {
		return patientID;
	}

	/**
	 * Set patient's id
	 * @param patientID integer patient's id
	 */
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	/**
	 * @return String insurance name
	 */
	public String getInsuranceName() {
		return insuranceName;
	}

	/**
	 * Set insurance name
	 * @param insuranceName String insurance name
	 */
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}
	
	/**
	 * @return String insurance group number
	 */
	public String getGroupNumber() {
		return groupNumber;
	}

	/**
	 * Set insurance group number
	 * @param groupNumber String insurance group number
	 */
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	/**
	 * @return String insurance member number
	 */
	public String getMemberNumber() {
		return memberNumber;
	}

	/**
	 * Set insurance member number
	 * @param memberNumber String insurance member number
	 */
	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}

	/**
	 * @return String get insurance type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set insurance type
	 * @param type String insurance type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
