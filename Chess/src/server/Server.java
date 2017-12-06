package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import game.Board;
import game.GUImanager;

/*
 * Server class responsible to open the server on a given
 * port and also, it always sends "test" string to a client
 * when its connected to the server.
 */
public class Server {
	
	/*
	 * ServerSocket and Socket objects.
	 */
	private ServerSocket serverSocket;
	private Socket socket;
	
	/*
	 * StartServer object.
	 */
	private StartServer startServer;
	
	/*
	 * Connection protocol object.
	 */
	private ConnectionProtocol connectionProtocol;
	
	/*
	 * Board object. 
	 */
	private Board board;
	
	/*
	 * GUImanager reference to print the results on the 
	 * board directly.
	 */
	private GUImanager guiManager;
	
	/*
	 * Some variables.
	 */
	private int portNumber;
	private boolean connection = true;
	
	/*
	 * Constructor takes some parameter and assigns them into
	 * their representations in this class. Then, creates a 
	 * connection method and starts the server thread.
	 */
	public Server(Board board, GUImanager guiManager, int portNumber) {
		this.board = board;
		this.guiManager = guiManager;
		this.portNumber = portNumber;
		
		connectionProtocol = new ConnectionProtocol(board);
		
		startServer = new StartServer();
		startServer.start();
		
	}
	
	/*
	 * This class is responsible to open the server socket 
	 * and start listening on that sever. If anyone connected 
	 * to the server, then the run method make the connection 
	 * and wait them to play.
	 */
	class StartServer extends Thread {
		
		@Override
		public void run() {
			try{
				serverSocket = new ServerSocket(portNumber);
				
				guiManager.getLogArea().append("- Server started to listen on the port " + portNumber + "\n");

				socket = serverSocket.accept();
				
				while(true) {
					try {
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						
						board.setPrintWriter(out);
						
						String inputLine = "test";
						int counter = 0;
						
						while(connection) {
							
							out.println("test");
							
							if(inputLine==null) {
								guiManager.getLogArea().append("- Your opponent disconnected. \n");
								guiManager.setDefaultMultiplayerButtonProperties();
								
								break;
							}
							
							while((inputLine = in.readLine())!=null) {
								if(inputLine.equals("test")) {
									if(counter == 0) {
										guiManager.getLogArea().append("- Your opponent connected. \n");
										counter++;
									}
									break;
								}
								
								connectionProtocol.play(inputLine);
								
								out.println("test");
							}

							
							
						}
						guiManager.getLogArea().append("- Server closed without a problem.\n");
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch(IOException e) { 
				guiManager.getLogArea().append("- Can't open that server.\n");
				guiManager.setDefaultMultiplayerButtonProperties();
			}
		}
	}
	
	/*
	 * This method is closing the server.
	 */
	public void close() throws IOException {
		serverSocket.close();
		startServer.stop();
	}
}
