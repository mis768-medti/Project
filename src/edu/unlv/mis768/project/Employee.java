package edu.unlv.mis768.project;

/**
 * Employee Class Definition
 */
public class Employee extends User{
	//fields 
	private String firstName;
	private String lastName;
	private int id;
	
	/**
	 * Employee object constructor 
	 * @param firstName String employee's first name
	 * @param lastName String employee's last name
	 * @param id integer employee's id
	 */
	public Employee(String firstName, String lastName, int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
	}

	/** 
	 * @return String employee's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets employee's first name
	 * @param name Sring employee's first name
	 */
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	/**
	 * @return String employee's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets employee's last name
	 * @param name String employee's last name
	 */
	public void setlastName(String name) {
		this.lastName = name;
	}

	/**
	 * @return integer employee's id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets employee's id
	 * @param id integer employee's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	/**
	 * Remove method to be implemented
	 * in subclasses
	 */
	public void remove() {}
}
