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

    //Valid ints of id
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
    private final ArrayList<Integer> ids = new ArrayList<>();

    //This cards suit and value
    private int suit = -1;
    private int id = -1;

    //Constructor (and only setter) of card
    PlayingCard(int s, int v) throws IllegalSuitException, IllegalValueException{
        populateLists();
        if(!suits.contains(s)){
            throw new IllegalSuitException("Suit must be 0 (CLUBS), 1 (DIAMONDS), 2 (HEARTS), or 3 (SPADES)");
        }
        if(!ids.contains(v)){
            throw new IllegalValueException("Value must be between 0 and 12 (0 = ACE, 12 = KING)");
        }
        suit = s;
        id = v;
    }

    //Returns suit as an int
    public int getSuit(){
        return suit;
    }

    //Returns id as an int
    public int getId(){
        return id;
    }

    //Returns in-game value of card (as opposed to id)
    public int getValue(){
        switch(id){
            case ACE:
                return 11;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            default:
                return 10;
        }
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

    //Returns id as a string
    public String getValueAsString(){
        switch(id){
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

    //Returns "<id> of <suit>"
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

        ids.add(ACE);
        ids.add(TWO);
        ids.add(THREE);
        ids.add(FOUR);
        ids.add(FIVE);
        ids.add(SIX);
        ids.add(SEVEN);
        ids.add(EIGHT);
        ids.add(NINE);
        ids.add(TEN);
        ids.add(JACK);
        ids.add(QUEEN);
        ids.add(KING);
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
