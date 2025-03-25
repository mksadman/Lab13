import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FlightScheduler {
    private final List<Flight> flightList;
    private final RandomGenerator randomGenerator;

    public FlightScheduler() {
        this.flightList = new ArrayList<>();
        this.randomGenerator = new RandomGenerator();
    }

    public List<Flight> scheduleFlights(int numOfFlights) {
        for (int i = 0; i < numOfFlights; i++) {
            String[][] chosenDestinations = randomGenerator.randomDestinations();
            String[] distanceBetweenTheCities = calculateDistance(chosenDestinations);
            String flightSchedule = createNewFlightsAndTime();
            String flightNumber = randomGenerator.randomFlightNumbGen(2, 1).toUpperCase();
            int numOfSeatsInTheFlight = randomGenerator.randomNumOfSeats();
            String gate = randomGenerator.randomFlightNumbGen(1, 30).toUpperCase();
            
            flightList.add(new Flight(flightSchedule, flightNumber, numOfSeatsInTheFlight, 
                                     chosenDestinations, distanceBetweenTheCities, gate));
        }
        return flightList;
    }

    private String[] calculateDistance(String[][] chosenDestinations) {
        return new DistanceCalculator().calculateDistance(
            Double.parseDouble(chosenDestinations[0][1]), 
            Double.parseDouble(chosenDestinations[0][2]),
            Double.parseDouble(chosenDestinations[1][1]), 
            Double.parseDouble(chosenDestinations[1][2]));
    }

    private String createNewFlightsAndTime() {
        Calendar c = Calendar.getInstance();
        int nextFlightDay = (int) (Math.random() * 7);
        c.add(Calendar.DATE, nextFlightDay);
        c.add(Calendar.HOUR, nextFlightDay);
        c.set(Calendar.MINUTE, ((c.get(Calendar.MINUTE) * 3) - (int) (Math.random() * 45)));
        Date myDateObj = c.getTime();
        LocalDateTime date = Instant.ofEpochMilli(myDateObj.getTime())
                                  .atZone(ZoneId.systemDefault())
                                  .toLocalDateTime();
        date = getNearestHourQuarter(date);
        return date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
    }

    private LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        int minutes = datetime.getMinute();
        int mod = minutes % 15;
        if (mod < 8) {
            return datetime.minusMinutes(mod);
        } else {
            return datetime.plusMinutes(15 - mod);
        }
    }
}
