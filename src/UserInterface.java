import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private final InputValidationService validationService;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
        this.validationService = new InputValidationService();
    }
    
    /**
     * Displays the main menu options
     */
    public void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option:    ");
    }
    
    /**
     * Displays the admin menu options
     * @param username The logged-in admin's username
     */
    public void displayAdminMenu(String username) {
        System.out.printf("\n\n%-60s+++++++++ 2nd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "",
                "", username);
        System.out.printf("%-30s (a) Enter 1 to add new Passenger....\n", "");
        System.out.printf("%-30s (b) Enter 2 to search a Passenger....\n", "");
        System.out.printf("%-30s (c) Enter 3 to update the Data of the Passenger....\n", "");
        System.out.printf("%-30s (d) Enter 4 to delete a Passenger....\n", "");
        System.out.printf("%-30s (e) Enter 5 to Display all Passengers....\n", "");
        System.out.printf("%-30s (f) Enter 6 to Display all flights registered by a Passenger...\n",
                "");
        System.out.printf("%-30s (g) Enter 7 to Display all registered Passengers in a Flight....\n",
                "");
        System.out.printf("%-30s (h) Enter 8 to Delete a Flight....\n", "");
        System.out.printf("%-30s (i) Enter 0 to Go back to the Main Menu/Logout....\n", "");
        System.out.print("Enter the desired Choice:   ");
    }
    
    /**
     * Displays the passenger menu options
     * @param username The logged-in passenger's username
     */
    public void displayPassengerMenu(String username) {
        System.out.printf("\n\n%-60s+++++++++ 3rd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "",
                "", username);
        System.out.printf("%-40s (a) Enter 1 to Book a flight....\n", "");
        System.out.printf("%-40s (b) Enter 2 to update your Data....\n", "");
        System.out.printf("%-40s (c) Enter 3 to delete your account....\n", "");
        System.out.printf("%-40s (d) Enter 4 to Display Flight Schedule....\n", "");
        System.out.printf("%-40s (e) Enter 5 to Cancel a Flight....\n", "");
        System.out.printf("%-40s (f) Enter 6 to Display all flights registered by \"%s\"....\n", "",
                username);
        System.out.printf("%-40s (g) Enter 0 to Go back to the Main Menu/Logout....\n", "");
        System.out.print("Enter the desired Choice:   ");
    }
    
    /**
     * Displays the user manual instructions
     * @param read Scanner for user input
     */
    public void displayManualInstructions() {
        System.out.printf("%n%n%50s %s Welcome to BAV Airlines User Manual %s", "", "+++++++++++++++++",
                "+++++++++++++++++");
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option:    ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        
        while (choice < 1 || choice > 2) {
            System.out.print("ERROR!!! Invalid entry...Please enter a value either 1 or 2....Enter again....");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        }
        
        if (choice == 1) {
            displayAdminManual();
        } else {
            displayPassengerManual();
        }
    }
    
    /**
     * Displays the admin manual
     */
    private void displayAdminManual() {
        System.out.println(
                "\n\n(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...\n");
        System.out.println(
                "(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...\n");
        System.out.println(
                "(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... \n");
        System.out.println(
                "(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...\n");
        System.out.println(
                "(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...\n");
        System.out.println(
                "(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  \n");
        System.out.println(
                "(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...\n");
        System.out.println("(8) Pressing \"4\" will let you delete any passenger given its ID provided...\n");
        System.out.println("(9) Pressing \"5\" will let you display all registered passenger...\n");
        System.out.println(
                "(10) Pressing \"6\" will let you display all registered passengers...After selecting, program will ask, if you want to display passengers for all flights(Y/y) or a specific flight(N/n)\n");
        System.out.println(
                "(11) Pressing \"7\" will let you delete any flight given its flight number provided...\n");
        System.out.println(
                "(12) Pressing \"0\" will make you logged out of the program...You can login again any time you want during the program execution....\n");
    }
    
    /**
     * Displays the passenger manual
     */
    private void displayPassengerManual() {
        System.out.println(
                "\n\n(1) Local user has the access to its data only...He/She won't be able to change/update other users data...\n");
        System.out.println(
                "(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...\n");
        System.out.println(
                "(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...\n");
        System.out.println(
                "(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...\n");
        System.out.println(
                "(5) Pressing \"1\" will display available/scheduled list of flights...To get yourself booked for a flight, enter the flight number and number of tickets for the flight...Max num of tickets at a time is 10 ...\n");
        System.out.println(
                "(7) Pressing \"2\" will let you update your own data...You won't be able to update other's data... \n");
        System.out.println("(8) Pressing \"3\" will delete your account... \n");
        System.out
                .println("(9) Pressing \"4\" will display randomly designed flight schedule for this runtime...\n");
        System.out.println("(10) Pressing \"5\" will let you cancel any flight registered by you...\n");
        System.out.println("(11) Pressing \"6\" will display all flights registered by you...\n");
        System.out.println(
                "(12) Pressing \"0\" will make you logout of the program...You can login back at anytime with your credentials...for this particular run-time... \n");
    }
    
    /**
     * Gets user credentials from console input
     * @param isAdmin Whether getting admin credentials or passenger credentials
     * @return UserCredentials object containing username and password
     */
    public UserCredentials getUserCredentials(boolean isAdmin) {
        String usernamePrompt = isAdmin ? 
            "\nEnter the UserName to login to the Management System:     " :
            "\n\nEnter the Email to Login:\t";
        
        String passwordPrompt = isAdmin ?
            "Enter the Password to login to the Management System:    " :
            "Enter the Password:\t";
        
        System.out.print(usernamePrompt);
        String username = scanner.nextLine();
        System.out.print(passwordPrompt);
        String password = scanner.nextLine();
        
        return new UserCredentials(username, password);
    }
    
    /**
     * Gets registration credentials from console input
     * @param isAdmin Whether registering admin or passenger
     * @return UserCredentials object containing username and password
     */
    public UserCredentials getRegistrationCredentials(boolean isAdmin) {
        String usernamePrompt = isAdmin ? 
            "\nEnter the UserName to Register:    " :
            "\n\nEnter the Email to Register:\t";
        
        String passwordPrompt = isAdmin ?
            "Enter the Password to Register:     " :
            "Enter the Password:\t";
        
        System.out.print(usernamePrompt);
        String username = scanner.nextLine();
        System.out.print(passwordPrompt);
        String password = scanner.nextLine();
        
        return new UserCredentials(username, password);
    }
    
    /**
     * Gets customer ID from console input
     * @param action The action being performed (search, update, delete)
     * @return The customer ID
     */
    public String getCustomerId(String action) {
        System.out.print("Enter the CustomerID to " + action + ":\t");
        return scanner.nextLine();
    }
    
    /**
     * Gets flight number from console input
     * @return The flight number
     */
    public String getFlightNumber() {
        System.out.print("Enter the Flight Number: ");
        return scanner.nextLine();
    }
    
    /**
     * Displays a message to the user
     * @param message The message to display
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    /**
     * Gets valid menu option from console input
     * @param minValue The minimum value for the valid menu option
     * @param maxValue The maximum value for the valid menu option
     * @return The valid menu option
     */
    public int getValidMenuOption(int minValue, int maxValue) {
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        while (!validationService.isValidMenuOption(option, minValue, maxValue)) {
            System.out.printf("ERROR!! Please enter value between %d - %d. Enter the value again:\t", minValue, maxValue);
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        }
        return option;
    }
}
