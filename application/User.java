package application;
import javafx.scene.SubScene;
import javafx.scene.input.*;
import javafx.geometry.BoundingBox;
import java.util.ArrayList;


public class User {



    //get a default solitaire table
    SolitaireTable t;

    Integer firstPile = null;
    Integer secondPile = null;

    public User(SolitaireTable table){

        t = table;
    }


    
    public void mouseClickEvents(MouseEvent e) { 
        
        //find the x and y coordinates of a clicked mouse
        double x = e.getSceneX();
        double y = e.getSceneY();

        //System.out.println("Mouse click registered at (" + e.getSceneX() + ", " + e.getSceneY() + ")!");
        //if(e != null)
            //return;

        //determine if the x and y coordinates match the stock pile
        if(t.getStockBounds().contains(x,y)) {
            //System.out.println("Stock clicked!");
            selectOrFlipCard(0);
            //t.flipCard(0);


        }
        
        //determine if the x and y coordinates match the waste pile
        if(t.getWasteBounds().contains(x,y)) {
            //System.out.println("Waste clicked!");
            selectOrFlipCard(Integer.valueOf(1));
        }
        
        //determine if the x and y coordinates match the foundations bounds
        for(int i = 0; i < 4; i++) {
            if(t.getFoundationsBounds().get(i).contains(x,y)) {
                //System.out.println("Foundation " + i + " clicked!");
                selectOrFlipCard(i + 2);
            }
        }

        int selectedDeck = -1;
        
        //determine if the x and y coordinates match the tableau bounds
        for(int i = 0; i < 7; i++) {   
            //get an arraylist of the column we're looking at
            ArrayList<BoundingBox> tabBoundsChecker = t.getTableauBounds().get(i);


            
            //check if the tableau is clicked
            for(int j = 0; j < tabBoundsChecker.size(); j++) {
                //Checks that the mouse click occurs within the width of a given tableau.
                //Also checks that the mouse click occurs below the top card of given tableau.
                if(x < tabBoundsChecker.get(j).getMaxX() &&
                        x > tabBoundsChecker.get(j).getMinX() &&
                        y > t.getTableauBounds().get(i).get(0).getMinY())
                    selectedDeck = i + 6;


                /*  As of testing, there was inconsistent behavior when clicking any card
                    that was not the last card of a tableau, each tableau pile somehow stored
                    the values 6 - 12 if possible.

                if(tabBoundsChecker.get(j).contains(x, y)) {
                    if((i + 6) > selectedDeck)
                        selectedDeck = i + 6;
                }

                */
            }

        }
        // I do not know why, but if I do this in the loop, I get multiple values sent.
        // May try putting a break statement in the loop
        if(selectedDeck > -1)
            selectOrFlipCard(selectedDeck);
        
    }

    private void selectOrFlipCard(Integer chosenPile){
        if (firstPile == null){
            if(t.pileHasCards(chosenPile)) { // chosen pile must have cards.
                if(t.cardVisible(chosenPile)) { // chosen pile's top card must be visible.
                    if (chosenPile < 2 || chosenPile > 5) { //Not a foundation.
                        firstPile = chosenPile;
                        //t.checkTopCard(firstPile).setSelected();
                        //t.redrawScreen();
                        System.out.println(chosenPile + " selected as the first pile!");
                    }
                }
                else
                    t.flipCard(chosenPile);
            }
        }
        else if(secondPile == null){
            if(t.pileHasCards(chosenPile))
                if(t.cardVisible(chosenPile)) {
                    secondPile = chosenPile;
                    System.out.println(secondPile + " selected as the second pile, which has cards!");
                    //t.checkTopCard(firstPile).setSelectedFalse();
                    checkIfValidMove();
                }
                else
                    t.flipCard(chosenPile);
            else {
                secondPile = chosenPile;
                System.out.println(secondPile + " is empty, but selected as the second pile!");
                //t.checkTopCard(firstPile).setSelectedFalse();
                checkIfValidMove();
            }

            firstPile = null;
            secondPile = null;
            //System.out.println(chosenPile + " selected as the second pile!");
        }
        else {
            //System.out.println("Two piles have already been selected!");
            //System.out.println("Valid Move: " + checkIfEmpty(firstPile, secondPile));
            //t.alphaDeckToDiscard();
            //firstPile = null;
            //secondPile = null;
        }
    }

    private boolean checkIfEmpty(Integer pileOne, Integer pileTwo){
        return (t.isPileEmpty(pileOne) && t.isPileEmpty(pileTwo));
    }

    private boolean checkIfValidMove(){
        // Handles moving card from deck to waste. Allows double-clicking of deck.
        if(firstPile == 0 && (secondPile == 0 || secondPile == 1))
            t.moveCardAcrossPiles(0, 1);
        //Handles moving card to empty pile, excluding the waste pile, handled above.
        else if(t.isPileEmpty(secondPile) && secondPile != 1) {
            // Handle moving to the foundations.
            if (secondPile > 1 && secondPile < 6) {
                if (t.checkTopCard(firstPile).value == 1) { // Only an Ace can be moved.
                    t.moveCardAcrossPiles(firstPile, secondPile);
                }
            }
            // Handles moving to the tableau.
            else if(secondPile > 5 && secondPile < 13){

                // Handle tableau to tableau move. For now, move all cards.
                if(firstPile > 5 && firstPile < 13 && firstPile != secondPile){
                    t.moveCardAcrossPiles(firstPile, secondPile);
                }
                //Handle waste to tableau.
                else if(firstPile == 1)
                    t.moveCardAcrossPiles(firstPile, secondPile);
            }
        }
        // Handle the second pile having cards.
        else if(t.pileHasCards(secondPile) && secondPile != 1){
            // Handle moving to foundation.
            if(secondPile > 1 && secondPile < 6){
                // Must be same suit, in ascending order.
                if(t.matchingSuit(firstPile, secondPile) && t.inOrder(firstPile, secondPile, true))
                    t.moveCardAcrossPiles(firstPile, secondPile);
            }
            //Handle moving to tableau.
            // Will need additional logic to handle moving more than one card.
            else if(secondPile > 5 && secondPile < 13){
                //Must be opposite color, descending order.
                if(!t.matchingColor(firstPile, secondPile) && t.inOrder(firstPile, secondPile, false))
                    t.moveCardAcrossPiles(firstPile, secondPile);
            }

        }
        t.redrawScreen();
        return true;
    }
    
    
}