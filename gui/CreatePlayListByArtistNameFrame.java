package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

import util.ArtistLookUpTool;

public class CreatePlayListByArtistNameFrame extends JFrame {
	
	public CreatePlayListByArtistNameFrame() {
		setSize(450, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane(); 
		contentPane.setLayout(new BorderLayout());
		
		ArtistPlaylistDisplayPanel artistDisplay = new ArtistPlaylistDisplayPanel(); 
		final ArtistChooserPanel chooser = new ArtistChooserPanel(artistDisplay); 
		
		new Thread(new Runnable() {
			@Override 
			public void run() {
				while(true) {
					
					repaint(); 
					chooser.updateAutoFillerSuggestions(); 
					
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start(); 
		
		contentPane.add(artistDisplay, BorderLayout.CENTER); 
		contentPane.add(chooser, BorderLayout.WEST); 
		
		setVisible(true);
	}

	
	public static void main(String[] args) {
		
		ArtistLookUpTool.setUp(); 
		new CreatePlayListByArtistNameFrame(); 
	}
}
