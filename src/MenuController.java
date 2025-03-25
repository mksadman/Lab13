import java.util.Scanner;

public class MenuController {
    private final UserInterface userInterface;
    private final UserAuthenticationService authService;
    private final Scanner scanner;
    private final Customer customerManager;
    private final Flight flightManager;
    private final FlightReservation bookingManager;

    public MenuController(Scanner scanner) {
        this.scanner = scanner;
        this.userInterface = new UserInterface(scanner);
        this.authService = new UserAuthenticationService(userInterface);
        this.customerManager = new Customer();
        this.flightManager = new Flight();
        this.bookingManager = new FlightReservation();
        
        // Initialize flight schedule
        this.flightManager.flightScheduler();
    }

    public void start() {
        int countNumOfUsers = 1;
        displayWelcomeMessage();
        
        int desiredOption = getValidMenuOption();
        do {
            switch (desiredOption) {
                case 1:
                    handleAdminLogin();
                    break;
                case 2:
                    handleAdminRegistration(countNumOfUsers++);
                    break;
                case 3:
                    handlePassengerLogin();
                    break;
                case 4:
                    customerManager.addNewCustomer();
                    break;
                case 5:
                    userInterface.displayManualInstructions();
                    break;
            }

            userInterface.displayMainMenu();
            desiredOption = getValidMenuOption();
        } while (desiredOption != 0);
    }

