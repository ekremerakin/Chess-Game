package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * PieceManager is responsible for taking the input from text
 * files, creates all the pieces written in the text files and
 * display them on the board. 
 */
public class PieceManager {

	/*
	 * pieceLocations to store the piece positions.
	 */
	private ArrayList<Piece> pieceLocations;
	
	/*
	 * This class reads the positions from a text file and 
	 * saves all the pieces into the pieceLocations array 
	 * list with 2 other methods helps.
	 */
	private void readPieceLocations(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line=br.readLine())!=null) {
				String whichPiece, whichColor, coordinate;
				whichPiece = ""+line.charAt(0);
				for(int i=2; i<line.length(); i++) {
					if(line.charAt(i) == '-') {
						whichColor = ""+line.charAt(1);
						coordinate = line.substring(i+1, i+3);
						createCoordinate(whichPiece,whichColor, coordinate);
					}
						
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method creates coordinates for each of the pieces individually. 
	 */
	private void createCoordinate(String whichPiece, String whichColor, String coordinate) {		
		switch(whichPiece) {
			case "K":
				King king = new King(whichColor, coordinate);
				pieceLocations.add(king);
				break;
			case "Q":
				Queen queen = new Queen(whichColor, coordinate);
				pieceLocations.add(queen);
				break;
			case "R":
				Rook rook = new Rook(whichColor, coordinate);
				pieceLocations.add(rook);
				break;
			case "B":
				Bishop bishop = new Bishop(whichColor, coordinate);
				pieceLocations.add(bishop);
				break;
			case "N":
				Knight knight = new Knight(whichColor, coordinate);
				pieceLocations.add(knight);
				break;
			case "P":
				Pawn pawn = new Pawn(whichColor, coordinate);
				pieceLocations.add(pawn);
				break;
		}
	}
	
	/*
	 * getPieceLocations returns the pieces in a given document.
	 */
	public ArrayList<Piece> getPieceLocations(String adress) {
		pieceLocations = new ArrayList<>();
		
		File defaultPositions = new File(adress);
		readPieceLocations(defaultPositions);
		return pieceLocations;		
	}
	
}
