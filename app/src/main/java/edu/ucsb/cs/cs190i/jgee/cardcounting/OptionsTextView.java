package edu.ucsb.cs.cs190i.jgee.cardcounting;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Text view class used by Spinner to match font
 */
public class OptionsTextView extends TextView{

    public OptionsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public OptionsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OptionsTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/BASKVILL.TTF");
        setTypeface(tf);
    }
}
