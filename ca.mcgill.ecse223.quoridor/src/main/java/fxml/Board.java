
package fxml;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.StepMove;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//import tools.StopWatch;

// TODO: set isHackable to false <---- IMPORTANT
public class Board extends Pane
{
	public final int COLS = 9;
	public final int ROWS = 9;
	public double WALLSIZE = 0.30;
	
	public Player[] players;
	public int activePlayer;
	
	private Cell[][] cells = new Cell[COLS][ROWS];
	private Wall[][] vWall = new Wall[COLS-1][ROWS-1];		// vWall[0][y]=null && vWall[x][ROWS-1]=null
	private Wall[][] hWall = new Wall[COLS-1][ROWS-1];		// hWall[x][0]=null && hWall[COLS-1][y]=null
	
	private Consumer<? super Player> onPlayerWin;
	
	private Thread game = new Thread(() -> {
		do
			try
			{
				if (activePlayer == players.length)
					activePlayer = 0;
				long elapsed = System.currentTimeMillis();
				players[activePlayer].takeTurn();
				elapsed = System.currentTimeMillis() - elapsed;
				Time rem = activePlayer == 0 ?
						QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime() :
						QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getRemainingTime();
				rem = new Time(rem.getTime() - elapsed);
				if (activePlayer == 0)
					QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setRemainingTime(rem);
				else
					QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setRemainingTime(rem);
			}
			catch (InterruptedException ex) { System.err.println("Interrupted???"); }
			catch (Exception ex) { ex.printStackTrace(); }
		while (!players[activePlayer++].hasWon());
		activePlayer--;
		if (onPlayerWin != null)
			Platform.runLater(() -> onPlayerWin.accept(players[activePlayer]));
	});
	
	public Board(boolean... isPlayerComputer)
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
		
		this.players = new Player[isPlayerComputer.length];		// 2 Players
		if (players.length >= 1)
			this.players[0] = new Player(3, Color.GREEN, isPlayerComputer[0]);
		if (players.length >= 2)
			this.players[1] = new Player(1, Color.RED, isPlayerComputer[1]);
		if (players.length >= 3)
			this.players[2] = new Player(2, Color.ORANGE, isPlayerComputer[2]);
		if (players.length >= 4)
			this.players[3] = new Player(0, Color.PURPLE, isPlayerComputer[3]);
		
		this.activePlayer = 0;
//		for (Move m : QuoridorApplication.getQuoridor().getCurrentGame().getMoves())
//		{
//			if (m instanceof StepMove)
//			{
//				
//			}
//			else
//			{
//				
//			}
//		}
		
		if (players.length > 0)
			this.game.start();
	}
	
	public void setOnPlayerWin(Consumer<? super Player> action) { this.onPlayerWin = action; }
	
	private void forEachCell(Consumer<? super Cell> action)
	{
		for (int i = 0; i < ROWS * COLS; i++)
			action.accept(cells[i/ROWS][i%ROWS]);
	}
	
	class Cell extends Pane
	{
		public final Background DEFAULT = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		public final Background SELECTED = new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY));
		private final int x, y;
		
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
			this.setOnMouseClicked(selected ? e -> Board.this.players[activePlayer].moveTo(this) : null);
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
	}
	
	class Wall extends Pane
	{
		private Background DEFAULT = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
		private Background SET = new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));
		private int x, y; 
		private boolean vertical, set = false;

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
			this.setOnMouseEntered(e -> this.setBackground(this.isSettable() ? SET : DEFAULT));
			this.setOnMouseExited(e -> this.setBackground(DEFAULT));
			this.setOnMouseClicked(e -> this.set());
		}
		
		public boolean isSet() { return set; }
		public boolean isSettable()
		{
			if (this.isSet())
				return false;
			if (Board.this.players[activePlayer].walls == 0)
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
		
		public boolean set()
		{
			if (!isSettable())
				return false;
			
			this.setBackground(SET);
			this.setOnMouseEntered(null);
			this.setOnMouseExited(null);
			this.set = true;
			
			Board.this.players[activePlayer].walls--;
			if (game.isAlive())
				synchronized (Board.this) { Board.this.notify(); }
			return true;
		}
	}
	
	public class Player extends Circle
	{
		public final int TOTALWALLS = 20;
		
		
		public int walls;
		
		private Cell position;
		private final double SIZE = 0.75;
		private final int start;
		
		public final boolean isComputer;
		
		public Player(int start, Color color, boolean isComputer)
		{
			this.setFill(color);
			this.centerXProperty().bind(Board.this.cells[0][0].widthProperty().divide(2));
			this.centerYProperty().bind(Board.this.cells[0][0].heightProperty().divide(2));
			this.radiusProperty().bind(Board.this.cells[0][0].widthProperty().multiply(SIZE/2));
			this.walls = TOTALWALLS / Board.this.players.length;
			this.start = start;
			this.isComputer = isComputer;
			
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
			
			synchronized (Board.this)
			{
				if (isComputer)
					Platform.runLater(() -> doBestMove());
				else
					getPossibleMoves().forEach(c -> c.setSelected(true));
				
				Board.this.wait();
				forEachCell(c -> c.setSelected(false));
			}
		}
		
		public void doBestMove()
		{
			assert players[activePlayer] == this;
			{
				boolean isAllComputer = true;
				for (Player p : players)
					if (!p.isComputer)
						isAllComputer = false;
				if (isAllComputer)
					try { Thread.sleep(5); }
					catch (InterruptedException ex) {}
			}
			
			//StopWatch w = new StopWatch();
			
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
			for (int i = 0; i < COLS; i++)
				for (int j = 0; j < ROWS; j += vertical ? 1 : 0, vertical = !vertical)
				{
					Wall curr = (vertical ? hWall : vWall)[i][j];
					
					if (curr == null)
						continue;
					
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
			
			//System.out.println("Time: " + w.getTimeElapsed() * 1000 + "ms");
			
			if (bestWalls.isEmpty() || minCellCost < minWallCost + (int)(Math.random() * 2))
				moveTo(bestCells.get((int)(Math.random()*bestCells.size())));
			else
				bestWalls.get((int)(Math.random()*bestWalls.size())).set();
			
			
		}
		
		private Cell save;
		private void setPosition(Cell pos) { save = position; position = pos; }
		private void restorePosition() { position = save; }
		
		public void moveTo(Cell dest)
		{
			position.getChildren().clear();
			(position = dest).getChildren().add(this);
			
			if (game.isAlive())
				synchronized (Board.this) { Board.this.notify(); }
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
		public void startClock()
		{
			clock = new Thread(()-> {
				while (!Thread.currentThread().isInterrupted() && remainingTime > 0)
					try {
						Thread.sleep(1000);
						remainingTime--;
					} catch (InterruptedException e) {e.printStackTrace();}
			});
			
			
		}
		public void stopClock() {
			clock.interrupt();
		}
		
		public boolean isClockStopped() {
			if(clock == null || !clock.isAlive()) return true;
			else return false; 
		}
		
	}
}
