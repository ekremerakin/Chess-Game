package utilities;

import java.util.ArrayList;

/*
 * This class a child class of the Piece class.
 * It overrides the move method with just the 
 * calculating the points it can go in a special
 * method called calculatePawnPoints.
 */
public class Pawn extends Piece {

	public Pawn(String whichColor, String coordinates) {
		super(whichColor, coordinates);
	}

	@Override
	public ArrayList<String> move() {
		possiblePoints = MovementManager.calculatePawnPoints(coordinates, false);
		return possiblePoints;
	}
	
}