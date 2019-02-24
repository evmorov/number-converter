package com.poorsquirrel.numberconverter.numeralsystem.baseconverter;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseConverter {

    private static final String FLOAT_SEPARATOR = ".";

    public static String convert(String numberToConvert, int baseFrom, int baseTo) {
        if (numberToConvert.isEmpty())
            return "";

        // Temp fix. Fraction convert has bug: dec 1.1000 to dec can be 1.0999 (so there are no
        // validation if baseTo is the same). It's not good because there are no input validation
        // now (for other cases there are can be NumberFormatException)
        if (baseFrom == baseTo)
            return numberToConvert;

        String converted = "";
        if (numberToConvert.contains(FLOAT_SEPARATOR)) {
            checkThatNotMoreThanOneSeparator(numberToConvert);
            converted = convertWithFraction(numberToConvert, baseFrom, baseTo);
        } else {
            converted = convertToBase(numberToConvert, baseFrom, baseTo);
        }
        if (baseTo > 10)
            converted = converted.toUpperCase(Locale.ENGLISH);
        return converted;
    }

    private static void checkThatNotMoreThanOneSeparator(String numberToConvert) {
        Matcher matcher = Pattern.compile("([" + FLOAT_SEPARATOR + "])").matcher(numberToConvert);
        int count = 0;
        while (matcher.find())
            count++;
        if (count > 1)
            throw new NumberFormatException();
    }

    private static String convertWithFraction(String numberToConvert, int baseFrom, int baseTo) {
        String[] numberParts = numberToConvert.split("\\" + FLOAT_SEPARATOR);
        String fraction = numberParts[1];
        if (!fraction.matches("^0*$")) {
            String numberPortionDec = Fractions.convertFractionPortionToDecimal(fraction, baseFrom);
            fraction = Fractions.convertFractionPortionFromDecimal(numberPortionDec, baseTo);
        }
        return convertToBase(numberParts[0], baseFrom, baseTo) + FLOAT_SEPARATOR + fraction;
    }

    private static String convertToBase(String numberToConvert, int baseFrom, int baseTo) {
        int numberDec = convertToDec(numberToConvert, baseFrom);
        String converted = "";
        switch (baseTo) {
            case 2:
                converted = Integer.toBinaryString(numberDec);
                break;
            case 8:
                converted = Integer.toOctalString(numberDec);
                break;
            case 10:
                converted = Integer.toString(numberDec);
                break;
            case 16:
                converted = Integer.toHexString(numberDec);
                break;
        }
        return converted;
    }

    private static int convertToDec(String number, int baseFrom) {
        return Integer.parseInt(number, baseFrom);
    }
}
