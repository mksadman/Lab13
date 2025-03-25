import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {

    // 2D Array to store admin credentials. Default credentials are stored on [0][0]
    // index. Max num of admins can be 10....

    static String[][] adminUserNameAndPassword = new String[10][2];
    private static List<Customer> customersCollection = new ArrayList<>();
    
    // Initialize default admin credentials
    static {
        adminUserNameAndPassword[0][0] = "root";
        adminUserNameAndPassword[0][1] = "root";
    }

    public static void main(String[] args) {
        int countNumOfUsers = 1;
        RolesAndPermissions r1 = new RolesAndPermissions();
        Flight f1 = new Flight();
        FlightReservation bookingAndReserving = new FlightReservation();
        Customer c1 = new Customer();
        f1.flightScheduler();
        
        // Use a single Scanner for the entire program
        Scanner read = new Scanner(System.in);
        Scanner read1 = new Scanner(System.in);
        
        // Create UserInterface instance for handling UI interactions
        UserInterface ui = new UserInterface(read1);
       
        System.out.println(
                "\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++\n\nTo Further Proceed, Please enter a value.");
        System.out.println(
                "\n***** Default Username && Password is root-root ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
        
        ui.displayMainMenu();
        int desiredOption = read.nextInt();
        while (desiredOption < 0 || desiredOption > 8) {
            System.out.print("ERROR!! Please enter value between 0 - 8. Enter the value again :\t");
            desiredOption = read.nextInt();
        }

        do {
            if (desiredOption == 1) {
                handleAdminLogin(r1, c1, f1, bookingAndReserving, ui, read);
            } else if (desiredOption == 2) {
                handleAdminRegistration(r1, ui, countNumOfUsers);
                countNumOfUsers++;
            } else if (desiredOption == 3) {
                handlePassengerLogin(r1, c1, f1, bookingAndReserving, ui, read);
            } else if (desiredOption == 4) {
                c1.addNewCustomer();
            } else if (desiredOption == 5) {
                ui.displayManualInstructions();
            }

            ui.displayMainMenu();
            desiredOption = read.nextInt();
            read.nextLine(); // Consume the newline character
            while (desiredOption < 0 || desiredOption > 8) {
                System.out.print("ERROR!! Please enter value between 0 - 8. Enter the value again :\t");
                desiredOption = read.nextInt();
                read.nextLine(); // Consume the newline character
            }
        } while (desiredOption != 0);
        
        // Close the scanners to prevent resource leaks
        read.close();
        read1.close();
    }
    
    /**
     * Handles admin login process
     */
    private static void handleAdminLogin(RolesAndPermissions r1, Customer c1, Flight f1, 
            FlightReservation bookingAndReserving, UserInterface ui, Scanner read) {
        
        UserCredentials credentials = ui.getUserCredentials(true);
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        System.out.println();

        if (r1.checkAdminAccess(username, password) == -1) {
            ui.displayMessage("\n                    ERROR!!! Unable to login Cannot find user with the entered credentials.... " +
                    "Try Creating New Credentials or get yourself register by pressing 4...\n");
        } else if (r1.checkAdminAccess(username, password) == 0) {
            ui.displayMessage("You've standard/default privileges to access the data... You can just view customers data..." +
                    "Can't perform any actions on them....");
            c1.displayCustomersData(true);
        } else {
            ui.displayMessage(String.format("%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", username));

            handleAdminMenu(c1, f1, bookingAndReserving, ui, read, username);
        }
    }
    
    /**
     * Handles admin menu navigation
     */
    private static void handleAdminMenu(Customer c1, Flight f1, FlightReservation bookingAndReserving, 
            UserInterface ui, Scanner read, String username) {
        int desiredOption;
        do {
            ui.displayAdminMenu(username);
            desiredOption = read.nextInt();
            read.nextLine(); // Consume newline
            
            if (desiredOption == 1) {
                c1.addNewCustomer();
            } else if (desiredOption == 2) {
                c1.displayCustomersData(false);
                String customerID = ui.getCustomerId("Search");
                System.out.println();
                c1.searchUser(customerID);
            } else if (desiredOption == 3) {
                c1.displayCustomersData(false);
                String customerID = ui.getCustomerId("Update its Data");
                if (customersCollection.size() > 0) {
                    c1.editUserInfo(customerID);
                } else {
                    ui.displayMessage(String.format("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID));
                }
            } else if (desiredOption == 4) {
                c1.displayCustomersData(false);
                String customerID = ui.getCustomerId("Delete its Data");
                if (customersCollection.size() > 0) {
                    c1.deleteUser(customerID);
                } else {
                    ui.displayMessage(String.format("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID));
                }
            } else if (desiredOption == 5) {
                c1.displayCustomersData(false);
            } else if (desiredOption == 6) {
                c1.displayCustomersData(false);
                System.out.print("\n\nEnter the ID of the user to display all flights registered by that user...");
                String id = read.nextLine();
                bookingAndReserving.displayFlightsRegisteredByOneUser(id);
            } else if (desiredOption == 7) {
                handleFlightPassengersDisplay(f1, bookingAndReserving, read);
            } else if (desiredOption == 8) {
                f1.displayFlightSchedule();
                System.out.print("Enter the Flight Number to delete the flight : ");
                String flightNum = read.nextLine();
                f1.deleteFlight(flightNum);
            } else if (desiredOption != 0) {
                ui.displayMessage("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                desiredOption = 0;
            }
        } while (desiredOption != 0);
    }
    
    /**
     * Handles admin registration process
     */
    private static void handleAdminRegistration(RolesAndPermissions r1, UserInterface ui, int countNumOfUsers) {
        UserCredentials credentials = ui.getRegistrationCredentials(true);
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        
        while (r1.checkAdminAccess(username, password) != -1) {
            System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
            username = ui.getRegistrationCredentials(true).getUsername();
            System.out.print("Enter the Password Again:   ");
            password = ui.getRegistrationCredentials(true).getPassword();
        }

        /* Setting the credentials entered by the user..... */
        adminUserNameAndPassword[countNumOfUsers][0] = username;
        adminUserNameAndPassword[countNumOfUsers][1] = password;
    }
    
    /**
     * Handles passenger login process
     */
    private static void handlePassengerLogin(RolesAndPermissions r1, Customer c1, Flight f1, 
            FlightReservation bookingAndReserving, UserInterface ui, Scanner read) {
        
        UserCredentials credentials = ui.getUserCredentials(false);
        String userName = credentials.getUsername();
        String password = credentials.getPassword();
        
        String[] result = r1.isPassengerRegistered(userName, password).split("-");

        if (Integer.parseInt(result[0]) == 1) {
            ui.displayMessage(String.format("\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", userName));
            
            handlePassengerMenu(c1, f1, bookingAndReserving, ui, read, userName, result);
        } else {
            ui.displayMessage(String.format("\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... " +
                    "Try Creating New Credentials or get yourself register by pressing 4....\n", ""));
        }
    }
    
    /**
     * Handles passenger menu navigation
     */
    private static void handlePassengerMenu(Customer c1, Flight f1, FlightReservation bookingAndReserving, 
            UserInterface ui, Scanner read, String userName, String[] result) {
        
        int desiredChoice;
        do {
            ui.displayPassengerMenu(userName);
            desiredChoice = read.nextInt();
            read.nextLine(); // Consume newline
            
            if (desiredChoice == 1) {
                f1.displayFlightSchedule();
                System.out.print("\nEnter the desired flight number to book:\t ");
                String flightToBeBooked = read.nextLine();
                System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight:   ");
                int numOfTickets = read.nextInt();
                read.nextLine(); // Consume newline
                
                while (numOfTickets > 10) {
                    System.out.print("ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
                    numOfTickets = read.nextInt();
                    read.nextLine(); // Consume newline
                }
                bookingAndReserving.bookFlight(flightToBeBooked, numOfTickets, result[1]);
            } else if (desiredChoice == 2) {
                c1.editUserInfo(result[1]);
            } else if (desiredChoice == 3) {
                System.out.print("Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
                char confirmationChar = read.nextLine().charAt(0);
                if (confirmationChar == 'Y' || confirmationChar == 'y') {
                    c1.deleteUser(result[1]);
                    ui.displayMessage(String.format("User %s's account deleted Successfully...!!!", userName));
                    desiredChoice = 0;
                } else {
                    ui.displayMessage("Action has been cancelled...");
                }
            } else if (desiredChoice == 4) {
                f1.displayFlightSchedule();
                f1.displayMeasurementInstructions();
            } else if (desiredChoice == 5) {
                bookingAndReserving.cancelFlight(result[1]);
            } else if (desiredChoice == 6) {
                bookingAndReserving.displayFlightsRegisteredByOneUser(result[1]);
            } else if (desiredChoice != 0) {
                ui.displayMessage("Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                desiredChoice = 0;
            }
        } while (desiredChoice != 0);
    }
    
    /**
     * Handles displaying passengers for flights
     */
    private static void handleFlightPassengersDisplay(Flight f1, FlightReservation bookingAndReserving, Scanner read) {
        System.out.print("Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a" +
                " specific flight.... ");
        char choice = read.nextLine().charAt(0);
        if ('y' == choice || 'Y' == choice) {
            bookingAndReserving.displayRegisteredUsersForAllFlight();
        } else if ('n' == choice || 'N' == choice) {
            f1.displayFlightSchedule();
            System.out.print("Enter the Flight Number to display the list of passengers registered in that flight... ");
            String flightNum = read.nextLine();
            bookingAndReserving.displayRegisteredUsersForASpecificFlight(flightNum);
        } else {
            System.out.println("Invalid Choice...No Response...!");
        }
    }

    public static List<Customer> getCustomersCollection() {
        return customersCollection;
    }
}
