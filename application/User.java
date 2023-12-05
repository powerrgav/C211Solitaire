package application;
import javafx.scene.input.*;


public class User {

    //get a default solitaire table
    SolitaireTable t;

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
        }
        
        //determine if the x and y coordinates match the waste pile
        if(t.getWasteBounds().contains(x,y)) {
            System.out.println("Waste clicked!");
        }
        
        //determine if the x and y coordinates match the foundations bounds
        for(int i = 0; i < 4; i++) {
            if(t.getFoundationsBounds().get(i).contains(x,y)) {
                System.out.println("Foundation " + i + " clicked!");
            }
        }
        
        //determine if the x and y coordinates match the tableau bounds
        for(int i = 0; i < 7; i++) {   
            for(int j = 0; j < 10; j++) {
                //10 is just a placeholder for now. will be changed as functionality is added.
            }
        }
        
    }
    
    
}
