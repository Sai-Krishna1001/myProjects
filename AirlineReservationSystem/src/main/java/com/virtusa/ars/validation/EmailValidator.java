package com.virtusa.ars.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	private Pattern pattern;

	private static final String EMAIL_PATTERN =
		    "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean validate(final String hex) {
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }
}

