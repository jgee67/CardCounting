package edu.ucsb.cs.cs190i.jgee.cardcounting;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * Represents a deck of standard playing cards. Utilizes PlayingCard class.
 *
 */
public class Deck extends Stack<PlayingCard>{

    //Constructor creates a standard playing card deck
    Deck(){
        for(int s = 0; s < 4; s++){
            for(int v = 0; v < 13; v++){
                try{
                    this.push(new PlayingCard(s, v));
                }
                catch(PlayingCard.IllegalSuitException e){
                    System.exit(-1);
                }
                catch(PlayingCard.IllegalValueException e){
                    System.exit(-2);
                }
            }
        }
    }

    //Constructor creates Deck with numDecks standard playing card decks in it
    Deck(int numDecks){
        for(int i = 0; i < numDecks; i++) {
            for (int s = 0; s < 4; s++) {
                for (int v = 0; v < 13; v++) {
                    try {
                        this.push(new PlayingCard(s, v));
                    } catch (PlayingCard.IllegalSuitException e) {
                        System.exit(-1);
                    } catch (PlayingCard.IllegalValueException e) {
                        System.exit(-2);
                    }
                }
            }
        }
    }

    //Shuffle the deck once
    public void shuffle(){
        long seed = System.nanoTime();
        Collections.shuffle(this, new Random(seed));
    }

    //Shuffle the deck n times
    public void shuffle(int n){
        for(int i = 0; i < n; i++) {
            long seed = System.nanoTime();
            Collections.shuffle(this, new Random(seed));
        }
    }

    //Repopulates deck (copy of default constructor)
    public void repopulate(){
        this.clear();
        for(int s = 0; s < 4; s++){
            for(int v = 0; v < 13; v++){
                try{
                    this.push(new PlayingCard(s, v));
                }
                catch(PlayingCard.IllegalSuitException e){
                    System.exit(-1);
                }
                catch(PlayingCard.IllegalValueException e){
                    System.exit(-2);
                }
            }
        }
    }

    //Repopulates deck with numDekcs (copy of numDeck constructor)
    public void repopulate(int numDecks){
        this.clear();
        for(int i = 0; i < numDecks; i++) {
            for (int s = 0; s < 4; s++) {
                for (int v = 0; v < 13; v++) {
                    try {
                        this.push(new PlayingCard(s, v));
                    } catch (PlayingCard.IllegalSuitException e) {
                        System.exit(-1);
                    } catch (PlayingCard.IllegalValueException e) {
                        System.exit(-2);
                    }
                }
            }
        }
    }

    //Draws a playing card from top of deck
    public PlayingCard draw() throws EmptyDeckException{
        if(this.size() > 0) return this.pop();
        else throw new EmptyDeckException("Draw called on an empty deck");
    }

    //Exception classes
    public class EmptyDeckException extends Exception{
        public EmptyDeckException(String message){
            super(message);
        }
    }
}
