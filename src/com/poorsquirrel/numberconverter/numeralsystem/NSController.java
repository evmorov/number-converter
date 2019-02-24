package com.poorsquirrel.numberconverter.numeralsystem;

public class NSController implements EditTextObserver {
    private NSActivity view;
    private NSModel model;

    public NSController(NSActivity numeralSystemActivity) {
        this.view = numeralSystemActivity;
        model = new NSModel();
        model.registerObserver(numeralSystemActivity);
        numeralSystemActivity.setModel(model);
    }

    private boolean convertFocused() {
        try {
            String focusedString = view.getFocusedText().toString();
            model.convert(focusedString, view.getFocusedBase());
        } catch (Exception e) {
            model.notifyObservers(); // set last valid numbers
            view.setSelectionStart();
            return false;
        }
        return true;
    }

    public void onTextCut() {
        view.updateSelection();
        convertFocused();
        view.setSelectionStart();
    }

    public void onTextPaste() {
        view.updateSelection();
        convertFocused();
        view.setSelectionEnd();
    }

    /* Handle keys */

    public void symbolPressed(String key) {
        view.updateSelection();
        addKey(key);
        if (convertFocused())
            view.setSelectionRight();
    }

    private void addKey(String key) {
        view.getFocusedText().replace(view.getFocusedSelectionStart(), view.getFocusedSelectionEnd(), key, 0,
                key.length());
    }

    public void backspacePressed() {
        view.updateSelection();
        int start = view.getFocusedSelectionStart();
        int end = view.getFocusedSelectionEnd();

        if (start == 0 && end == 0) {
            return;
        } else if (start > 0 && start == end) {
            view.getFocusedText().delete(--start, end);
            if (convertFocused())
                view.setSelectionLeft();
        } else if (start != end) {
            view.getFocusedText().delete(start, end);
            if (convertFocused())
                view.setSelectionStart();
        }
    }

    public void clearAllPressed() {
        view.getFocusedText().clear();
        convertFocused();
        view.setSelectionStart();
    }
}
