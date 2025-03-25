public class UserAuthenticationService {
    private final UserManager userManager;
    private final UserInterface userInterface;

    public UserAuthenticationService(UserInterface userInterface) {
        this.userManager = new UserManager();
        this.userInterface = userInterface;
    }

    public boolean authenticateAdmin(String username, String password) {
        String[][] adminCredentials = UserManager.getAdminCredentials();
        for (String[] credential : adminCredentials) {
            if (username.equals(credential[0]) && password.equals(credential[1])) {
                return true;
            }
        }
        return false;
    }

    public Customer authenticatePassenger(String email, String password) {
        return UserManager.getCustomers().stream()
                .filter(customer -> customer.getEmail().equals(email) 
                        && customer.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean registerAdmin(String username, String password, int index) {
        if (!UserManager.isAdminUsernameAvailable(username)) {
            return false;
        }
        UserManager.addAdminCredentials(username, password, index);
        return true;
    }

    public void registerPassenger(Customer customer) {
        UserManager.addCustomer(customer);
    }
}