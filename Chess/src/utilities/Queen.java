package utilities;

import java.util.ArrayList;

/*
 * This class a child class of the Piece class.
 * It overrides the move method with calculating the points 
 * it can go in a straight way and a cross way.
 */
public class Queen extends Piece {


	public Queen(String whichColor, String coordinates) {
		super(whichColor, coordinates);
	}

	@Override
	public ArrayList<String> move() {
		possiblePoints = MovementManager.calculateStraightPoints(coordinates, false);
		possiblePoints.addAll(MovementManager.calculateCrossPoints(coordinates, false));
		return possiblePoints;
	}

	
}
