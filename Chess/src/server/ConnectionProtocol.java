package server;

import game.Board;

/*
 * Connection protocol connects the server or client class
 * with the board class. This allows us to play new pieces.
 */
public class ConnectionProtocol {

	/*
	 * Letters and numbers constants.
	 */
	private final String letters = "abcdefgh";
	private final String numbers = "87654321";

	/*
	 * Variables to store where the piece came from 
	 * and where is goes.
	 */
	private String oldPosition, newPosition; 
	private int xOld, yOld, x, y;
	
	/*
	 * Board object.
	 */
	private Board board;
	
	/*
	 * Constructor which takes board object as a parameter
	 * and assigns that object to this classes board object.
	 */
	public ConnectionProtocol(Board board) {
		this.board = board;
	}
	
	/*
	 * This method takes the input as a parameter and it 
	 * substrings it to obtain the coordinates. After that, 
	 * just plays the piece with clickByClient method from
	 * the Square class.
	 */
	public void play(String input) {
		oldPosition = input.substring(0, 2);
		newPosition = input.substring(2, 4);
		
		xOld = findXcoordinate(oldPosition);
		yOld = findYcoordinate(oldPosition);
		
		x = findXcoordinate(newPosition);
		y = findYcoordinate(newPosition);
		
		
		board.getSquares().get(yOld).get(xOld).clickByClient();
		board.getSquares().get(y).get(x).clickByClient();
	}

	/*
	 * Methods to find X and Y coordinates.
	 */
	public int findXcoordinate(String input) {
		int output = letters.indexOf(input.charAt(0));
		return output;
	}
	
	public int findYcoordinate(String input) {
		int output = numbers.indexOf(input.charAt(1));
		return output;
	}
	
}
