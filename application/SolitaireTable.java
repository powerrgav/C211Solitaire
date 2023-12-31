package application;
import java.awt.event.ActionListener;
import java.security.cert.TrustAnchor;
import java.util.*;
import javafx.scene.text.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;

import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.metal.MetalSliderUI;
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

    private ArrayList<Card>[] decksAsData;

    private BoundingBox[] decksAsObjects;
    
    //constructor to initialize the board
    SolitaireTable(GraphicsContext theGraphics) {
        this.theGraphics = theGraphics;
        for(int i = 0; i < 4; i++)
            foundations.add(new ArrayList<>());
        shuffledDeck();
        foundations();
        wastePile();
        tableau();
        stockPile();
        setDecksAsData();
        redrawScreen();

    }
    
    //default constructor
    SolitaireTable(){
        
    }

    private void setDecksAsData(){
        decksAsData = new ArrayList[13];
        decksAsData[0] = deck;
        decksAsData[1] = waste;
        decksAsData[2] = foundations.get(0);
        decksAsData[3] = foundations.get(1);
        decksAsData[4] = foundations.get(2);
        decksAsData[5] = foundations.get(3);
        decksAsData[6] = theTableau.get(0);
        decksAsData[7] = theTableau.get(1);
        decksAsData[8] = theTableau.get(2);
        decksAsData[9] = theTableau.get(3);
        decksAsData[10] = theTableau.get(4);
        decksAsData[11] = theTableau.get(5);
        decksAsData[12] = theTableau.get(6);

    }

    private void setDecksAsObjects(){
        decksAsObjects = new BoundingBox[13];
        decksAsObjects[0] = stockBounds;
        decksAsObjects[1] = wasteBounds;
        decksAsObjects[2] = wasteBounds;
        decksAsObjects[3] = foundationsBounds.get(0);
        decksAsObjects[4] = foundationsBounds.get(1);
        decksAsObjects[5] = foundationsBounds.get(2);
        decksAsObjects[6] = foundationsBounds.get(3);
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
        String valueAsString = card.getFaceValue();
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


        //if the card is selected, draw a border indicating it.
        if(card.getSelected()) {
            theGraphics.setStroke(Color.GOLD);
            theGraphics.setLineWidth(5);
            //theGraphics.fillRect(x, y, cardWidth, cardHeight);
            theGraphics.strokeRect(x, y, cardWidth, cardHeight);
        }

    }
    
    //the tableau is the 7 columns of cards that comprise of the main area
    //at the start of the game, there are 28 cards on the tableau
    private void tableau() 
    {
        double x;
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
            x = 100 * i + 75;
            //create an arraylist that makes the vertical columns
            ArrayList<Card> tableauColumn = theTableau.get(i);
            
            if(tableauColumn.isEmpty()) {
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
                tableauBounds.get(j).add(new BoundingBox(100 * i + 75, spaceBetween * j + 175, cardWidth, cardHeight ));
            }
        }
    }


    public void redrawTheTableau() {

        double x;
        theGraphics.setFill(Color.DARKGREEN);
        theGraphics.fillRect(0, 150, 800, 500);


        //draw the tableau
        for (int i = 0; i < 7; i++) {
            x = 100 * i + 75;
            //create an arraylist that makes the vertical columns
            ArrayList<Card> tableauColumn = theTableau.get(i);

            if (tableauColumn.isEmpty()) {
                emptyPlace(x, spaceBetween * 2 + cardHeight + 35);
            } else {
                for (int j = 0; j < tableauColumn.size(); j++) {
                    //create two cards. one to be drawn as revealed and one hidden
                    Card card = tableauColumn.get(j);
                    Card topCard = tableauColumn.get(tableauColumn.size() - 1);
                    topCard.setRevealed();

                    double y = spaceBetween * j + 175;

                    //add an assert statement to make sure we're on the right track
                    assert (!card.getRevealed());

                    //if a card is marked as revealed, show its face. if not, its back.
                    if (card.getRevealed()) {
                        cardFace(x, y, card);
                    } else {
                        cardBack(x, y);
                    }
                }
            }
        }
    }

        //declare the bounds for each tableau card
        //bounding box is declared by (x , y , width , height)
        //the x and y for each boundary are the same as x and y
        //in the previous for loop in this method
        //for(int i = 0; i < 7; i++) {
    
    //the foundations are the 4 piles upon which cards must be placed
    //to complete the game, starting with ace. One pile for each suit
    private void foundations() 
    {
        double x;
        
        //start the game with 4 empty places at the top right of the canvas
        for(int i = 0; i < 4; i++) {
            
          //declare clickable area in the foundations
            foundationsBounds.add(new BoundingBox(100 *i + 400, spaceBetween, cardWidth, cardHeight));
            
            x = 100 *i + 400;
            
            emptyPlace(x,50);
        }
        
        //redraw the foundations after a change is made. pretty much the same as the for loop above
        //but this time draws a card face if the foundation is not empty.
        for(int i = 0; i < 4; i++) {
            x = 100 *i + 400;
            
            ArrayList<Card> redrawnFoundation = foundations.get(i);
            
            if(redrawnFoundation.isEmpty()) {
                emptyPlace(x,50);
            }else {
                cardFace(x, 50, redrawnFoundation.get(redrawnFoundation.size()-1));
            }
        }
    }
    
    //the stock is the pile of cards not currently in play, which are 
    //brought into play according to the rules.
    //at the start of the game, there are 24 cards in the stock pile
    private void stockPile() 
    {
        //draws an empty place is the deck is empty, a card back if there are cards remianing
        if(deck.isEmpty()) {
            //redraw over the card back bug
            theGraphics.setFill(Color.DARKGREEN);
            theGraphics.fillRect(45, 45, cardWidth+15, cardHeight+15);
            emptyPlace(50,50);

            //System.out.println("End of deck");
        }else if(!deck.get(deck.size() - 1).getRevealed()) {
            cardBack(50,50);
        }else{
            cardFace(50, 50, deck.get(deck.size() - 1));
        }
    }
    
    //the waste pile is the pile of face up cards not put into play
    private void wastePile() 
    {
        //draws an empty place is the deck is empty, a card face if there are cards remaining
        if(waste.isEmpty()) {
            //System.out.println("Waste pile is empty");
            emptyPlace(150,50);
        }else {
            cardFace(150,50, waste.get(waste.size() - 1));
        }
    }

    public boolean isPileEmpty(int pileToCheck){
            if(pileToCheck == 0)
                return deck.isEmpty();
            else if (pileToCheck == 1)
                return waste.isEmpty();
            else if (pileToCheck >= 2 && pileToCheck <= 5)
                return foundations.get(pileToCheck - 2).isEmpty();
            else if(pileToCheck >= 6 && pileToCheck < 13 )
                return theTableau.get(pileToCheck - 6).isEmpty();
            else{
                System.out.println("Something went horribly wrong!");
                return false;
            }
    }

    public void flipCard(Integer intendedPile){
        //System.out.println("intendedPile is: " + intendedPile);
        if(intendedPile == 0){
            if(!deck.isEmpty()) {
                decksAsData[intendedPile].get(decksAsData[intendedPile].size() - 1).setRevealed();

                stockPile();
            }
        }
        else if (intendedPile > 5 && intendedPile < 13){
            decksAsData[intendedPile].get(decksAsData[intendedPile].size() - 1).setRevealed();
            redrawScreen();

        }
    }

    public boolean selectCards(Integer intendedPile, Integer cardsFromEnd, boolean deselect){

        int cardToSelect = decksAsData[intendedPile].size() - cardsFromEnd;
        System.out.println("Card " + cardToSelect + " selected.");

        if(!deselect){
            if(decksAsData[intendedPile].get(cardToSelect).getRevealed()) {
                decksAsData[intendedPile].get(cardToSelect).setSelected();
                return true;
            }
            else {
                selectCards(intendedPile, cardsFromEnd, true);
                return false;
            }
        }
        else{
            for(Card curCard : decksAsData[intendedPile]){
                curCard.setSelectedFalse();
            }
        }
        redrawScreen();
        return false;
    }

    public Card checkTopCard(Integer intendedPile){
        if(!decksAsData[intendedPile].isEmpty())
            return decksAsData[intendedPile].get(decksAsData[intendedPile].size() - 1);
        else
            return null;
    }

    public boolean pileHasCards(Integer intendedPile){
        return !decksAsData[intendedPile].isEmpty();
    }

    public boolean cardVisible(Integer intendedPile){
        return decksAsData[intendedPile].get(decksAsData[intendedPile].size() - 1).getRevealed();
    }

    public int pileSize(Integer intendedPile){
        return decksAsData[intendedPile].size();
    }

    public Card checkCardInStack(Integer intendedPile, Integer index){
        return decksAsData[intendedPile].get(index);
    }

    public void moveCardAcrossPiles(Integer sendingPile, Integer receivingPile){
        decksAsData[receivingPile].add(decksAsData[sendingPile].remove(decksAsData[sendingPile].size() - 1));
        deselectAll();
        redrawScreen();
    }

    public boolean selectNextCard(Integer intendedPile, int desiredIndex){

        ArrayList<Card> curPile = decksAsData[intendedPile];
        ListIterator<Card> reverseBoi = curPile.listIterator(curPile.size());

        if(reverseBoi.hasPrevious() && reverseBoi.previous().getRevealed())
            reverseBoi.next().setSelected();
        else {
            System.out.println("we can select no more cards");
            return false;
        }

        redrawScreen();
        return true;
    }

    public void movePartialPile(Integer sendPile, Integer receivePile, int sendingIndex){
        int lastCardRemoved = decksAsData[sendPile].size() - sendingIndex;
        while(decksAsData[sendPile].size() > lastCardRemoved){
            decksAsData[sendPile].get(lastCardRemoved).setSelectedFalse();
            decksAsData[receivePile].add(decksAsData[sendPile].remove(lastCardRemoved));
            deselectAll();
            redrawScreen();
        }
    }

    public void movePileAcrossPile(Integer sendingPile, Integer receivingPile){
        while(!decksAsData[sendingPile].isEmpty())
            decksAsData[receivingPile].add(decksAsData[receivingPile].remove(0));
        redrawScreen();
    }

    public boolean matchingSuit(Integer sendingPile, Integer receivingPile){
        return checkTopCard(sendingPile).suit == checkTopCard(receivingPile).suit;
    }

    public boolean matchingColor(Integer sendingPile, Integer receivingPile){
        return checkTopCard(sendingPile).color == checkTopCard(receivingPile).color;
    }

    public boolean inOrder(Integer sendingPile, Integer receivingPile, boolean ascending){
        if(ascending)
            return (checkTopCard(sendingPile).value - checkTopCard(receivingPile).value == 1);
        else
            return (checkTopCard(sendingPile).value - checkTopCard(receivingPile).value == -1);
    }

    public void deselectAll(){
        for(ArrayList<Card> curPile : decksAsData)
            for(Card curCard : curPile)
                curCard.setSelectedFalse();
        redrawScreen();
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
    
    public void redrawScreen(){
        redrawTheTableau();
        foundations();
        stockPile();
        wastePile();
        String firstLine = "";
        if(!deck.isEmpty())
            firstLine += deck.get(deck.size() - 1).getFace() + "  ";
        else
            firstLine += "[]   ";
        if(!waste.isEmpty())
            firstLine += waste.get(waste.size() - 1).getFace() + "     ";
        else
            firstLine += "[]     ";
        for(ArrayList<Card> curPilie : foundations){
            if(!curPilie.isEmpty())
                firstLine += curPilie.get(curPilie.size() - 1).getFace() + "  ";
            else
                firstLine += "[]  ";
        }
        System.out.println(firstLine);
        System.out.println();


        /*  while cards to display
        *   for each pile
        *       print face or mystery or empty space of curCard
        *       print special spot if space is empty
        *       print newline when end of pile is reach
        *       if curCard < curPile.size keep going.
        *
        *
        * */
        for(int card = 0; card < 19; card++){
            for(int pile = 0; pile < theTableau.size(); pile++){
                String cardText = (theTableau.get(pile).size() > card) ? theTableau.get(pile).get(card).getFace() + "  " : "    ";
                System.out.print(cardText);

            }
            System.out.println();
        }

        /*
        for(ArrayList<Card> pile : theTableau){
            for(Card curCard : pile)
                System.out.print(curCard.getFace() + " ");
            System.out.println();
        } */
        System.out.println();
    }



}//end class