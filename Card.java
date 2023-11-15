package application;
/*  Project:     C211Solitaire
*   Author:      Brandon J. AKA TarquisTrueshot
*   Date:        11/13/2023
*   Description: Card class that can be used for comparison and output. */

// This class represents one of the 52 cards in a standard deck of cards.
public class Card {

    //These constants allow red Cards to print red when output to the console.
    final String RED_COLOR = "\u001B[31m";
    final String DEFAULT = "\u001B[0m"; // Returns command line to default color.
    final int value;
    final char suit;
    final char color;

    // Constructor to create Card object of a specific suit and value.
    Card(int val, char suitChar){
        value = val;
        suit = suitChar;
        if(suitChar == '♣' || suitChar == '♠')
            color = 'B';
        else
            color = 'R';
    }

    // Getter method returning the suit char of the Card.
    public char getSuit(){
        return suit;
    }

    // Getter method returning the color char of the Card.
    public char getColor() {
        return color;
    }

    // Getter method returning the numeric value of the Card.
    public int getValue() {
        return value;
    }

    // Returns a formatted String representation of the Card's face, including color!
    public String getFace(){
        String faceAsString = "";

        //Card is not a face card, formatting not needed.
        if( value > 1 && value < 11)
            faceAsString = String.format("%d%s", value, suit);
        //Card is a face card, and needs formatting.
        else{

            faceAsString = switch (value) {
                case 1 -> "A" + suit;   //Ace.
                case 11 -> "J" + suit;  //Jack.
                case 12 -> "Q" + suit;  //Queen.
                case 13 -> "K" + suit;  //King.
                default -> faceAsString;
            };
        }
        if(color == 'R')    //Change output to red if card is Heart or Diamond.
            faceAsString = RED_COLOR + faceAsString + DEFAULT;

        return faceAsString;
    }
}