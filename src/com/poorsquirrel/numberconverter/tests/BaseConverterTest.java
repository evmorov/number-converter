package com.poorsquirrel.numberconverter.tests;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.poorsquirrel.numberconverter.numeralsystem.baseconverter.BaseConverter;

public class BaseConverterTest {

    private Map<String, String> data;

    @Before
    public void setUp() {
        data = new HashMap<String, String>();
    }

    private void assertConvertition(int baseFrom, int baseTo) {
        for (Map.Entry<String, String> entry : data.entrySet())
            assertEquals(entry.getValue(), BaseConverter.convert(entry.getKey(), baseFrom, baseTo));
    }

    @Test
    public void zeroAfterDot() {
        data.put("10.0", "A.0");
        assertConvertition(10, 16);
    }

    @Test(expected = NumberFormatException.class)
    public void wrongBase1() {
        data.put("A", "A");
        assertConvertition(2, 16);
    }

    @Test(expected = NumberFormatException.class)
    public void wrongBase2() {
        data.put("100.B", "A");
        assertConvertition(10, 16);
    }

    @Test(expected = NumberFormatException.class)
    public void severalSeparators() {
        data.put("1010.1011.1010", "A");
        assertConvertition(2, 16);
    }

    @Test
    public void binToBin() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("10101", "10101");
        assertConvertition(2, 2);
    }

    @Test
    public void binToOct() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("10100", "24");
        assertConvertition(2, 8);
    }

    @Test
    public void binToDec() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("10100", "20");
        assertConvertition(2, 10);
    }

    @Test
    public void binToHex() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("10100", "14");
        assertConvertition(2, 16);
    }

    @Test
    public void octalToBin() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("24", "10100");
        assertConvertition(8, 2);
    }

    @Test
    public void octToOct() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("7777", "7777");
        assertConvertition(8, 8);
    }

    @Test
    public void octToDec() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("360363", "123123");
        assertConvertition(8, 10);
    }

    @Test
    public void octalToHex() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("125252", "AAAA");
        assertConvertition(8, 16);
    }

    @Test
    public void decToBin() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("100", "1100100");
        assertConvertition(10, 2);
    }

    @Test
    public void decToOct() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("100", "144");
        assertConvertition(10, 8);
    }

    @Test
    public void decToDec() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("100", "100");
        assertConvertition(10, 10);
    }

    @Test
    public void decToHex() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("100", "64");
        assertConvertition(10, 16);
    }

    @Test
    public void hexToBin() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("ABCDE", "10101011110011011110");
        assertConvertition(16, 2);
    }

    @Test
    public void hexToOct() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("ABCDE", "2536336");
        assertConvertition(16, 8);
    }

    @Test
    public void hexToDec() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("ABCDE", "703710");
        assertConvertition(16, 10);
    }

    @Test
    public void hexToHex() {
        data.put("0", "0");
        data.put("1", "1");
        data.put("ABCDE", "ABCDE");
        assertConvertition(16, 16);
    }

    @Test
    public void binToBinFloat() {
        data.put("1.1", "1.1");
        data.put("1010.1011", "1010.1011");
        data.put("0.1", "0.1");
        assertConvertition(2, 2);
    }

    @Test
    public void binToOctFloat() {
        data.put("1.1", "1.4");
        data.put("1010.1011", "12.54");
        data.put("0.1", "0.4");
        assertConvertition(2, 8);
    }

    @Test
    public void binToDecFloat() {
        data.put("1.1", "1.5");
        data.put("1010.1011", "10.6875");
        data.put("0.1", "0.5");
        assertConvertition(2, 10);
    }

    @Test
    public void binToHexFloat() {
        data.put("1.1", "1.8");
        data.put("1010.1011", "A.B");
        data.put("0.1", "0.8");
        assertConvertition(2, 16);
    }

    @Test
    public void octalToBinFloat() {
        data.put("1.1", "1.001");
        data.put("12.54", "1010.1011");
        data.put("0.4", "0.1");
        assertConvertition(8, 2);
    }

    @Test
    public void octToOctFloat() {
        data.put("1.1", "1.1");
        data.put("12.54", "12.54");
        data.put("0.4", "0.4");
        assertConvertition(8, 8);
    }

    @Test
    public void octToDecFloat() {
        data.put("1.1", "1.125");
        data.put("12.54", "10.6875");
        data.put("0.4", "0.5");
        assertConvertition(8, 10);
    }

    @Test
    public void octalToHexFloat() {
        data.put("1.1", "1.2");
        data.put("12.54", "A.B");
        data.put("0.4", "0.8");
        assertConvertition(8, 16);
    }

    @Test
    public void decToBinFloat() {
        data.put("1.5", "1.1");
        data.put("10.6875", "1010.1011");
        data.put("0.5", "0.1");
        assertConvertition(10, 2);
    }

    @Test
    public void decToOctFloat() {
        data.put("1.125", "1.1");
        data.put("10.6875", "12.54");
        data.put("0.5", "0.4");
        assertConvertition(10, 8);
    }

    @Test
    public void decToDecFloat() {
        data.put("1.5", "1.5");
        data.put("10.6875", "10.6875");
        data.put("1.12", "1.12");
        data.put("0.5", "0.5");
        assertConvertition(10, 10);
    }

    @Test
    public void decToHexFloat() {
        data.put("1.5", "1.8");
        data.put("10.6875", "A.B");
        data.put("0.5", "0.8");
        assertConvertition(10, 16);
    }

    @Test
    public void hexToBinFloat() {
        data.put("1.8", "1.1");
        data.put("A.B", "1010.1011");
        data.put("0.8", "0.1");
        assertConvertition(16, 2);
    }

    @Test
    public void hexToOctFloat() {
        data.put("1.2", "1.1");
        data.put("A.B", "12.54");
        data.put("0.8", "0.4");
        assertConvertition(16, 8);
    }

    @Test
    public void hexToDecFloat() {
        data.put("1.8", "1.5");
        data.put("A.B", "10.6875");
        data.put("0.8", "0.5");
        assertConvertition(16, 10);
    }

    @Test
    public void hexToHexFloat() {
        data.put("1.8", "1.8");
        data.put("A.B", "A.B");
        data.put("0.8", "0.8");
        assertConvertition(16, 16);
    }
}
