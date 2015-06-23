package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.JTextArea;

public class AutoFillSuggestionArea extends JTextArea { 
	
	private static BasicStroke stroke = new BasicStroke(2.5f); 
	
	private int highlighArea = -1;
	private final static int deltaY = 16; 
	
	public void setHighLightArea(int newArea) {
		highlighArea = newArea; 
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		drawHighlight(g);
	}
	
	private void drawHighlight(Graphics g) {
		
		if (highlighArea == -1)
			return; 
		
		Graphics2D g2d = (Graphics2D)g; 
		
		g2d.setStroke(stroke);
		g2d.setColor(Color.CYAN);
		g2d.drawRect(0, highlighArea * deltaY, 230, deltaY);
	}
}
