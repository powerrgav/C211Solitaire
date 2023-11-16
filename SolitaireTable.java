package application;
import java.util.*;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private static double cardWidth = 60;
    private static double cardHeight = 100;
    
    //constructor to initialize the board
    SolitaireTable(GraphicsContext theGraphics) {
        this.theGraphics = theGraphics;
        foundations();
        wastePile();
    }
    
    //create the arraylist of cards for the deck
    public void createDeck() {
      //Declare local variables.
        char[] SUITS = {'♣', '♦', '♥', '♠'};
        ArrayList<Card> deck = new ArrayList<>();

        //Populate deck with cards.
        for(int value = 1; value < 14; value++){
            for(char currentSuit : SUITS)
                deck.add(new Card(value, currentSuit));
        }
    }
    
    //this method draws an empty place for a card to be placed
    //x and y refer to the coordinates the rectangle (empty place) is drawn at
    public void emptyPlace(double x, double y) {
        theGraphics.setStroke(Color.LIGHTGRAY);
        theGraphics.setLineWidth(1);
        theGraphics.strokeRect(x, y, cardWidth, cardHeight);
    }
    
    //the tableau is the 7 columns of cards that comprise of the main area
    public void tableau() 
    {
       
    }
    
    //the foundations are the 4 piles upon which cards must be placed
    //to complete the game, starting with ace. One pile for each suit
    public void foundations() 
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
    public void stockPile() 
    {
        
    }
    
    //the waste pile is the pile of face up cards not put into play
    public void wastePile() 
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
