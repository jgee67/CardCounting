package edu.ucsb.cs.cs190i.jgee.cardcounting;

import java.util.ArrayList;

/**
 * A class representing a single standard playing card
 */
public class PlayingCard {

    //Valid ints of suit
    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;

    //Valid ints of value
    public static final int ACE = 0;
    public static final int TWO = 1;
    public static final int THREE = 2;
    public static final int FOUR = 3;
    public static final int FIVE = 4;
    public static final int SIX = 5;
    public static final int SEVEN = 6;
    public static final int EIGHT = 7;
    public static final int NINE = 8;
    public static final int TEN = 9;
    public static final int JACK = 10;
    public static final int QUEEN = 11;
    public static final int KING = 12;

    //Used to check validity of suit and value
    private final ArrayList<Integer> suits = new ArrayList<>();
    private final ArrayList<Integer> values = new ArrayList<>();

    //This cards suit and value
    private int suit = -1;
    private int value = -1;

    //Constructor (and only setter) of card
    PlayingCard(int s, int v) throws IllegalSuitException, IllegalValueException{
        populateLists();
        if(!suits.contains(s)){
            throw new IllegalSuitException("Suit must be 0 (CLUBS), 1 (DIAMONDS), 2 (HEARTS), or 3 (SPADES)");
        }
        if(!values.contains(v)){
            throw new IllegalValueException("Value must be between 0 and 12 (0 = ACE, 12 = KING)");
        }
        suit = s;
        value = v;
    }

    //Returns suit as an int
    public int getSuit(){
        return suit;
    }

    //Returns value as an int
    public int getValue(){
        return value;
    }

    //Returns suit as a string
    public String getSuitAsString(){
        switch(suit){
            case CLUBS:
                return "clubs";
            case DIAMONDS:
                return "diamonds";
            case HEARTS:
                return "hearts";
            case SPADES:
                return "spades";
            default:
                return "bad suit.";
        }
    }

    //Returns value as a string
    public String getValueAsString(){
        switch(value){
            case ACE:
                return "ace";
            case TWO:
                return "two";
            case THREE:
                return "three";
            case FOUR:
                return "four";
            case FIVE:
                return "five";
            case SIX:
                return "six";
            case SEVEN:
                return "seven";
            case EIGHT:
                return "eight";
            case NINE:
                return "nine";
            case TEN:
                return "ten";
            case JACK:
                return "jack";
            case QUEEN:
                return "queen";
            case KING:
                return "king";
            default:
                return "Bad value.";
        }
    }

    //Returns "<value> of <suit>"
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("_of_");
        builder.append(getSuitAsString());
        builder.insert(0, getValueAsString());
        return builder.toString();
    }

    //Helper to check validity of suit and value
    private void populateLists(){
        suits.add(CLUBS);
        suits.add(DIAMONDS);
        suits.add(HEARTS);
        suits.add(SPADES);

        values.add(ACE);
        values.add(TWO);
        values.add(THREE);
        values.add(FOUR);
        values.add(FIVE);
        values.add(SIX);
        values.add(SEVEN);
        values.add(EIGHT);
        values.add(NINE);
        values.add(TEN);
        values.add(JACK);
        values.add(QUEEN);
        values.add(KING);
    }

    //Exception classes
    public class IllegalSuitException extends Exception{
        public IllegalSuitException(String message){
            super(message);
        }
    }

    public class IllegalValueException extends Exception{
        public IllegalValueException(String message){
            super(message);
        }
    }
}
