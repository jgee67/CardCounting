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
    private static final int TIME_PER_CARD_DEFAULT = 5;
    private static final int TIME_PER_CARD_MIN = 1;
    private static final int TIME_PER_CARD_MAX = 10;
    private static final int NUM_DECKS_DEFAULT = 1;

    private static CheckBox timer_off_cb;
    private static TextView time_option_tv;
    private static CheckBox endless_mode_cb;
    private static TextView deck_option_tv;
    private static CheckBox actual_count_cb;
    private static CheckBox random_buttons_cb;
    private static Button ok_btn;
    private static Button reset_btn;
    private static NumberPicker time_picker;
    private static NumberPicker deck_picker;

    private static int timePerCard;
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
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        timer_off_cb = (CheckBox) findViewById(R.id.timer_off_cb);
        time_option_tv = (TextView) findViewById(R.id.time_option_tv);
        endless_mode_cb = (CheckBox) findViewById(R.id.endless_mode_cb);
        deck_option_tv = (TextView) findViewById(R.id.deck_option_tv);
        actual_count_cb = (CheckBox) findViewById(R.id.actual_count_cb);
        random_buttons_cb = (CheckBox) findViewById(R.id.random_buttons_cb);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        reset_btn = (Button) findViewById(R.id.reset_btn);


        View div = findViewById(R.id.div);
        div.setBackgroundColor(time_option_tv.getTextColors().getDefaultColor());
        div.setAlpha((float) 0.5);


        time_picker = (NumberPicker) findViewById(R.id.time_picker);
        time_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        time_picker.setMinValue(TIME_PER_CARD_MIN);
        time_picker.setMaxValue(TIME_PER_CARD_MAX);
        time_picker.setValue(TIME_PER_CARD_DEFAULT);
        time_picker.setWrapSelectorWheel(false);
        time_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                timePerCard = newVal;
            }
        });


        deck_picker = (NumberPicker) findViewById(R.id.deck_picker);
        deck_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        deck_picker.setMinValue(TIME_PER_CARD_MIN);
        deck_picker.setMaxValue(TIME_PER_CARD_MAX);
        deck_picker.setValue(NUM_DECKS_DEFAULT);
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
                    deck_option_tv.setEnabled(false);
                    deck_picker.setEnabled(false);
                } else {
                    isEndlessMode = false;
                    deck_option_tv.setEnabled(true);
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
        time_picker.setValue(TIME_PER_CARD_DEFAULT);
        deck_picker.setValue(NUM_DECKS_DEFAULT);
        timer_off_cb.setChecked(false);
        endless_mode_cb.setChecked(false);
        random_buttons_cb.setChecked(false);
        actual_count_cb.setChecked(false);
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
