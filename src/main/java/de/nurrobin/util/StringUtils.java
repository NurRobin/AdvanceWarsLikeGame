package de.nurrobin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static int extractIntValue(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1).trim());
        }
        throw new IllegalArgumentException("Could not find integer value for regex: " + regex);
    }

    public static boolean extractBooleanValue(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return Boolean.parseBoolean(matcher.group(1).trim());
        }
        throw new IllegalArgumentException("Could not find boolean value for regex: " + regex);
    }

    public static String extractStringValue(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        throw new IllegalArgumentException("Could not find string value for regex: " + regex);
    }
}
