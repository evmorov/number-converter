package com.poorsquirrel.numberconverter.tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.poorsquirrel.numberconverter.numeralsystem.NSModel;

public class NSModelTest {
    private static NSModel model;

    @BeforeClass
    public static void setUpBeforeClass() {
        model = new NSModel();
    }

    @Test
    public void startsWithZero() {
        model.convert("0100", 10);
        assertEquals("1100100", model.getBin());
        assertEquals("144", model.getOct());
        assertEquals("100", model.getDec());
        assertEquals("64", model.getHex());
    }

    @Test
    public void startsWithZeroAndDot() {
        model.convert("0.5", 10);
        assertEquals("0.1", model.getBin());
        assertEquals("0.4", model.getOct());
        assertEquals("0.5", model.getDec());
        assertEquals("0.8", model.getHex());
    }

    @Test
    public void onlyZero() {
        model.convert("0", 10);
        assertEquals("0", model.getBin());
        assertEquals("0", model.getOct());
        assertEquals("0", model.getDec());
        assertEquals("0", model.getHex());
    }

    @Test
    public void endsWithDot() {
        model.convert("100.", 10);
        assertEquals("1100100.", model.getBin());
        assertEquals("144.", model.getOct());
        assertEquals("100.", model.getDec());
        assertEquals("64.", model.getHex());
    }

    @Test
    public void startsWithDot() {
        model.convert(".100", 10);
        assertEquals("0.000110", model.getBin());
        assertEquals("0.063146", model.getOct());
        assertEquals("0.100", model.getDec());
        assertEquals("0.19999A", model.getHex());
    }

    @Test
    public void zeroAfterDot() {
        model.convert("5.0", 10);
        assertEquals("101.0", model.getBin());
        assertEquals("5.0", model.getOct());
        assertEquals("5.0", model.getDec());
        assertEquals("5.0", model.getHex());
    }

    @Test
    public void withSpace() {
        model.convert("101 00", 2);
        assertEquals("20", model.getDec());
    }

    @Test
    public void emptyString() {
        model.convert("", 2);
        assertEquals("", model.getBin());
        assertEquals("", model.getOct());
        assertEquals("", model.getDec());
        assertEquals("", model.getHex());
    }

    @Test(expected = NumberFormatException.class)
    public void wrongSymbol() {
        model.convert("-", 2);
    }

    @Test(expected = NumberFormatException.class)
    public void severalSeparators() {
        model.convert("1010.1011.1010", 2);
    }
}
