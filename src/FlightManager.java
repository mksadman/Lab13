import java.util.List;

public class FlightManager {

    public void registerNewFlight(Customer customer, Flight flight) {
        customer.flightsRegisteredByUser.add(flight);
    }

    public void addTicketsToExistingFlight(Customer customer, int index, int numOfTickets) {
        int newNumOfTickets = customer.numOfTicketsBookedByUser.get(index) + numOfTickets;
        customer.numOfTicketsBookedByUser.set(index, newNumOfTickets);
    }

    public boolean isFlightRegistered(List<Flight> flightList, Flight flight) {
        for (Flight f : flightList) {
            if (f.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                return true;
            }
        }
        return false;
    }

    public int getFlightIndex(List<Flight> flightList, Flight flight) {
        for (int i = 0; i < flightList.size(); i++) {
            if (flightList.get(i).getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                return i;
            }
        }
        return -1;
    }

    public void bookNewFlight(Customer customer, Flight flight, int numOfTickets) {
        registerNewFlight(customer, flight);
        customer.numOfTicketsBookedByUser.add(numOfTickets);
    }

    public void updateFlightBooking(Customer customer, Flight flight, int numOfTickets) {
        int index = getFlightIndex(customer.flightsRegisteredByUser, flight);
        if (index != -1) {
            addTicketsToExistingFlight(customer, index, numOfTickets);
        }
    }
}
