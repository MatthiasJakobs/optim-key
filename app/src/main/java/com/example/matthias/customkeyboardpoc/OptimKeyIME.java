package com.example.matthias.customkeyboardpoc;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * Created by Matthias on 14.07.17.
 */

public class OptimKeyIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private OptimKeyboardView keyboardView;
    private Keyboard keyboard;

    private boolean blue = false;
    private boolean red = false;

    private double start = System.currentTimeMillis();
    private double stop = System.currentTimeMillis();

    private boolean caps = false;



    @Override
    public View onCreateInputView(){
        keyboardView = (OptimKeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.opt01);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }


    @Override
    public void onPress(int primaryCode) {
        this.start = System.currentTimeMillis();
    }

    @Override
    public void onRelease(int primaryCode) {
        this.stop = System.currentTimeMillis();
        InputConnection ic = getCurrentInputConnection();
        if(this.stop - this.start < 600 || primaryCode == Keyboard.KEYCODE_DELETE){
            // normal press
            switch(primaryCode){
                case Keyboard.KEYCODE_DELETE :
                    ic.deleteSurroundingText(1, 0);
                    break;

                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;

                case Keyboard.KEYCODE_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;

                case -100:
                    if (red) {
                        // Rot war an und wird wieder ausgeschaltet
                        red = false;
                        keyboardView.highlightLevel(1);
                        keyboardView.invalidateAllKeys();
                    } else {
                        // Rot wird eingeschaltet; blau wird ggf. ueberschrieben
                        red = true;
                        blue = false;
                        keyboardView.highlightLevel(2);
                        keyboardView.invalidateAllKeys();
                    }
                    break;

                case -101:
                    if (blue) {
                        // Blau war an und wird wieder ausgeschaltet
                        blue = false;
                        keyboardView.highlightLevel(1);
                        keyboardView.invalidateAllKeys();
                    } else {
                        // Blau wird eingeschaltet; rot wird ggf. ueberschrieben
                        blue = true;
                        red = false;
                        keyboardView.highlightLevel(3);
                        keyboardView.invalidateAllKeys();
                    }
                    break;

                default:
                    boolean dirty = red || blue || caps;

                    // richtige Ebene auswaehlen
                    char realKey = KeyMapping.LEVEL1.get(primaryCode);
                    if(red)
                        realKey = KeyMapping.LEVEL2.get(primaryCode);
                    if(blue)
                        realKey = KeyMapping.LEVEL3.get(primaryCode);

                    // Ebene auf 1 zuruecksetzen
                    red = false;
                    blue = false;
                    keyboardView.highlightLevel(1);

                    // String erstellen (GROSS/klein!)
                    String text = String.valueOf(realKey);
                    if (caps) {
                        text = text.toUpperCase();
                        caps = false;
                        keyboard.setShifted(false);
                    }

                    ic.commitText(text, 1);

                    if (dirty)
                        keyboardView.invalidateAllKeys();
            }
        } else {
            // long press

            Vibrator vrrr = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vrrr.vibrate(50);

            if(primaryCode < 12 && primaryCode >= 0){
                char output = KeyMapping.LEVEL4.get(primaryCode);
                ic.commitText(String.valueOf(output),1);
            }
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode >= 0 && keyCode < 12) || keyCode == -100){
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int primaryCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        //return super.onKeyLongPress(keyCode, event);

        return super.onKeyLongPress(keyCode, event);

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
