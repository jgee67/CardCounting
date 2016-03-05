package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
}
