public class RolesAndPermissions extends User {

    public int checkAdminAccess(String username, String password) {
        return checkAdminCredentials(username, password);
    }
    
    private int checkAdminCredentials(String username, String password) {
        int isFound = -1;
        for (int i = 0; i < adminUserNameAndPassword.length; i++) {
            if (username.equals(adminUserNameAndPassword[i][0])) {
                if (password.equals(adminUserNameAndPassword[i][1])) {
                    isFound = i;
                    break;
                }
            }
        }
        return isFound;
    }
    public String isPassengerRegistered(String email, String password) {
        return checkPassengerCredentials(email, password);
    }
    
    private String checkPassengerCredentials(String email, String password) {
        String isFound = "0";
        for (Customer c : Customer.customerCollection) {
            if (email.equals(c.getEmail())) {
                if (password.equals(c.getPassword())) {
                    isFound = "1-" + c.getUserID();
                    break;
                }
            }
        }
        return isFound;
    }
}
