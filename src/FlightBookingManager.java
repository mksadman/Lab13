import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * FlightBookingManager class handles the flight booking and cancellation operations.
 * This class is extracted from FlightReservation to improve separation of concerns.
 */
public class FlightBookingManager {
    private final Flight flight = new Flight();
    private int flightIndexInFlightList;

    /**
     * Book the flight based on the provided booking request.
     * Updates the available seats in main system and manages customer registrations.
     *
     * @param request The flight booking request containing all necessary booking information
     * @return true if booking was successful, false otherwise
     */
    public boolean bookFlight(FlightBookingRequest request) {
        boolean isFound = false;
        String flightNo = request.getFlightNo();
        int numOfTickets = request.getNumOfTickets();
        String userID = request.getUserID();
        
        for (Flight f1 : flight.getFlightList()) {
            if (flightNo.equalsIgnoreCase(f1.getFlightNumber())) {
                for (Customer customer : Customer.customerCollection) {
                    if (userID.equals(customer.getUserID())) {
                        isFound = true;
                        processFlightBooking(f1, customer, numOfTickets);
                        break;
                    }
                }
            }
        }
        
        if (!isFound) {
            System.out.println("Invalid Flight Number...! No flight with the  ID \"" + flightNo + "\" was found...");
        } else {
            System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flightNo.toUpperCase());
        }
        
