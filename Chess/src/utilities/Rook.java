package utilities;

import java.util.ArrayList;

/*
 * This class a child class of the Piece class.
 * It overrides the move method with just the 
 * calculating the points it can go straight.
 */
public class Rook extends Piece {

	public Rook(String whichColor, String coordinates) {
		super(whichColor, coordinates);
	}

	@Override
	public ArrayList<String> move() {
		possiblePoints = MovementManager.calculateStraightPoints(coordinates, false);
		return possiblePoints;
	}

}
