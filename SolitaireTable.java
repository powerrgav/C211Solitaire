package application;
import java.util.*;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
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
 */

public class SolitaireTable {

    //set some attributes
    private static int tableauColumns = 7;
    private static int foundations = 4;
    private GraphicsContext theGraphics;
    private Random random = new Random();
    private static double cardWidth = 45;
    private static double cardHeight = 80;
    private static double spaceBetween = 30;
    
    //variables to store board and boundaries
    private ArrayList<ArrayList<Card>> theTableau = new ArrayList<>();
    
    //constructor to initialize the board
    SolitaireTable(GraphicsContext theGraphics) {
        this.theGraphics = theGraphics;
        foundations();
        wastePile();
        tableau();
        stockPile();
    }
    
    //create the arraylist of cards for the deck
    public ArrayList<Card> createDeck() {
      //Declare local variables.
        char[] SUITS = {'♣', '♦', '♥', '♠'};
        ArrayList<Card> deck = new ArrayList<>();

        //Populate deck with cards.
        for(int value = 1; value < 14; value++){
            for(char currentSuit : SUITS)
                deck.add(new Card(value, currentSuit));
        }
        return deck;
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
    
    private void cardFace(double x, double y) 
    {
        //set the color of the card face
        theGraphics.setFill(Color.WHITE);  
        theGraphics.fillRect(x, y, cardWidth, cardHeight);
        
        //get a random card and write on the face of the card
        int randCardFace = random.nextInt(52);
        String valueAsString = Integer.toString(createDeck().get(randCardFace).value);
        String suitAsString = Character.toString(createDeck().get(randCardFace).getSuit());
    
        
        theGraphics.setTextAlign(TextAlignment.CENTER);
        theGraphics.setTextBaseline(VPos.CENTER);
        
        if(createDeck().get(randCardFace).getColor() == 'B') 
        {
            theGraphics.setFill(Color.BLACK);
        }
        else 
            theGraphics.setFill(Color.RED);
        
        theGraphics.setFont(new Font("BOLD", 20));
        theGraphics.fillText(valueAsString, x+22.5, y+25, 25);
        
        if(createDeck().get(randCardFace).getSuit() == '♣') 
        {
            theGraphics.fillText(suitAsString, x+22.5, y+50);
        }
        else if(createDeck().get(randCardFace).getSuit() == '♦') 
        {
            theGraphics.fillText(suitAsString, x+22.5, y+50);
        }
        else if(createDeck().get(randCardFace).getSuit() == '♥') 
        {
            theGraphics.fillText(suitAsString, x+22.5, y+50);
        }
        else if(createDeck().get(randCardFace).getSuit() =='♠' ) 
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
            ArrayList<Card> stackOfCards = new ArrayList<>();
            for(int j = 0 ; j < i + 1; j++) 
            {
                stackOfCards.add(null);
            }
            theTableau.add(stackOfCards);
        }
        
        //draw the tableau
        for(int i = 0; i < 7; i++) 
        {
            double x = spaceBetween + (cardWidth + spaceBetween) * i + 100;
            ArrayList<Card> tableauColumn = theTableau.get(i);
            if(tableauColumn.isEmpty()) {
                emptyPlace(x, spaceBetween * 2 + cardHeight);
            }else 
            {
                for(int j = 0; j < tableauColumn.size(); j++) {
                    double y = spaceBetween * (2 + j) + cardHeight + 30;
                    
                    cardFace(x, y);
                }
            }
        }
    }
    
    //the foundations are the 4 piles upon which cards must be placed
    //to complete the game, starting with ace. One pile for each suit
    private void foundations() 
    {
        //start the game with 4 empty places at the top right of the canvas
        //from left to right, the places are assigned club, diamond, heart, spade
        emptyPlace(400,50);
        emptyPlace(500,50);
        emptyPlace(600,50);
        emptyPlace(700,50);
    }
    
    //the stock is the pile of cards not currently in play, which are 
    //brought into play according to the rules.
    //at the start of the game, there are 24 cards in the stock pile
    private void stockPile() 
    {
        cardBack(50,50);
    }
    
    //the waste pile is the pile of face up cards not put into play
    private void wastePile() 
    {
        //the waste pile should be empty at the start of the game
        emptyPlace(150,50);
    }
    
    //---------------------------------------------------------------------
    //separating the get/set methods from the rest of the class for easier reading
    //---------------------------------------------------------------------
    
    /**
     * @return the tableauColumns
     */
    public int getTableauColumns() {
        return tableauColumns;
    }

    /**
     * @param tableauColumns the tableauColumns to set
     */
    public void setTableauColumns(int tableauColumns) {
        SolitaireTable.tableauColumns = tableauColumns;
    }

    /**
     * @return the foundations
     */
    public int getFoundations() {
        return foundations;
    }

    /**
     * @param foundations the foundations to set
     */
    public void setFoundations(int foundations) {
        SolitaireTable.foundations = foundations;
    }
    

}
