package utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Piece class is an abstract class. It helps other
 * classes to take some of the essential features. And
 * another specific features that a piece need to have 
 * declared in other child classes. 
 */
public abstract class Piece {

	/*
	 * possiblePoints array list. Used to store 
	 * the coordinates for a piece to go.
	 */
	ArrayList<String> possiblePoints = null;
	
	/*
	 * 3 essential variables to show the coordinate,
	 * piece's color and its name.
	 */
	String coordinates;
	String whichColor;
	String pieceName;
	
	boolean isAlive = true;

	
	/*
	 * Constructor.
	 * - Creates pieceName from the class names and piece's color.
	 */
	public Piece(String whichColor, String coordinates) {
		this.whichColor = whichColor.toUpperCase();
		this.coordinates = coordinates;
		pieceName = getClass().getSimpleName() + whichColor;
	}
	
	/*
	 * Creates a label for a Piece. Assets are
	 * taking from the assets folder and displays 
	 * via BufferedImage class.
	 */
	public JLabel getLabel() {
		JLabel label = null;
		try {
			String adress = "/assets/" + pieceName + ".png";
			BufferedImage bfi = ImageIO.read(getClass().getResource(adress));
			ImageIcon imageIcon = new ImageIcon(bfi);
			label = new JLabel(imageIcon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return label;
	}
	
	/*
	 * Encapsulated variables.
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public String getPieceName() {
		return pieceName;
	}
	
	public String getPieceColor() {
		return whichColor;
	}
	
	public void died() {
		isAlive = false;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	
	/*
	 * Abstract section.
	 */
	public abstract ArrayList<String> move();
}
