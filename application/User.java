package application;
import javafx.scene.SubScene;
import javafx.scene.input.*;

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
            setUserPiles(Integer.valueOf(0));
            t.flipCard(0);


        }
        
        //determine if the x and y coordinates match the waste pile
        if(t.getWasteBounds().contains(x,y)) {
            System.out.println("Waste clicked!");
            setUserPiles(Integer.valueOf(1));
        }
        
        //determine if the x and y coordinates match the foundations bounds
        for(int i = 0; i < 4; i++) {
            if(t.getFoundationsBounds().get(i).contains(x,y)) {
                System.out.println("Foundation " + i + " clicked!");
                setUserPiles(Integer.valueOf(i + 2));
            }
        }
        
        //determine if the x and y coordinates match the tableau bounds
        for(int i = 0; i < 7; i++) {   
            for(int j = 0; j < 10; j++) {
                //10 is just a placeholder for now. will be changed as functionality is added.
            }
        }
        
    }

    private void setUserPiles(Integer chosenPile){
        if (firstPile == null){
            firstPile = chosenPile;
            //System.out.println(chosenPile + " selected as the first pile!");
        }
        else if(secondPile == null){
            secondPile = chosenPile;
            t.alphaDeckToDiscard();
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
    
    
}
