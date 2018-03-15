public class RyanFlightSearch {

	
	//Create global variables that will be used within the class
	private static String flightNumber = null;
	private static String flightSource = null;
	private static String flightDest = null;
	private static String FlightTime = null;
	private static String numberOfSeatsAviable = null;
	private static String passengerFirstName = null;
	private static String passengerLastName = null;
	
	//Create the getters and setters
	public static String getPassangerFirstName() {
		return passengerFirstName;
	}
	public static void setPassangerFirstName(String passangerFirstName) {
		passengerFirstName = passangerFirstName;
	}
	public static String getPassagnerLastName() {
		return passengerLastName;
	}
	public void setPassagnerLastName(String passagnerLastName) {
		RyanFlightSearch.passengerLastName = passagnerLastName;
	}
	
	public static String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		RyanFlightSearch.flightNumber = flightNumber;
	}
	public static String getFlightSource() {
		return flightSource;
	}
	public void setFlightSource(String flightSource) {
		RyanFlightSearch.flightSource = flightSource;
	}
	public static String getFlightDest() {
		return flightDest;
	}
	public void setFlightDest(String flightDest) {
		RyanFlightSearch.flightDest = flightDest;
	}
	public static String getFlightTime() {
		return FlightTime;
	}
	public static void setFlightTime(String flightTime) {
		FlightTime = flightTime;
	}
	public static String getNumberOfSeatsAviable() {
		return numberOfSeatsAviable;
	}
	public void setNumberOfSeatsAviable(String numberOfSeatsAviable) {
		RyanFlightSearch.numberOfSeatsAviable = numberOfSeatsAviable;
	}
	public static String getNumberofSeatsRequested() {
		return numberofSeatsRequested;
	}
	public void setNumberofSeatsRequested(String numberofSeatsRequested) {
		RyanFlightSearch.numberofSeatsRequested = numberofSeatsRequested;
	}
	private static String numberofSeatsRequested = null;
	public static String getPassagnerName() {
		return passengerFirstName + passengerLastName;
	}
	
}
