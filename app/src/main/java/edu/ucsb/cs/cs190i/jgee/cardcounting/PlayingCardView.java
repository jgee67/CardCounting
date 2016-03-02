package edu.ucsb.cs.cs190i.jgee.cardcounting;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * View representing a PlayingCard
 */
public class PlayingCardView extends ImageView {

    private PlayingCard card;

    //Necessary default constructor
    public PlayingCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //Default card setter sets to face down card
    public void setCard(){
        int ResID = getContext().getResources().getIdentifier("back", "drawable", "edu.ucsb.cs.cs190i.jgee.cardcounting");
        this.setImageResource(ResID);
        invalidate();
    }

    //Card setter
    public void setCard(PlayingCard c){
        card = c;
        int ResID = getContext().getResources().getIdentifier(card.toString(), "drawable", "edu.ucsb.cs.cs190i.jgee.cardcounting");
        this.setImageResource(ResID);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }

}
