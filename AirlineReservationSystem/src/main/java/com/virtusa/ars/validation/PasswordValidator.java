package com.virtusa.ars.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private Pattern pattern;

    private static final String PASSWORD_PATTERN =
        "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public String validate(String password) {
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            StringBuilder errorMsg = new StringBuilder();
            if (password.length() < 8) {
                errorMsg.append("Password must be at least 8 characters long. ");
            }
            if (!password.matches(".*\\d.*")) {
                errorMsg.append("Password must contain at least one digit (0-9). ");
            }
            if (!password.matches(".*[a-z].*")) {
                errorMsg.append("Password must contain at least one lowercase letter (a-z). ");
            }
            if (!password.matches(".*[A-Z].*")) {
                errorMsg.append("Password must contain at least one uppercase letter (A-Z). ");
            }
            if (!password.matches(".*[@#$%^&+=!].*")) {
                errorMsg.append("Password must contain at least one special character from the set [@#$%^&+=!]. ");
            }
            if (password.matches(".*\\s.*")) {
                errorMsg.append("Password should not contain any whitespace characters. ");
            }
            return errorMsg.toString();
        }
        return null;
    }
}


