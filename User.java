package application;
import javafx.scene.input.*;

public class User {

    //get a default solitaire table
    SolitaireTable t = new SolitaireTable();
    
    public void mouseClickEvents(MouseEvent e) { 
        
        //find the x and y coordinates of a clicked mouse
        double x = e.getSceneX();
        double y = e.getSceneY(); 
        
        //determine if the x and y coordinates match the stock pile
        if(t.getStockBounds().contains(x,y)) {
            
        }
        
        //determine if the x and y coordinates match the waste pile
        if(t.getWasteBounds().contains(x,y)) {
            
        }
        
        //determine if the x and y coordinates match the foundations bounds
        for(int i = 0; i < 4; i++) {
            if(t.getFoundationsBounds().get(i).contains(x,y)) {
                
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
