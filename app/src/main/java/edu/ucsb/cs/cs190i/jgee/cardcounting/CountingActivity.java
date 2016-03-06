package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;


public class CountingActivity extends AppCompatActivity {

    private static final String PLUS = "PLUS";
    private static final String ZERO = "ZERO";
    private static final String MINUS = "MINUS";

    private static TextView prompt;
    private static TextView time_header;
    private static TextView time_tv;
    private static TextView count_header;
    private static TextView count_tv;
    private static TextView cards_counted_header;
    private static TextView cards_counted;
    private static PlayingCardView card;
    private static Button left_button;
    private static Button middle_button;
    private static Button right_button;
    private static Deck deck;
    private static int count;
    private static int expectedCount;
    private static CountDownTimer countDownTimer;
    private static int secondsLeft = 0;
    private static int currentCardsCounted;
    private static int sessionTime;
    private static Chronometer chron;
    private static boolean success;
    private static final ArrayList<String> random = new ArrayList<>();
    private static FragmentManager fragManager;

    private static int timePerCard;
    private static int numDecks;
    private static boolean isTimerOffMode;
    private static boolean isEndlessMode;
    private static boolean isRandomizeButtonsMode;
    private static boolean isActualCountMode;
    private static SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        sp = this.getSharedPreferences(MenuActivity.PREFS, MODE_PRIVATE);

        Intent intent = getIntent();
        timePerCard = intent.getIntExtra(MenuActivity.KEY_TIME_PER_CARD, 5);
        numDecks = intent.getIntExtra(MenuActivity.KEY_NUM_DECKS, 1);
        isTimerOffMode = intent.getBooleanExtra(MenuActivity.KEY_IS_TIMER_OFF, false);
        isEndlessMode = intent.getBooleanExtra(MenuActivity.KEY_IS_ENDLESS, false);
        isActualCountMode = intent.getBooleanExtra(MenuActivity.KEY_IS_ACTUAL_CNT, false);
        isRandomizeButtonsMode= intent.getBooleanExtra(MenuActivity.KEY_IS_RAND_BTNS, false);

