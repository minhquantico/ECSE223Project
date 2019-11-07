package ca.mcgill.ecse223.quoridor.gui;


import java.io.File;
import java.io.FileNotFoundException;

import ca.mcgill.ecse223.quoridor.Controller;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.gui.Board.Player;
import ca.mcgill.ecse223.quoridor.gui.Board.Wall;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Tile;
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

    @FXML
    MouseEvent event = null;
    
    @FXML
    public void createWall(MouseEvent e)
    {
    	if(Controller.checkCurrentPlayerStock())
    	{
    		Controller.grabWallFromStock(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove());
			wall = new Rectangle(97,17);
			wall.setFill(Color.GREY);
			dragWall(e);
			pane.getChildren().add(wall);
			wall.setMouseTransparent(true);
			event = e;
			pane.requestFocus();
			
			updateWallCount();
    	}
    	else
    	{
    		wallLabel.setText("You have no walls left!");
    		wallLabel.setTextFill(Color.RED);
    	}
    }

    @FXML
    public void dragWall(MouseEvent e)
    {
    	if (!pane.getChildren().contains(wall))
    		return;
    	
       wall.setLayoutX(e.getSceneX() - wall.getWidth()/2); 
       wall.setLayoutY(e.getSceneY() - wall.getHeight()/2);
       
       int[] coord = getWallMoveTile((int)e.getX()+wallRectX, (int)e.getY()+wallRectY);
       if (coord != null)
       {
    	   Controller.setWallMoveCandidate(coord[0], coord[1], null);
    	   updateWallMoveVisualFeedback(true);
       }
       else
    	   updateWallMoveVisualFeedback(false);
    }
    
    public void updateWallMoveVisualFeedback(boolean onBoard)
    {
    	board.forEachWall(w ->
    	{
    		if (w.isSet()) w.set();
    		else w.unset();
    	});
    	
    	if (!onBoard)
    		return;
    	
    	int x = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getColumn()-1;
    	int y = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getTargetTile().getRow()-1;
    	boolean vertical = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection().equals(Direction.Vertical);
    	
    	if (Controller.initPosValidation())
    		(vertical ? board.vWall : board.hWall)[x][y].setPossible();
    	else
    		(vertical ? board.vWall : board.hWall)[x][y].setIllegal();
    }
    
    private int wallRectX;
    private int wallRectY;

    @FXML
    public void releaseWall(MouseEvent e)
    {
    	if (!pane.getChildren().contains(wall))
    		return;
    	
    	pane.getChildren().remove(wall);
    	if (getWallMoveTile((int)e.getX()+wallRectX, (int)e.getY()+wallRectY) != null)
    		Controller.dropWall(true);
    	else
    		Controller.cancelCandidate();
    	
    	updateWallMoveVisualFeedback(false);
    	updateWallCount();
    }
    
    @FXML
    public void onRotation(KeyEvent event)
    {
    	if (!pane.getChildren().contains(wall))
    		return;
        if(!event.getCode().equals(KeyCode.R))
        	return;
        
        ca.mcgill.ecse223.quoridor.Controller.flipWall();
        wall.setRotate(wall.getRotate() == 0 ? 90 : 0);
        //if (); TODO
    }
    
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
    	
    	board = new Board(2);
    	board.prefWidthProperty().bind(boardPane.widthProperty());
    	board.prefHeightProperty().bind(boardPane.heightProperty());
    	
    	boardPane.getChildren().add(board);
    	
    	for (Player player : board.players)
    		player.setOnRemainingTimeChange(t ->
    		{
    			long minutes = (board.getActivePlayer().getRemainingTime()) / 60;
    			long seconds = (board.getActivePlayer().getRemainingTime()) % 60;
    			
    			if(seconds > 9)
    				timeLabel.setText(minutes + " : " + seconds);
    			else
    				timeLabel.setText(minutes + " : 0" + seconds);
    		});
    	
    	wallRectX=(int)wallStock.getLayoutX();
    	wallRectY=(int)wallStock.getLayoutY();
    	pane.getChildren().remove(BlackPlayerImage);
    	wallLabel.setText("10");
    	wallLabel.setWrapText(true);
    	wallLabel.setMaxWidth(80);		// TODO
    }
    
    
    public int[] getWallMoveTile(int x, int y)
    {
		ca.mcgill.ecse223.quoridor.gui.Board board = PlayScreenController.instance.board;
		Direction direction = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection();

		int boardPaneX = 229;
		int boardPaneY = 110;
		int wallWidth = 106;
		int wallHeight = 29;

		Double minDist;
		Double dist;

		int chosenXCoord = 0;
		int chosenYCoord = 0;

		int deltaX = 0;
		int deltaY = 0;

		if (direction == Direction.Horizontal) {
			// check if drop location is close to horizontal wall location
			dist = (double) 1000;
			minDist = (double) 1100;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {

					int xcoord = (int) (boardPaneX + board.hWall[i][j].getLayoutX() + wallWidth / 2);
					int ycoord = (int) (boardPaneY + board.hWall[i][j].getLayoutY() + wallHeight / 2);

					deltaX = xcoord - x;
					deltaY = ycoord - y;

					// calculate distance from mmouse click to wall position
					dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

					if (Double.compare(dist, minDist) < 0) {
						minDist = dist;

						// xCoord of shortest distance wall

						chosenXCoord = xcoord;
						chosenYCoord = ycoord;
					}

					if (Math.abs(chosenXCoord - x) < 25 && Math.abs(chosenYCoord - y) < 25)
						return new int[] {i+1, j+1};
				}
			}
		} else {
			// check if drop location is close to horizontal wall location
			dist = (double) 1000;
			minDist = (double) 1100;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {

					int xcoord = (int) (boardPaneX + board.vWall[i][j].getLayoutX() + wallHeight / 2);
					int ycoord = (int) (boardPaneY + board.vWall[i][j].getLayoutY() + wallWidth / 2);

					deltaX = xcoord - x;
					deltaY = ycoord - y;

					// calculate distance from mmouse click to wall position
					dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

					if (Double.compare(dist, minDist) < 0) {
						minDist = dist;

						// xCoord of shortest distance wall

						chosenXCoord = xcoord;
						chosenYCoord = ycoord;
					}

					if (Math.abs(chosenXCoord - x) < 25 && Math.abs(chosenYCoord - y) < 25)
						return new int[] {i+1, j+1};
				}
			}
		}

		return null;
	}
}
