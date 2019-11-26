package ca.mcgill.ecse223.quoridor.gui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Controller;
import ca.mcgill.ecse223.quoridor.gui.Board.Player;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;



public class PlayScreenController {

    @FXML private Label wallLabel;
    @FXML public ImageView statusImage;
    @FXML public Pane pane;
    @FXML private Label timeLabel;
    @FXML private Button saveGame;
    @FXML private Button savePosition;
    @FXML public Rectangle wallStock;
    @FXML private Pane boardPane;
    @FXML private Button continueGame;
    @FXML private Button stepNext;
    @FXML private Button jumpEnd;
    @FXML private Button stepPrevious;
    @FXML private Button jumpBeginning;
    @FXML private Pane replayPane;
    
    public Board board;
    public Pane wall;
    
    @FXML
    void stepPrevious() {
    	assertReplay();
    	Controller.stepBackwards();
    	Controller.updateStatusGUI();
    	board.loadFromModel();
    }

    @FXML
    void stepNext() {
    	assertReplay();
    	Controller.stepForwards();
    	Controller.updateStatusGUI();
    	board.loadFromModel();
    }

    @FXML
    void jumpBeginning() {
    	assertReplay();
    	Controller.jumpToStartPos();
    	Controller.updateStatusGUI();
    	board.loadFromModel();
    }
    
    @FXML
    void jumpEnd() {
    	assertReplay();
    	Controller.jumpToFinalPos();
    	Controller.updateStatusGUI();
    	board.loadFromModel();
    }
    
    void assertReplay() throws AssertionError
    {
    	if (!QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().equals(GameStatus.Replay))
    		throw new AssertionError("Game not in replay mode!");
    }
    
    @FXML
    void continueGame() {
    	replayPane.setVisible(false);
    	Controller.jumpToFinalPos();
    	board.loadFromModel();
    	ca.mcgill.ecse223.quoridor.controller.Controller.StartClock();
    	board.getActivePlayer().getOnRemainingTimeChange().accept(board.getActivePlayer().getRemainingTime());
    	QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Running);
		Controller.updateGameStatus();
    	Controller.updateStatusGUI();
    	if (isRunning())
    	{
    		board.setOnGameEnded(() ->
    		{
    			replayPane.setVisible(true);
        		QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Replay);
    		});
    		board.startGame();
    	}
    }
    
    @FXML
    void resign()
    {
    	if (isRunning())
    	{
    		Controller.resign();
    		if (board.getActivePlayer().isNetwork())
    			try { board.getActivePlayer().disconnect(); }
    			catch (IOException ex) { System.err.println(ex.getMessage()); }
    	}
    }
    
    @FXML
    void savePosition() throws FileNotFoundException
    {
    	FileChooser chooser = new FileChooser();
    	File f = chooser.showSaveDialog(QuoridorApplication.getPrimaryStage());
    	if (f != null)
			Controller.savePosition(f, true);
    }
    
    @FXML
    void saveGame() throws FileNotFoundException
    {
    	FileChooser chooser = new FileChooser();
    	File f = chooser.showSaveDialog(QuoridorApplication.getPrimaryStage());
    	if (f != null)
			Controller.saveGame(f, true);
    }
    
    public static PlayScreenController instance;
    
    @FXML
    public void createWall(MouseEvent e)
    {
    	if (!isRunning())
    		return;		// Nothing to do here
    	
    	if (!board.getActivePlayer().isUser())
    	{
    		wallLabel.setText(
    				board.getActivePlayer().isComputer() ?
    						"Wait for computer!" :
    						"Wait for " + QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().getUser().getName() + "!");
    		wallLabel.setTextFill(Color.RED);
    		return;
    	}
    	
    	if (!Controller.checkCurrentPlayerStock())
    	{
    		wallLabel.setText("Empty stock!");
    		wallLabel.setTextFill(Color.RED);
    		return;
    	}
    	
		Controller.grabWallFromStock(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove());
		wall.setRotate(0);
		pane.getChildren().add(wall);
		pane.requestFocus();
		
		dragWall(e);
		updateWallCount();
    }

    @FXML
    public void dragWall(MouseEvent e)
    {
    	if (!pane.getChildren().contains(wall))
    		return;
    	
       wall.setLayoutX(e.getSceneX() - wall.getPrefWidth()/2); 
       wall.setLayoutY(e.getSceneY() - wall.getPrefHeight()/2);
       
       int[] coord = getWallMoveTile();
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
    	
    	if (Controller.isValidWallMove())
    		(vertical ? board.vWall : board.hWall)[x][y].setPossible();
    	else
    		(vertical ? board.vWall : board.hWall)[x][y].setIllegal();
    }

    @FXML
    public void releaseWall(MouseEvent e)
    {
    	if (!pane.getChildren().contains(wall))
    		return;
    	
    	pane.getChildren().remove(wall);
    	
    	if (getWallMoveTile() != null)
    		Controller.dropWall();
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
        
        ca.mcgill.ecse223.quoridor.controller.Controller.flipWall();
        wall.setRotate(wall.getRotate() == 0 ? 90 : 0);
        
        if (getWallMoveTile() != null)
        	updateWallMoveVisualFeedback(true);
    }
    
    public void updateWallCount()
    {
    	if(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite())
     	   wallLabel.setText(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock().size() + "");
        else 
     	   wallLabel.setText(QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock().size() + "");
    	wallLabel.setTextFill(Color.BLACK);
    }
 

    public static final Image whiteTurn = new Image(QuoridorApplication.class.getClassLoader().getResourceAsStream("White_Player_Image.png"));
    public static final Image blackTurn = new Image(QuoridorApplication.class.getClassLoader().getResourceAsStream("Black_Player_Image.png"));
    public static final Image whiteWon = new Image(QuoridorApplication.class.getClassLoader().getResourceAsStream("WhiteWon.png"));
    public static final Image blackWon = new Image(QuoridorApplication.class.getClassLoader().getResourceAsStream("BlackWon.png"));
    public static final Image draw = new Image(QuoridorApplication.class.getClassLoader().getResourceAsStream("DRAW.png"));
    
    @FXML
    public void initialize()
    {
    	instance = this;
    	
    	board = new Board(
    			QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getUser().getName(),
    			QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getUser().getName());
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
    	
    	wall = new Pane();
    	wall.prefWidthProperty().bind(board.hWall[0][0].prefWidthProperty());
    	wall.prefHeightProperty().bind(board.hWall[0][0].prefHeightProperty());
    	wall.setBackground(board.hWall[0][0].SET);
    	
    	wallLabel.setText("10");
    	wallLabel.setWrapText(true);
    	wallLabel.setMaxWidth(80);		// TODO
    	
    	replayPane.setVisible(QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().equals(GameStatus.Replay));
    	if (!replayPane.isVisible())
    		continueGame();
    }
    
    private boolean isRunning() { return QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().equals(GameStatus.Running); }
    
    public int[] getWallMoveTile()
    {
		ca.mcgill.ecse223.quoridor.gui.Board board = PlayScreenController.instance.board;
		Direction direction = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallDirection();

		double x = wall.getLayoutX() + wall.getWidth()/2;
		double y = wall.getLayoutY() + wall.getHeight()/2;
		
		int boardPaneX = 229;
		int boardPaneY = 110;
		int wallWidth = 106;
		int wallHeight = 29;

		Double minDist;
		Double dist;

		int chosenXCoord = 0;
		int chosenYCoord = 0;

		double deltaX = 0;
		double deltaY = 0;

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
