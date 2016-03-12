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
    private boolean faceUp;

    //Necessary default constructor
    public PlayingCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //Card setter
    public void setCard(PlayingCard c, boolean f){
        faceUp = f;
        card = c;
        int ResID;
        if(faceUp) ResID = getContext().getResources().getIdentifier(card.toString(), "drawable", "edu.ucsb.cs.cs190i.jgee.cardcounting");
        else ResID = getContext().getResources().getIdentifier("back", "drawable", "edu.ucsb.cs.cs190i.jgee.cardcounting");
        this.setImageResource(ResID);
        invalidate();
    }

    //Flip card
    public void flip(){
        int ResID;
        if(faceUp) ResID = getContext().getResources().getIdentifier("back", "drawable", "edu.ucsb.cs.cs190i.jgee.cardcounting");
        else ResID = getContext().getResources().getIdentifier(card.toString(), "drawable", "edu.ucsb.cs.cs190i.jgee.cardcounting");
        this.setImageResource(ResID);
        faceUp = !faceUp;
    }

    //Returns card's value (1-11)
    public int getCardValue(){
        return card.getValue();
    }

    //Returns card's suit (0-3)
    public int getCardSuit(){
        return card.getSuit();
    }

    //Returns card's id (0-12)
    public int getCardId(){
        return card.getId();
    }

    //Returns true if card is face up
    public boolean isFaceUp(){
        return faceUp;
    }

}
