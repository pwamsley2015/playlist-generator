package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import util.Scripts;

public class MainFrame extends JFrame implements ActionListener {

	public static final int WIDTH = 400, HEIGHT = 400; 
	private static final String[] EMPTY_STRING_ARRAY = {}; 
	
	private JTextArea textArea; 
	private ArrayList<String> artistList = new ArrayList<>(); 
	
	public MainFrame() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		Container contentPane = getContentPane(); 
		contentPane.setLayout(new BorderLayout());
		
		JButton testButton = new JButton("add"); 
		testButton.setActionCommand("pressed");
		testButton.addActionListener(this); 
		
		JButton playlistButton = new JButton("make playlist"); 
		playlistButton.setActionCommand("make");
		playlistButton.addActionListener(this);
		
		Box boxAroundButton = Box.createHorizontalBox(); 
		boxAroundButton.add(testButton); 
		
		Box boxAroundOtherButton = Box.createVerticalBox(); 
		boxAroundButton.add(playlistButton); 
		
		textArea = new JTextArea(); 
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		Box boxAroundTextArea = Box.createHorizontalBox(); 
		boxAroundTextArea.add(textArea); 
		
		contentPane.add(boxAroundTextArea, BorderLayout.PAGE_START); 
		contentPane.add(boxAroundButton, BorderLayout.LINE_START); 
		contentPane.add(boxAroundOtherButton, BorderLayout.LINE_END); 
	
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); 
		
		if (command.equals("pressed")) {
			artistList.add(textArea.getText());  
			textArea.setText("");
		} 
		if (command.equals("make")) 
			Scripts.createPlaylistByArtist("test", artistList.toArray(EMPTY_STRING_ARRAY));
	}
}
