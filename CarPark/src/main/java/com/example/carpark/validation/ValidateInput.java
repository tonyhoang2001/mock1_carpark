package com.example.carpark.validation;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ValidateInput {

    private static final String NO_SPECIAL_CHAR_REGEX = "^[a-z0-9_\\s]{3,20}$";
    private static final String NAME_REGEX = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    private static final String EMAIL_REGEX = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";

    public boolean validatePassword(String pass) {

        int countLower = 0;
        int countUpper = 0;
        int countNum = 0;

        for (int i = 0; i < pass.length(); i++) {
            if (Character.isDigit(pass.charAt(i))) {
                countNum++;
            } else if (Character.isUpperCase(pass.charAt(i))) {
                countUpper++;
            } else if (Character.isLowerCase(pass.charAt(i))) {
                countLower++;
            }
        }

        if (countNum == 0 || countLower == 0 || countUpper == 0) {
            throw new RuntimeException("Password must include uppercase, lowercase and number.");
        }
        return true;
    }

    public boolean validateDOB(LocalDate localDate) {
        if (validateDateFormat(localDate.toString(), "Birth date") && validateFutureDate(localDate, "Birth date") && LocalDate.now().getYear() - localDate.getYear() >= 16) {
            return true;
        }
        throw new RuntimeException("Employee must be at least 16 years old.");
    }

    public boolean validateFutureDate(LocalDate localDate, String msg) {
        if (validateDateFormat(localDate.toString(), msg) && localDate.isAfter(LocalDate.now())) {
            throw new RuntimeException(msg + " must not be a future date.");
        }
        return true;
    }

    public boolean validateDateFormat(String value, String msg) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            throw new RuntimeException(msg + " is wrong format (yyyy-MM-dd).");
        }
        return date != null;
    }

    public boolean validatePhone(String phone) {
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                throw new RuntimeException("Phone is invalid.");
            }
        }

        return true;
    }

//    public boolean validateLongNumber(String value) {
//
//    }

}
