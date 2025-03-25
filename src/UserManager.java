import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final int MAX_ADMINS = 10;
    private static String[][] adminCredentials = new String[MAX_ADMINS][2];
    private static List<Customer> customers = new ArrayList<>();
    
    // Initialize default admin credentials
    static {
        adminCredentials[0][0] = "root";
        adminCredentials[0][1] = "root";
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static List<Customer> getCustomers() {
        return new ArrayList<>(customers); // Return a copy to prevent direct modification
    }

    public static void removeCustomer(Customer customer) {
        customers.remove(customer);
    }

    public static void addAdminCredentials(String username, String password, int index) {
        if (index >= 0 && index < MAX_ADMINS) {
            adminCredentials[index][0] = username;
            adminCredentials[index][1] = password;
        }
    }

    public static String[][] getAdminCredentials() {
        return adminCredentials.clone(); // Return a copy to prevent direct modification
    }

    public static Customer findCustomerById(String customerId) {
        return customers.stream()
                .filter(customer -> customer.getUserID().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    public static boolean isAdminUsernameAvailable(String username) {
        for (String[] credential : adminCredentials) {
            if (username.equals(credential[0])) {
                return false;
            }
        }
        return true;
    }
}