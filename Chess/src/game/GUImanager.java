package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * This class is responsible for creating the 
 * GUI and initializing its main properties.
 */
public class GUImanager extends JFrame {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 4389487919219922486L;

	/*
	 * Chess game referance to start new games.
	 */
	private ChessGame chessGame;
	
	/*
	 * Board object.
	 */
	private Board board;
	
	/*
	 * 2D ArrayList to hold the 8x8 Square objects.
	 */
	private ArrayList<ArrayList<Square>> squares;
	
	/*
	 * Game panel object.
	 */
	private JPanel chessBoardPanel;
	
	private JPanel upperPanel;
	
	private JPanel settingsPanel;
	
	private JTextArea logArea;
	
	private JButton startButton, closeButton, 
		connectButton, disconnectButton;
	
	/*
	 * Constructor takes the board object as an input and 
	 * initializes all the window properties.
	 */
	public GUImanager(ChessGame chessGame, Board board) {
		this.chessGame = chessGame;
		this.board = board;
		
		TurnManager.addGUI(this);
		
		initializeMainFrame();
		initializeUpperPanel();
		initializeChessBoardPanel();
		initializeSettingsPanel();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	/*
	 * This method updates the GUI for a new game.
	 */
	public void updateGUI(Board board) {
		this.board = board;
		TurnManager.addGUI(this);
		TurnManager.startNewGame();
		remove(chessBoardPanel);
		initializeChessBoardPanel();
		revalidate();
		repaint();
		pack();
	}
	
	/*
	 * Setting the properties of the game panel.
	 */
	private void initializeChessBoardPanel() {
		chessBoardPanel = new JPanel();
		chessBoardPanel.setLayout(new GridLayout(9, 10));
		chessBoardPanel.setPreferredSize(new Dimension(777, 700));
		chessBoardPanel.setBackground(Color.BLACK);
		squares = board.getSquares();

		int numberCounter = 9;
		String letterCounter = " abcdefgh";
		
		for(int i=0;i<8;i++) {
			JLabel numberLabel = new JLabel(--numberCounter+"");
			numberLabel.setForeground(Color.WHITE);
			numberLabel.setHorizontalAlignment(JLabel.CENTER);
			chessBoardPanel.add(numberLabel);
			for(int j=0;j<8;j++) {
				chessBoardPanel.add(squares.get(i).get(j));
			}
			JLabel numberLabel2 = new JLabel(numberCounter+"");
			numberLabel2.setForeground(Color.WHITE);
			numberLabel2.setHorizontalAlignment(JLabel.CENTER);
			chessBoardPanel.add(numberLabel2);
		}
		for(int i=0;i<9;i++) {
			JLabel letterLabel = new JLabel(letterCounter.charAt(i)+"");
			letterLabel.setHorizontalAlignment(JLabel.CENTER);
			letterLabel.setForeground(Color.WHITE);
			chessBoardPanel.add(letterLabel);
		}
		add(chessBoardPanel, BorderLayout.CENTER);
	}
	
	private void initializeUpperPanel() {
		upperPanel = new JPanel();
		upperPanel.setBackground(Color.BLACK);
		
		JLabel chessTitle = new JLabel("Chess Game");
		chessTitle.setForeground(Color.WHITE);
		chessTitle.setFont(new Font("", Font.BOLD, 45));
		upperPanel.add(chessTitle);
		
		add(upperPanel, BorderLayout.NORTH);
	}
	
	/*
	 * Setting the properties of the side panel.
	 */
	private void initializeSettingsPanel() {
		JButton buttonPlay, buttonExit, buttonAbout;
		settingsPanel = new JPanel();
		settingsPanel.setBackground(Color.BLACK);
		settingsPanel.setPreferredSize(new Dimension(260, 700));
		settingsPanel.setLayout(new BorderLayout());

		logArea = new JTextArea();
		logArea.setEditable(false);
		settingsPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		//Game Tab
		JPanel gameTab= new JPanel();
		gameTab.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagC = new GridBagConstraints();
		gridBagC.gridwidth = GridBagConstraints.REMAINDER;
		gridBagC.anchor = GridBagConstraints.CENTER;
		gridBagC.fill = GridBagConstraints.HORIZONTAL;
		gridBagC.insets = new Insets(3, 3, 10, 3);
		
		JPanel buttons = new JPanel(new GridBagLayout());
		
		buttonPlay = new JButton("Play");
		buttonPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chessGame.startNewGame();
			}
		});
		buttons.add(buttonPlay, gridBagC);
		
		buttonAbout = new JButton("About");
		buttonAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutFrame();
			}
		});
		buttons.add(buttonAbout, gridBagC);
		
		buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttons.add(buttonExit, gridBagC);
		gridBagC.weighty = 1;
		
		gameTab.add(buttons, gridBagC);
		
		tabbedPane.add("Game", gameTab);
		
		//Multiplayer Tab
		JPanel multiplayerTab= new JPanel();
		multiplayerTab.add(createServerCreationPanel());
		tabbedPane.add("Multiplayer", multiplayerTab);
		settingsPanel.add(tabbedPane, BorderLayout.SOUTH);
		
		add(settingsPanel, BorderLayout.EAST);
	}
	
	private JPanel createServerCreationPanel() {
		
		JPanel panel = new JPanel(new GridLayout(3,1));
		
		JPanel serverPanel = new JPanel(new GridLayout(3, 2));
		
		JLabel portLabelServer = new JLabel("Port: ");
		serverPanel.add(portLabelServer);
		
		JTextField portNumberServer = new JTextField("1500");
		serverPanel.add(portNumberServer);
		
		startButton = new JButton("Start Server");
		serverPanel.add(startButton);
		
		closeButton = new JButton("Close Server");
		closeButton.setEnabled(false);
		serverPanel.add(closeButton);

		
		JPanel clientPanel = new JPanel(new GridLayout(3, 2));

		JLabel portLabelClient = new JLabel("Port: ");
		clientPanel.add(portLabelClient);
		
		JTextField portNumberClient = new JTextField("1500");
		clientPanel.add(portNumberClient);
		
		JLabel ipLabel = new JLabel("Server ip: ");
		clientPanel.add(ipLabel);
		
		JTextField ipNumber = new JTextField("127.0.0.1");
		clientPanel.add(ipNumber);
		
		connectButton = new JButton("Connect");
		clientPanel.add(connectButton);
		
		disconnectButton = new JButton("Disconnect");
		disconnectButton.setEnabled(false);
		clientPanel.add(disconnectButton);

		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chessGame.startNewMultiplayerGame(portNumberServer.getText());
				startButton.setEnabled(false);
				closeButton.setEnabled(true);
				connectButton.setEnabled(false);
				disconnectButton.setEnabled(false);
				
			}
		});

		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					chessGame.closeServer();
					setDefaultMultiplayerButtonProperties();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				chessGame.joinNewMultiplayerGame(ipNumber.getText(), portNumberClient.getText());
				startButton.setEnabled(false);
				closeButton.setEnabled(false);
				connectButton.setEnabled(false);
				disconnectButton.setEnabled(true);
			}
		});
		
		disconnectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					chessGame.closeServer();
					setDefaultMultiplayerButtonProperties();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(serverPanel);
		panel.add(clientPanel);
		
		return panel;
	}
	
	/*
	 * This method sets the buttons for their default 
	 * positions when a server closed or client exits.
	 */
	public void setDefaultMultiplayerButtonProperties() {
		chessGame.startNewGame();
		startButton.setEnabled(true);
		closeButton.setEnabled(false);
		connectButton.setEnabled(true);
		disconnectButton.setEnabled(false);
	}
	
	
	/*
	 * Setting some extra properties for main frame.
	 */
	private void initializeMainFrame() {
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public JTextArea getLogArea() {
		return logArea;
	}
	

	
}
