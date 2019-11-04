package fxml;


import java.net.URL;
import java.util.ResourceBundle;

import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import fxml.Board.Player;

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
    void buttonClickedSaveGame(ActionEvent event) {

    }    
    
    public static PlayScreenController instance;
    
    //gohar works here
    
//---------------------------------------------------------------------------------------------------------
    //david works here

    Direction direction = Direction.Horizontal;
    
    public Rectangle wall;

    @FXML
    MouseEvent event = null;
    
    WallMove wallMove;

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
       ca.mcgill.ecse223.quoridor.Controller.dropWall(Board.board,(int)e.getX()+wallRectX, (int)e.getY()+wallRectY, direction);
       direction=Direction.Horizontal;
    }
    
    @FXML
    public void onRotation(KeyEvent event){
        if(event.getCode().equals(KeyCode.R)) {
            ca.mcgill.ecse223.quoridor.Controller.flip_wall(wall);
        }
        
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
    	
    	for (Player player : board.players) {
    		player.setOnRemainingTimeChange(t ->
    		{
    			long minutes = (board.getActivePlayer().getRemainingTime()) / 60;
    			long seconds = (board.getActivePlayer().getRemainingTime()) % 60;
    			
    			if(seconds > 9)
    			timeLabel.setText(minutes + " : " + seconds);
    			else
    			timeLabel.setText(minutes + " : 0" + seconds);
    		});
    	}
    	
    	wallRectX=(int)wallStock.getLayoutX();
    	wallRectY=(int)wallStock.getLayoutY();
    	wallWidth=(int)wallStock.getWidth();
    	wallHeight=(int)wallStock.getHeight();
    	pane.getChildren().remove(BlackPlayerImage);
    	System.out.println("Yes");
    }
}
