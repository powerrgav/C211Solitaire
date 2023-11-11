public class Card {

    //These constants allow the cards to display red or black when output to the console.
    final String RED_COLOR = "\u001B[31m";
    final String DEFAULT = "\u001B[0m";
    final int value;
    final char suit;
    //This attribute may be unneccesary, can just have if statement on return for getColor()
    final char color;

    Card(int val, char suitChar){
        value = val;
        suit = suitChar;
        if(suitChar == '♣' || suitChar == '♠')
            color = 'B';
        else
            color = 'R';
    }

    public char getSuit(){
        return suit;
    }

    public char getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

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
