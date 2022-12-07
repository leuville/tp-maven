package com.rlc.jtetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import com.rlc.jtetris.bean.Bloc;
import com.rlc.jtetris.bean.Grille;

public class NextBlocComponent extends JComponent {
	
	private static final long serialVersionUID = 1341733303209137839L;
	
	private Grille grille;
	
	public NextBlocComponent(Grille grille) {
		super();
		this.grille = grille;
	}
	
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		
		g.setColor(Color.red);
		g.fillRect(0, 5, getWidth()-1, getHeight()-6);
		g.setColor(Color.black);
		g.fillRect(3,8, getWidth()-7,getHeight()-13);
		g.fillRect(5,0, getWidth() / 2, 8);
		g.setColor(Color.white);
		char[] msg = "Next".toCharArray();
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		g.drawChars(msg, 0, msg.length, 10, 12);
		
		if (grille != null) {
			Bloc bloc = grille.getNextBloc();
			
			if (bloc != null) {
				final int largeurBloc = bloc.getLargeur();
				final int hauteurBloc = bloc.getHauteur();
				final int largeurCase = 15;
				final int hauteurCase = 15;
				
				int x = (getWidth() - (largeurBloc * largeurCase)) / 2;
				int y = (getHeight() - (hauteurBloc * hauteurCase)) / 2;
				
				int[] parts = bloc.getParts();
				int i= 0;
				for (int part : parts) {
					for (int j=0; j<largeurBloc; j++) {
						if ((part & (1 << j)) > 0) {
							
							// dessin du bloc
							g.setColor(bloc.getCouleur());
							graphics.fill3DRect((int) (x + ((largeurBloc - 1 - j) * largeurCase)), 
												(int) (y + (i * hauteurCase)),(int) largeurCase, (int) hauteurCase, true);
							g.setColor(Color.black);
							graphics.drawRect((int) (x + ((largeurBloc - 1 - j) * largeurCase)), 
									(int) (y + (i * hauteurCase)),(int) largeurCase, (int) hauteurCase);
						}
					}		
					i++;
				}
			}
		}
	}

}
