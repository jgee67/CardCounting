package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

    private static int timePerCard;
    private static int numDecks;
    private static boolean isTimerOffMode;
    private static boolean isEndlessMode;
    private static boolean isRandomizeButtonsMode;
    private static boolean isActualCountMode;
    private static int totalCardsCounted;
    private static int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        // Hide action bar and status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setButtonFont();

        Intent intent = getIntent();
        timePerCard = intent.getIntExtra(KEY_TIME_PER_CARD, 5);
        numDecks = intent.getIntExtra(KEY_NUM_DECKS, 1);
        isTimerOffMode = intent.getBooleanExtra(KEY_IS_TIMER_OFF, false);
        isEndlessMode = intent.getBooleanExtra(KEY_IS_ENDLESS, false);
        isActualCountMode = intent.getBooleanExtra(KEY_IS_ACTUAL_CNT, false);
        isRandomizeButtonsMode= intent.getBooleanExtra(KEY_IS_RAND_BTNS, false);
        totalCardsCounted = intent.getIntExtra(KEY_TOTAL_CARDS, 0);
        totalTime = intent.getIntExtra(KEY_TOTAL_TIME, 0);

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
                break;
            case R.id.stats:
                intent = new Intent(this, CountingActivity.class);
                break;
            case R.id.options:
                intent = new Intent(this, OptionsActivity.class);
                break;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                break;
            default:
                intent = new Intent(this, CountingActivity.class);
                break;
        }
        intent.putExtra(MenuActivity.KEY_TIME_PER_CARD, timePerCard);
        intent.putExtra(MenuActivity.KEY_NUM_DECKS, numDecks);
        intent.putExtra(MenuActivity.KEY_IS_TIMER_OFF, isTimerOffMode);
        intent.putExtra(MenuActivity.KEY_IS_ACTUAL_CNT, isActualCountMode);
        intent.putExtra(MenuActivity.KEY_IS_ENDLESS, isEndlessMode);
        intent.putExtra(MenuActivity.KEY_IS_RAND_BTNS, isRandomizeButtonsMode);
        intent.putExtra(MenuActivity.KEY_TOTAL_CARDS, totalCardsCounted);
        intent.putExtra(MenuActivity.KEY_TOTAL_TIME, totalTime);
        startActivity(intent);
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
        Button start_button = (Button)findViewById(R.id.start);
        Button stats_button = (Button)findViewById(R.id.stats);
        Button options_button = (Button)findViewById(R.id.options);
        Button help_button = (Button)findViewById(R.id.help);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Castellar.ttf");
        start_button.setTypeface(font);
        stats_button.setTypeface(font);
        options_button.setTypeface(font);
        help_button.setTypeface(font);
    }
}
