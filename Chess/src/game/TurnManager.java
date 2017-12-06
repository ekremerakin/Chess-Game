package game;

import javax.swing.border.Border;

import utilities.Piece;

/*
 * TurnManager is a crucial class because it hold the turn data
 * and decide which player is going to play.
 */
public class TurnManager {
	
	/*
	 * Player objects.
	 */
	private static Player whitePlayer;
	private static Player blackPlayer;
	
	/*
	 * GUImager object.
	 */
	private static GUImanager guiManager;
	
	/*
	 * TurnCounter to store the turn data.
	 */
	private static int turnCounter = 1;
	
	/*
	 * Default constructor. It just equals the turnCounter to 1.
	 */
	public TurnManager() {
		turnCounter = 1;
	}
	
	/*
	 * This method adds player to the TurnManager.
	 */
	public static void addPlayer(Player player) {
		if(player.getWhichColor().equals("W"))
			whitePlayer = player;
		else
			blackPlayer = player;
	}
	
	/*
	 * This method returns the opponent player by looking at
	 * the turnCounter.
	 */
	public Player getOpponentPlayer() {
		if(turnCounter%2 == 1)
			return blackPlayer;
		else 
			return whitePlayer;
	}
	
	/*
	 * This method returns the current player by looking at
	 * the turnCounter.
	 */
	public Player getOwnPlayer() {
		if(turnCounter%2 == 1)
			return whitePlayer;
		else 
			return blackPlayer;
	}

	/*
	 * This method adds a GUImanager referance to this class.
	 */
	public static void addGUI(GUImanager gui) {
		guiManager = gui;
	}
	
	/*
	 * canPlay checks that which player is going to play by looking at
	 * the turnCounter and which square was selected.
	 */
	public boolean canPlay(Border border, Square whoOrdered) {
		
		if(border!=null) {
			if((turnCounter%2 == 1 && whoOrdered.getPiece().getPieceColor().equals("W"))
					|| (turnCounter%2 == 0 && whoOrdered.getPiece().getPieceColor().equals("B"))) 
				return true;
		}
		
		return false;	
	}
	
	/*
	 * Overloaded canPlay method checks the situation for the 
	 * piece instead of the square objects.
	 */
	public boolean canPlay(Square square, Piece piece) {
		
		if(piece!=null) {
			
			if((turnCounter%2==1 && piece.getPieceColor().equals("W")
					|| (turnCounter%2==0 && piece.getPieceColor().equals("B")))) {
				return true;
			}
		}
		return false;
		
	}
	
	/*
	 * Increases the turn number and updates the log screen.
	 */
	public void nextTurn() {
		if(turnCounter%2 == 1)
			getGuiManager().getLogArea().append("- White player played.\n------\n");
		else 
			getGuiManager().getLogArea().append("- Black player played.\n------\n");
		turnCounter++;
	}
	
	/*
	 * After starting a new game, turn number needs to be 1.
	 */
	public static void startNewGame() {
		turnCounter = 1;
	}
	
	/*
	 * For the multiplayer mode, methods get sure about server
	 * or client called this method.
	 */
	public boolean multiplayerMode() {
		if(whitePlayer.getMultiplayerSituation().equals("server") ||
			blackPlayer.getMultiplayerSituation().equals("client")) {
			return true;
		}
		return false;
	}
	
	/*
	 * This method check who would play for the multiplayer games.
	 */
	public boolean multiplayerCanPlay() {
		if(turnCounter%2==1 && whitePlayer.getMultiplayerSituation().equals("server") ||
				turnCounter%2==0 && blackPlayer.getMultiplayerSituation().equals("client")) {

			return true;
		}	
		return false;
	}
	
	/*
	 * Encapsulated variables.
	 */
	public int getTurn() {
		return turnCounter;
	}
	
	public static GUImanager getGuiManager() {
		return guiManager;
	}
	
	public Player getWhitePlayer() {
		return whitePlayer;
	}
	
	public Player getBlackPlayer() {
		return blackPlayer;
	}
	
}
