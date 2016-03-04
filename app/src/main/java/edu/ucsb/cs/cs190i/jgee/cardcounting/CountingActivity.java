package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CountingActivity extends AppCompatActivity {

    private static TextView prompt;
    private static TextView time_header;
    private static TextView time_tv;
    private static TextView count_header;
    private static TextView count_tv;
    private static PlayingCardView card;
    private static Button left_button;
    private static Button middle_button;
    private static Button right_button;
    private static Deck deck;
    private static int count;
    private static int expectedCount;
    private static CountDownTimer countDownTimer;
    private static long timePerCard = 5;
    private static int secondsLeft = 0;
    private static int currentCardsCounted;
    private static int totalCardsCounted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        prompt = (TextView) findViewById(R.id.prompt);
        time_header = (TextView) findViewById(R.id.time_header);
        time_tv = (TextView) findViewById(R.id.time);
        count_header = (TextView) findViewById(R.id.count_header);
        count_tv = (TextView) findViewById(R.id.count);
        card = (PlayingCardView) findViewById(R.id.card);
        left_button = (Button) findViewById(R.id.left_button);
        middle_button = (Button) findViewById(R.id.middle_button);
        right_button = (Button) findViewById(R.id.right_button);
        count = 0;
        currentCardsCounted = 0;
        count_tv.setText(String.format("%d", count));
        expectedCount = 0;
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
        count_header.setVisibility(View.VISIBLE);
        count_tv.setVisibility(View.VISIBLE);
        resetCountDownTimer();
        left_button.setVisibility(View.VISIBLE);
        middle_button.setVisibility(View.VISIBLE);
        right_button.setVisibility(View.VISIBLE);
        card.flip();
    }

    public void leftPress(View v){
        count--;
        if(count != expectedCount){
            finishCounting();
            return;
        }
        resetCountDownTimer();
        currentCardsCounted++;
        count_tv.setText(String.format("%d", count));
        drawNext();
    }

    public void middlePress(View v){
        if(count != expectedCount){
            finishCounting();
            return;
        }
        resetCountDownTimer();
        currentCardsCounted++;
        drawNext();
    }

    public void rightPress(View v){
        count++;
        if(count != expectedCount) {
            finishCounting();
            return;
        }
        resetCountDownTimer();
        currentCardsCounted++;
        count_tv.setText(String.format("%d", count));
        drawNext();
    }

    //Helper used in button clicks, draws next card and resets field on empty deck
    private void drawNext(){
        try {
            card.setCard(deck.draw(), true);
            setExpected();
        }
        catch(Deck.EmptyDeckException e){
            finishCounting();
        }
    }

    //Resets to beginning state
    private static void reset(){
        count = 0;
        count_tv.setText(String.format("%d", count));
        expectedCount = 0;
        currentCardsCounted = 0;
        Log.w("currentCardsCounted", String.valueOf(currentCardsCounted));
        setFirst();
        card.flip();
        resetCountDownTimer();
    }

    //Sets the initial card
    private static void setFirst(){
        deck = new Deck();
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
                    time_tv.setText(String.format("%d", secondsLeft));
                    if(secondsLeft < 1) finishCounting();
                }
            }
            public void onFinish() {
                finishCounting();
            }
        };
    }

    //Ends the current counting session
    private void finishCounting(){
        countDownTimer.cancel();
        GameOverFragment gameOver = new GameOverFragment();
        gameOver.setCancelable(false);
        gameOver.show(getSupportFragmentManager(), "gameover");
    }

    public static class GameOverFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            totalCardsCounted += currentCardsCounted;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Game Over!")
                    .setMessage(String.format("You counted %d cards. Would you like to play again?", currentCardsCounted))
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            reset();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getContext(), MenuActivity.class);
                            startActivity(intent);
                        }
                    });
            return builder.create();
        }
    }

    //Stop timer on activity end
    @Override
    public void onBackPressed(){
        countDownTimer.cancel();
        super.onBackPressed();
    }
}

