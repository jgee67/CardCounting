package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setFont();

        // Hide action bar and status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void setFont() {
        ArrayList<TextView> inst = new ArrayList<TextView>();
        inst.add((TextView)findViewById(R.id.help_title));
        inst.add((TextView)findViewById(R.id.help_inst1));
        inst.add( (TextView)findViewById(R.id.help_inst2));
        inst.add( (TextView)findViewById(R.id.help_inst3));
        inst.add( (TextView)findViewById(R.id.help_inst4));
        inst.add((TextView) findViewById(R.id.p1));
        inst.add((TextView) findViewById(R.id.z));
        inst.add((TextView) findViewById(R.id.m1));

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Castellar.ttf");
        inst.get(0).setTypeface(font);

        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/BASKVILL.TTF");
        for(int i = 1; i < inst.size(); i++) {
            inst.get(i).setTypeface(font1);
        }
    }
}
