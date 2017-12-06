package game;

import java.util.ArrayList;

import utilities.Piece;

/*
 * RuleManager is all about the movement calculations 
 * for the king, for the check and pieces that can't move 
 * because of the check situation.
 */
public class RuleManager {

	/*
	 * This method checks that is the movement creates a check position or not.
	 */
	public boolean calculateCheck(Piece piece, String kingCoordinate) {
		
		for(String possiblePoint: piece.move()) {
				if(possiblePoint.equals(kingCoordinate)) 
					return true;
		}
		return false;
	}
	
	/*
	 * This method calculates that is any of the alive enemy pieces creates a 
	 * check position or not.
	 */
	public boolean calculateCheck(ArrayList<Piece> alivePieces, String kingCoordinate) {	
		
		for(Piece piece: alivePieces) {
			for(String possiblePoint: piece.move()) {
				if(possiblePoint.equals(kingCoordinate)) 
					return true;
			}
		}
		return false;
		
	}
	
	/*
	 * This method prints on the log screen if a check position occurs.
	 */
	public boolean displayCheck(TurnManager turnManager) {
		if(calculateCheck(
				turnManager.getOpponentPlayer().getListOfAlivePieces(),
				turnManager.getOwnPlayer().getKing().getCoordinates())) {
			TurnManager.getGuiManager().getLogArea().append("- Check\n");
			return true;
		}
		return false;
	}
	
	/*
	 * This method calculates that is a piece in a danger or not. Meaning of it if there is 
	 * a check position and if any piece breaks the check position then that piece is going to 
	 * be in danger. So that, piece is not suppose to move. This method checks that situation and returns 
	 * an ArrayList with coordinates of the piece can go.
	 */
	public ArrayList<String> isPieceInDanger(ArrayList<Piece> alieveEnemyPieces, Piece piece, Square square, String kingsCoordinate) {
		
		ArrayList<String> possiblePoints = new ArrayList<>();
		
		String letters = "abcdefgh";
		String numbers = "87654321";
		
		int counter = 0;
		boolean isCheck = false;
		
		if(piece!=null) {
			
			for(Piece enemyPiece: alieveEnemyPieces) {
				
				square.setPiece(null);
				counter = 0;
				
				for(String coordinate: enemyPiece.move()) {
					if(coordinate.equals(kingsCoordinate)) {
						counter++;
						break;
					}
				}
				if(counter == 1) {
					square.setPiece(piece);
					isCheck = false;
					for(String coordinate: enemyPiece.move()) {	
						if(coordinate.equals(kingsCoordinate)) {
							isCheck = true;
						}
						
					}
					
				}
				square.setPiece(piece);
				
				if(!isCheck && counter == 1) {
					
					String pieceCoordinate = piece.getCoordinates();
					String enemyCoordinate = enemyPiece.getCoordinates();
					ArrayList<String> coordinatesBetween = new ArrayList<>();
					
					int x = letters.indexOf(pieceCoordinate.charAt(0));
					int y = numbers.indexOf(pieceCoordinate.charAt(1));
					
					int xEnemy = letters.indexOf(enemyCoordinate.charAt(0));
					int yEnemy = numbers.indexOf(enemyCoordinate.charAt(1));
					
					int xAdder = 0;
					int yAdder = 0;
					
					if(x>xEnemy)
						xAdder = -1;
					else if(x<xEnemy)
						xAdder = 1;
					else 
						xAdder = 0;
					
					if(y>yEnemy)
						yAdder = -1;
					else if(y<yEnemy)
						yAdder = 1;
					else
						yAdder = 0;
					
					
					for( ; x<8 && y<8 && x>=0 && y>=0 ; x+=xAdder, y+=yAdder) {
						coordinatesBetween.add("" + letters.charAt(x) + numbers.charAt(y));
						if(("" + letters.charAt(x) + numbers.charAt(y))
								.equals("" +letters.indexOf(enemyCoordinate.charAt(0)) +numbers.indexOf(enemyCoordinate.charAt(1))))
							break;	
					}
					
					for(String coordinateOwn: piece.move()) {
						
						if(coordinateOwn.equals(enemyPiece.getCoordinates())) {
							possiblePoints.add(coordinateOwn);
						}
						
						for(String coordinateEnemy: enemyPiece.move()) {
							for(String coordinatesBetweenPieces: coordinatesBetween) {
								if(coordinateOwn.equals(coordinateEnemy) && coordinateOwn.equals(coordinatesBetweenPieces)) {
									possiblePoints.add(coordinateOwn);
								}
							}			
						}
					}
					break;
					
				} else
					counter = 0;
	
			}
			if(!isCheck && counter == 1)
				return possiblePoints;
		}	
			
		return null;
	}

	/*
	 * This method calculates the points that a king go. So basically the king can go the 
	 * squares that are not under danger.
	 */
	public ArrayList<String> calculateSafePoints(ArrayList<Piece> alivePieces, Piece piece, Square squareObject, String kingsCoordinates) {
		
		ArrayList<String> possiblePoints = new ArrayList<>();
		ArrayList<Piece> dangerousPieces = new ArrayList<>();
		
		String letters = "abcdefgh";
		String numbers = "87654321";
		
		
		for(Piece enemy: alivePieces) {
			if(calculateCheck(enemy, kingsCoordinates))
				dangerousPieces.add(enemy);
		}
		
		
		
		for(Piece enemy: dangerousPieces) {
			for(String enemyCoordinate: enemy.move()) {
				int xE = letters.indexOf(enemyCoordinate.charAt(0));
				int yE = numbers.indexOf(enemyCoordinate.charAt(1));
				
				for(String pieceCoordinate: piece.move()) {

					if(squareObject.getBoard().getSquares().get(yE).get(xE).getPiece()!=null &&
							squareObject.getBoard().getSquares().get(yE).get(xE).getPiece().getPieceColor().equals(piece.getPieceColor())) {

						continue;
					}
					
					if(pieceCoordinate.equals(enemyCoordinate)){
						int x = letters.indexOf(pieceCoordinate.charAt(0));
						int y = numbers.indexOf(pieceCoordinate.charAt(1));
						Square square = squareObject.getBoard().getSquares().get(y).get(x);
						square.setPiece(piece);

						if(!calculateCheck(enemy, kingsCoordinates)) {
							possiblePoints.add(pieceCoordinate);
						}
						
						square.cleanPanel();
					} else if(pieceCoordinate.equals(enemyCoordinate) || pieceCoordinate.equals(enemy.getCoordinates())) {
						possiblePoints.add(pieceCoordinate);
					}
					
				}
			}
		}
		
		return possiblePoints;
	}
	
	/*
	 * - Calculate each situation for the each position. if there is no thread, add the point.
	 * - If the coordinate is equal to the danger zone, cant move there.
	 */
	public ArrayList<String> calculateKingsMove(ArrayList<Piece> aliveEnemyPieces, Piece piece, Square square) {
		
		ArrayList<String> possiblePoints = new ArrayList<>();
		
		boolean flag = true;
		
		for(String coordinates: piece.move()) {
			
			flag = true;
			
			for(Piece enemyPiece: aliveEnemyPieces) {
				for(String enemyCoordinates: enemyPiece.move()) {
					if(coordinates.equals(enemyCoordinates)) {
						flag = false;
					} 
				}
			}
			
			if(flag) 
				possiblePoints.add(coordinates);
			
		}
		
		return possiblePoints;
	}
	
}
