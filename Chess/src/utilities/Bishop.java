package utilities;

import java.util.ArrayList;

/*
 * This class a child class of the Piece class.
 * It overrides the move method with just the 
 * calculating the cross points.
 */
public class Bishop extends Piece {

	public Bishop(String whichColor, String coordinates) {
		super(whichColor, coordinates);
	}

	@Override
	public ArrayList<String> move() {
		possiblePoints = MovementManager.calculateCrossPoints(coordinates, false);
		return possiblePoints;
	}

}
