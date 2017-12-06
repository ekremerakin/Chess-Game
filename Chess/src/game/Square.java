package game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import utilities.Piece;

/*
 * Square class created 64 times by the Board class. It stores 
 * the piece information and when a player press a square, it 
 * just stores it until a valid point selected. If user select 
 * a valid point, player would be able to play its turn. But, if 
 * the player selects an unsuitable place, then selected square
 * would be reseted.
 */
public class Square extends JPanel{
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Board object.
	 */
	private Board board;

	/*
	 * Piece object.
	 */
	private Piece piece = null;
	
	/*
	 * Square's coordinate.
	 */
	private String coordinate;
	
	/*
	 * Saves the Square object which called last pressed Square object.
	 */
	private Square whoOrdered;
	
	/*
	 * Crates the turnManager statically. So, turnManager would be same
	 * for all the Square objects.
	 */
	private static TurnManager turnManager = new TurnManager();
	
	/*
	 * Some variables.
	 */
	private static boolean isCheck = false;
	
	/*
	 * Constructor takes parameters and assign them their corresponding values.
	 * Then, adds a mouse listener to be able to click by the user.
	 */
	public Square(Board board, Color squareColor, String coordinate) {
		this.board = board;
		this.coordinate = coordinate;
		
		Square squareObject = this;
		
		setBackground(squareColor);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseClick(squareObject, true);
			}
		});
	}
	
	/*
	 * MouseClick manages the clicking operation either for player and multiplayer opponent.
	 */
	private void mouseClick(Square squareObject, boolean playerOrProgram) {
		
		if(turnManager.canPlay(getBorder(), whoOrdered)) {						
			if(turnManager.multiplayerMode() && playerOrProgram) {
				if(turnManager.multiplayerCanPlay()) 
					assignNewPiece();
			} else 
				assignNewPiece();
		}
		
		board.cleanAllSquares();
		
		if(turnManager.canPlay(squareObject, piece)) {
			if(turnManager.multiplayerMode() && playerOrProgram) {
				if(turnManager.multiplayerCanPlay()) 
					calculatePossibleLocations(squareObject);
			} else 
				calculatePossibleLocations(squareObject);
				
		}
		System.out.println("coordinate: " + coordinate + "\n---------------");
	}
	
	/*
	 * This method assigns a new piece for the given coordinates. It also
	 * prints the the actions like if a player eats a piece, it can be 
	 * seen on the log screen.
	 */
	private void assignNewPiece() {
		
		Piece eatenPiece = piece;
		
		if(piece!=null)
			piece.died();

		System.out.println("next turn: " + whoOrdered.getPiece().getCoordinates()  + " new : " + coordinate); 
		if(board.getPrintWriter() != null)
		board.getPrintWriter().println(whoOrdered.getPiece().getCoordinates() + coordinate);
		cleanPanel();
		setPiece(whoOrdered.getPiece());				
		whoOrdered.cleanPanel();
		piece.setCoordinates(coordinate);
		
		if(eatenPiece != null && piece.getPieceColor().equals("W"))
			TurnManager.getGuiManager().getLogArea().append("- Black " + 
					eatenPiece.getClass().getSimpleName() + " was eaten by a White " + piece.getClass().getSimpleName() + "\n");
		else if(eatenPiece != null)
			TurnManager.getGuiManager().getLogArea().append("- White " + 
					eatenPiece.getClass().getSimpleName() + " was eaten by a Black  " + piece.getClass().getSimpleName() + "\n");
		
		turnManager.nextTurn();
		
		if(board.getRuleManager().displayCheck(turnManager)) {
			isCheck = true;
		} else 
			isCheck = false;
		
	}
	
	/*
	 * This method calculates possible locations for a piece. It's connected with the 
	 * ruleManager. So, if any situation satisfies with the if statements, this method
	 * just calls the required ruleManager method and saves the return.
	 */
	private void calculatePossibleLocations(Square squareObject) {
		ArrayList<Piece> alivePieces = turnManager.getOpponentPlayer().getListOfAlivePieces();
		String kingsCoordinates = turnManager.getOwnPlayer().getKing().getCoordinates();
		
		ArrayList<String> possibleLocations = piece.move();
		
		if(!piece.getClass().getSimpleName().equals("King") && !isCheck &&
				board.getRuleManager().isPieceInDanger(alivePieces, piece, squareObject, kingsCoordinates) != null) {
			possibleLocations = board.getRuleManager().isPieceInDanger(alivePieces, piece, squareObject, kingsCoordinates);
		} else if(!piece.getClass().getSimpleName().equals("King") && isCheck) {
			possibleLocations = board.getRuleManager().calculateSafePoints(alivePieces, piece, squareObject, kingsCoordinates);
		}
		
		if(piece.getClass().getSimpleName().equals("King")) {
			possibleLocations = board.getRuleManager().calculateKingsMove(alivePieces, piece, squareObject);
		}
		
		if(board.getRuleManager().calculateCheck(alivePieces, kingsCoordinates) 
				&& piece.getClass().getSimpleName().equals("King")) {
			possibleLocations = board.getRuleManager().calculateKingsMove(alivePieces, piece, squareObject);
		}
		
		for(String coordinate : possibleLocations) {
			displayPossibleLocations(coordinate);
		}
		
		System.out.println(piece.getPieceName() + " at " + coordinate);	
	}
	
	/*
	 * Displays the locations that a piece can go with the colors of green and red.
	 * If red is displayed, it means you're going to eat an enemy piece.
	 * If green is displayed, it means you're not going to eat anything.
	 */
	private void displayPossibleLocations(String coordinate) {
		String letters = "abcdefgh";
		String numbers = "87654321";
		int x = letters.indexOf(coordinate.charAt(0));
		int y = numbers.indexOf(coordinate.charAt(1));
	
		if(board.getSquares().get(y).get(x).getPiece() == null)
			board.getSquares().get(y).get(x).setBorder(BorderFactory.createLineBorder(Color.GREEN, 5), this);
		else if(!board.getSquares().get(y).get(x).getPiece().getPieceColor().equals(piece.getPieceColor()))
			board.getSquares().get(y).get(x).setBorder(BorderFactory.createLineBorder(Color.RED, 5), this);
	}
	
	/*
	 * Cleans the piece, borders and etc.
	 */
	public void cleanPanel() {
		piece = null;
		setBorder(null);
		removeAll();
		repaint();
	}
	
	/*
	 * Sets the border to another value.
	 */
	public void setBorder(Border border, Square square) {
		super.setBorder(border);
		whoOrdered = square;
	}
	
	/*
	 * Setting the new piece.
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
		if(piece != null)
			add(piece.getLabel());
	}

	public void clickByClient() {
		mouseClick(this, false);
	}
	
	/*
	 * Encapsulation.
	 */
	public Piece getPiece() {
		return piece;
	}
	
	public Board getBoard() {
		return board;
	}


}