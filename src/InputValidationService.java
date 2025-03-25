public class InputValidationService {
    /**
     * Validates menu option input
     * @param option The input option
     * @param minValue The minimum allowed value
     * @param maxValue The maximum allowed value
     * @return boolean indicating if the input is valid
     */
    public boolean isValidMenuOption(int option, int minValue, int maxValue) {
        return option >= minValue && option <= maxValue;
    }

    /**
     * Validates ticket quantity
     * @param numOfTickets Number of tickets requested
     * @param maxTickets Maximum allowed tickets
     * @return boolean indicating if the quantity is valid
     */
    public boolean isValidTicketQuantity(int numOfTickets, int maxTickets) {
        return numOfTickets > 0 && numOfTickets <= maxTickets;
    }

    /**
     * Validates manual choice input
     * @param choice The input choice
     * @return boolean indicating if the choice is valid
     */
    public boolean isValidManualChoice(int choice) {
        return choice == 1 || choice == 2;
    }
}