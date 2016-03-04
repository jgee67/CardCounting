package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {
    private static final long TIME_PER_CARD_DEFAULT = 5;
    private static final long TIME_PER_CARD_MIN = 1;
    private static final long TIME_PER_CARD_MAX = 10;

    private static final int NUM_DECKS_DEFAULT = 1;

    private static CheckBox timer_off_cb;
    private static TextView time_option_tv;
    private static CheckBox endless_mode_cb;
    private static TextView deck_option_tv;
    private static CheckBox actual_count_cb;
    private static CheckBox random_buttons_cb;
    private static Button ok_btn;
    private static Button reset_btn;
    private static View div;
    private static NumberPicker time_picker;
    private static NumberPicker deck_picker;

    private long timePerCard;
    private static int numDecks;
    private static boolean isTimerOffMode;
    private static boolean isEndlessMode;
    private static boolean isRandomizeButtonsMode;
    private static boolean isActualCountMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_options);
        setSupportActionBar(toolbar);

        timer_off_cb = (CheckBox) findViewById(R.id.timer_off_cb);
        time_option_tv = (TextView) findViewById(R.id.time_option_tv);
        endless_mode_cb = (CheckBox) findViewById(R.id.endless_mode_cb);
        deck_option_tv = (TextView) findViewById(R.id.deck_option_tv);
        actual_count_cb = (CheckBox) findViewById(R.id.actual_count_cb);
        random_buttons_cb = (CheckBox) findViewById(R.id.random_buttons_cb);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        reset_btn = (Button) findViewById(R.id.reset_btn);
        div = (View) findViewById(R.id.div);
        div.setBackgroundColor(time_option_tv.getTextColors().getDefaultColor());
        div.setAlpha((float) 0.5);


        time_picker = (NumberPicker) findViewById(R.id.time_picker);
        time_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        time_picker.setMinValue(1);
        time_picker.setMaxValue(10);
        time_picker.setValue(5);
        time_picker.setWrapSelectorWheel(false);
        time_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                timePerCard = newVal;
            }
        });


        deck_picker = (NumberPicker) findViewById(R.id.deck_picker);
        deck_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        deck_picker.setMinValue(1);
        deck_picker.setMaxValue(10);
        deck_picker.setValue(1);
        deck_picker.setWrapSelectorWheel(false);
        deck_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numDecks = newVal;
            }
        });


        setupOnCheckChangeListeners();
        setDefaultValues();
    }

    // Set up listeners for the checkbox fields
    public void setupOnCheckChangeListeners() {
        timer_off_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isTimerOffMode = true;
                    time_option_tv.setEnabled(false);
                    time_picker.setEnabled(false);
                } else {
                    isTimerOffMode = false;
                    time_option_tv.setEnabled(true);
                    time_picker.setEnabled(true);
                }
            }
        });
        endless_mode_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    isEndlessMode = true;
                    deck_picker.setEnabled(false);
                } else {
                    isEndlessMode = false;
                    deck_picker.setEnabled(true);
                }
            }
        });
        actual_count_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isActualCountMode = isChecked;
            }
        });
        random_buttons_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRandomizeButtonsMode = isChecked;
            }
        });
    }

    // Set the default values for each option.
    public void setDefaultValues() {
        timePerCard = TIME_PER_CARD_DEFAULT;
        numDecks = NUM_DECKS_DEFAULT;
        isTimerOffMode = false;
        isEndlessMode = false;
        isRandomizeButtonsMode = false;
        isActualCountMode = false;
//        time_per_card_tv.setText(timePerCard + "");
    }

    // OnClick method for time plus button
    public void onTimePlusClick(View v) {
        if(timePerCard < TIME_PER_CARD_MAX)
            timePerCard++;
//        time_per_card_tv.setText(timePerCard + "");
    }

    // OnClick method for time minus button
    public void onTimeMinusClick(View v) {
        if(timePerCard > TIME_PER_CARD_MIN)
        timePerCard--;
//        time_per_card_tv.setText(timePerCard + "");
    }

    // OnClick method for deck plus button
    public void onDeckPlusClick(View v) {
        numDecks++;
    }

    // OnClick method for deck minus button
    public void onDeckMinusClick(View v) {
        if(numDecks > NUM_DECKS_DEFAULT)
            numDecks--;
    }

    // OnClick method for reset button - set options to default
    public void onResetClick(View v) {
        setDefaultValues();
        Toast.makeText(this, "Options have been reset to its default values.", Toast.LENGTH_SHORT)
                .show();
    }

    // OnClick method for OK button
    public void onOKClick(View v) {}
}
