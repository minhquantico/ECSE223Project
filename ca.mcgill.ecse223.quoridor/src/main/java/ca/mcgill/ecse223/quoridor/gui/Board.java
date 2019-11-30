
package ca.mcgill.ecse223.quoridor.gui;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Controller;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
//import tools.StopWatch;
import javafx.scene.text.Text;

// TODO: set isHackable to false <---- IMPORTANT
public class Board extends Pane
{
	public final int COLS = 9;
	public final int ROWS = 9;
	public double WALLSIZE = 0.30;
	
	public Player[] players;
	public int activePlayer;
	
	private Cell[][] cells = new Cell[COLS][ROWS];
	public Wall[][] vWall = new Wall[COLS-1][ROWS-1];
	public Wall[][] hWall = new Wall[COLS-1][ROWS-1];
	
	private volatile boolean waitingForMove = false;
	private Runnable onGameEnded;
	
	private Thread game = new Thread(() -> {
		try
		{
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Initializing);
			for (Player p : players)
				if (p.isNetwork())
				{
					Platform.runLater(() -> this.getChildren().add(new LoadingPane(p)));
					p.connect();
					Platform.runLater(() -> this.getChildren().removeIf(c -> c instanceof LoadingPane));
				}
			
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.Running);
			while (QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus().equals(GameStatus.Running))
			{
				players[activePlayer].takeTurn();
				Platform.runLater(() ->
				{
					Controller.updateStatusGUI();
					this.loadFromModel();
					synchronized (Board.this) { Board.this.notify(); }
				});
				synchronized (Board.this) { Board.this.wait(); }
			}
			
			for (Player p : players)
				if (p.isNetwork())
					p.disconnect();
		}
		catch (InterruptedException ex) { System.err.println("Interrupted???"); }
		catch (Exception ex) { ex.printStackTrace(); }
		
