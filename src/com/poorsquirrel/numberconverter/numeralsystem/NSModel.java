package com.poorsquirrel.numberconverter.numeralsystem;

import com.poorsquirrel.numberconverter.numeralsystem.baseconverter.BaseConverter;

public class NSModel implements Observable {

    private Observer observer;
    private String bin, oct, dec, hex;
    private boolean separatorAtEndRemoved;
    static final String FLOAT_SEPARATOR = ".";

    public void convert(String numberToConvert, int baseFrom) throws NumberFormatException {
        separatorAtEndRemoved = false;
        if (!numberToConvert.isEmpty())
            numberToConvert = parseInput(numberToConvert);

        String binTemp = BaseConverter.convert(numberToConvert, baseFrom, 2);
        String octTemp = BaseConverter.convert(numberToConvert, baseFrom, 8);
        String decTemp = BaseConverter.convert(numberToConvert, baseFrom, 10);
        String hexTemp = BaseConverter.convert(numberToConvert, baseFrom, 16);

        // There is a bug with no validation when converting one base to the same base. So we need
        // use temp strings to not override last successful data
        bin = binTemp;
        oct = octTemp;
        dec = decTemp;
        hex = hexTemp;

        postUpdate();
        notifyObservers();
    }

    private String parseInput(String numberToConvert) {
        numberToConvert = removeWhiteSpaces(numberToConvert);
        numberToConvert = removeSingleSeparator(numberToConvert);
        numberToConvert = removeSeparatorIfAtTheEnd(numberToConvert);
        numberToConvert = onlySingleZeroAtStart(numberToConvert);
        return numberToConvert;
    }

    private String removeWhiteSpaces(String s) {
        return s.replaceAll("\\s+", "");
    }

    private String removeSingleSeparator(String numberToConvert) {
        if ((numberToConvert).equals(FLOAT_SEPARATOR))
            numberToConvert = "";
        return numberToConvert;
    }

    private String removeSeparatorIfAtTheEnd(String numberToConvert) {
        if (!numberToConvert.isEmpty()
                && (numberToConvert.charAt(numberToConvert.length() - 1) + "").equals(FLOAT_SEPARATOR)) {
            numberToConvert = numberToConvert.substring(0, numberToConvert.length() - 1);
            if (!numberToConvert.contains(FLOAT_SEPARATOR))
                separatorAtEndRemoved = true;
        }
        return numberToConvert;
    }

    private String onlySingleZeroAtStart(String numberToConvert) {
        numberToConvert = numberToConvert.replaceFirst("^0+(?!$)", "");
        if (!numberToConvert.isEmpty() && (numberToConvert.charAt(0) + "").equals(FLOAT_SEPARATOR))
            numberToConvert = "0" + numberToConvert;
        return numberToConvert;
    }

    private void postUpdate() {
        if (separatorAtEndRemoved) {
            bin += FLOAT_SEPARATOR;
            oct += FLOAT_SEPARATOR;
            dec += FLOAT_SEPARATOR;
            hex += FLOAT_SEPARATOR;
        }
    }

    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    public void notifyObservers() {
        if (observer != null)
            observer.updateObserver();
    }

    public String getBin() {
        return bin;
    }

    public String getOct() {
        return oct;
    }

    public String getDec() {
        return dec;
    }

    public String getHex() {
        return hex;
    }
}
