public class FlightTimeCalculator {
    private static final double GROUND_SPEED = 450;

    /**
     * Calculates the flight time, using avg. ground speed of 450 knots.
     *
     * @param distanceInMiles distance between the cities/airports in miles
     * @return formatted flight time
     */
    public String calculateFlightTime(double distanceInMiles) {
        double time = (distanceInMiles / GROUND_SPEED);
        String timeInString = String.format("%.4s", time);
        String[] timeArray = timeInString.replace('.', ':').split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);
        
        // Changing flight time to make minutes near/divisible to 5
        minutes = roundToNearestFive(minutes);
        
        if (minutes >= 60) {
            minutes -= 60;
            hours++;
        }
        
        return formatTime(hours, minutes);
    }

    private int roundToNearestFive(int minutes) {
        int modulus = minutes % 5;
        if (modulus < 3) {
            return minutes - modulus;
        } else {
            return minutes + (5 - modulus);
        }
    }

    private String formatTime(int hours, int minutes) {
        if (hours <= 9 && Integer.toString(minutes).length() == 1) {
            return String.format("0%s:%s0", hours, minutes);
        } else if (hours <= 9 && Integer.toString(minutes).length() > 1) {
            return String.format("0%s:%s", hours, minutes);
        } else if (hours > 9 && Integer.toString(minutes).length() == 1) {
            return String.format("%s:%s0", hours, minutes);
        } else {
            return String.format("%s:%s", hours, minutes);
        }
    }
}