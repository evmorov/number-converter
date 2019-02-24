package com.poorsquirrel.numberconverter.numeralsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.evmorov.numberconverter.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;

public class NSActivity extends Activity implements OnClickListener, OnFocusChangeListener, Observer {

    private NSModel model;
    private NSController controller;
    private NSEditText decField, binField, octField, hexField;
    private int[] decInactiveKeys, binInactiveKeys, octInactiveKeys, hexInactiveKeys;
    private List<Integer> numberKeys, functionalKeys;
    private NSEditText focusedView;
    private int focusedViewSelectionStart, focusedViewSelectionEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numeral_system_activity);

        controller = new NSController(this);

        (findViewById(R.id.field_name_dec)).setOnClickListener(this);
        (findViewById(R.id.field_name_bin)).setOnClickListener(this);
        (findViewById(R.id.field_name_oct)).setOnClickListener(this);
        (findViewById(R.id.field_name_hex)).setOnClickListener(this);
        decField = (NSEditText) findViewById(R.id.field_dec);
        binField = (NSEditText) findViewById(R.id.field_bin);
        octField = (NSEditText) findViewById(R.id.field_oct);
        hexField = (NSEditText) findViewById(R.id.field_hex);
        configureEditText(decField, 10);
        configureEditText(binField, 2);
        configureEditText(octField, 8);
        configureEditText(hexField, 16);

        // init keyboards
        decInactiveKeys = new int[] { R.id.key_A, R.id.key_B, R.id.key_C, R.id.key_D, R.id.key_E, R.id.key_F };
        binInactiveKeys = new int[] { R.id.key_2, R.id.key_3, R.id.key_4, R.id.key_5, R.id.key_6, R.id.key_7,
                R.id.key_8, R.id.key_9, R.id.key_A, R.id.key_B, R.id.key_C, R.id.key_D, R.id.key_E, R.id.key_F };
        octInactiveKeys = new int[] { R.id.key_8, R.id.key_9, R.id.key_A, R.id.key_B, R.id.key_C, R.id.key_D,
                R.id.key_E, R.id.key_F };
        hexInactiveKeys = new int[] {};
        numberKeys = Arrays.asList(R.id.key_0, R.id.key_1, R.id.key_2, R.id.key_3, R.id.key_4, R.id.key_5, R.id.key_6,
                R.id.key_7, R.id.key_8, R.id.key_9, R.id.key_A, R.id.key_B, R.id.key_C, R.id.key_D, R.id.key_E,
                R.id.key_F);
        functionalKeys = Arrays.asList(R.id.key_backspace, R.id.key_clear_all, R.id.key_separator);

        for (int key : numberKeys)
            (findViewById(key)).setOnClickListener(this);
        for (int key : functionalKeys)
            (findViewById(key)).setOnClickListener(this);

        if (savedInstanceState != null) {
            focusedView = (NSEditText) findViewById(savedInstanceState.getInt("focusedViewId"));
            setActiveColor(focusedView);
            focusedView.setText(savedInstanceState.getString("focusedViewString"));
            focusField(focusedView);
        }
    }

    private void configureEditText(NSEditText editText, int base) {
        editText.setBase(base);
        editText.registerEditTextObserver(controller);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnFocusChangeListener(this);
        if (android.os.Build.VERSION.SDK_INT >= 11) { // don't show the keyboard
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.field_name_dec:
                focusField(decField);
                break;
            case R.id.field_name_bin:
                focusField(binField);
                break;
            case R.id.field_name_oct:
                focusField(octField);
                break;
            case R.id.field_name_hex:
                focusField(hexField);
                break;
            case R.id.key_0:
            case R.id.key_1:
            case R.id.key_2:
            case R.id.key_3:
            case R.id.key_4:
            case R.id.key_5:
            case R.id.key_6:
            case R.id.key_7:
            case R.id.key_8:
            case R.id.key_9:
            case R.id.key_A:
            case R.id.key_B:
            case R.id.key_C:
            case R.id.key_D:
            case R.id.key_E:
            case R.id.key_F:
            case R.id.key_separator:
                String s = ((Button) v).getText().toString();
                controller.symbolPressed(s);
                break;
            case R.id.key_backspace:
                controller.backspacePressed();
                break;
            case R.id.key_clear_all:
                controller.clearAllPressed();
                break;
        }
    }

    private void focusField(NSEditText editText) {
        editText.requestFocus(); // onFocusChange will be called after this
        setSelectionEnd();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            focusedView = (NSEditText) v;
            switch (focusedView.getBase()) {
                case 2:
                    changeKeyboard(binInactiveKeys);
                    break;
                case 8:
                    changeKeyboard(octInactiveKeys);
                    break;
                case 10:
                    changeKeyboard(decInactiveKeys);
                    break;
                case 16:
                    changeKeyboard(hexInactiveKeys);
                    break;
            }
            setActiveColor(focusedView);
        }
    }

    private void changeKeyboard(int[] inactiveKeys) {
        List<Integer> activeKeys = new ArrayList<Integer>();
        for (int numberKey : numberKeys)
            activeKeys.add(numberKey);

        for (int inactiveKey : inactiveKeys) {
            Button inactiveButton = (Button) findViewById(inactiveKey);
            disableButton(inactiveButton);
            activeKeys.remove(Integer.valueOf(inactiveKey));
        }

        for (int activeKey : activeKeys) {
            Button activeButton = (Button) findViewById(activeKey);
            if (!activeButton.getText().toString().equals("0")) // this key handles separatly
                enableButton(activeButton);
        }
    }

    private void disableButton(Button button) {
        button.setAlpha(0.5f);
        button.setClickable(false);
    }

    private void enableButton(Button button) {
        button.setAlpha(1f);
        button.setClickable(true);
    }

    public int getFocusedSelectionStart() {
        return Math.min(focusedViewSelectionStart, focusedViewSelectionEnd);
    }

    public int getFocusedSelectionEnd() {
        return Math.max(focusedViewSelectionStart, focusedViewSelectionEnd);
    }

    public void updateSelection() {
        focusedViewSelectionStart = Math.max(focusedView.getSelectionStart(), 0);
        focusedViewSelectionEnd = Math.max(focusedView.getSelectionEnd(), 0);
    }

    public void setModel(NSModel model) {
        this.model = model;
    }

    public int getFocusedBase() {
        return focusedView.getBase();
    }

    public Editable getFocusedText() {
        return focusedView.getText();
    }

    public void updateObserver() {
        decField.setText(model.getDec());
        binField.setText(model.getBin());
        octField.setText(model.getOct());
        hexField.setText(model.getHex());
    }

    public void setSelectionRight() {
        setSelection(++focusedViewSelectionStart);
    }

    public void setSelectionLeft() {
        setSelection(--focusedViewSelectionStart);
    }

    public void setSelectionStart() {
        setSelection(focusedViewSelectionStart);
    }

    private void setSelection(int index) {
        try {
            focusedView.setSelection(index);
        } catch (Exception IndexOutOfBoundException) {
            setSelectionEnd();
        }
    }

    public void setSelectionEnd() {
        focusedView.setSelection(focusedView.length());
    }

    private void setActiveColor(NSEditText view) {
        for (NSEditText nsEditText : new NSEditText[] { decField, binField, octField, hexField })
            setNormalColor(nsEditText);
        view.setBackgroundColor(getResources().getColor(R.color.cyan));
    }

    private void setNormalColor(NSEditText view) {
        view.setBackgroundColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt("focusedViewId", focusedView.getId());
        state.putString("focusedViewString", focusedView.getText().toString());
    }
}
