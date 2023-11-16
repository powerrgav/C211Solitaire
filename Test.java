package application;
import java.util.ArrayList;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application {
    public static void main(String[] args) {
        testCard();
        launch(args);
    }

    //Tests functionality of the Card class.

    public static void testCard(){
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
    
  //display the stage, which is our "table"
    public void start(Stage primaryStage){
       
        //create the canvas to work on and display it
        Canvas canvas = new Canvas (800, 500);
        
        Pane thePane = new Pane(canvas);
        
        Scene scene = new Scene(thePane, Color.DARKGREEN);
        
        primaryStage.setTitle("Team 8 Solitaire");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}