        return isFound;
    }
    
    /**
     * Process the flight booking by updating seats and managing customer registrations.
     * 
     * @param flight The flight to be booked
     * @param customer The customer booking the flight
     * @param numOfTickets Number of tickets to book
     */
    private void processFlightBooking(Flight flight, Customer customer, int numOfTickets) {
        flight.setNoOfSeatsInTheFlight(flight.getNoOfSeats() - numOfTickets);
        
        if (!flight.isCustomerAlreadyAdded(flight.getListOfRegisteredCustomersInAFlight(), customer)) {
            flight.addNewCustomerToFlight(customer);
        }
        
        if (isFlightAlreadyAddedToCustomerList(customer.flightsRegisteredByUser, flight)) {
            addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets);
            if (flightIndex(this.flight.getFlightList(), flight) != -1) {
                customer.addExistingFlightToCustomerList(flightIndex(this.flight.getFlightList(), flight), numOfTickets);
            }
        } else {
            customer.addNewFlightToCustomerList(flight);
            addNumberOfTicketsForNewFlight(customer, numOfTickets);
        }
    }

    /**
     * Cancels the flight for a particular user and returns the tickets back to the main flight scheduler.
     *
     * @param userID ID of the user for whom the flight is to be cancelled
     * @return true if cancellation was successful, false otherwise
     */
    public boolean cancelFlight(String userID) {
        String flightNum = "";
        Scanner read = new Scanner(System.in);
        int index = 0, ticketsToBeReturned;
        boolean isFound = false;
        
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                if (customer.getFlightsRegisteredByUser().size() != 0) {
                    System.out.printf("%50s %s Here is the list of all the Flights registered by you %s", " ", "+++++++++++++", "+++++++++++++");
                    displayFlightsRegisteredByOneUser(userID);
                    System.out.print("Enter the Flight Number of the Flight you want to cancel : ");
                    flightNum = read.nextLine();
                    System.out.print("Enter the number of tickets to cancel : ");
                    int numOfTickets = read.nextInt();
                    
                    isFound = processCancellation(customer, flightNum, numOfTickets);
                } else {
                    System.out.println("No Flight Has been Registered by you with ID \"\"" + flightNum.toUpperCase() +"\"\".....");
                } 
                
                if (!isFound) {
                    System.out.println("ERROR!!! Couldn't find Flight with ID \"" + flightNum.toUpperCase() + "\".....");
                }
            }
        }
        
        return isFound;
    }
    
    /**
     * Process the flight cancellation by updating tickets and customer registrations.
     * 
     * @param customer The customer cancelling the flight
     * @param flightNum The flight number to cancel
     * @param numOfTickets Number of tickets to cancel
     * @return true if cancellation was processed successfully, false otherwise
     */
    private boolean processCancellation(Customer customer, String flightNum, int numOfTickets) {
        Scanner read = new Scanner(System.in);
        int index = 0;
        boolean isFound = false;
        
        Iterator<Flight> flightIterator = customer.getFlightsRegisteredByUser().iterator();
        while (flightIterator.hasNext()) {
            Flight f = flightIterator.next();
            if (flightNum.equalsIgnoreCase(f.getFlightNumber())) {
                isFound = true;
                int numOfTicketsForFlight = customer.getNumOfTicketsBookedByUser().get(index);
                
                while (numOfTickets > numOfTicketsForFlight) {
                    System.out.print("ERROR!!! Number of tickets cannot be greater than " + numOfTicketsForFlight + " for this flight. Please enter the number of tickets again : ");
                    numOfTickets = read.nextInt();
                }
                
                int ticketsToBeReturned;
                if (numOfTicketsForFlight == numOfTickets) {
                    ticketsToBeReturned = f.getNoOfSeats() + numOfTicketsForFlight;
                    customer.numOfTicketsBookedByUser.remove(index);
                    flightIterator.remove();
                } else {
                    ticketsToBeReturned = numOfTickets + f.getNoOfSeats();
                    customer.numOfTicketsBookedByUser.set(index, (numOfTicketsForFlight - numOfTickets));
                }
                
                f.setNoOfSeatsInTheFlight(ticketsToBeReturned);
                break;
            }
            index++;
        }
        
        return isFound;
    }

    /**
     * Adds tickets to an already booked flight for a customer.
     *
     * @param customer The customer booking additional tickets
     * @param numOfTickets Number of additional tickets to book
     */
    void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets) {
        FlightManager flightManager = new FlightManager();
        flightManager.addTicketsToExistingFlight(customer, flightIndexInFlightList, numOfTickets);
    }

    /**
     * Adds tickets for a new flight booking.
     *
     * @param customer The customer booking the flight
     * @param numOfTickets Number of tickets to book
     */
    void addNumberOfTicketsForNewFlight(Customer customer, int numOfTickets) {
        customer.numOfTicketsBookedByUser.add(numOfTickets);
    }

    /**
     * Checks if a flight is already added to a customer's list of flights.
     *
     * @param flightList The list of flights to check
     * @param flight The flight to look for
     * @return true if the flight is already in the list, false otherwise
     */
    boolean isFlightAlreadyAddedToCustomerList(List<Flight> flightList, Flight flight) {
        FlightManager flightManager = new FlightManager();
        boolean addedOrNot = flightManager.isFlightRegistered(flightList, flight);
        if (addedOrNot) {
            this.flightIndexInFlightList = flightManager.getFlightIndex(flightList, flight);
        }
        return addedOrNot;
    }

    /**
     * Gets the index of a flight in a list of flights.
     *
     * @param flightList The list of flights to search
     * @param flight The flight to find the index of
     * @return The index of the flight, or -1 if not found
     */
    int flightIndex(List<Flight> flightList, Flight flight) {
        FlightManager flightManager = new FlightManager();
        return flightManager.getFlightIndex(flightList, flight);
    }

    /**
     * Gets the status of a flight.
     *
     * @param f The flight to check the status of
     * @return The status of the flight as a string
     */
    String flightStatus(Flight f) {
        boolean isFlightAvailable = false;
        for (Flight list : flight.getFlightList()) {
            if (list.getFlightNumber().equalsIgnoreCase(f.getFlightNumber())) {
                isFlightAvailable = true;
                break;
            }
        }
        if (isFlightAvailable) {
            return "As Per Schedule";
        } else {
            return "   Cancelled   ";
        }
    }
    
    /**
     * Displays the flights registered by a specific user.
     *
     * @param userID The ID of the user whose flights to display
     */
    public void displayFlightsRegisteredByOneUser(String userID) {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO |  Booked Tickets  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |  FLIGHT STATUS  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        for (Customer customer : Customer.customerCollection) {
            List<Flight> f = customer.getFlightsRegisteredByUser();
            int size = customer.getFlightsRegisteredByUser().size();
            if (userID.equals(customer.getUserID())) {
                for (int i = 0; i < size; i++) {
                    System.out.println(toString((i + 1), f.get(i), customer));
                    System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
                }
            }
        }
    }
    
    /**
     * Formats flight information for display.
     *
     * @param serialNum The serial number of the flight
     * @param flights The flight to display
     * @param customer The customer who booked the flight
     * @return A formatted string with flight information
     */
    public String toString(int serialNum, Flight flights, Customer customer) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9d | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  | %-10s |", serialNum, flights.getFlightSchedule(), flights.getFlightNumber(), customer.numOfTicketsBookedByUser.get(serialNum - 1), flights.getFromWhichCity(), flights.getToWhichCity(), flights.fetchArrivalTime(), flights.getFlightTime(), flights.getGate(), flightStatus(flights));
    }
}