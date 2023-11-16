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
    private final GraphicsContext theGraphics;
    private Random random = new Random();
    private final double cardWidth = 100, cardHeight = 150;
    
    //constructor to initialize the board
    public SolitaireTable(GraphicsContext theGraphics) {
        this.theGraphics = theGraphics;
    }
    
    //the tableau is the 7 columns of cards that comprise of the main area
    public static void tableau() 
    {
        
    }
    
    //the foundations are the 4 piles upon which cards must be placed
    //to complete the game, starting with ace. One pile for each suit
    public static void foundations() 
    {
        
    }
    
    //the stock is the pile of cards not currently in play, which are 
    //brought into play according to the rules.
    public static void stockPile() 
    {
        
    }
    
    //the waste pile is the pile of face up cards not put into play
    public static void wastePile() {
        
    }
    
    //---------------------------------------------------------------------
    //separating the get/set methods from the rest of the class for easier reading
    //---------------------------------------------------------------------
    
    /**
     * @return the tableauColumns
     */
    public static int getTableauColumns() {
        return tableauColumns;
    }

    /**
     * @param tableauColumns the tableauColumns to set
     */
    public static void setTableauColumns(int tableauColumns) {
        SolitaireTable.tableauColumns = tableauColumns;
    }

    /**
     * @return the foundations
     */
    public static int getFoundations() {
        return foundations;
    }

    /**
     * @param foundations the foundations to set
     */
    public static void setFoundations(int foundations) {
        SolitaireTable.foundations = foundations;
    }
    

}
