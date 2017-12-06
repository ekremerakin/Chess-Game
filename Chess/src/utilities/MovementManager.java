package utilities;

import java.util.ArrayList;

import game.Square;

public class MovementManager {
	
	/*
	 * Some constants.
	 */
	private final static String letters = "abcdefgh";
	private final static String numbers = "87654321";
	
	/*
	 * 2D ArrayList holds the Square objects.
	 */
	private static ArrayList<ArrayList<Square>> squares;
	
	/*
	 * Constructor takes 2D ArrayList as a parameter and 
	 * assign it to its equivalent in this class.
	 */
 	public MovementManager(ArrayList<ArrayList<Square>> squares) {
		this.squares = squares;
	}
	
 	/*
 	 * This method calculates a piece's points that can go just crosswise and
 	 * returns the points in an ArrayList.
 	 */
	public static ArrayList<String> calculateCrossPoints(String coordinate, boolean checkControlMode) {
		ArrayList<String> possiblePoints = new ArrayList<>();
		
		int x = letters.indexOf(coordinate.charAt(0));
		int y = numbers.indexOf(coordinate.charAt(1));
		
		while( x>0 && y>0 ) {
			x--;
			y--;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		x = letters.indexOf(coordinate.charAt(0));
		y = numbers.indexOf(coordinate.charAt(1));
		
		while( (x+1)<8 && y>0 ) {
			x++;
			y--;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		x = letters.indexOf(coordinate.charAt(0));
		y = numbers.indexOf(coordinate.charAt(1));
		
		while( x>0 && (y+1)<8 ) {
			x--;
			y++;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		x = letters.indexOf(coordinate.charAt(0));
		y = numbers.indexOf(coordinate.charAt(1));
		
		while( (x+1)<8 && (y+1)<8 ) {
			x++;
			y++;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		return possiblePoints;
	}
	
	/*
	 * This method calculate the points that a piece go straight and returns 
	 * the points in an ArrayList.
	 */
	public static ArrayList<String> calculateStraightPoints(String coordinate, boolean kingCheckMode) {
		ArrayList<String> possiblePoints = new ArrayList<>();
		
		int x = letters.indexOf(coordinate.charAt(0));
		int y = numbers.indexOf(coordinate.charAt(1));
		
		while( x>0 ) {
			x--;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		x = letters.indexOf(coordinate.charAt(0));
		y = numbers.indexOf(coordinate.charAt(1));
		
		while( (x+1)<8 ) {
			x++;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		x = letters.indexOf(coordinate.charAt(0));
		y = numbers.indexOf(coordinate.charAt(1));
		
		while( y>0 ) {
			y--;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		x = letters.indexOf(coordinate.charAt(0));
		y = numbers.indexOf(coordinate.charAt(1));
		
		while( (y+1)<8 ) {
			y++;
			possiblePoints.add(intToStringCoordinate(x, y));
			if(squares.get(y).get(x).getPiece() != null)
				break;	
		}
		
		return possiblePoints;
	}
	
	/*
	 * This method calculates knight's points that the 
	 * piece can place on. 
	 */
	public static ArrayList<String> calculateKnightsPoints(String coordinate, boolean kingCheckMode) {
		ArrayList<String> possiblePoints = new ArrayList<>();
		
		int x = letters.indexOf(coordinate.charAt(0));
		int y = numbers.indexOf(coordinate.charAt(1));
		
		if( (x-2)>=0 ){
			if(y>0)
				possiblePoints.add(intToStringCoordinate(x-2, y-1));
			if((y+1)<8)
				possiblePoints.add(intToStringCoordinate(x-2, y+1));
		}
		
		if( (x+2)<8 ){
			if(y>0)
				possiblePoints.add(intToStringCoordinate(x+2, y-1));
			if((y+1)<8)
				possiblePoints.add(intToStringCoordinate(x+2, y+1));
		}
		
		if( (y+2)<8 ){
			if(x>0)
				possiblePoints.add(intToStringCoordinate(x-1, y+2));
			if((x+1)<8)
				possiblePoints.add(intToStringCoordinate(x+1, y+2));
		}
		
		if( (y-2)>=0 ){
			if(x>0)
				possiblePoints.add(intToStringCoordinate(x-1, y-2));
			if((x+1)<8)
				possiblePoints.add(intToStringCoordinate(x+1, y-2));
		}
		
		return possiblePoints;
	}
	
	/*
	 * This method calculates the possible places that a pawn go. This method is 
	 * specific to pawns because they may move 2 sqaures at the first round or 
	 * they could go just 1. So that, another method was made for just the
	 * pawn piece.
	 */
	public static ArrayList<String> calculatePawnPoints(String coordinate, boolean kingCheckMode) {
		ArrayList<String> possiblePoints = new ArrayList<>();
		
		int x = letters.indexOf(coordinate.charAt(0));
		int y = numbers.indexOf(coordinate.charAt(1));
		
		if(squares.get(y).get(x).getPiece().getPieceColor().equals("W")) {
			if( coordinate.charAt(1) == '2' 
					&& squares.get(y-1).get(x).getPiece() == null
					&& squares.get(y-2).get(x).getPiece() == null) {
				possiblePoints.add(intToStringCoordinate(x, y-1));
				possiblePoints.add(intToStringCoordinate(x, y-2));
			} else if( (y-1)>=0 && squares.get(y-1).get(x).getPiece() == null) {
				possiblePoints.add(intToStringCoordinate(x, y-1));
			}
			
			if((x+1)<8 && (y-1)>=0 
					&& squares.get(y-1).get(x+1).getPiece() != null
					&& squares.get(y-1).get(x+1).getPiece().getPieceColor().equals("B")) {
				possiblePoints.add(intToStringCoordinate(x+1, y-1));
			}
			
			if((x-1)>=0 && (y-1)>=0 
					&& squares.get(y-1).get(x-1).getPiece() != null
					&& squares.get(y-1).get(x-1).getPiece().getPieceColor().equals("B")) {
				possiblePoints.add(intToStringCoordinate(x-1, y-1));
			}
		}
		
		if(squares.get(y).get(x).getPiece().getPieceColor().equals("B")) {
			if( coordinate.charAt(1) == '7' 
					&& squares.get(y+1).get(x).getPiece() == null
					&& squares.get(y+2).get(x).getPiece() == null) {
				possiblePoints.add(intToStringCoordinate(x, y+1));
				possiblePoints.add(intToStringCoordinate(x, y+2));
			} else if( (y+1)<8 && squares.get(y+1).get(x).getPiece() == null) {
				possiblePoints.add(intToStringCoordinate(x, y+1));
			}
		}
		
		if((x+1)<8 && (y+1)<8 
				&& squares.get(y+1).get(x+1).getPiece() != null
				&& squares.get(y+1).get(x+1).getPiece().getPieceColor().equals("W")) {
			possiblePoints.add(intToStringCoordinate(x+1, y+1));
		}
		
		if((x-1)>=0 && (y+1)<8 
				&& squares.get(y+1).get(x-1).getPiece() != null
				&& squares.get(y+1).get(x-1).getPiece().getPieceColor().equals("W")) {
			possiblePoints.add(intToStringCoordinate(x-1, y+1));
		}
		
		return possiblePoints;
	}
	
	/*
	 * This method calculates the possible locations for the king since it can only go 1 square
	 * for each round, it's a straightforward method to understand.
	 */
	public static ArrayList<String> calculateKingPoints(String coordinate, boolean kingCheckMode) {
		ArrayList<String> possiblePoints = new ArrayList<>();
		
		int x = letters.indexOf(coordinate.charAt(0));
		int y = numbers.indexOf(coordinate.charAt(1));
		
		if( x>0 ) 
			possiblePoints.add(intToStringCoordinate(x-1, y));
		
		if( (x+1)<8 ) 
			possiblePoints.add(intToStringCoordinate(x+1, y));
		
		if( y>0 ) 
			possiblePoints.add(intToStringCoordinate(x, y-1));
	
		if( (y+1)<8 ) 
			possiblePoints.add(intToStringCoordinate(x, y+1));
		
		if( x>0 && y>0)
			possiblePoints.add(intToStringCoordinate(x-1, y-1));
		
		if( x>0 && (y+1)<8 )
			possiblePoints.add(intToStringCoordinate(x-1, y+1));
		
		if( (x+1)<8 && y>0 )
			possiblePoints.add(intToStringCoordinate(x+1, y-1));
		
		if( (x+1)<8 && (y+1)<8 )
			possiblePoints.add(intToStringCoordinate(x+1, y+1));
			
		return possiblePoints;
		
	}
	
	/*
	 * Converts integer coordinates to string coordinates.
	 */
	private static String intToStringCoordinate(int x, int y) {
		String coordinate = "" + letters.charAt(x) + numbers.charAt(y);
		return coordinate;
	}
}
