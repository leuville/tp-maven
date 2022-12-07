package com.rlc.jtetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

public class NbLignesComponent extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3836374843215022065L;
	private int nbLignes;
	
	public NbLignesComponent() {
		super();
		setForeground(Color.white);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(new Font("Arial", Font.BOLD, 20));
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(0, 5, getWidth()-1, getHeight()-6);
		g.setColor(Color.black);
		g.fillRect(3,8, getWidth()-7,getHeight()-13);
		g.fillRect(5,0, (int) (getWidth() / 1.5), 8);
		g.setColor(Color.white);
		char[] msg = "Lignes".toCharArray();
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawChars(msg, 0, msg.length, 10, 12);
		
		setText(String.valueOf(nbLignes));
		super.paint(g);
		
		
	}

	public int getNbLignes() {
		return nbLignes;
	}

	public void setNbLignes(int nbLignes) {
		this.nbLignes = nbLignes;
		repaint();
	}

}
