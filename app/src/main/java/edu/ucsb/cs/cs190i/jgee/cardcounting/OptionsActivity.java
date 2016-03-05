package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

public class OptionsActivity extends AppCompatActivity {
    private static final String LOG = "OPTIONS_LOG";
    private static final int TIME_PER_CARD_DEFAULT = 5;
    private static final int OPTION_MIN = 1;
    private static final int OPTION_MAX = 10;
    private static final int NUM_DECKS_DEFAULT = 1;

    private static CheckBox timer_off_cb;
    private static TextView time_option_tv;
    private static CheckBox endless_mode_cb;
    private static TextView deck_option_tv;
    private static CheckBox actual_count_cb;
    private static CheckBox random_buttons_cb;
    private static NumberPicker time_picker;
    private static NumberPicker deck_picker;

    private static int timePerCard;
    private static int numDecks;
    private static boolean isTimerOffMode;
    private static boolean isEndlessMode;
    private static boolean isRandomizeButtonsMode;
    private static boolean isActualCountMode;
    private static int totalCardsCounted;
    private static int totalTime;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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

        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();

        View div = findViewById(R.id.div);
        div.setBackgroundColor(time_option_tv.getTextColors().getDefaultColor());
        div.setAlpha((float) 0.5);

        initTimePicker();
        initDeckPicker();

        setupOnCheckChangeListeners();
        setOptionValues();
    }

    private void initTimePicker() {
        time_picker = (NumberPicker) findViewById(R.id.time_picker);
        time_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        time_picker.setMinValue(OPTION_MIN);
        time_picker.setMaxValue(OPTION_MAX);
        time_picker.setValue(TIME_PER_CARD_DEFAULT);
        time_picker.setWrapSelectorWheel(false);
        time_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                timePerCard = newVal;
            }
        });
    }

    private void initDeckPicker() {
        deck_picker = (NumberPicker) findViewById(R.id.deck_picker);
        deck_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        deck_picker.setMinValue(OPTION_MIN);
        deck_picker.setMaxValue(OPTION_MAX);
        deck_picker.setValue(NUM_DECKS_DEFAULT);
        deck_picker.setWrapSelectorWheel(false);
        deck_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numDecks = newVal;
            }
        });
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
                if (isChecked) {
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
    public void setOptionValues() {
        timePerCard = sharedPreferences.getInt(MenuActivity.KEY_TIME_PER_CARD, TIME_PER_CARD_DEFAULT);
        numDecks = sharedPreferences.getInt(MenuActivity.KEY_NUM_DECKS, NUM_DECKS_DEFAULT);
        isTimerOffMode = sharedPreferences.getBoolean(MenuActivity.KEY_IS_TIMER_OFF, false);
        isEndlessMode = sharedPreferences.getBoolean(MenuActivity.KEY_IS_ENDLESS, false);
        isActualCountMode = sharedPreferences.getBoolean(MenuActivity.KEY_IS_ACTUAL_CNT, false);
        isRandomizeButtonsMode = sharedPreferences.getBoolean(MenuActivity.KEY_IS_RAND_BTNS, false);
        updateOptionsUI();
    }

    // OnClick method for reset button - set options to default, need to hit save to confirm
    public void onResetClick(View v) {
        timePerCard = TIME_PER_CARD_DEFAULT;
        numDecks = NUM_DECKS_DEFAULT;
        isTimerOffMode = false;
        isEndlessMode = false;
        isActualCountMode = false;
        isRandomizeButtonsMode = false;

        updateOptionsUI();

        Toast.makeText(this, "Press \"Save\" to confirm reset to default values.", Toast.LENGTH_SHORT)
                .show();
    }

    // OnClick method for Save button
    public void onSaveClick(View v) {
        printDebugLog();
        saveSharedPrefs();

        Intent optionsIntent = new Intent(this, MenuActivity.class);
        optionsIntent.putExtra(MenuActivity.KEY_TIME_PER_CARD, timePerCard);
        optionsIntent.putExtra(MenuActivity.KEY_NUM_DECKS, numDecks);
        optionsIntent.putExtra(MenuActivity.KEY_IS_TIMER_OFF, isTimerOffMode);
        optionsIntent.putExtra(MenuActivity.KEY_IS_ACTUAL_CNT, isActualCountMode);
        optionsIntent.putExtra(MenuActivity.KEY_IS_ENDLESS, isEndlessMode);
        optionsIntent.putExtra(MenuActivity.KEY_IS_RAND_BTNS, isRandomizeButtonsMode);
        startActivity(optionsIntent);
    }

    // Update UI with the current values in variables
    private void updateOptionsUI() {
        time_picker.setValue(timePerCard);
        deck_picker.setValue(numDecks);
        timer_off_cb.setChecked(isTimerOffMode);
        endless_mode_cb.setChecked(isEndlessMode);
        random_buttons_cb.setChecked(isRandomizeButtonsMode);
        actual_count_cb.setChecked(isActualCountMode);
    }

    private void saveSharedPrefs() {
        editor.putInt(MenuActivity.KEY_TIME_PER_CARD, timePerCard);
        editor.putInt(MenuActivity.KEY_NUM_DECKS, numDecks);
        editor.putBoolean(MenuActivity.KEY_IS_TIMER_OFF, isTimerOffMode);
        editor.putBoolean(MenuActivity.KEY_IS_ENDLESS, isEndlessMode);
        editor.putBoolean(MenuActivity.KEY_IS_ACTUAL_CNT, isActualCountMode);
        editor.putBoolean(MenuActivity.KEY_IS_RAND_BTNS, isRandomizeButtonsMode);
        editor.commit();
    }

    // Prints out the values stored in the option variables
    private void printDebugLog() {
        Log.v(LOG, "tpc: " + timePerCard);
        Log.v(LOG, "numDecks: " + numDecks);
        Log.v(LOG, "isTimerOff: " + isTimerOffMode);
        Log.v(LOG, "isEndless: " + isEndlessMode);
        Log.v(LOG, "isAcualCnt: " + isActualCountMode);
        Log.v(LOG, "isRandBtns: " + isRandomizeButtonsMode);
    }
}
