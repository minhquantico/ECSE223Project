package ca.mcgill.ecse223.quoridor.controller;

import ca.mcgill.ecse223.quoridor.controller.PawnBehaviour.MoveDirection;
import ca.mcgill.ecse223.quoridor.model.Player;

public class IllegalMoveException extends RuntimeException
{
	public IllegalMoveException(Player p, MoveDirection d)
	{
		super((p.hasGameAsWhite() ? "white" : "black") + ": " + d);
	}
	
	public IllegalMoveException(Player p, MoveDirection d1, MoveDirection d2)
	{
		super((p.hasGameAsWhite() ? "white" : "black") + ": " + d1 + ", " + d2);
	}
	
	public IllegalMoveException() {}
}