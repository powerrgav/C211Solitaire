package application;
import java.awt.event.ActionListener;
import java.util.*;
import javafx.scene.text.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
/*
 * C211 Solitaire Project Table class
 * Author: Gavin Power
 * 10 November 2023
 * This class arranges cards for the solitaire game and ensures card functions
 * abide by the rules of the game.
 * 
 * All info regarding terms comes from:
 * https://solitaired.com/solitaire-terms
 * 
 * BoundingBox and GraphicsContext were learned from Java Oracle Docs.
 * https://docs.oracle.com/javase/8/javafx/api/toc.htm
 */

public class SolitaireTable {

    //set some attributes
    private GraphicsContext theGraphics;
    private static double cardWidth = 45;
    private static double cardHeight = 80;
    private static double spaceBetween = 30;
    
    //variables to store board and boundaries
    private  ArrayList <Card> deck = new ArrayList<>();
    private ArrayList<Card> waste = new ArrayList<>();
    private ArrayList<ArrayList<Card>> foundations = new ArrayList<>();
    private ArrayList<ArrayList<Card>> theTableau = new ArrayList<>();
    public ArrayList<ArrayList<BoundingBox>>tableauBounds = new ArrayList<>();
    public ArrayList <BoundingBox> foundationsBounds = new ArrayList<>();
    public BoundingBox wasteBounds = new BoundingBox (150, 50, cardWidth, cardHeight);
    public BoundingBox stockBounds = new BoundingBox(50, 50, cardWidth, cardHeight);

    
    //constructor to initialize the board
    SolitaireTable(GraphicsContext theGraphics) {
        this.theGraphics = theGraphics;
        shuffledDeck();
        foundations();
        wastePile();
        tableau();
        stockPile();
    }
    
    //default constructor
    SolitaireTable(){
        
    }

    //create the arraylist of cards for the deck
    public ArrayList<Card> createDeck() {
        //Declare local variables.
        char[] SUITS = {'♣', '♦', '♥', '♠'};   
        
        //Populate deck with cards.
        for(int value = 1; value < 14; value++){
            for(char currentSuit : SUITS)
                deck.add(new Card(value, currentSuit));
        }
        return deck;
    }
    
    //I'm not totally sure why, but shuffling the deck inside createDeck
    //messes up the colors of the cards.
    private ArrayList<Card> shuffledDeck() {
        Collections.shuffle(createDeck());
        return deck; 
    }
    
    //this method returns and removes the last card in the arraylist
    private Card getTopCard() {
        return deck.remove(deck.size()-1);
    }
    
    //this method draws an empty place for a card to be placed
    //x and y refer to the coordinates the rectangle (empty place) is drawn at
    private void emptyPlace(double x, double y) {
        theGraphics.setStroke(Color.LIGHTGRAY);
        theGraphics.setLineWidth(1);
        theGraphics.strokeRect(x, y, cardWidth, cardHeight);
    }
    
    //this method draws the back of a card
    private void cardBack(double x, double y) {
        //set the color of the card back
        theGraphics.setFill(Color.CORNFLOWERBLUE);  
        
        //create a border around the card
        theGraphics.setStroke(Color.WHITE);
        theGraphics.setLineWidth(5);
        theGraphics.fillRect(x, y, cardWidth, cardHeight);
        theGraphics.strokeRect(x, y, cardWidth, cardHeight);
    }
    
    private void cardFace(double x, double y, Card card) 
    {
        //set the color of the card face
        theGraphics.setFill(Color.WHITE);  
        theGraphics.fillRect(x, y, cardWidth, cardHeight);
        
        //get a card and write on the face of the card
        String valueAsString = Integer.toString(card.getValue());
        String suitAsString = Character.toString(card.getSuit());
    
        //Ensure the graphics are in the center of the card
        theGraphics.setTextAlign(TextAlignment.CENTER);
        theGraphics.setTextBaseline(VPos.CENTER);
        
        //Set the color of the card based on its attribute in Card
        if(card.getColor() == 'B') 
        {
            theGraphics.setFill(Color.BLACK);
        }
        else 
            theGraphics.setFill(Color.RED);
        
        //Set our font
        theGraphics.setFont(new Font("BOLD", 20));
        theGraphics.fillText(valueAsString, x+22.5, y+25, 25);
        
        //Draw the text onto the card
        if(card.getSuit() == '♣') 
        {
            theGraphics.fillText(suitAsString, x+22.5, y+50);
        }
        else if(card.getSuit() == '♦') 
        {
            theGraphics.fillText(suitAsString, x+22.5, y+50);
        }
        else if(card.getSuit() == '♥') 
        {
            theGraphics.fillText(suitAsString, x+22.5, y+50);
        }
        else if(card.getSuit() =='♠' ) 
        {
            theGraphics.fillText(suitAsString, x+22.5, y+50);
        }
       
    }
    
