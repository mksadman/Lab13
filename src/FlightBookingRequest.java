/**
 * Parameter object class to encapsulate flight booking parameters.
 * This class groups related parameters used in flight booking operations.
 */
public class FlightBookingRequest {
    private final String flightNo;
    private final int numOfTickets;
    private final String userID;

    /**
     * Constructor to create a flight booking request with all required parameters.
     * 
     * @param flightNo     FlightID of the flight to be booked
     * @param numOfTickets number of tickets to be booked
     * @param userID       userID of the user which is booking the flight
     */
    public FlightBookingRequest(String flightNo, int numOfTickets, String userID) {
        this.flightNo = flightNo;
        this.numOfTickets = numOfTickets;
        this.userID = userID;
    }

    /**
     * Gets the flight number.
     * 
     * @return the flight number
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * Gets the number of tickets.
     * 
     * @return the number of tickets
     */
    public int getNumOfTickets() {
        return numOfTickets;
    }

    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public String getUserID() {
        return userID;
    }
}