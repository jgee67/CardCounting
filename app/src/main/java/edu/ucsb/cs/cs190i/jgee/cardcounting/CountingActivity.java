package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.os.Bundle;
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
    private PlayingCardView card;
    private Button left_button;
    private Button middle_button;
    private Button right_button;
    private static Deck deck;
    private static int count;
    private static int expectedCount;

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
        count_tv.setText(String.format("%d", count));
        expectedCount = 0;
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
    public void startCounting(View v){
        card.setClickable(false);
        time_header.setVisibility(View.VISIBLE);
        time_tv.setVisibility(View.VISIBLE);
        count_header.setVisibility(View.VISIBLE);
        count_tv.setVisibility(View.VISIBLE);
        left_button.setVisibility(View.VISIBLE);
        middle_button.setVisibility(View.VISIBLE);
        right_button.setVisibility(View.VISIBLE);
        card.flip();
    }

    public void leftPress(View v){
        count--;
        if(count != expectedCount){
            reset();
            new Toast(this).makeText(this, "Wrong! Try again!", Toast.LENGTH_SHORT).show();
            return;
        }
        count_tv.setText(String.format("%d", count));
        drawNext();
    }

    public void middlePress(View v){
        if(count != expectedCount){
            reset();
            new Toast(this).makeText(this, "Wrong! Try again!", Toast.LENGTH_SHORT).show();
            return;
        }
        drawNext();
    }

    public void rightPress(View v){
        count++;
        if(count != expectedCount) {
            reset();
            new Toast(this).makeText(this, "Wrong! Try again!", Toast.LENGTH_SHORT).show();
            return;
        }
        count_tv.setText(String.format("%d", count));
        drawNext();
    }

    //Helper used in button clicks, draws next card and resets field on empty deck
    private void drawNext(){
        try {
            card.setCard(deck.draw(), true);
            setExpected();
            Log.w("Counting", String.format("expectedAction: %d", expectedAction()));
        }
        catch(Deck.EmptyDeckException e){
            reset();
            new Toast(this).makeText(this, "Deck empty, tap card to replay", Toast.LENGTH_SHORT).show();
        }
    }

    //Resets to beginning state
    private void reset(){
        left_button.setVisibility(View.INVISIBLE);
        middle_button.setVisibility(View.INVISIBLE);
        right_button.setVisibility(View.INVISIBLE);
        time_header.setVisibility(View.INVISIBLE);
        time_tv.setVisibility(View.INVISIBLE);
        count_header.setVisibility(View.INVISIBLE);
        count_tv.setVisibility(View.INVISIBLE);
        count = 0;
        count_tv.setText(String.format("%d", count));
        expectedCount = 0;
        setFirst();
        card.setClickable(true);
    }

    //Sets the initial card
    private void setFirst(){
        deck = new Deck();
        deck.shuffle(6);
        try{
            card.setCard(deck.draw(), false);
            setExpected();
            Log.w("Counting", String.format("expectedAction: %d", expectedAction()));
        }
        catch(Deck.EmptyDeckException e){

        }
    }

    //Gets the next increment based on next card value
    private void setExpected(){
        int value = card.getCardValue();
        if(value < 7) expectedCount++;
        if(value > 9) expectedCount--;
    }

    //Gets the expectedAction based on next card value
    private int expectedAction(){
        int value = card.getCardValue();
        if(value < 7) return 1;
        if(value < 10) return 0;
        else return -1;
    }

}
