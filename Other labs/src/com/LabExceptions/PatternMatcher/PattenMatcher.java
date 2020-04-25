package com.LabExceptions.PatternMatcher;

import java.util.regex.Pattern;
import java.util.Objects;

public class PattenMatcher {
    private static final Pattern Hex_Pattern = Pattern.compile("#[0-9a-fA-F]{6}");

    public boolean isValid(String input) throws PatternMatcherException{
        if(Objects.isNull(input) || input.isEmpty()) {
            throw new PatternMatcherException("[lab2] String can not be null or empty!");
        }

        return Hex_Pattern.matcher(input).matches();
    }
}
