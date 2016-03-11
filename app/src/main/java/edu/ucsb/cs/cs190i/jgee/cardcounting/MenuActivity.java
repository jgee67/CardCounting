package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private static final String LOG = "MENU_LOG";

    public static final String KEY_TIME_PER_CARD = "KEY_TIME_PER_CARD";
    public static final String KEY_NUM_DECKS = "KEY_NUM_DECKS";
    public static final String KEY_IS_TIMER_OFF = "KEY_IS_TIMER_OFF";
    public static final String KEY_IS_ENDLESS = "KEY_IS_ENDLESS";
    public static final String KEY_IS_ACTUAL_CNT = "KEY_IS_ACTUAL_CNT";
    public static final String KEY_IS_RAND_BTNS = "KEY_IS_RAND_BTNS";
    public static final String KEY_TOTAL_CARDS = "KEY_TOTAL_CARDS";
    public static final String KEY_TOTAL_TIME = "KEY_TOTAL_TIME";
    public static final String PREFS = "CardCounting_prefs";
    public static final int HILO = 0;
    public static final int HI_OPT_1 = 1;
    public static final int HI_OPT_2 = 2;
    public static final int KO = 3;
    public static final int OMEGA_2 = 4;
    public static final int RED_7 = 5;
    public static final int ZEN_COUNT = 6;
    private static FragmentManager fragManager;

    private static int timePerCard;
    private static int numDecks;
    private static boolean isTimerOffMode;
    private static boolean isEndlessMode;
    private static boolean isRandomizeButtonsMode;
    private static boolean isActualCountMode;
    private static int totalCardsCounted;
    private static int totalTime;
    private static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setButtonFont();

        fragManager = getSupportFragmentManager();

        Intent intent = getIntent();
        timePerCard = intent.getIntExtra(KEY_TIME_PER_CARD, 5);
        numDecks = intent.getIntExtra(KEY_NUM_DECKS, 1);
        isTimerOffMode = intent.getBooleanExtra(KEY_IS_TIMER_OFF, false);
        isEndlessMode = intent.getBooleanExtra(KEY_IS_ENDLESS, false);
        isActualCountMode = intent.getBooleanExtra(KEY_IS_ACTUAL_CNT, false);
        isRandomizeButtonsMode= intent.getBooleanExtra(KEY_IS_RAND_BTNS, false);
        sp = this.getSharedPreferences(PREFS, MODE_PRIVATE);
        totalCardsCounted = sp.getInt(KEY_TOTAL_CARDS, 0);
        totalTime = sp.getInt(KEY_TOTAL_TIME, 0);

        printDebugLog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void menuPress(View v){
        int id = v.getId();
        Intent intent;
        switch(id){
            case R.id.start:
                intent = new Intent(this, CountingActivity.class);
                intent.putExtra(MenuActivity.KEY_TIME_PER_CARD, timePerCard);
                intent.putExtra(MenuActivity.KEY_NUM_DECKS, numDecks);
                intent.putExtra(MenuActivity.KEY_IS_TIMER_OFF, isTimerOffMode);
                intent.putExtra(MenuActivity.KEY_IS_ACTUAL_CNT, isActualCountMode);
                intent.putExtra(MenuActivity.KEY_IS_ENDLESS, isEndlessMode);
                intent.putExtra(MenuActivity.KEY_IS_RAND_BTNS, isRandomizeButtonsMode);
                intent.putExtra(MenuActivity.KEY_TOTAL_CARDS, totalCardsCounted);
                intent.putExtra(MenuActivity.KEY_TOTAL_TIME, totalTime);
                startActivity(intent);
                break;
            case R.id.stats:
                StatsFragment stats = new StatsFragment();
                stats.setCancelable(false);
                stats.show(fragManager, "stats");
                break;
            case R.id.options:
                intent = new Intent(this, OptionsActivity.class);
                intent.putExtra(MenuActivity.KEY_TIME_PER_CARD, timePerCard);
                intent.putExtra(MenuActivity.KEY_NUM_DECKS, numDecks);
                intent.putExtra(MenuActivity.KEY_IS_TIMER_OFF, isTimerOffMode);
                intent.putExtra(MenuActivity.KEY_IS_ACTUAL_CNT, isActualCountMode);
                intent.putExtra(MenuActivity.KEY_IS_ENDLESS, isEndlessMode);
                intent.putExtra(MenuActivity.KEY_IS_RAND_BTNS, isRandomizeButtonsMode);
                intent.putExtra(MenuActivity.KEY_TOTAL_CARDS, totalCardsCounted);
                intent.putExtra(MenuActivity.KEY_TOTAL_TIME, totalTime);
                startActivity(intent);
                break;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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

    public void setButtonFont() {
        Button start_button = (Button) findViewById(R.id.start);
        Button stats_button = (Button) findViewById(R.id.stats);
        Button options_button = (Button) findViewById(R.id.options);
        Button help_button = (Button) findViewById(R.id.help);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Castellar.ttf");
        start_button.setTypeface(font);

        stats_button.setTypeface(font);
        options_button.setTypeface(font);
        help_button.setTypeface(font);
    }
    //DialogFragment that displays user stats
    public static class StatsFragment extends DialogFragment {
        @Override
        @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = "Stats";
            float avg = (float) totalCardsCounted / (float) totalTime;
            if(totalCardsCounted == 0) avg = 0;
            String message = "Cards Counted: %d\n\nTime Played: %d seconds\n\nAverage Cards Counted per Second: %.2f";
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title)
                    .setMessage(String.format(message, totalCardsCounted, totalTime, avg))
                    .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ConfirmationFragment confirm = new ConfirmationFragment();
                            confirm.setCancelable(false);
                            confirm.show(fragManager, "confirm");
                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //close dialog
                        }
                    });
            return builder.create();
        }
    }

    //DialogFragment that displays user stats
    public static class ConfirmationFragment extends DialogFragment {
        @Override
        @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = "Are you sure?";
            String message = "This will permanently reset your stats.";
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            totalCardsCounted = 0;
                            totalTime = 0;
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt(KEY_TOTAL_CARDS, totalCardsCounted);
                            editor.putInt(KEY_TOTAL_TIME, totalTime);
                            editor.apply();
                            StatsFragment stats = new StatsFragment();
                            stats.setCancelable(false);
                            stats.show(fragManager, "stats");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            StatsFragment stats = new StatsFragment();
                            stats.setCancelable(false);
                            stats.show(fragManager, "stats");
                        }
                    });
            return builder.create();
        }
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}
