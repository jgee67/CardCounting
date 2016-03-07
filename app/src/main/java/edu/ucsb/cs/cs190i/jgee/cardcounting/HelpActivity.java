package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
//        setButtonFont();
    }

    public void setButtonFont() {
        ArrayList<TextView> inst = new ArrayList<TextView>();
        inst.add((TextView)findViewById(R.id.help_title));
        inst.add((TextView)findViewById(R.id.help_inst1));
        inst.add((TextView)findViewById(R.id.help_inst2));
        inst.add((TextView)findViewById(R.id.help_inst3));
        inst.add((TextView)findViewById(R.id.help_inst4));

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Castellar.ttf");
        for(int i = 0; i < 5; i++) {
            inst.get(i).setTypeface(font);
        }    }
}
