package fxml;


import java.net.URL;
import java.util.ResourceBundle;

import ca.mcgill.ecse223.quoridor.model.Direction;

import static java.lang.Math.sqrt;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;



public class PlayScreenController {

    @FXML
    private Button EndTurnLabel;

    @FXML
    private Label wallLabel;

    @FXML
    public ImageView WhitePlayerImage;

    @FXML
    public Pane pane;

    @FXML
    private Label timeLabel;

    @FXML
    private Button SaveGameLabel;

    @FXML
    public ImageView BlackPlayerImage;
    
    @FXML
    private Label abcd;
    
    @FXML
    public Rectangle wallStock;

    @FXML
    private Pane boardPane;
    
    public Board board;

    @FXML
    void buttonClickedEndTurn(ActionEvent event) {

    }

    @FXML
    void buttonClickedSaveGame(ActionEvent event) {

    }    
    
    public static PlayScreenController instance;
    
    //gohar works here
    
//---------------------------------------------------------------------------------------------------------
    //david works here

    Direction direction = Direction.Horizontal;
    @FXML
    Rectangle wall;

    @FXML
    MouseEvent event = null;

    @FXML
    public void createWall(MouseEvent e) {
       wall = new Rectangle(97,17);
    	   wall.setFill(Color.GREY);
    	   dragWall(e);
    	   pane.getChildren().add(wall);
    	   wall.setMouseTransparent(true);
    	   event = e;
    	   pane.requestFocus();
    }

    @FXML
    public void dragWall(MouseEvent e) {
       wall.setLayoutX(e.getSceneX() - wall.getWidth()/2); 
    	   wall.setLayoutY(e.getSceneY() - wall.getHeight()/2);
    }
    
static int wallRectX;
static int wallRectY;


    @FXML
    public void releaseWall(MouseEvent e) {
     //  event = null;
       pane.getChildren().remove(wall);
       selectWallForDrop(Board.board,(int)e.getX()+wallRectX, (int)e.getY()+wallRectY, direction);
       direction=Direction.Horizontal;
    }
    
    @FXML
    public void onRotation(KeyEvent event){
        if(event.getCode().equals(KeyCode.R))
            wall.setRotate(wall.getRotate() + 90);
        if(direction==Direction.Horizontal){
        	direction=Direction.Vertical;
        }else {
        	direction=Direction.Horizontal;
        }	
    }
    
    static int boardPaneX=0;
    static int boardPaneY=0;
    
    static int wallWidth;
    static int wallHeight;
    
    //helper method for placing the wall
    public static void selectWallForDrop(Board board, int x, int y, Direction direction) {
    	
    	
    	int walli=0;
    	int wallj=0;
    	Double minDist;
    	Double dist;
    	
    	int chosenXCoord=0;
    	int chosenYCoord=0;
    	
    	int deltaX=0;
    	int deltaY=0;
    	
    	if(direction==direction.Horizontal) {
    		//check if drop location is close to horizontal wall location
    		dist=(double)1000;
    		minDist=(double)1100;
    		for(int i=0; i<8;i++) {
    			for(int j=0; j<8; j++) {
    				
    				
    				
    				int xcoord =(int)(boardPaneX+board.hWall[i][j].getLayoutX()+wallWidth/2);
    				int ycoord=(int)(boardPaneY+board.hWall[i][j].getLayoutY()+wallHeight/2);
    		
    				
    				deltaX=xcoord-x;
    				deltaY=ycoord-y;
    				
    				//calculate distance from mmouse click to wall position
    				dist=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    				
    				if(Double.compare(dist,minDist)<0) { 
    					minDist=dist;
    					walli=i;
    					wallj=j;
    					
    					//xCoord of shortest distance wall
    					
    					chosenXCoord=xcoord;
    					chosenYCoord=ycoord;
    				}
    				
    				if(25>(chosenXCoord - x) && -25<(chosenXCoord-x) && (chosenYCoord-y)<25 && (chosenYCoord-y)>-25) {
    					board.hWall[walli][wallj].set();
    			
    					return;
    				
    				}
    			}
    		}
    	}else {
    		//check if drop location is close to horizontal wall location
    		dist=(double)1000;
    		minDist=(double)1100;
    		for(int i=0; i<8;i++) {
    			for(int j=0; j<8; j++) {
    				
    				
    				
    				int xcoord =(int)(boardPaneX+board.vWall[i][j].getLayoutX()+wallHeight/2);
    				int ycoord=(int)(boardPaneY+board.vWall[i][j].getLayoutY()+wallWidth/2);
    				
    				deltaX=xcoord-x;
    				deltaY=ycoord-y;
    				
    				//calculate distance from mmouse click to wall position
    				dist=Math.sqrt(deltaX*deltaX+deltaY*deltaY);
    				
    				if(Double.compare(dist,minDist)<0) { 
    					minDist=dist;
    					walli=i;
    					wallj=j;
    					
    					//xCoord of shortest distance wall
    					
    					chosenXCoord=xcoord;
    					chosenYCoord=ycoord;
    				}
    				
    				if(25>(chosenXCoord - x) && -25<(chosenXCoord-x) && (chosenYCoord-y)<25 && (chosenYCoord-y)>-25) {
    					board.vWall[walli][wallj].set();
    					return;
    				
    				}
    			}
    		}}
    }
    
    @FXML
    public void initialize()
    {
    	instance = this;
    	
    	board = new Board(false, true);
    	board.prefWidthProperty().bind(boardPane.widthProperty());
    	board.prefHeightProperty().bind(boardPane.heightProperty());
    	
    	boardPane.getChildren().add(board);
    	
    	boardPaneX=(int)boardPane.getLayoutX();
    	boardPaneY=(int)boardPane.getLayoutY();
    	
    	wallRectX=(int)wallStock.getLayoutX();
    	wallRectY=(int)wallStock.getLayoutY();
    	wallWidth=(int)wallStock.getWidth();
    	wallHeight=(int)wallStock.getHeight();
    	System.out.println("Yes");
    }
}
