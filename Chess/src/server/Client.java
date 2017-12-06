package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import game.Board;
import game.GUImanager;

/*
 * Client class responsible to connect a server on a given
 * ip and port number and also, it always sends "test" string to a server
 * when its connected to the server.
 */
public class Client {

	/*
	 * Socket object.
	 */
	private Socket socket;
	
	/*
	 * Objects to take input and send output.
	 */
	private PrintWriter out;
	private BufferedReader in;
	
	/*
	 * GUImanager and board connection to do all the playing new 
	 * piece operations.
	 */
	private Board board;
	private GUImanager guiManager;
	
	/*
	 * ConnectionProtocol object.
	 */
	private ConnectionProtocol connectionProtocol;
	
	/*
	 * Some variables.
	 */
	private String serverIp;
	private int portNumber;	
	private boolean connection = true;
	
	/*
	 * Constructor takes some parameter and assigns them into
	 * their representations in this class. Then, creates a 
	 * connection method and starts the connect server thread.
	 */
	public Client(Board board, GUImanager guiManager, String serverIp, int portNumber) {
		this.board = board;
		this.guiManager = guiManager;
		this.serverIp = serverIp;
		this.portNumber = portNumber;
		
		connectionProtocol = new ConnectionProtocol(board);
		
		ConnectServer connectServer = new ConnectServer();
		connectServer.start();
		
	}
	
	/*
	 * This class is responsible to connect a socket 
	 * and start listening on that sever. After connecting the server, 
	 * it waits the server to play. So that, server is always going 
	 * to be the white player.
	 */
	class ConnectServer extends Thread{
		@Override
		public void run() {
			try {
				socket = new Socket(serverIp, portNumber);
				guiManager.getLogArea().append(("- Connected to server. " + "\n"));
				
				out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				board.setPrintWriter(out);
				
				String inputLine = "test";
				
				while(connection) {

					if(inputLine==null) {
						guiManager.getLogArea().append("- Your opponent disconnected. \n");
						guiManager.setDefaultMultiplayerButtonProperties();
						break;
					}
					
					while((inputLine = in.readLine())!=null) {
						if(inputLine.equals("test"))
							break;
						connectionProtocol.play(inputLine);
						out.println("test");
					}
					
				}
				guiManager.getLogArea().append(("- Disconnected from server on port " + portNumber + "\n" + "with ip " + serverIp + "\n"));
				socket.close();
			} catch (ConnectException e) {
				guiManager.getLogArea().append(("- There is no server on " + portNumber + "\n"));
				guiManager.setDefaultMultiplayerButtonProperties();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * This method closes the socket connection.
	 */
	public void close() throws IOException{
		socket.close();
	}

}
