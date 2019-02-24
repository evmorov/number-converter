package com.poorsquirrel.numberconverter.numeralsystem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class NSEditText extends EditText {

    private EditTextObserver observer;
    private int base;

    public NSEditText(Context context) {
        super(context);
    }

    public NSEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NSEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        boolean consumed = super.onTextContextMenuItem(id);
        switch (id) {
            case android.R.id.cut:
                observer.onTextCut();
                break;
            case android.R.id.paste:
                observer.onTextPaste();
                break;
        }
        return consumed;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getBase() {
        return base;
    }

    public void registerEditTextObserver(EditTextObserver observer) {
        this.observer = observer;
    }
}