    //the tableau is the 7 columns of cards that comprise of the main area
    //at the start of the game, there are 28 cards on the tableau
    private void tableau() 
    {
        
        //logic to lay out the tableau
        for(int i = 0; i < 7; i++) 
        {
            
            //create an arraylist to use to make the cards appear
            ArrayList<Card> stackOfCards = new ArrayList<>();
            
            //we want j to be less than i + 1 because, at the start of the game,
            //the very first column on the tableau (index 0) will start with one card.
            for(int j = 0 ; j < i + 1; j++) 
            {
                //get the top card off of the deck
                stackOfCards.add(getTopCard());
            }
            //add each stack of cards on the tableau to the tableau
            theTableau.add(stackOfCards);
        }
        
        //draw the tableau
        for(int i = 0; i < 7; i++) 
        {
            double x = 100 * i + 75;
            //create an arraylist that makes the vertical columns
            ArrayList<Card> tableauColumn = theTableau.get(i);
            
            if(tableauColumn.isEmpty()) {
                //this y value is a placeholder for now, subject to change
                emptyPlace(x, spaceBetween * 2 + cardHeight);
            }else 
            {               
                for(int j = 0; j < tableauColumn.size(); j++) {
                    //create two cards. one to be drawn as revealed and one hidden
                    Card card = tableauColumn.get(j);
                    Card topCard = tableauColumn.get(tableauColumn.size()-1);
                    topCard.setRevealed();
                    
                    double y = spaceBetween * j + 175;
                    
                    //add an assert statement to make sure we're on the right track
                    assert(!card.getRevealed());
                    
                    //if a card is marked as revealed, show its face. if not, its back.
                    if(card.getRevealed()) {
                        cardFace(x, y, card);
                    }else 
                        
                    {
                        cardBack(x , y);
                    }
                }
            }
        }
        
        //declare the bounds for each tableau card
        //bounding box is declared by (x , y , width , height)
        //the x and y for each boundary are the same as x and y 
        //in the previous for loop in this method
        for(int i = 0; i < 7; i++) {
            
            //we need multiple dimensions for the ArrayList because we have multiple columns
            //that need a boundary
            tableauBounds.add(new ArrayList<>());
            ArrayList<Card> columns = theTableau.get(i);
            
            tableauBounds.get(i).add(new BoundingBox(100 * i + 75, spaceBetween * 2 + cardHeight, cardWidth, cardHeight));
            
            for(int j = 1; j < columns.size(); j++) {
                tableauBounds.get(i).add(new BoundingBox(100 * i + 75, spaceBetween * j + 175, cardWidth, cardHeight ));
            }
        }
    }
    
    //the foundations are the 4 piles upon which cards must be placed
    //to complete the game, starting with ace. One pile for each suit
    private void foundations() 
    {
        //start the game with 4 empty places at the top right of the canvas
        for(int i = 0; i < 4; i++) {
            
          //declare clickable area in the foundations
            foundationsBounds.add(new BoundingBox(100 *i + 400, spaceBetween, cardWidth, cardHeight));
            
            double x = 100 *i + 400;

            emptyPlace(x,50);
            
            //to add: moving card from board to foundations
        }
    }
    
    //the stock is the pile of cards not currently in play, which are 
    //brought into play according to the rules.
    //at the start of the game, there are 24 cards in the stock pile
    private void stockPile() 
    {
        //draws an empty place is the deck is empty, a card back if there are cards remianing
        if(deck.isEmpty()) {
            emptyPlace(50,50);
        }else {
            cardBack(50,50);
        }
    }
    
    //the waste pile is the pile of face up cards not put into play
    private void wastePile() 
    {
        //draws an empty place is the deck is empty, a card face if there are cards remianing
        if(waste.isEmpty()) {
            emptyPlace(150,50);
        }else {
            cardFace(150,50, getTopCard());
        }
    }
    
    //--------------------------------------------------------
    //----------------------some get methods------------------
    //--------------------------------------------------------
    

    /**
     * @return the tableauBounds
     */
    public ArrayList<ArrayList<BoundingBox>> getTableauBounds() {
        return tableauBounds;
    }

    /**
     * @return the foundationsBounds
     */
    public ArrayList<BoundingBox> getFoundationsBounds() {
        return foundationsBounds;
    }

    /**
     * @return the wasteBounds
     */
    public BoundingBox getWasteBounds() {
        return wasteBounds;
    }

    /**
     * @return the stockBounds
     */
    public BoundingBox getStockBounds() {
        return stockBounds;
    }
}//end class