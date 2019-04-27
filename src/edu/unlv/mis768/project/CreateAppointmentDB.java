package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Chloe Fletcher
 * This class creates and populates the database 
 * 		with at least 15 records per table
 * 
 * The following fields have NULL values throughout:
 * 	Admin table: AccessLevel, Role
 * 	Appointment table: Comments, Status
 * 
 */



public class CreateAppointmentDB {

	   public static void main(String[] args) {
		      // Create a named constant for the URL.
		      // NOTE: This value is specific for MySQL.
		      final String DB_URL = "jdbc:mysql://localhost:3306/";
		      final String DB_APPOINTMENT_URL = "jdbc:mysql://localhost:3306/appointmentDB";
		      final String USERNAME = "root";
		      final String PASSWORD = "";
		      
		      try {
		         // Create a connection to the database.
		         Connection conn =
		                DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		            // Create the database. If the database already exists, drop it. 
		            createDataBase(conn);
		            conn.close();
		            
		         //Create a connection to the database and to the coffee database   
		         Connection conn2 =
		        		 DriverManager.getConnection(DB_APPOINTMENT_URL, USERNAME, PASSWORD);
					
		         	// Build the User table.
		         	buildUserTable(conn2);
		         	
		         	// Build the Provider table.
		         	buildProviderTable(conn2);
		         	
		         	// Build the Patient table.
		         	buildPatientTable(conn2);
		         	
		         	// Build the PatientInsurance table.
		         	buildPatientInsuranceTable(conn2);
		         	
		         	// Build the PatientDependent table.
		         	buildPatientDependentTable(conn2);
		         	
		         	// Build the Specialty table.
		         	buildSpecialtyTable(conn2);
		         	
		         	// Build the Admin table.
		         	buildAdminTable(conn2);
		         
		         	// Build the Appointment table.
		         	buildAppointmentTable(conn2);
		         	

		         // Close the connection.
		         conn2.close();
		      }
		      catch (Exception ex) {
		         System.out.println("ERROR: " + ex.getMessage());
		      }
		   }

		   /**
		    * The createDatabase method creates the DB. If the DB is already existed, drop the DB first.
		    */
		   
		   public static void createDataBase(Connection conn) {
			   System.out.println("Checking for existing database.");
			   
			   try{
				   Statement stmt = conn.createStatement();
				   
				   //Drop the existing database
				   try {
					   stmt.executeUpdate("Drop DATABASE appointmentDB");
				   }
				   catch(SQLException ex) {
						// No need to report an error.
						// The database simply did not exist.
					}
				   //Create a new database
				   try {
					   stmt.execute("Create DATABASE appointmentDB");
					   //stmt.execute("USE coffee");
					   System.out.println("Database appointment created.");
				   }
				   catch(SQLException ex) {
						// No need to report an error.
						// The database simply did not exist.
					}			   
			   }
		  	   catch(SQLException ex) {
		  		   System.out.println("ERROR: " + ex.getMessage());
		  		   ex.printStackTrace();
				}
		   }
			
