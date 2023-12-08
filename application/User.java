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
            System.out.println("Stock clicked!");
            selectOrFlipCard(0);
            //t.flipCard(0);


        }
        
        //determine if the x and y coordinates match the waste pile
        if(t.getWasteBounds().contains(x,y)) {
            System.out.println("Waste clicked!");
            selectOrFlipCard(Integer.valueOf(1));
        }
        
        //determine if the x and y coordinates match the foundations bounds
        for(int i = 0; i < 4; i++) {
            if(t.getFoundationsBounds().get(i).contains(x,y)) {
                System.out.println("Foundation " + i + " clicked!");
                selectOrFlipCard(i + 2);
            }
        }
        
        //determine if the x and y coordinates match the tableau bounds
        for(int i = 0; i < 7; i++) {   
            
            ArrayList<BoundingBox> tabBoundsChecker = t.tableauBounds.get(i);
            
            for(int j = 0; j < tabBoundsChecker.size(); j++) {
                if(tabBoundsChecker.get(j).contains(x, y)) {
                    System.out.println("Tableau " + i + " clicked!");
                }
            }
            
        }
        
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
                    //t.checkTopCard(firstPile).setSelectedFalse();
                    checkIfValidMove();
                }
                else
                    t.flipCard(chosenPile);
            else {
                secondPile = chosenPile;
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
            else if(secondPile > 5 && secondPile < 10){

                // Handle tableau to tableau move. For now, move all cards.
                if(firstPile > 5 && firstPile < 10 && firstPile != secondPile){
                    t.movePileAcrossPile(firstPile, secondPile);
                }
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
            else if(secondPile > 5 && secondPile < 10){
                //Must be opposite color, descending order.
                if(!t.matchingColor(firstPile, secondPile) && t.inOrder(firstPile, secondPile, false))
                    t.moveCardAcrossPiles(firstPile, secondPile);
            }

        }
        return true;
    }
    
    
}