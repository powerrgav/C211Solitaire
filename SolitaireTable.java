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
import javafx.geometry.BoundingBox;
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
    private GraphicsContext theGraphics;
    private Random random = new Random();
    private static double cardWidth = 45;
    private static double cardHeight = 80;
    private static double spaceBetween = 30;
    
    //variables to store board and boundaries
    private  ArrayList <Card> deck = new ArrayList<>();
    private ArrayList<Card> waste = new ArrayList<>();
    private ArrayList<ArrayList<Card>> foundations = new ArrayList<>();
    private ArrayList<ArrayList<Card>> theTableau = new ArrayList<>();
    public ArrayList <BoundingBox> tableauBounds = new ArrayList<>();
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
    
        
        theGraphics.setTextAlign(TextAlignment.CENTER);
        theGraphics.setTextBaseline(VPos.CENTER);
        
        if(card.getColor() == 'B') 
        {
            theGraphics.setFill(Color.BLACK);
        }
        else 
            theGraphics.setFill(Color.RED);
        
        theGraphics.setFont(new Font("BOLD", 20));
        theGraphics.fillText(valueAsString, x+22.5, y+25, 25);
        
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
            for(int j = 0 ; j < i + 1; j++) 
            {
                stackOfCards.add(getTopCard());
            }
            theTableau.add(stackOfCards);
        }
        
        //draw the tableau
        for(int i = 0; i < 7; i++) 
        {
            double x = (cardWidth * 1.5 + spaceBetween) * i + 100;
            //create an arraylist that makes the vertical columns
            ArrayList<Card> tableauColumn = theTableau.get(i);
            
            if(tableauColumn.isEmpty()) {
                //this y value is a placeholder for now, subject to change
                emptyPlace(x, spaceBetween * 2 + cardHeight);
            }else 
            {
                for(int j = 0; j < tableauColumn.size(); j++) {
                    Card card = tableauColumn.get(j);
                    double y = spaceBetween * j + 175;
                    
                    cardFace(x, y, card);
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
}