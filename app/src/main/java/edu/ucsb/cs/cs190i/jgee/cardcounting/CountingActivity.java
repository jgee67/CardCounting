package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;


public class CountingActivity extends AppCompatActivity {

    private static TextView prompt;
    private static TextView time_header;
    private static TextView time_tv;
    private static TextView count_header;
    private static TextView count_tv;
    private PlayingCardView card;
    private static Deck deck;

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
        card.setCard();
        deck = new Deck();
        deck.shuffle(6);

        fadePrompt();
    }

    private void fadePrompt(){
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(2000);
        fadeOut.setStartOffset(5000);

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

    public void startCounting(View v){
//        card.setClickable(false);
        time_header.setVisibility(View.VISIBLE);
        time_tv.setVisibility(View.VISIBLE);
        count_header.setVisibility(View.VISIBLE);
        count_tv.setVisibility(View.VISIBLE);
        try {
            card.setCard(deck.draw());
        }
        catch(Deck.EmptyDeckException e){
            card.setCard();
            new Toast(this).makeText(this, "Deck is empty, count should be 0", Toast.LENGTH_SHORT).show();
        }
    }

}
