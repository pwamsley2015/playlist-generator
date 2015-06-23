package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import util.ArtistLookUpTool;
import util.AutoFiller;
import util.AutoFiller.Type;

public class ArtistChooserPanel extends JPanel implements ActionListener {

	private static final String[] EMPTY_STRING_ARRAY = {};

	private ArtistPlaylistDisplayPanel displayReferance; 
	private AutoFiller autoFiller; 
	
	private JTextArea typeArea; 
	private AutoFillSuggestionArea autoFillSuggestions; 

	private int autoFillSelectionPos = -1; 

	public ArtistChooserPanel(ArtistPlaylistDisplayPanel display) {

		this.displayReferance 	= display; 
		this.autoFiller 		= new AutoFiller(Type.ARTISTS, 15); 

		setSize(250, 400); 
		setBackground(Color.BLUE); 

		setLayout(new BorderLayout());

		JButton addButton = new JButton("Add Artist"); 
		addButton.setActionCommand("add");
		addButton.addActionListener(this);

		Box boxAroundButton = Box.createHorizontalBox(); 
		boxAroundButton.add(addButton); 

		typeArea = new JTextArea(1, 20); 
		typeArea.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode(); 

				switch (key) {

					case KeyEvent.VK_ENTER: 

						e.consume();

						/* 
						 * If none of the suggestings are highlighted, 
						 * then pressing the enter keys adds whatever 
						 * is in the typeArea to the playlist gen
						 */
						if (autoFillSelectionPos == -1) {
							addArtistName(typeArea.getText());
							return; 
						}
						
						/* \n
						 * artist1\n
						 * artist2\n
						 * artist3\n
						 * \n
						 */
						String text = "\n" + autoFillSuggestions.getText() + '\n';
								
						int numLines = 0, pos = 0; 
						while (numLines <= autoFillSelectionPos) {
							if (text.charAt(pos) == '\n')
								numLines++; 
							pos++; 
						}
						
						//pos = beginning index of the line we care about
						
						//get rid of everything before that line
						text = text.substring(pos); 
						//and now just get that line
						text = text.substring(0, text.indexOf('\n')); 
									
						typeArea.setText(text); 
						autoFillSelectionPos = -1;
						break;

					case KeyEvent.VK_UP: 

						e.consume();

						if (autoFillSelectionPos == -1)
							return; 

						autoFillSelectionPos--; 
						break; 
					case KeyEvent.VK_DOWN:

						e.consume();

						int numberLines = 0; 
						for (char c : autoFillSuggestions.getText().toCharArray())
							if (c == '\n')
								numberLines++; 
						if (autoFillSelectionPos == numberLines)
							return; 

						autoFillSelectionPos++; 
						break; 
					default:
						//Don't consume... just make sure to reset this.
						autoFillSelectionPos = -1; 
						break; 
				}
			}

			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
		});

		Box boxAroundTypeArea = Box.createVerticalBox(); 
		boxAroundTypeArea.add(typeArea); 

		Box boxAroundSuggestions = Box.createVerticalBox(); 
		autoFillSuggestions = new AutoFillSuggestionArea(); 

		boxAroundSuggestions.add(autoFillSuggestions); 

		Box boxAroundTypingAreaAndSuggestions = Box.createVerticalBox(); 

		//		boxAroundTypingAreaAndSuggestions.add(boxAroundTypeArea); 
		//		boxAroundTypingAreaAndSuggestions.add(boxAroundSuggestions); 
		//		
		//		add(boxAroundTypingAreaAndSuggestions, BorderLayout.CENTER); 
		add(boxAroundTypeArea, BorderLayout.NORTH); 
		add(boxAroundSuggestions, BorderLayout.CENTER); 
		add(boxAroundButton, BorderLayout.SOUTH);

	}
	
	public void updateAutoFillerSuggestions() {

		//not sure why I'm working in arrays here... I think there used to be a good reason?
		String[] autoFillerResults = autoFiller.get(typeArea.getText()).toArray(EMPTY_STRING_ARRAY); 

		autoFillSuggestions.setText("");

		for (int i = 0; i < autoFillerResults.length; i++) 
			autoFillSuggestions.append(autoFillerResults[i] + "\n"); 
		
		// Update the highlight area
		autoFillSuggestions.setHighLightArea(autoFillSelectionPos);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); 

		if (command.equals("add")) 
			addArtistName(typeArea.getText());

	}

	private void addArtistName(String artistName) {

		if (!(ArtistLookUpTool.getCompleteArtistList().contains(artistName))) {
			getToolkit().beep(); 
			return; 
		}

		displayReferance.addArtistName(typeArea.getText());
		typeArea.setText("");
		autoFiller.reset(); 

	}

	@Override
	public Dimension getSize() {
		return new Dimension(250, 400);
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
		return 250;
	}

	@Override
	public int getHeight() {
		return 400;
	}
}
