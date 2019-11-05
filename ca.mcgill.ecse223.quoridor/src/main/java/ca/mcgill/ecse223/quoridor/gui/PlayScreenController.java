package ca.mcgill.ecse223.quoridor.gui;


import java.io.File;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.gui.Board.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;



public class PlayScreenController {

    @FXML private Button EndTurnLabel;
    @FXML private Label wallLabel;
    @FXML public ImageView WhitePlayerImage;
    @FXML public Pane pane;
    @FXML private Label timeLabel;
    @FXML private Button SaveGameLabel;
    @FXML public ImageView BlackPlayerImage;
    @FXML private Label abcd;
    @FXML public Rectangle wallStock;
    @FXML private Pane boardPane;
    
    public Board board;

    @FXML
    void buttonClickedSaveGame(ActionEvent event) throws FileNotFoundException
    {
    	FileChooser chooser = new FileChooser();
    	File f = chooser.showSaveDialog(QuoridorApplication.getPrimaryStage());
    	if (f != null)
			Controller.saveGame(f, true);
    } 
    
    public static PlayScreenController instance;
    
    public Rectangle wall;
    
    public static boolean isWallInHand = false;
    public static boolean noMoreWalls = false;

    @FXML
    MouseEvent event = null;
    
    @FXML
    public void createWall(MouseEvent e) {
    	
    	boolean wallsLeft=ca.mcgill.ecse223.quoridor.Controller.checkCurrentPlayerStock();
    	if(wallsLeft) {
    		 ca.mcgill.ecse223.quoridor.Controller.grabWallFromStock(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove());
    	}else {
    		wallLabel.setText("You have no walls left!");
    		wallLabel.setTextFill(Color.RED);
    		noMoreWalls = true;
    	}
    	wall = new Rectangle(97,17);
    	   wall.setFill(Color.GREY);
    	   dragWall(e);
    	   pane.getChildren().add(wall);
    	   wall.setMouseTransparent(true);
    	   event = e;
    	   pane.requestFocus();
    	
    	   updateWallCount();
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
       boolean wallsLeft=ca.mcgill.ecse223.quoridor.Controller.checkCurrentPlayerStock();
       if(wallsLeft) {
    	   ca.mcgill.ecse223.quoridor.Controller.dropWall((int)e.getX()+wallRectX, (int)e.getY()+wallRectY);
    	   isWallInHand = false;
       }
       updateWallCount();
    }
    
    @FXML
    public void onRotation(KeyEvent event){
    	if (!pane.getChildren().contains(wall))
    		return;
    	
        if(event.getCode().equals(KeyCode.R))
            ca.mcgill.ecse223.quoridor.Controller.flipWall(wall);
    }
    
    static int boardPaneX=0;
    static int boardPaneY=0;
    
    static int wallWidth;
    static int wallHeight;
    
    public void updateWallCount()
    {
    	if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
     	   wallLabel.setText(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() + "");
        else 
     	   wallLabel.setText(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size() + "");
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
    	wallLabel.setText("10");
    	System.out.println("Yes");
    }
}
