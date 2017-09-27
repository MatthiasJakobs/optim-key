package com.example.matthias.customkeyboardpoc;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Matthias on 27.09.17.
 */

public class CustomKeyboardView extends KeyboardView {
    Context context;

    public CustomKeyboardView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        this.context = context;
    }

    @Override
    protected boolean onLongPress(Keyboard.Key popupKey) {
        Log.d("MODIFIER", popupKey.codes.toString());
        return super.onLongPress(popupKey);
    }
}
