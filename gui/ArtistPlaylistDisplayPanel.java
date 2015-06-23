package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import util.Scripts;

public class ArtistPlaylistDisplayPanel extends JPanel implements ActionListener {

	private static final String[] EMPTY_STRING_ARRAY = {}; 
	
	private ArrayList<String> artistList; 
	private JTextArea titleArea; 
	private JTextArea displayArea; 
	
	private static final int WIDTH = 300, HEIGHT = 400;
	
	public ArtistPlaylistDisplayPanel() {
		
		artistList = new ArrayList<>(); 
		
		setSize(WIDTH, HEIGHT); 
		setBackground(Color.RED); //nice and ugly so we can see it for testing.  
		setLayout(new BorderLayout());
		
		Box box = Box.createVerticalBox(); 
		Box boxAroundButton = Box.createHorizontalBox(); 
		
		JButton createPlaylistButton = new JButton("Create Playlist"); 
		createPlaylistButton.setActionCommand("make");
		createPlaylistButton.addActionListener(this);
		
		titleArea = new JTextArea();
		
		box.add(titleArea);
		boxAroundButton.add(createPlaylistButton); 
		box.add(boxAroundButton); 
		
		//now the display Area
		
		displayArea = new JTextArea("Current Artists Added:\n", 10, 8);
		displayArea.setEditable(false); 
		
		add(displayArea, BorderLayout.NORTH);
		add(box, BorderLayout.PAGE_END); 
		
	}
	
	public void addArtistName(String artistName) {
		artistList.add(artistName); 
		displayArea.append(artistName + "\n"); 
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawString("Title:", 2, 331); 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand(); 
		
		if (command.equals("make")) 
			Scripts.createPlaylistByArtist(titleArea.getText(), artistList.toArray(EMPTY_STRING_ARRAY));
	}
	@Override
	public Dimension getSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public Dimension getMinimumSize() {
		return getSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getSize();
	}

	@Override
	public Dimension getPreferredSize() {
		return getSize();
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}