		   /**
		    * The buildUserTable method creates the
		    * User table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildUserTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE User (" + 
						   "Username VARCHAR(25) PRIMARY KEY NOT NULL, " + 
						   "Password VARCHAR(25) NOT NULL, " + 
						   "AccountType VARCHAR(25) NOT NULL" + 
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'JohnDoe01', " +
						   		"'password', " +
						   		"'patient')");
				   	
				   // Insert row #2
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'HenryBrumer01', " +
						   		"'password', " +
						   		"'patient')");
				   
				   // Insert row #3
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'NolaDelisa01', " +
						   		"'password', " +
						   		"'patient')");
				   
				   // Insert row #4
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'KrissyHarold01', " +
						   		"'password', " +
						   		"'patient')");
				   
				   // Insert row #5
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'ScotHeyward01', " +
						   		"'password', " +
						   		"'patient')");
				   
				   // Insert row #6
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'MartinJones01', " +
						   		"'password', " +
						   		"'provider')");
				   
				   // Insert row #7
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'MegAlphin01', " +
						   		"'password', " +
						   		"'provider')");
				   
				   // Insert row #8
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'ConcettaWhitesell01', " +
						   		"'password', " +
						   		"'provider')");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'MyriamZachary01', " +
						   		"'password', " +
						   		"'provider')");
				   
				   // Insert row #10
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'SantanaGuth01', " +
						   		"'password', " +
						   		"'provider')");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'AltaCueto01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'FredricHundley01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #13
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'NatachaDorgan01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #14
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'LyndaHardt01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'LatanyaMcgonigal01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #16
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'LiseRossin01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #17
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'ArnulfoZell01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #18
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'LaticiaMaloy01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #19
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'KatiLasater01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #20
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'LetaMcnew01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #21
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'SeptemberMccrum01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #22
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'DrewAlvarado01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #23
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'MaeganPouliot01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #24
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'TommyFines01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   // Insert row #25
				   stmt.execute("INSERT INTO User VALUES (" +
						   		"'AlisaBonnet01', " +
						   		"'password', " +
						   		"'admin')");
				   
				   System.out.println("User table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
			   
		   }
		   
		   /**
		    * The buildPatientTable method creates the
		    * Patient table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildPatientTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE Patient (" +
						   "PatientID INT PRIMARY KEY AUTO_INCREMENT, " +
						   "Username VARCHAR(25), " + 
						   "FirstName VARCHAR(25) NOT NULL, " + 
						   "LastName VARCHAR(25) NOT NULL, " + 
						   "DateOfBirth date NOT NULL, " +
						   "PrimaryPhysician int, " +
						   "FOREIGN KEY (Username) REFERENCES User(Username), " +
						   "FOREIGN KEY (PrimaryPhysician) REFERENCES Provider(ProviderID) " + 
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"'JohnDoe01', " +
						   		"'John', " +
						   		"'Doe' , " + 
						   		"'1990-01-01', "+ 
						   		"1)");
				   
				   // Insert row #2
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"'HenryBrumer01', " +
						   		"'Henry', " +
						   		"'Brumer' , " + 
						   		"'1990-02-01', "+ 
						   		"2)");

				   // Insert row #3
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"'NolaDelisa01', " +
						   		"'Nola', " +
						   		"'Delisa' , " + 
						   		"'1990-03-01', "+ 
						   		"3)");
				   
				   // Insert row #4
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"'KrissyHarold01', " +
						   		"'Krissy', " +
						   		"'Harold' , " + 
						   		"'1990-04-01', "+ 
						   		"4)");
				   
				   // Insert row #5
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"'ScotHeyward01', " +
						   		"'Scot', " +
						   		"'Heyward' , " + 
						   		"'1990-05-01', "+ 
						   		"5)");
				   
				   // Insert row #6
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Fredrick', " +
						   		"'Templeman' , " + 
						   		"'1990-06-01', "+ 
						   		"NULL)");
				   
				   // Insert row #7
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Chi', " +
						   		"'Hopple' , " + 
						   		"'1990-07-01', "+ 
						   		"NULL)");
				     
				   // Insert row #8
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Vinnie', " +
						   		"'Whitesel' , " + 
						   		"'2010-01-01', "+ 
						   		"6)");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Ethan', " +
						   		"'Cappel' , " + 
						   		"'2011-01-01', "+ 
						   		"7)");
				   
				   // Insert row #10
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Jona', " +
						   		"'Backhaus' , " + 
						   		"'2012-01-01', "+ 
						   		"8)");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Ettie', " +
						   		"'Fuquay' , " + 
						   		"'2013-01-01', "+ 
						   		"9)");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Shelia', " +
						   		"'Goodfellow' , " + 
						   		"'2014-01-01', "+ 
						   		"10)");
				    
				   // Insert row #13
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Dinah', " +
						   		"'Foskey' , " + 
						   		"'2015-01-01', "+ 
						   		"9)");
				     
				   // Insert row #14
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Ai', " +
						   		"'Lamirande' , " + 
						   		"'2016-01-01', "+ 
						   		"8)");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Kena', " +
						   		"'Scaglione' , " + 
						   		"'2017-01-01', "+ 
						   		"7)");
				   
				   // Insert row #16
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Renaldo', " +
						   		"'Hooley' , " + 
						   		"'2018-01-01', "+ 
						   		"NULL)");

				   // Insert row #17
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Alfreda', " +
						   		"'Yeh' , " + 
						   		"'2019-01-01', "+ 
						   		"NULL)");

				   // Insert row #18
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Lorinda', " +
						   		"'Rodas' , " + 
						   		"'2010-01-01', "+ 
						   		"NULL)");
				   
				   // Insert row #19
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
						   		"NULL, " +
						   		"'Sheri', " +
						   		"'Duffield' , " + 
						   		"'2011-01-01', "+ 
						   		"NULL)");
				     
				   // Insert row #20
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
					   		"NULL, " +
					   		"'Lemuel', " +
					   		"'Busch' , " + 
					   		"'2012-01-01', "+ 
					   		"NULL)");
				   
				   // Insert row #21
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
					   		"NULL, " +
					   		"'Ambrose', " +
					   		"'Dobyns' , " + 
					   		"'2013-01-01', "+ 
					   		"NULL)");
				   
				   // Insert row #22
				   stmt.execute("INSERT INTO Patient (Username, FirstName, LastName, DateOfBirth, PrimaryPhysician) VALUES (" +
					   		"NULL, " +
					   		"'Rebbecca', " +
					   		"'Shufelt' , " + 
					   		"'2014-01-01', "+ 
					   		"NULL)");
  
				   System.out.println("Patient table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
		   }
		   
		   /**
		    * The buildPatientInsuranceTable method creates the
		    * PatientInsurance table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildPatientInsuranceTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE PatientInsurance (" +
						   "PatientID INT, " + 
						   "InsuranceName VARCHAR(25), " + 
						   "GroupNo VARCHAR(25), " + 
						   "MemberNo VARCHAR(25), " + 
						   "InsuranceType VARCHAR(25), " +
						   "PRIMARY KEY (PatientID, InsuranceName, GroupNo), " + 
						   "FOREIGN KEY (PatientID) REFERENCES Patient(PatientID)" +
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"1, " +
						   		"'BlueCross BlueShield', " +
						   		"'12345', " + 
						   		"'55A12', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #2
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"1, " +
						   		"'Aetna', " +
						   		"'12345', " + 
						   		"'55A12', " + 
						   		"'secondary'" + 
						   		")");
				   
				   // Insert row #3
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"2, " +
						   		"'Aetna', " +
						   		"'838560', " + 
						   		"'930419', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #4
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"3, " +
						   		"'Cigna', " +
						   		"'113013', " + 
						   		"'212885', " + 
						   		"'primary'" + 
						   		")");

				   // Insert row #5
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"4, " +
						   		"'Humana', " +
						   		"'125652', " + 
						   		"'924256', " + 
						   		"'primary'" + 
						   		")");

				   // Insert row #6
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"5, " +
						   		"'Tricare', " +
						   		"'891755', " + 
						   		"'25536', " + 
						   		"'primary'" + 
						   		")");

				   // Insert row #7
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"6, " +
						   		"'Aetna', " +
						   		"'949399', " + 
						   		"'121883', " + 
						   		"'secondary'" + 
						   		")");

				   // Insert row #8
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"7, " +
						   		"'BlueCross BlueShield', " +
						   		"'686408', " + 
						   		"'529528', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"8, " +
						   		"'BlueCross BlueShield', " +
						   		"'12345', " + 
						   		"'55A12', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #10
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"9, " +
						   		"'BlueCross BlueShield', " +
						   		"'12345', " + 
						   		"'55A12', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"10, " +
						   		"'Aetna', " +
						   		"'838560', " + 
						   		"'930419', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"11, " +
						   		"'Aetna', " +
						   		"'838560', " + 
						   		"'930419', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #13
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"12, " +
						   		"'Aetna', " +
						   		"'838560', " + 
						   		"'930419', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #14
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"13, " +
						   		"'BlueCross BlueShield', " +
						   		"'12345', " + 
						   		"'55A12', " + 
						   		"'primary'" + 
						   		")");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO PatientInsurance VALUES (" +
						   		"14, " +
						   		"'BlueCross BlueShield', " +
						   		"'12345', " + 
						   		"'55A12', " + 
						   		"'primary'" + 
						   		")");
				   
				   System.out.println("PatientInsurance table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
		   }

		   /**
		    * The buildPatientDependentTable method creates the
		    * PatientDependent table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildPatientDependentTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE PatientDependent (" +
						   "PatientID INT, " + 
						   "DependentID INT, " + 
						   "PRIMARY KEY (PatientID, DependentID), " +
						   "FOREIGN KEY (PatientID) REFERENCES Patient(PatientID), " + 
						   "FOREIGN KEY (DependentID) REFERENCES Patient(PatientID)" + 
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"1, " +
						   		"8" +
						   		")");
				   
				   // Insert row #2
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"1, " +
						   		"9" +
						   		")");
				   
				   // Insert row #3
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"2, " +
						   		"10" +
						   		")");
				   
				   // Insert row #4
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"2, " +
						   		"11" +
						   		")");
				   
				   // Insert row #5
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"2, " +
						   		"12" +
						   		")");
				   
				   // Insert row #6
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"4, " +
						   		"13" +
						   		")");
				   
				   // Insert row #7
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"4, " +
						   		"14" +
						   		")");
				   
				   // Insert row #8
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"5, " +
						   		"15" +
						   		")");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"6, " +
						   		"16" +
						   		")");
				   
				   // Insert row #10
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"6, " +
						   		"17" +
						   		")");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"6, " +
						   		"18" +
						   		")");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"6, " +
						   		"19" +
						   		")");
				   
				   // Insert row #13
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"7, " +
						   		"20" +
						   		")");
				   
				   // Insert row #14
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"7, " +
						   		"21" +
						   		")");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO PatientDependent VALUES (" +
						   		"7, " +
						   		"22" +
						   		")");
				   
				   System.out.println("PatientDependent table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
		   }
		   
		   /**
		    * The buildProviderTable method creates the
		    * Provider table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildProviderTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE Provider (" +
						   "ProviderID INT PRIMARY KEY AUTO_INCREMENT, " +
						   "Username VARCHAR(25), " + 
						   "FirstName VARCHAR(25) NOT NULL, " + 
						   "LastName VARCHAR(25) NOT NULL, " + 
						   "Specialty VARCHAR(25), " +
						   "FOREIGN KEY (Username) REFERENCES User(Username)" + 
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"'MartinJones01', " +
						   		"'Martin', " +
						   		"'Jones', " + 
						   		"'General Practice' " +
						   		")");
				   
				   // Insert row #2
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"'MegAlphin01', " +
						   		"'Meg', " +
						   		"'Alphin', " + 
						   		"'General Practice' " +
						   		")");
				   
				   // Insert row #3
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"'ConcettaWhitesell01', " +
						   		"'Concetta', " +
						   		"'Whitesell', " + 
						   		"'General Practice' " +
						   		")");
				   
				   // Insert row #4
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"'MyriamZachary01', " +
						   		"'Myriam', " +
						   		"'Zachary', " + 
						   		"'General Practice' " +
						   		")");
				   
				   // Insert row #5
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"'SantanaGuth01', " +
						   		"'Santana', " +
						   		"'Guth', " + 
						   		"'General Practice' " +
						   		")");
				   
				   // Insert row #6
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Ofelia', " +
						   		"'Bernier', " + 
						   		"'Pediatrician' " +
						   		")");
				     
				   // Insert row #7
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Jammie', " +
						   		"'Webber', " + 
						   		"'Pediatrician' " +
						   		")");
				   
				   // Insert row #8
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Abe', " +
						   		"'Cuen', " + 
						   		"'Pediatrician' " +
						   		")");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Joette', " +
						   		"'Everman', " + 
						   		"'Pediatrician' " +
						   		")");
				   
				   // Insert row #10
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Lien', " +
						   		"'Arbuckle', " + 
						   		"'Pediatrician' " +
						   		")");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Emmy', " +
						   		"'Dansie', " + 
						   		"'Surgery' " +
						   		")");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Freeman', " +
						   		"'Banker', " + 
						   		"'Surgery' " +
						   		")");
				   
				   // Insert row #13
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Lauryn', " +
						   		"'Glymph', " + 
						   		"'Surgery' " +
						   		")");
				   
				   // Insert row #14
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Fidelia', " +
						   		"'Sato', " + 
						   		"'Surgery' " +
						   		")");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO Provider(Username, FirstName, LastName, Specialty) VALUES (" +
						   		"NULL, " +
						   		"'Sabrina', " +
						   		"'Langlais', " + 
						   		"'Surgery' " +
						   		")");
				    				   
				   System.out.println("Provider table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
		   }
		   
		   /**
		    * The buildSpecialtyTable method creates the
		    * Specialty table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildSpecialtyTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE Specialty (" +
						   "Specialty VARCHAR(25), " + 
						   "VisitReason VARCHAR(25), " + 
						   "Duration INT, " +  
						   "PRIMARY KEY (Specialty, VisitReason)" + 
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Pediatrician', " +
						   		"'Physical', " +
						   		"45" +  
						   		")");
				   
				   // Insert row #2
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Pediatrician', " +
						   		"'New Patient', " +
						   		"30" +  
						   		")");
				   
				   // Insert row #3
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Pediatrician', " +
						   		"'Vaccine', " +
						   		"20" +  
						   		")");
				   
				   // Insert row #4
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Pediatrician', " +
						   		"'Illness', " +
						   		"45" +  
						   		")");
				   
				   // Insert row #5
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Pediatrician', " +
						   		"'Injury', " +
						   		"60" +  
						   		")");
				   
				   // Insert row #6
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'General Practice', " +
						   		"'Physical', " +
						   		"45" +  
						   		")");
				   
				   // Insert row #7
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'General Practice', " +
						   		"'New Patient', " +
						   		"20" +  
						   		")");
				   
				   // Insert row #8
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'General Practice', " +
						   		"'Vaccine', " +
						   		"15" +  
						   		")");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'General Practice', " +
						   		"'Illness', " +
						   		"30" +  
						   		")");
				   
				   // Insert row #10
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'General Practice', " +
						   		"'Injury', " +
						   		"45" +  
						   		")");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Surgery', " +
						   		"'Pre-op', " +
						   		"60" +  
						   		")");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Surgery', " +
						   		"'Post-op', " +
						   		"30" +  
						   		")");
				   
				   // Insert row #13
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Surgery', " +
						   		"'Surgery (minor)', " +
						   		"90" +  
						   		")");
				   
				   // Insert row #14
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Surgery', " +
						   		"'Surgery (major)', " +
						   		"240" +  
						   		")");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO Specialty VALUES (" +
						   		"'Surgery', " +
						   		"'Consultation', " +
						   		"20" +  
						   		")");
				   
				   System.out.println("Specialty table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
		   }
		   
		   /**
		    * The buildAdminTable method creates the
		    * Admin table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildAdminTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE Admin (" +
						   "AdminID INT PRIMARY KEY AUTO_INCREMENT, " +
						   "Username VARCHAR(25), " + 
						   "FirstName VARCHAR(25), " + 
						   "LastName VARCHAR(25), " + 
						   "AccessLevel VARCHAR(25), " + 
						   "Role VARCHAR(25), " +
						   "FOREIGN KEY (Username) REFERENCES User(Username)" + 
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'AltaCueto01', " +
						   		"'Alta', " +
						   		"'Cueto', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #2
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'FredricHundley01', " +
						   		"'Fredric', " +
						   		"'Hundley', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #3
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'NatachaDorgan01', " +
						   		"'Natacha', " +
						   		"'Dorgan', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #4
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'LyndaHardt01', " +
						   		"'Lynda', " +
						   		"'Hardt', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #5
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'LatanyaMcgonigal01', " +
						   		"'Latanya', " +
						   		"'Mcgonigal', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #6
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'LiseRossin01', " +
						   		"'Lise', " +
						   		"'Rossin', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #7
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'ArnulfoZell01', " +
						   		"'Arnulfo', " +
						   		"'Zell', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #8
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'LaticiaMaloy01', " +
						   		"'Laticia', " +
						   		"'Maloy', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'KatiLasater01', " +
						   		"'Kati', " +
						   		"'Lasater', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");

				   // Insert row #10
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'LetaMcnew01', " +
						   		"'Leta', " +
						   		"'Mcnew', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'SeptemberMccrum01', " +
						   		"'September', " +
						   		"'Mccrum', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'DrewAlvarado01', " +
						   		"'Drew', " +
						   		"'Alvarado', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");

				   // Insert row #13
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'MaeganPouliot01', " +
						   		"'Maegan', " +
						   		"'Pouliot', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				
				   // Insert row #14
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'TommyFines01', " +
						   		"'Tommy', " +
						   		"'Fines', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO Admin(Username, FirstName, LastName, AccessLevel, Role) VALUES (" +
						   		"'AlisaBonnet01', " +
						   		"'Alisa', " +
						   		"'Bonnet', " + 
						   		"NULL, " + 
						   		"NULL" + 
						   		")");
				   
				   System.out.println("Admin table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
		   }
		   
		   /**
		    * The buildAppointmentTable method creates the
		    * Appointment table and adds rows to it
		    * @param conn SQL connection to appointment database
		    */
		   public static void buildAppointmentTable(Connection conn) {
			   try {
				   // Get a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // Create the table
				   stmt.execute("CREATE TABLE Appointment (" +
						   "PatientID INT, " + 
						   "PhysicianID INT, " + 
						   "AppointmentDateTime DATETIME, " + 
						   "VisitReason VARCHAR(25), " + 
						   "Comments VARCHAR(100), " +
						   "Status VARCHAR(25), " + 
						   "PRIMARY KEY (PatientID, PhysicianID, AppointmentDateTime), " +
						   "FOREIGN KEY (PatientID) REFERENCES Patient(PatientID), " +
						   "FOREIGN KEY (PhysicianID) REFERENCES Provider(ProviderID) " + 
						   ")");
				   
				   // Insert row #1
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"1, " +
						   		"1, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Vaccine', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #2
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"2, " +
						   		"2, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'New Patient', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #3
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"3, " +
						   		"3, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Physical', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #4	
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"4, " +
						   		"4, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Injury', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #5
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"5, " +
						   		"5, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Illness', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #6
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"6, " +
						   		"11, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Consultation', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #7
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"7, " +
						   		"12, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Pre-Op', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #8
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"8, " +
						   		"6, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Physical', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #9
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"9, " +
						   		"7, " +
						   		"'2019-04-27 14:00:00.00', " + 
						   		"'New Patient', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #10
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"10, " +
						   		"8, " +
						   		"'2019-04-27 14:00:00.00', " + 
						   		"'Physical', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #11
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"11, " +
						   		"9, " +
						   		"'2019-04-27 14:00:00.00', " + 
						   		"'Injury', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #12
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"12, " +
						   		"10, " +
						   		"'2019-04-27 14:00:00.00', " + 
						   		"'Illness', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #13
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"13, " +
						   		"9, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Physical', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #14
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"14, " +
						   		"8, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'Vaccine', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   // Insert row #15
				   stmt.execute("INSERT INTO Appointment VALUES (" +
						   		"15, " +
						   		"7, " +
						   		"'2019-04-27 13:00:00.00', " + 
						   		"'New Patient', " + 
						   		"NULL," +
						   		"NULL" + 
						   		")");
				   
				   System.out.println("Appointment table created.");
			   }
			   catch(SQLException ex) {
				   System.out.println("ERROR: " + ex.getMessage());
			   }
		   }

}


