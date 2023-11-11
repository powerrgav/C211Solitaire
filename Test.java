import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        testCard();
    }

    //Tests functionality of the Card class.

    private static void testCard(){
        //Declare local variables.
        char[] SUITS = {'♣', '♦', '♥', '♠'};
        ArrayList<Card> deck = new ArrayList<>();

        //Populate deck with cards.
        for(int value = 1; value < 14; value++){
            for(char currentSuit : SUITS)
                deck.add(new Card(value, currentSuit));
        }

        //Print out cards added to deck.
        for(Card currentCard : deck)
            System.out.println(currentCard.getFace() + " Suit: " + currentCard.getSuit() + " Value: " + currentCard.getValue());
    }
}
