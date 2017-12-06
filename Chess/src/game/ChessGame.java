package game;

import java.io.IOException;

import javax.swing.SwingUtilities;

import server.Client;
import server.Server;

/*
 * This program was written by Ekrem ERAKIN and for more 
 * information, feel free to visit my blog  
 * www.ekremerakin.wordpress.com
 * 
 * This class is the main class. It creates the board, 
 * players and the GUI. It also can start a new offline game
 * or a new multiplayer game.
 */
public class ChessGame {
	
	/*
	 * 3 essential class' objects.
	 */
	private Board board;
	private Player whitePlayer, blackPlayer;	
	private GUImanager guiManager;
	
	/*
	 * Server and client objects.
	 */
	private Server server = null;
	private Client client = null;
	
	/*
	 * Default constructor creates the main objects for this 
	 * program like the board and players. 
	 */
	public ChessGame() {
		
		board = new Board();

		whitePlayer = new Player("src/utilities/whiteDefaultPositions.txt", board, "W");
		blackPlayer = new Player("src/utilities/blackDefaultPositions.txt", board, "B");

		guiManager = new GUImanager(this, board);
	
		TurnManager.getGuiManager().getLogArea().append("- New game had been started.\n------\n");
	}
	
	/*
	 * Starts new game with the default positions of the pieces. But method
	 * does not create a new guiManager object, instead updates it.
	 */
	public void startNewGame() {
		
		board = new Board();
		
		whitePlayer = new Player("src/utilities/whiteDefaultPositions.txt", board, "W");
		blackPlayer = new Player("src/utilities/blackDefaultPositions.txt", board, "B");

		guiManager.updateGUI(board);
		TurnManager.getGuiManager().getLogArea().append("- New game had been started.\n------\n");
	}
	
	/*
	 * Starts a new multiplayer game and creates the server and wait a 
	 * client to connect.
	 */
	public void startNewMultiplayerGame(String portNumber) {

		startNewGame();
		whitePlayer.setMultiplayerSituation("server");
		blackPlayer.setMultiplayerSituation("");
		
		server = new Server(board, guiManager, Integer.parseInt(portNumber));
		TurnManager.getGuiManager().getLogArea().append("- New multiplayer game had been started.\n------\n");
	}
	
	/*
	 * Starts a new multiplayer game and connects to a socket with server
	 * ip and port number.
	 */
	public void joinNewMultiplayerGame(String serverIp, String portNumber) {

		startNewGame();
		blackPlayer.setMultiplayerSituation("client");
		
		client = new Client(board, guiManager, serverIp, Integer.parseInt(portNumber));
		TurnManager.getGuiManager().getLogArea().append("- New multiplayer game had been started.\n------\n");
	}
	
	/*
	 * This method close the server or the client.
	 */
	public void closeServer() throws IOException {
		if(server!=null) {
			server.close();
			guiManager.getLogArea().append("- Server closed.\n");
		}
		if(client!=null) {
			client.close();
			guiManager.getLogArea().append("- Disconnected from the server.\n");
		}
	}
	
	/*
	 * Main method. 
	 * -Creates an instance for this class and runs the program. 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ChessGame();
			}
		});
	}
}