		onGameEnded.run();
	});
	
	
	
	public Board(String whiteUser, String blackUser)
	{
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		for (int i = 0; i < COLS; i++)
			for (int j = 0; j < ROWS; j++)
			{
				this.getChildren().add(cells[i][j] = new Cell(i, j));
				if (i != COLS-1 && j != ROWS-1)
				{
					this.getChildren().add(vWall[i][j] = new Wall(i, j, true));
					this.getChildren().add(hWall[i][j] = new Wall(i, j, false));
				}
			}
		
		this.players = new Player[2];
		if (players.length >= 1)
			this.players[0] = new Player(2, Color.GREEN);
		if (players.length >= 2)
			this.players[1] = new Player(0, Color.RED);
//		if (players.length >= 3)
//			this.players[2] = new Player(2, Color.ORANGE);
//		if (players.length >= 4)
//			this.players[3] = new Player(0, Color.PURPLE);
		this.activePlayer = 0;
		
		if (whiteUser.equals("Computer"))
    		players[0].setComputer(true);
    	if (blackUser.equals("Computer"))
    		players[1].setComputer(true);
    	
    	try
    	{
	    	if (Controller.isIPAdress(whiteUser))
	    		players[0].setNetwork(InetAddress.getByName(whiteUser));
	    	if (Controller.isIPAdress(blackUser))
	    		players[1].setNetwork(InetAddress.getByName(blackUser));
    	}
    	catch (IOException ex) { assert false; }
	}
	
	public boolean isWaitingForMove() { return waitingForMove; }
	public void setOnGameEnded(Runnable r) { this.onGameEnded = r; }
	
	public void loadFromModel()
	{
		GamePosition curr = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		players[0].moveTo(cells[curr.getWhitePosition().getTile().getColumn()-1][curr.getWhitePosition().getTile().getRow()-1]);
		players[1].moveTo(cells[curr.getBlackPosition().getTile().getColumn()-1][curr.getBlackPosition().getTile().getRow()-1]);
		
		forEachWall(w -> w.unset());
		for (ca.mcgill.ecse223.quoridor.model.Wall wall : curr.getWhiteWallsOnBoard())
			(wall.getMove().getWallDirection().equals(Direction.Horizontal) ? hWall : vWall)
					[wall.getMove().getTargetTile().getColumn()-1][wall.getMove().getTargetTile().getRow()-1]
					.set();
		
		for (ca.mcgill.ecse223.quoridor.model.Wall wall : curr.getBlackWallsOnBoard())
			(wall.getMove().getWallDirection().equals(Direction.Horizontal) ? hWall : vWall)
					[wall.getMove().getTargetTile().getColumn()-1][wall.getMove().getTargetTile().getRow()-1]
					.set();
		
		activePlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove().hasGameAsWhite() ? 0 : 1;
		forEachCell(c -> c.setSelected(false));
	}
	
	public Player getActivePlayer() { return this.players[activePlayer]; }
	
	public void startGame() { loadFromModel(); if (players.length > 0) this.game.start(); }
	public void setGameLoop(Runnable r) { game = new Thread(r); }
	
	void forEachCell(Consumer<? super Cell> action)
	{
		for (int i = 0; i < ROWS * COLS; i++)
			action.accept(cells[i/ROWS][i%ROWS]);
	}
	
	void forEachWall(Consumer<? super Wall> action)
	{
		boolean vertical = false;
		for (int i = 0; i < (ROWS-1) * (COLS-1); i += vertical ? 1 : 0, vertical = !vertical)
			action.accept((vertical ? hWall : vWall)[i/(ROWS-1)][i%(ROWS-1)]);
	}
	
	public class Cell extends Pane implements Move
	{
		public final Background DEFAULT = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		public final Background SELECTED = new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY));
		public final Background RECOMMENDED = new Background(new BackgroundFill(Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY));
		public final int x, y;
		
		// Used for Dijkstra's algorithm
		boolean visited;
		int distance;
		
		public Cell(int x, int y)
		{
			this.x = x;
			this.y = y;
			this.setBackground(DEFAULT);
			this.prefWidthProperty().bind(Board.this.widthProperty().divide(Board.this.COLS).multiply(1 - Board.this.WALLSIZE));
			this.prefHeightProperty().bind(Board.this.heightProperty().divide(Board.this.ROWS).multiply(1 - Board.this.WALLSIZE));
			this.layoutXProperty().bind(Board.this.widthProperty().divide(Board.this.COLS).multiply(x + Board.this.WALLSIZE/2));
			this.layoutYProperty().bind(Board.this.heightProperty().divide(Board.this.ROWS).multiply(y + Board.this.WALLSIZE/2));
		}
		
		public boolean isSelected() { return this.getBackground() == SELECTED; }
		public void setSelected(boolean selected)
		{
			this.setBackground(selected ? SELECTED : DEFAULT);
			this.setOnMouseClicked(selected ? e -> this.doMove() : null);
		}
		
		public boolean hasPlayer()
		{
			for (Player p : players)
				if (p.position == this)
					return true;
			return false;
		}
		
		public Cell direction(int d)
		{
			switch ((d + 4) % 4)
			{
			case 0: return y != 0 ? Board.this.cells[x][y-1] : null;
			case 1: return x != COLS-1 ? Board.this.cells[x+1][y] : null;
			case 2: return y != ROWS-1 ? Board.this.cells[x][y+1] : null;
			case 3: return x != 0 ? Board.this.cells[x-1][y] : null;
			default: return null;
			}
		}
		
		public boolean isBlockedDirection(int d)
		{
			switch ((d + 4) % 4)
			{
			case 0: return y == 0 || x != 0 && hWall[x-1][y-1].isSet() || x != COLS-1 && hWall[x][y-1].isSet();
			case 1: return x == COLS-1 || y != 0 && vWall[x][y-1].isSet() || y != ROWS-1 && vWall[x][y].isSet();
			case 2: return y == ROWS-1 || x != 0 && hWall[x-1][y].isSet() || x != COLS-1 && hWall[x][y].isSet();
			case 3: return x == 0 || y != 0 && vWall[x-1][y-1].isSet() || y != ROWS-1 && vWall[x-1][y].isSet();
			default: return false;
			}
		}
		
		@Override
		public void doMove()
		{
			Controller.doPawnMoveStateMachine(this.x+1, this.y+1);
		}
		
		@Override
		public void recommend()
		{
			this.setBackground(RECOMMENDED);
		}
	}
	
	public class Wall extends Pane implements Move
	{
		public Background DEFAULT = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
		public Background SET = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
		public Background POSSIBLE = new Background(new BackgroundFill(Color.rgb(0x80, 0x80, 0x80, 0.5), CornerRadii.EMPTY, Insets.EMPTY));
		public Background ILLEGAL = new Background(new BackgroundFill(Color.rgb(0xFF, 0x00, 0x00, 0.5), CornerRadii.EMPTY, Insets.EMPTY));
		
		private int x, y; 
		public boolean vertical, set = false;
		
		public int getX() { return this.x; }
		public int getY() { return this.y; }
		
		public Wall(int x, int y, boolean vertical)
		{
			this.x = x;
			this.y = y;
			this.vertical = vertical;
			
			if (vertical)
			{
				this.layoutXProperty().bind(Board.this.widthProperty().divide(Board.this.COLS).multiply((x+1) - Board.this.WALLSIZE/2));
				this.layoutYProperty().bind(Board.this.heightProperty().divide(Board.this.ROWS).multiply(y + Board.this.WALLSIZE/2));			
				this.prefWidthProperty().bind(Board.this.widthProperty().divide(Board.this.COLS).multiply(Board.this.WALLSIZE));
				this.prefHeightProperty().bind(Board.this.heightProperty().divide(Board.this.ROWS).multiply(2 - Board.this.WALLSIZE));
			}
			else
			{
				this.layoutXProperty().bind(Board.this.widthProperty().divide(Board.this.COLS).multiply(x + Board.this.WALLSIZE/2));
				this.layoutYProperty().bind(Board.this.heightProperty().divide(Board.this.ROWS).multiply((y+1) - Board.this.WALLSIZE/2));			
				this.prefWidthProperty().bind(Board.this.widthProperty().divide(Board.this.COLS).multiply(2 - Board.this.WALLSIZE));
				this.prefHeightProperty().bind(Board.this.heightProperty().divide(Board.this.ROWS).multiply(Board.this.WALLSIZE));
			}
			
			this.setBackground(DEFAULT);
		}
		
		
		public boolean isSet() { return set; }
		public boolean isSettable()
		{
			if (this.isSet())
				return false;
			if (!Board.this.players[activePlayer].hasWalls())
				return false;

			if (vertical)
			{
				if (y > 0 && vWall[x][y-1].isSet())
					return false;
				if (y < ROWS-2 && vWall[x][y+1].isSet())
					return false;
				if (hWall[x][y].isSet())
					return false;
			}
			else
			{
				if (x > 0 && hWall[x-1][y].isSet())
					return false;
				if (x < COLS-2 && hWall[x+1][y].isSet())
					return false;
				if (vWall[x][y].isSet())
					return false;
			}
			
			this.set = true;		// Temporarily set wall
			for (Player p : players)
				if (p.getShortestPathLength() == -1)
					{ this.set = false; break; }
			
			if (!this.set)
				return false;
			else
			{
				this.set = false;
				return true;
			}
		}
		
		public void set()
		{
			this.set = true;
			this.setBackground(SET);
		}
		
		public void unset()
		{
			this.set = false;
			this.setBackground(DEFAULT);
		}
		
		public void setIllegal()
		{
			this.toFront();
			this.setBackground(ILLEGAL);
		}
		public void setPossible()
		{
			this.toFront();
			this.setBackground(POSSIBLE);
		}
		
		@Override
		public void doMove()
		{
			Controller.setWallMoveCandidate(this.x+1, this.y+1, this.vertical ? Direction.Vertical : Direction.Horizontal);
			Controller.doWallMove();
		}
		
		@Override
		public void recommend()
		{
			this.setPossible();
		}
	}
	
	public class Player extends Circle
	{
		//public final int TOTALWALLS = 20;
		
		//public int walls;
		
		private Cell position;
		private final double SIZE = 0.75;
		private final int start;
		
		public boolean computer = false;
		
		public Player(int start, Color color)
		{
			this.setFill(color);
			this.centerXProperty().bind(Board.this.cells[0][0].widthProperty().divide(2));
			this.centerYProperty().bind(Board.this.cells[0][0].heightProperty().divide(2));
			this.radiusProperty().bind(Board.this.cells[0][0].widthProperty().multiply(SIZE/2));
			//this.walls = TOTALWALLS / Board.this.players.length;
			this.start = start;
			
			switch (start)
			{
			case 0: (this.position = cells[COLS/2][0]).getChildren().add(this); break;
			case 1: (this.position = cells[COLS-1][ROWS/2]).getChildren().add(this); break;
			case 2: (this.position = cells[COLS/2][ROWS-1]).getChildren().add(this); break;
			case 3: (this.position = cells[0][ROWS/2]).getChildren().add(this); break;
			default: assert false;
			}
		}
		
		public Set<Cell> getPossibleMoves()
		{
			Set<Cell> moves = new HashSet<>();
			for (int d = 0; d < 4; d++)
			{
				if (position.isBlockedDirection(d))
					continue;
				if (!position.direction(d).hasPlayer())
					moves.add(position.direction(d));
				else
					if (!position.direction(d).isBlockedDirection(d) && !position.direction(d).direction(d).hasPlayer())
						moves.add(position.direction(d).direction(d));
					else
					{
						if (!position.direction(d).isBlockedDirection(d-1) &&
								!position.direction(d).direction(d-1).hasPlayer())
							moves.add(position.direction(d).direction(d-1));
						if (!position.direction(d).isBlockedDirection(d+1) &&
								!position.direction(d).direction(d+1).hasPlayer())
							moves.add(position.direction(d).direction(d+1));
					}
			}	
			return moves;
		}
		
		private int shortestPathLength;
		public int getShortestPathLength()
		{
			if (isWinner(position))
				return 0;
			
			forEachCell(c ->
			{
				c.visited = false;
				c.distance = -1;
			});
			
			position.distance = 0;
			Set<Cell> toVisit = getPossibleMoves();
			toVisit.forEach(c -> c.distance = 1);
			position.visited = true;
			
			while (!toVisit.isEmpty())
			{
				Cell min = toVisit.iterator().next();
				for (Cell c : toVisit)
					if (c.distance < min.distance)
						min = c;
				toVisit.remove(min);
				min.visited = true;
				
				if (isWinner(min))
					return this.shortestPathLength = min.distance;
				
				for (int i = 0; i < 4; i++)
					if (!min.isBlockedDirection(i) && !min.direction(i).visited)
						if (min.direction(i).distance == -1 || min.distance + 1 < min.direction(i).distance)
						{
							min.direction(i).distance = min.distance + 1;
							toVisit.add(min.direction(i));
						}
			}
			return this.shortestPathLength = -1;
		}
		
		public void takeTurn() throws InterruptedException
		{
			assert players[activePlayer] == this;
			
			this.startClock();
			
			if (isComputer())
				this.getBestMove().doMove();
			else if (isNetwork())
				try { Controller.doMove(networkIn.nextLine()); }
				catch (NoSuchElementException ex) { Controller.resign(); }	// Resign if player disconnected
			else		// Local user
				synchronized (Board.this)
				{
					getPossibleMoves().forEach(c -> c.setSelected(true));
					
					Board.this.waitingForMove = true;
					Board.this.wait();
					Board.this.waitingForMove = false;
				}
			if (adversary().isNetwork())
				adversary().networkOut.println(Controller.moveToToken(
						QuoridorApplication.getQuoridor().getCurrentGame().getMove(
								QuoridorApplication.getQuoridor().getCurrentGame().numberOfMoves()-1)));
			
			this.stopClock();
		}
		
		public Move getBestMove()
		{
			assert players[activePlayer] == this;
			{
				boolean isAllComputer = true;
				for (Player p : players)
					if (!p.isComputer())
						isAllComputer = false;
				if (isAllComputer)
					try { Thread.sleep(100); }
					catch (InterruptedException ex) {}
			}
			
			Set<Cell> moves = getPossibleMoves();
			
			List<Cell> bestCells = new ArrayList<>();
			int minCellCost = Integer.MAX_VALUE;
			for (Cell move : moves)
			{
				for (Player p : players)
					p.setPosition(p == this ? move : null);
				
				int cost = getShortestPathLength();
				for (Player p : players)
					if (p != this)
					{
						p.restorePosition();
						cost -= p.getShortestPathLength();
					}
				
				if (cost < minCellCost)
				{
					bestCells.clear();
					minCellCost = cost;
				}
				if (cost == minCellCost)
					bestCells.add(move);
				
				this.restorePosition();
			}
			
			List<Wall> bestWalls = new ArrayList<>();
			int minWallCost = Integer.MAX_VALUE;
			
			boolean vertical = false;
			for (int i = 0; i < COLS-1; i++)
				for (int j = 0; j < ROWS-1; j += vertical ? 1 : 0, vertical = !vertical)
				{
					Wall curr = (vertical ? hWall : vWall)[i][j];
					
					if (curr.isSettable())
					{
						int cost = this.shortestPathLength;
						for (Player p : players)
							if (p != this)
								cost -= p.shortestPathLength;
						
						if (cost < minWallCost)
						{
							bestWalls.clear();
							minWallCost = cost;
						}
						if (cost == minWallCost)
							bestWalls.add(curr);
					}
				}
			
			if (bestWalls.isEmpty() || minCellCost < minWallCost + (int)(Math.random() * 2))
				return bestCells.get((int)(Math.random()*bestCells.size()));
			else
				return bestWalls.get((int)(Math.random()*bestWalls.size()));
		}
		
		private Cell save;
		private void setPosition(Cell pos) { save = position; position = pos; }
		private void restorePosition() { position = save; }
		
		public void moveTo(Cell dest)
		{
			position.getChildren().remove(this);
			(position = dest).getChildren().add(this);
		}
		
		private boolean isWinner(Cell c)
		{
			switch (start)
			{
			case 0: return c.y == ROWS-1;
			case 1: return c.x == 0;
			case 2: return c.y == 0;
			case 3: return c.x == COLS-1;
			default: return false;
			}
		}
		
		public boolean hasWon() { return isWinner(position); }

		private Thread clock;
		private long remainingTime;
		public void startClock(long remainingTime)
		{
			this.remainingTime = remainingTime * 100;
			startClock();
		}
		
		public boolean hasWalls()
		{
			switch (start)
			{
			case 0: return false;
			case 1: return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasBlackWallsInStock();
			case 2: return false;
			case 3: return QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().hasWhiteWallsInStock();
			default: return false;
			}
		}
		
		private Consumer<Long> onRemainingTimeChanged;
		public void startClock()
		{
			clock = new Thread(()->{
				while (!Thread.currentThread().isInterrupted() && remainingTime > 0)
					try {
						Thread.sleep(10);
						remainingTime--;
						if (onRemainingTimeChanged != null)
							Platform.runLater(() -> onRemainingTimeChanged.accept(remainingTime));
					} catch (InterruptedException e) { break; }
				
				if (remainingTime == 0)
				{
					Controller.countdownZero(activePlayer == 0 ?
							QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer() :
							QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
					synchronized(Board.this) { Board.this.notify(); System.out.println("notify timer"); }
				}
			});
			clock.start();
		}
		public void stopClock() { clock.interrupt(); }
		
		public boolean isClockStopped() {
			if(clock == null || !clock.isAlive()) return true;
			else return false; 
		}
		
		public long getRemainingTime() { return this.remainingTime / 100; }
		public void setOnRemainingTimeChange(Consumer<Long> action) { this.onRemainingTimeChanged = action;; }
		public Consumer<Long> getOnRemainingTimeChange() { return this.onRemainingTimeChanged; }
		
		public void setComputer(boolean computer) { this.computer = computer; }
		public boolean isComputer() { return this.computer; }
		public boolean isUser() { return !isComputer() && !isNetwork(); }
		
		private InetAddress address = null;
		private Socket socket = null;
		private PrintStream networkOut = null;
		private Scanner networkIn = null;
		public boolean isNetwork() { return address != null; }
		public void setNetwork(InetAddress address) { this.address = address; }
		public void connect() throws IOException
		{
			try { socket = new Socket(address, 5000); }
			catch (ConnectException ex)
			{
				ServerSocket server = new ServerSocket(5000);
				do
				{
					if (socket != null)
						socket.close();
					socket = server.accept();
				}
				while (!socket.getInetAddress().equals(address));
				server.close();
			}
			
			networkIn = new Scanner(socket.getInputStream());
			networkOut = new PrintStream(socket.getOutputStream());
		}
		public void disconnect() throws IOException
		{
			if (socket != null && !socket.isClosed())
				socket.close();
			socket = null;
		}
		
		public boolean isWhite() { return this == players[0]; }
		public Player adversary() { return players[isWhite() ? 1 : 0]; }
		
	}
	
	public interface Move
	{
		public void doMove();
		public void recommend();
	}
	
	private class LoadingPane extends Pane
	{
		public LoadingPane(Player player)
		{
			this.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.75), CornerRadii.EMPTY, Insets.EMPTY)));
			this.prefWidthProperty().bind(Board.this.widthProperty());
			this.prefHeightProperty().bind(Board.this.heightProperty());
			
		    ImageView loading = new ImageView(new Image(QuoridorApplication.class.getClassLoader().getResourceAsStream("loading.gif")));
		    loading.fitWidthProperty().bind(Board.this.widthProperty().divide(2));
		    loading.fitHeightProperty().bind(Board.this.heightProperty().divide(2));
		    loading.layoutXProperty().bind(Board.this.widthProperty().divide(4));
		    loading.layoutYProperty().bind(Board.this.heightProperty().divide(16).multiply(5));
		    
		    Text text = new Text("Waiting for " + (player.isWhite() ? "white" : "black") + "@" + player.address.toString());
		    text.setFont(Font.font(20));
		    ChangeListener<Number> listener = (e, m, n) ->
		    {
		    	text.setLayoutX((Board.this.getWidth() - text.getLayoutBounds().getWidth()) / 2);
		    	text.setLayoutY(Board.this.getHeight() / 4);
		    };
		    Board.this.widthProperty().addListener(listener);
		    Board.this.heightProperty().addListener(listener);
		    text.layoutBoundsProperty().addListener(e -> listener.changed(null, null, null));
		    listener.changed(null, null, null);
		    
		    this.getChildren().addAll(loading, text);
		}
	}
}
