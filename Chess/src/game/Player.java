package game;

import java.util.ArrayList;

import utilities.Piece;
import utilities.PieceManager;
import utilities.MovementManager;

/*
 * Player class first creates the pieces that the white or 
 * black player owns. Then, it stores them in an ArrayList 
 * so that whenever a class wants to reach that pieces, they 
 * can reach through that ArrayList.
 */
public class Player {

	/*
	 * 2D ArrayList stores the Square objects.
	 */
	private ArrayList<ArrayList<Square>> squares;
	
	/*
	 * ArrayList stores the pieces.
	 */
	private ArrayList<Piece> pieceLocations;
	
	/*
	 * Movement manager object to calculate the possible places 
	 * that a piece go.
	 */
	private static MovementManager movementManager;
	
	/*
	 * Stores the king to check the check.
	 */
	private Piece king;
	
	/*
	 * MultiplayerSituation string to store is the program
	 * is opened in multiplayer mode or not.
	 */
	private String multiplayerSituation = "";
	
	/*
	 * Some variables.
	 */
	private String whichColor;
	
	/*
	 * Constructor takes parameter and assign them their corresponding values 
	 * in this class. Then, adds the current player
	 * to the TurnManager, creates pieceManager and stores all the pieces that were
	 * given in the positions txt file.
	 */
	public Player(String pieceLocationsAdress, Board board, String whichColor) {
		this.whichColor = whichColor;
		
		squares = board.getSquares();
		movementManager = new MovementManager(squares);
		
		TurnManager.addPlayer(this);
		
		PieceManager pieceManager = new PieceManager();
		pieceLocations = pieceManager.getPieceLocations(pieceLocationsAdress);
		for(int i=0; i<pieceLocations.size(); i++) {
			if(pieceLocations.get(i).getPieceName().substring(0, pieceLocations.get(i).getPieceName().length()-1).equals("King")) {
				king = pieceLocations.get(i);
			}
			placePieceOnBoard(pieceLocations.get(i));
		}
	}
	
	/*
	 * Places pieces on the board.
	 */
	private void placePieceOnBoard(Piece piece) {
		String letters = "abcdefgh";
		String numbers = "87654321";
		int x = letters.indexOf(piece.getCoordinates().charAt(0));
		int y = numbers.indexOf(piece.getCoordinates().charAt(1));
		
		squares.get(y).get(x).setPiece(piece);
	}
	
	/*
	 * This method returns arrayList of alive pieces.
	 */
	public ArrayList<Piece> getListOfAlivePieces() {
		ArrayList<Piece> listOfAlivePieces = new ArrayList<>();
		for(Piece piece: pieceLocations) {
			if(piece.isAlive())
				listOfAlivePieces .add(piece);
		}
		return listOfAlivePieces;
	}
	
	/*
	 * Encapsulated variables.
	 */
	public Piece getKing() {
		return king;
	}
	
	public String getWhichColor() {
		return whichColor;
	}
	
	public ArrayList<Piece> getPieceLocations() {
		return pieceLocations;
	}
	
	public void setMultiplayerSituation(String multiplayerSituation) {
		this.multiplayerSituation = multiplayerSituation;
	}
	
	public String getMultiplayerSituation() {
		return multiplayerSituation;
	}
}