    private void displayWelcomeMessage() {
        System.out.println("\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++\n\nTo Further Proceed, Please enter a value.");
        System.out.println("\n***** Default Username && Password is root-root ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
    }

    private int getValidMenuOption() {
        return userInterface.getValidMenuOption(0, 8);
    }

    private void handleFlightBooking(String userId) {
        flightManager.displayFlightSchedule();
        System.out.print("\nEnter the desired flight number to book:\t ");
        String flightToBeBooked = scanner.nextLine();
        System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight:   ");
        int numOfTickets = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        while (!userInterface.getValidationService().isValidTicketQuantity(numOfTickets, 10)) {
            System.out.print("ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
            numOfTickets = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        }
        bookingManager.bookFlight(flightToBeBooked, numOfTickets, userId);
    }

    private void handleAdminLogin() {
        UserCredentials credentials = userInterface.getUserCredentials(true);
        if (authService.authenticateAdmin(credentials.getUsername(), credentials.getPassword())) {
            userInterface.displayMessage(String.format("%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", credentials.getUsername()));
            handleAdminMenu(credentials.getUsername());
        } else {
            userInterface.displayMessage("\n                    ERROR!!! Unable to login Cannot find user with the entered credentials.... " +
                    "Try Creating New Credentials or get yourself register by pressing 4...\n");
        }
    }

    private void handleAdminRegistration(int userCount) {
        UserCredentials credentials = userInterface.getRegistrationCredentials(true);
        while (!authService.registerAdmin(credentials.getUsername(), credentials.getPassword(), userCount)) {
            System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
            credentials = userInterface.getRegistrationCredentials(true);
        }
    }

    private void handlePassengerLogin() {
        UserCredentials credentials = userInterface.getUserCredentials(false);
        Customer authenticatedCustomer = authService.authenticatePassenger(credentials.getUsername(), credentials.getPassword());
        
        if (authenticatedCustomer != null) {
            userInterface.displayMessage(String.format("\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", credentials.getUsername()));
            handlePassengerMenu(credentials.getUsername(), authenticatedCustomer.getUserID());
        } else {
            userInterface.displayMessage(String.format("\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... " +
                    "Try Creating New Credentials or get yourself register by pressing 4....\n", ""));
        }
    }

    private void handleAdminMenu(String username) {
        int desiredOption;
        do {
            userInterface.displayAdminMenu(username);
            desiredOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (desiredOption) {
                case 1:
                    customerManager.addNewCustomer();
                    break;
                case 2:
                    handleCustomerSearch();
                    break;
                case 3:
                    handleCustomerUpdate();
                    break;
                case 4:
                    handleCustomerDelete();
                    break;
                case 5:
                    customerManager.displayCustomersData(false);
                    break;
                case 6:
                    handleFlightDisplay();
                    break;
                case 7:
                    handlePassengerDisplay();
                    break;
                case 8:
                    handleFlightDeletion();
                    break;
                case 0:
                    break;
                default:
                    userInterface.displayMessage("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                    desiredOption = 0;
            }
        } while (desiredOption != 0);
    }

    private void handlePassengerMenu(String username, String userId) {
        int desiredChoice;
        do {
            userInterface.displayPassengerMenu(username);
            desiredChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (desiredChoice) {
                case 1:
                    handleFlightBooking(userId);
                    break;
                case 2:
                    customerManager.editUserInfo(userId);
                    break;
                case 3:
                    handleAccountDeletion(username, userId);
                    break;
                case 4:
                    flightManager.displayFlightSchedule();
                    flightManager.displayMeasurementInstructions();
                    break;
                case 5:
                    bookingManager.cancelFlight(userId);
                    break;
                case 6:
                    bookingManager.displayFlightsRegisteredByOneUser(userId);
                    break;
                case 0:
                    break;
                default:
                    userInterface.displayMessage("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                    desiredChoice = 0;
            }
        } while (desiredChoice != 0);
    }

    private void handleCustomerSearch() {
        customerManager.displayCustomersData(false);
        String customerID = userInterface.getCustomerId("Search");
        System.out.println();
        customerManager.searchUser(customerID);
    }

    private void handleCustomerUpdate() {
        customerManager.displayCustomersData(false);
        String customerID = userInterface.getCustomerId("Update its Data");
        if (UserManager.getCustomers().size() > 0) {
            customerManager.editUserInfo(customerID);
        } else {
            userInterface.displayMessage(String.format("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID));
        }
    }

    private void handleCustomerDelete() {
        customerManager.displayCustomersData(false);
        String customerID = userInterface.getCustomerId("Delete its Data");
        if (UserManager.getCustomers().size() > 0) {
            customerManager.deleteUser(customerID);
        } else {
            userInterface.displayMessage(String.format("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID));
        }
    }

    private void handleFlightDisplay() {
        customerManager.displayCustomersData(false);
        System.out.print("\n\nEnter the ID of the user to display all flights registered by that user...");
        String id = scanner.nextLine();
        bookingManager.displayFlightsRegisteredByOneUser(id);
    }

    private void handlePassengerDisplay() {
        System.out.print("Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a" +
                " specific flight.... ");
        char choice = scanner.nextLine().charAt(0);
        if ('y' == choice || 'Y' == choice) {
            bookingManager.displayRegisteredUsersForAllFlight();
        } else if ('n' == choice || 'N' == choice) {
            flightManager.displayFlightSchedule();
            System.out.print("Enter the Flight Number to display the list of passengers registered in that flight... ");
            String flightNum = scanner.nextLine();
            bookingManager.displayRegisteredUsersForASpecificFlight(flightNum);
        } else {
            System.out.println("Invalid Choice...No Response...!");
        }
    }

    private void handleFlightDeletion() {
        flightManager.displayFlightSchedule();
        System.out.print("Enter the Flight Number to delete the flight : ");
        String flightNum = scanner.nextLine();
        flightManager.deleteFlight(flightNum);
    }

    private void handleAccountDeletion(String username, String userId) {
        System.out.print("Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
        char confirmationChar = scanner.nextLine().charAt(0);
        if (confirmationChar == 'Y' || confirmationChar == 'y') {
            customerManager.deleteUser(userId);
            userInterface.displayMessage(String.format("User %s's account deleted Successfully...!!!", username));
        } else {
            userInterface.displayMessage("Action has been cancelled...");
        }
    }
}