package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/*
 * This class shows the developers notes.
 */
public class AboutFrame extends JFrame {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Constructor.
	 */
	public AboutFrame() {
		super("About");
		
		initialize();
	}
	
	/*
	 * Initialize the GUI features.
	 */
	private void initialize() {
		setSize(300, 100);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new BorderLayout());
		
		String text =
				"<html>"
				+ "<p>This program was developed by M.Ekrem ERAKIN.</p>"
				+ "<p>My blog: www.ekremerakin.wordpress.com</p>"
				+ "<p>My github: https://github.com/ekremerakin</p>"
				+ "</html>";
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		add(label);
		
		setVisible(true);
	}
}