        prompt = (TextView) findViewById(R.id.prompt);
        time_header = (TextView) findViewById(R.id.time_header);
        time_tv = (TextView) findViewById(R.id.time);
        count_header = (TextView) findViewById(R.id.count_header);
        count_tv = (TextView) findViewById(R.id.count);
        cards_counted_header = (TextView) findViewById(R.id.cards_counted_header);
        cards_counted = (TextView) findViewById(R.id.cards_counted);
        card = (PlayingCardView) findViewById(R.id.card);
        left_button = (Button) findViewById(R.id.left_button);
        middle_button = (Button) findViewById(R.id.middle_button);
        right_button = (Button) findViewById(R.id.right_button);
        count = 0; // player's count
        count_tv.setText(String.format("%d", count));
        currentCardsCounted = 0;
        cards_counted.setText(String.format("%d", currentCardsCounted));
        expectedCount = 0;
        if(isTimerOffMode) time_tv.setText(R.string.disabled);
        else time_tv.setText(String.format("%d", timePerCard));
        sessionTime = 0;
        success = false;
        chron = new Chronometer(this);
        random.clear();
        random.add(0, PLUS);
        random.add(0, ZERO);
        random.add(0, MINUS);
        fragManager = getSupportFragmentManager();
        setButtons();
        initCountDownTimer();
        setFirst();
        fadePrompt();
    }

    private void fadePrompt(){
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(2000);
        fadeOut.setStartOffset(1500);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                prompt.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        prompt.startAnimation(fadeOut);
    }

    //PlayingCardView's onClickListener that starts the counting
    public void startCounting(View v) {
        card.setClickable(false);
        time_header.setVisibility(View.VISIBLE);
        time_tv.setVisibility(View.VISIBLE);
        if(!isActualCountMode){
            count_header.setVisibility(View.VISIBLE);
            count_tv.setVisibility(View.VISIBLE);
        }
        cards_counted_header.setVisibility(View.VISIBLE);
        cards_counted.setVisibility(View.VISIBLE);
        resetCountDownTimer();
        left_button.setVisibility(View.VISIBLE);
        middle_button.setVisibility(View.VISIBLE);
        right_button.setVisibility(View.VISIBLE);
        card.flip();
        chron.setBase(SystemClock.elapsedRealtime());
        chron.start();
    }

    public static class minusPress implements View.OnClickListener {

        @Override
        public void onClick(View v){
            count--;
            if(count != expectedCount){
                finishCounting();
                return;
            }
            if(isRandomizeButtonsMode){
                setRandomButton(left_button);
                setRandomButton(middle_button);
                setRandomButton(right_button);
            }
            else if(isActualCountMode){
                left_button.setText(String.format("%d", count - 1));
                middle_button.setText(String.format("%d", count));
                right_button.setText(String.format("%d", count + 1));
            }
            resetCountDownTimer();
            currentCardsCounted++;
            count_tv.setText(String.format("%d", count));
            cards_counted.setText(String.format("%d", currentCardsCounted));
            drawNext();
        }

    }

    public static class zeroPress implements View.OnClickListener {

        @Override
        public void onClick(View v){
            if(count != expectedCount){
                finishCounting();
                return;
            }
            if(isRandomizeButtonsMode){
                setRandomButton(left_button);
                setRandomButton(middle_button);
                setRandomButton(right_button);
            }
            resetCountDownTimer();
            currentCardsCounted++;
            cards_counted.setText(String.format("%d", currentCardsCounted));
            drawNext();
        }

    }

    public static class plusPress implements View.OnClickListener{

        @Override
        public void onClick(View v){
            count++;
            if(count != expectedCount) {
                finishCounting();
                return;
            }
            if(isRandomizeButtonsMode){
                setRandomButton(left_button);
                setRandomButton(middle_button);
                setRandomButton(right_button);
            }
            else if(isActualCountMode){
                left_button.setText(String.format("%d", count - 1));
                middle_button.setText(String.format("%d", count));
                right_button.setText(String.format("%d", count + 1));
            }
            resetCountDownTimer();
            currentCardsCounted++;
            count_tv.setText(String.format("%d", count));
            cards_counted.setText(String.format("%d", currentCardsCounted));
            drawNext();
        }

    }

    //Helper used in button clicks, draws next card and resets field on empty deck
    private static void drawNext(){
        if(isEndlessMode){
            card.setCard(randomCard(), true);
            setExpected();
        }
        else {
            try {
                card.setCard(deck.draw(), true);
                setExpected();
            } catch (Deck.EmptyDeckException e) {
                success = true;
                finishCounting();
            }
        }
    }

    //Helper used to generate random card for endless mode (no deck)
    private static PlayingCard randomCard(){
        Random rand = new Random();
        int randomSuit = rand.nextInt(4);
        int randomVal = rand.nextInt(13);
        try {
            return new PlayingCard(randomSuit, randomVal);
        }
        catch(PlayingCard.IllegalSuitException e){
            System.exit(-1);
        }
        catch(PlayingCard.IllegalValueException e){
            System.exit(-2);
        }
        return null;
    }

    //Resets to beginning state
    private static void reset(){
        count = 0;
        count_tv.setText(String.format("%d", count));
        expectedCount = 0;
        currentCardsCounted = 0;
        cards_counted.setText(String.format("%d", currentCardsCounted));
        sessionTime = 0;
        setButtons();
        setFirst();
        card.flip();
        resetCountDownTimer();
        chron.stop();
        chron.setBase(SystemClock.elapsedRealtime());
        chron.start();
    }

    //Sets the initial card
    private static void setFirst(){
        deck = new Deck(numDecks);
        deck.shuffle(6);
        try{
            card.setCard(deck.draw(), false);
            setExpected();
        }
        catch(Deck.EmptyDeckException e){
            Log.w("EmptyDeckException", "called inside setFirst, should not happen");
        }
    }

    //Gets the next increment based on next card value
    private static void setExpected(){
        int value = card.getCardValue();
        if(value < 7) expectedCount++;
        if(value > 9) expectedCount--;
    }

    //Reset the countdown timer
    private static void resetCountDownTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    //Initializes the CountDownTimer
    private void initCountDownTimer(){
        double bufferedTime = timePerCard + .3;
        bufferedTime = bufferedTime * 1000;
        countDownTimer = new CountDownTimer((long) bufferedTime, 100) {
            public void onTick(long millisUntilFinished) {
                if (Math.round((float)millisUntilFinished / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float)millisUntilFinished / 1000.0f);
                    if(!isTimerOffMode) {
                        time_tv.setText(String.format("%d", secondsLeft));
                        if (secondsLeft < 1) finishCounting();
                    }
                }
            }
            //Only finishes on TimerOff mode
            public void onFinish() {
                //do nothing
            }
        };
    }

    //Ends the current counting session
    private static void finishCounting(){
        countDownTimer.cancel();
        sessionTime = (int) Math.round((SystemClock.elapsedRealtime() - chron.getBase()) / 1000.0);
        chron.stop();

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(MenuActivity.KEY_TOTAL_CARDS, sp.getInt(MenuActivity.KEY_TOTAL_CARDS, 0) + currentCardsCounted);
        editor.putInt(MenuActivity.KEY_TOTAL_TIME, sp.getInt(MenuActivity.KEY_TOTAL_TIME, 0) + sessionTime);
        editor.apply();

        GameOverFragment gameOver = new GameOverFragment();
        gameOver.setCancelable(false);
        gameOver.show(fragManager, "gameover");
    }

    //initializes buttons
    private static void setButtons(){
        if(isRandomizeButtonsMode){
            setRandomButton(left_button);
            setRandomButton(middle_button);
            setRandomButton(right_button);
        } else {
            left_button.setText("-1");
            middle_button.setText("0");
            right_button.setText("+1");
            if (isActualCountMode) {
                left_button.setText(String.format("%d", count - 1));
                middle_button.setText(String.format("%d", count));
                right_button.setText(String.format("%d", count + 1));
            }
            left_button.setOnClickListener(new minusPress());
            middle_button.setOnClickListener(new zeroPress());
            right_button.setOnClickListener(new plusPress());
        }
    }

    //Sets a button randomly as minus, zero, or plus
    private static void setRandomButton(Button button){
        Random rand = new Random();
        int randIndex = rand.nextInt(random.size());
        String task = random.remove(randIndex);
        if(task.equals(MINUS)){
            button.setText("-1");
            if(isActualCountMode) button.setText(String.format("%d", count - 1));
            button.setOnClickListener(new minusPress());
        }
        if(task.equals(ZERO)){
            button.setText("0");
            if(isActualCountMode) button.setText(String.format("%d", count));
            button.setOnClickListener(new zeroPress());
        }
        if(task.equals(PLUS)){
            button.setText("+1");
            if(isActualCountMode) button.setText(String.format("%d", count + 1));
            button.setOnClickListener(new plusPress());
        }
        if(random.size() == 0){
            random.add(0, PLUS);
            random.add(0, ZERO);
            random.add(0, MINUS);
        }
    }

    //DialogFragment that displays when the game is over
    public static class GameOverFragment extends DialogFragment {
        @Override
        @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = "Game Over!";
            String message = "You counted %d cards in %d seconds. Would you like to try again?";
            if(success){
                success = false;
                title = "Congratulations!";
                message = "You counted all %d cards in %d seconds! Would you like to play again?";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title)
                    .setMessage(String.format(message, currentCardsCounted, sessionTime))
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            reset();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getContext(), MenuActivity.class);
                            intent.putExtra(MenuActivity.KEY_TIME_PER_CARD, timePerCard);
                            intent.putExtra(MenuActivity.KEY_NUM_DECKS, numDecks);
                            intent.putExtra(MenuActivity.KEY_IS_TIMER_OFF, isTimerOffMode);
                            intent.putExtra(MenuActivity.KEY_IS_ACTUAL_CNT, isActualCountMode);
                            intent.putExtra(MenuActivity.KEY_IS_ENDLESS, isEndlessMode);
                            intent.putExtra(MenuActivity.KEY_IS_RAND_BTNS, isRandomizeButtonsMode);
                            startActivity(intent);
                        }
                    });
            return builder.create();
        }
    }

    //Stop timer on activity ends
    @Override
    public void onBackPressed(){
        countDownTimer.cancel();
        chron.stop();
        super.onBackPressed();
    }

    @Override
    public void onPause(){
        countDownTimer.cancel();
        chron.stop();
        super.onPause();
    }

    @Override
    public void onDestroy(){
        countDownTimer.cancel();
        chron.stop();
        super.onDestroy();
    }
}

