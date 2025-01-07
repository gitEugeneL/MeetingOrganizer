package com.euglihon.meetingorganizer.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRules {

    public static boolean isNameOrSurname(String str) {
        int strLength = str.length();
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher matcher = pattern.matcher(str);
        return strLength > 1 && strLength < 20 && matcher.matches();
    }

    public static boolean isValidPhoneNumber(String str) {
        Pattern pattern = Pattern.compile("^\\+\\d{2}-\\d{3}-\\d{3}-\\d{3}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
