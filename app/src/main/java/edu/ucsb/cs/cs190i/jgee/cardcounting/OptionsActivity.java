package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends AppCompatActivity {
    private static final String LOG = "OPTIONS_LOG";
    private static final int TIME_PER_CARD_DEFAULT = 5;
    private static final int OPTION_MIN = 1;
    private static final int OPTION_MAX = 10;
    private static final int NUM_DECKS_DEFAULT = 1;

    private static TextView method_options_header;
    private static Spinner method_spinner;
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
    private static int method;

    private List<String> methods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setFont();

        Intent intent = getIntent();
        timePerCard = intent.getIntExtra(MenuActivity.KEY_TIME_PER_CARD, 5);
        numDecks = intent.getIntExtra(MenuActivity.KEY_NUM_DECKS, 1);
        isTimerOffMode = intent.getBooleanExtra(MenuActivity.KEY_IS_TIMER_OFF, false);
        isEndlessMode = intent.getBooleanExtra(MenuActivity.KEY_IS_ENDLESS, false);
        isActualCountMode = intent.getBooleanExtra(MenuActivity.KEY_IS_ACTUAL_CNT, false);
        isRandomizeButtonsMode= intent.getBooleanExtra(MenuActivity.KEY_IS_RAND_BTNS, false);
        method = intent.getIntExtra(MenuActivity.KEY_METHOD, 0);

        method_options_header = (TextView) findViewById(R.id.method_options_header);
        method_spinner = (Spinner) findViewById(R.id.method_spinner);
        timer_off_cb = (CheckBox) findViewById(R.id.timer_off_cb);
        time_option_tv = (TextView) findViewById(R.id.time_option_tv);
        endless_mode_cb = (CheckBox) findViewById(R.id.endless_mode_cb);
        deck_option_tv = (TextView) findViewById(R.id.deck_option_tv);
        actual_count_cb = (CheckBox) findViewById(R.id.actual_count_cb);
        random_buttons_cb = (CheckBox) findViewById(R.id.random_buttons_cb);

        initTimePicker();
        initDeckPicker();
        addSpinnerMethods();
        method_spinner.setSelection(method);
        method_spinner.setOnItemSelectedListener(new methodSelectedListener());

        setupOnCheckChangeListeners();
        updateOptionsUI();
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
        setNumberPickerTextColor(time_picker, Color.WHITE);
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
        setNumberPickerTextColor(deck_picker, Color.WHITE);
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

    // OnClick method for reset button - set options to default, need to hit save to confirm
    public void onResetClick(View v) {
        timePerCard = TIME_PER_CARD_DEFAULT;
        numDecks = NUM_DECKS_DEFAULT;
        isTimerOffMode = false;
        isEndlessMode = false;
        isActualCountMode = false;
        isRandomizeButtonsMode = false;
        method = 0;

        updateOptionsUI();

        Toast.makeText(this, "Press \"Save\" to confirm reset to default values.", Toast.LENGTH_SHORT)
                .show();
    }

    // OnClick method for Save button
    public void onSaveClick(View v) {
        printDebugLog();

        Intent optionsIntent = new Intent(this, MenuActivity.class);
        optionsIntent.putExtra(MenuActivity.KEY_TIME_PER_CARD, timePerCard);
        optionsIntent.putExtra(MenuActivity.KEY_NUM_DECKS, numDecks);
        optionsIntent.putExtra(MenuActivity.KEY_IS_TIMER_OFF, isTimerOffMode);
        optionsIntent.putExtra(MenuActivity.KEY_IS_ACTUAL_CNT, isActualCountMode);
        optionsIntent.putExtra(MenuActivity.KEY_IS_ENDLESS, isEndlessMode);
        optionsIntent.putExtra(MenuActivity.KEY_IS_RAND_BTNS, isRandomizeButtonsMode);
        optionsIntent.putExtra(MenuActivity.KEY_METHOD, method);
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
        method_spinner.setSelection(method);
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

    // Set the font for the views in this activity
    private void setFont() {
        ArrayList<TextView> inst = new ArrayList<TextView>();
        inst.add((TextView)findViewById(R.id.options_title));
        inst.add((TextView)findViewById(R.id.time_option_tv));
        inst.add( (TextView)findViewById(R.id.deck_option_tv));
        inst.add( (TextView)findViewById(R.id.method_options_header));

        ArrayList<CheckBox> cb_list = new ArrayList<CheckBox>();
        cb_list.add((CheckBox) findViewById(R.id.actual_count_cb));
        cb_list.add((CheckBox) findViewById(R.id.random_buttons_cb));
        cb_list.add((CheckBox) findViewById(R.id.timer_off_cb));
        cb_list.add((CheckBox) findViewById(R.id.endless_mode_cb));

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Castellar.ttf");
        inst.get(0).setTypeface(font);

        TextView difficulty_tv = (TextView) findViewById(R.id.difficulty_options_header);
        difficulty_tv.setTypeface(font);

        Button reset = (Button) findViewById(R.id.reset_btn);
        Button save = (Button) findViewById(R.id.save_btn);
        reset.setTypeface(font);
        save.setTypeface(font);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/BASKVILL.TTF");
        for(int i = 1; i < inst.size(); i++) {
            inst.get(i).setTypeface(font1);
        }

        for(int i = 0; i < cb_list.size(); i++) {
            cb_list.get(i).setTypeface(font1);
        }
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
//                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalAccessException e){
//                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalArgumentException e){
//                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }

    public void addSpinnerMethods() {
        methods.add(getString(R.string.hilo));
        methods.add(getString(R.string.hiopt1));
        methods.add(getString(R.string.hiopt2));
        methods.add(getString(R.string.ko));
        methods.add(getString(R.string.omega2));
        methods.add(getString(R.string.red7));
        methods.add(getString(R.string.zencount));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, methods);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        method_spinner.setAdapter(dataAdapter);
    }

    public class methodSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            method = pos;
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }
}
