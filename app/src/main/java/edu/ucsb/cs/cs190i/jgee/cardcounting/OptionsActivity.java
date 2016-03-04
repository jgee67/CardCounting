package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {
    private static final long TIME_PER_CARD_DEFAULT = 5;
    private static final long TIME_PER_CARD_MIN = 1;
    private static final long TIME_PER_CARD_MAX = 10;

    private static final int NUM_DECKS_DEFAULT = 1;

    private static CheckBox timer_off_cb;
    private static TextView time_option_tv;
    private static TextView time_per_card_tv;
    private static Button time_plus_btn;
    private static Button time_minus_btn;
    private static CheckBox endless_mode_cb;
    private static TextView deck_option_tv;
    private static TextView deck_count_tv;
    private static Button deck_plus_btn;
    private static Button deck_minus_btn;
    private static CheckBox actual_count_cb;
    private static CheckBox random_buttons_cb;
    private static Button ok_btn;
    private static Button reset_btn;

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
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        timer_off_cb = (CheckBox) findViewById(R.id.timer_off_cb);
        time_option_tv = (TextView) findViewById(R.id.time_option_tv);
        time_per_card_tv = (TextView) findViewById(R.id.time_per_card_tv);
        time_plus_btn = (Button) findViewById(R.id.time_plus_btn);
        time_minus_btn = (Button) findViewById(R.id.time_minus_btn);
        endless_mode_cb = (CheckBox) findViewById(R.id.endless_mode_cb);
        deck_option_tv = (TextView) findViewById(R.id.deck_option_tv);
        deck_count_tv = (TextView) findViewById(R.id.deck_count_tv);
        deck_plus_btn = (Button) findViewById(R.id.deck_plus_btn);
        deck_minus_btn = (Button) findViewById(R.id.deck_minus_btn);
        actual_count_cb = (CheckBox) findViewById(R.id.actual_count_cb);
        random_buttons_cb = (CheckBox) findViewById(R.id.random_buttons_cb);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        reset_btn = (Button) findViewById(R.id.reset_btn);

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
                    time_per_card_tv.setEnabled(false);
                    time_option_tv.setEnabled(false);
                    time_plus_btn.setEnabled(false);
                    time_minus_btn.setEnabled(false);
                } else {
                    isTimerOffMode = false;
                    time_per_card_tv.setEnabled(true);
                    time_option_tv.setEnabled(true);
                    time_plus_btn.setEnabled(true);
                    time_minus_btn.setEnabled(true);
                }
            }
        });
        endless_mode_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    isEndlessMode = true;
                    deck_count_tv.setEnabled(false);
                    deck_option_tv.setEnabled(false);
                    deck_plus_btn.setEnabled(false);
                    deck_minus_btn.setEnabled(false);
                } else {
                    isEndlessMode = false;
                    deck_count_tv.setEnabled(true);
                    deck_option_tv.setEnabled(true);
                    deck_plus_btn.setEnabled(true);
                    deck_minus_btn.setEnabled(true);
                }
            }
        });
        actual_count_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    isActualCountMode = true;
                } else {
                    isActualCountMode = false;
                }

            }
        });
        random_buttons_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    isRandomizeButtonsMode = true;
                } else {
                    isRandomizeButtonsMode = false;
                }

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
        time_per_card_tv.setText(timePerCard + "");
        deck_count_tv.setText(numDecks + "");
    }

    // OnClick method for time plus button
    public void onTimePlusClick(View v) {
        if(timePerCard < TIME_PER_CARD_MAX)
            timePerCard++;
        time_per_card_tv.setText(timePerCard + "");
    }

    // OnClick method for time minus button
    public void onTimeMinusClick(View v) {
        if(timePerCard > TIME_PER_CARD_MIN)
        timePerCard--;
        time_per_card_tv.setText(timePerCard + "");
    }

    // OnClick method for deck plus button
    public void onDeckPlusClick(View v) {
        numDecks++;
        deck_count_tv.setText(numDecks + "");
    }

    // OnClick method for deck minus button
    public void onDeckMinusClick(View v) {
        if(numDecks > NUM_DECKS_DEFAULT)
            numDecks--;
        deck_count_tv.setText(numDecks + "");
    }

    // OnClick method for reset button - set options to default
    public void onResetClick(View v) {
        setDefaultValues();
    }

    // OnClick method for OK button
    public void onOKClick(View v) {}
}
