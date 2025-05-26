package chatapp;

public class Login {
    private String username;
    private String password;
    private String cellNumber;

    // ✅ Constructor
    public Login(String username, String password, String cellNumber) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }

    // ✅ Checks if username contains an underscore and is max 5 characters
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // ✅ Checks if password has min 8 characters, uppercase, number, special char
    public boolean checkPasswordComplexity() {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*()].*");
    }

    // ✅ Validates SA phone number format (must start with +27 and be 12 characters total)
    public boolean checkCellPhoneNumber() {
        return cellNumber.matches("^\\+27\\d{9}$");
    }

    // ✅ Main registration method with all validations
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        return "Registration successful.";
    }

    // ✅ Checks login credentials
    public boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername.equals(this.username) && inputPassword.equals(this.password);
    }

    // ✅ Returns login status message
    public String returnLoginStatus(boolean loginSuccess, String firstName, String lastName) {
        return loginSuccess
            ? "Welcome " + firstName + " " + lastName + ", it is great to see you again."
            : "Username or password incorrect, please try again.";
    }
}
