package utilities;

import java.util.ArrayList;

/*
 * This class a child class of the Piece class.
 * It overrides the move method with just the 
 * calculating the points it can go in a special
 * method called calculateKnightsPoints.
 */
public class Knight extends Piece {

	public Knight(String whichColor, String coordinates) {
		super(whichColor, coordinates);
	}

	@Override
	public ArrayList<String> move() {
		possiblePoints = MovementManager.calculateKnightsPoints(coordinates, false);
		return possiblePoints;
	}

}
