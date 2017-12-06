package game;

import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * Board panel is responsible to create and control each of the
 * square of the 8x8 board.
 */
public class Board {
	
	/*
	 * 2D ArrayList for square objects.
	 */
	private ArrayList<ArrayList<Square>> squares;
	
	/*
	 * Rule manager object.
	 */
	private RuleManager ruleManager;
	
	/*
	 * PrintWriter object.
	 */
	private PrintWriter out;
	
	/*
	 * Default constructor.
	 * -Create 8x8 Square objects.
	 */
	public Board() {
		
		ruleManager = new RuleManager();
		
		squares = new ArrayList<ArrayList<Square>>();
		
		for(int i=8;i>0;i--) {
			ArrayList<Square> squaresCol = new ArrayList<>();
			for(int j=0;j<8;j++) {
				Square square = null;
				if((j%2 == 1 && i%2 == 1) || (j%2 == 0 && i%2 == 0))
					square = new Square(this, Color.decode("#ffebd6"), createCoordinate(j, i));
				else 
					square = new Square(this, Color.decode("#705333"), createCoordinate(j, i));
				squaresCol.add(square);
			}
			squares.add(squaresCol);
		}
	}
	
	/*
	 * Create chess board coordinates for a given x and y values.
	 */
	private String createCoordinate(int x, int y) {
		String letters = "abcdefgh";
		String coordinate = "" + letters.charAt(x) + (y);
		return coordinate;
	}
	
	/*
	 * Clean all the squares to display new routes for the selected
	 * piece.
	 */
	public void cleanAllSquares() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				squares.get(i).get(j).setBorder(null);
			}
		}
	}
	
	/*
	 * Encapsulated variables.
	 */
	public ArrayList<ArrayList<Square>> getSquares() {
		return squares;
	}
	
	public RuleManager getRuleManager() {
		return ruleManager;
	}
	
	public void setPrintWriter(PrintWriter out) {
		this.out = out;
	}
	
	public PrintWriter getPrintWriter() {
		return out;
	}
}
