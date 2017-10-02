package com.example.matthias.customkeyboardpoc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.Keyboard.Key;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by smuecke on 01.10.17.
 */

public class OptimKeyboardView extends KeyboardView {

    private final int MARGIN = 20;
    private final Rect textBounds = new Rect();

    private final int FONTSIZE_LARGE = 90;
    private final int FONTSIZE_SMALL = 60;

    private int highlightedLevel = 1;

    public OptimKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OptimKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OptimKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        String text = "";

        int w, h;

        List<Key> keys = getKeyboard().getKeys();
        for (Key key : keys) {
            if(key.label != null && key.codes[0] < 12 && key.codes[0] >= 0) {
                w = key.width - 2*MARGIN;
                h = key.height - 2*MARGIN;

                // Level 1
                paint.setTextSize(highlightedLevel == 1 ? FONTSIZE_LARGE : FONTSIZE_SMALL);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.keyLevel1));
                text = String.valueOf(KeyMapping.LEVEL1.get(key.codes[0]));
                if (this.isShifted())
                    text = text.toUpperCase();
                paint.getTextBounds(text, 0, text.length(), textBounds);
                canvas.drawText(text, key.x + MARGIN + w/4 - textBounds.exactCenterX(),
                        key.y + MARGIN + h/4 - textBounds.exactCenterY(), paint);

                // Level 2
                paint.setTextSize(highlightedLevel == 2 ? FONTSIZE_LARGE : FONTSIZE_SMALL);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.keyLevel2));
                text = String.valueOf(KeyMapping.LEVEL2.get(key.codes[0]));
                if (this.isShifted())
                    text = text.toUpperCase();
                paint.getTextBounds(text, 0, text.length(), textBounds);
                canvas.drawText(text, key.x + MARGIN + w/4 - textBounds.exactCenterX(),
                        key.y + MARGIN + 3*h/4 - textBounds.exactCenterY(), paint);

                // Level 3
                paint.setTextSize(highlightedLevel == 3 ? FONTSIZE_LARGE : FONTSIZE_SMALL);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.keyLevel3));
                text = String.valueOf(KeyMapping.LEVEL3.get(key.codes[0]));
                if (this.isShifted())
                    text = text.toUpperCase();
                paint.getTextBounds(text, 0, text.length(), textBounds);
                canvas.drawText(text, key.x + MARGIN + 3*w/4 - textBounds.exactCenterX(),
                        key.y + MARGIN + 3*h/4 - textBounds.exactCenterY(), paint);

                // Level 4
                paint.setTextSize(highlightedLevel == 4 ? FONTSIZE_LARGE : FONTSIZE_SMALL);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.keyLevel4));
                text = String.valueOf(KeyMapping.LEVEL4.get(key.codes[0]));
                if (this.isShifted())
                    text = text.toUpperCase();
                paint.getTextBounds(text, 0, text.length(), textBounds);
                canvas.drawText(text, key.x + MARGIN + 3*w/4 - textBounds.exactCenterX(),
                        key.y + MARGIN + h/4 - textBounds.exactCenterY(), paint);
            }
        }
    }

    public void highlightLevel(int layer) {
        if (layer >= 0 && layer <= 4) {
            highlightedLevel = layer;
        } else {
            highlightedLevel = 1;
        }
    }
}
