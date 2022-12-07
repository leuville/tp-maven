package com.rlc.jtetris.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

import com.rlc.jtetris.Constantes;
import com.rlc.jtetris.bean.Bloc;
import com.rlc.jtetris.bean.Grille;

public class GrillePanel extends JComponent implements ActionListener {

	private static final long serialVersionUID = 7195859300331067745L;

	private Grille grille;
	
	private Timer timerMvt;
	private Timer timerChute;
	
	private BufferedImage fond;
	
	private int difficulte;
	
	private boolean gameStarted;
	
	public GrillePanel(Grille grille) {
		super();
		this.grille = grille;
		difficulte = 1;
		
		try {
			fond = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("place_rouge2.jpg"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void startGame() {
		difficulte = 1;
		grille.reinit();
		grille.start();
		
		initTimerChute();
		if (timerMvt == null) {
			timerMvt = new Timer(50, this);
		}
		
		timerChute.start();
		timerMvt.start();
		gameStarted = true;
	}
	
	private void initTimerChute() {
		if (timerChute != null) {
			timerChute.stop();
			timerChute = null;
		}
		
		timerChute = new Timer(1000 / difficulte, this);
	}
	
	public void stopGame() {
		if (timerMvt != null) {
			timerMvt.stop();
			timerMvt = null;
		}
		if (timerChute != null) {
			timerChute.stop();
			timerChute = null;
		}
		
		gameStarted = false;
	}
	
	public void paint(Graphics g) {
		
		Graphics2D graphics = (Graphics2D) g;
		
		final int width = getWidth();
		final int height = getHeight();
		final float largeurCase = ((float) width / (float) (Constantes.NB_COLONNES + 2));
		final float hauteurCase = ((float) height / (float) (Constantes.NB_TOTAL_LIGNES + 1));
		
		// image de fond
		if (fond == null) {
			g.setColor(Color.BLACK);
			graphics.fillRect(0, 0, width, height);
		} else {
			g.drawImage(fond, 0, 0, width, height, Color.BLACK, null);
		}
		
		// Affichage du cadre
		g.setColor(Color.RED);
		graphics.fillRect(0, 0, (int) largeurCase, height);
		graphics.fillRect((int) (width - largeurCase + 0.5f), 0, (int) (largeurCase + 0.5f), height);
		graphics.fillRect(0, (int) (height - hauteurCase + 0.5f), width, (int) (hauteurCase + 0.5f));
		
		// Dessin des lignes
		int[] lignes = grille.getLignes();
		int indexLigne=0;

		for (int ligne : lignes) {			
			if (ligne > 0) {
				int y = (int) ((indexLigne * hauteurCase) + 0.5f);
				for (int i = 0; i < Constantes.NB_COLONNES; i++) {
					int x = (int) ((width - ((2 + i)*largeurCase)) + 0.5f);
					if ((ligne & (1 << i)) > 0) {
						g.setColor(grille.getCouleurs()[indexLigne][i]);
						graphics.fill3DRect(x, y, (int) largeurCase, (int) hauteurCase, true);
						g.setColor(Color.black);
						graphics.drawRect(x, y, (int) largeurCase, (int) hauteurCase);
					}
				}
			}
			
			indexLigne++;
		}
		
		// Dessin du bloc courant
		Bloc bloc = grille.getBlocCourant();
		
		if (bloc != null) {
			final int largeurBloc = bloc.getLargeur();
			
			int x = (int) (width - ((grille.getPosXBloc() + 1 + largeurBloc) * largeurCase + 0.5f));
			int y = (int) ((grille.getPosYBloc() * hauteurCase) + 0.5f);
			int yGhost = (int) ((grille.getPosYGhost() * hauteurCase) + 0.5f);
			
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
						
						// dessin du ghost
						g.setColor(bloc.getCouleur().brighter());
						graphics.drawRect((int) (x + ((largeurBloc - 1 - j) * largeurCase)), 
								(int) (yGhost + (i * hauteurCase)),(int) largeurCase, (int) hauteurCase);
					}
				}		
				i++;
			}
		}
		
		if (!gameStarted) {
			g.setColor(Color.BLACK);
			g.fillRect(5, getHeight() / 2 - 20, getWidth()-10, 40);
			
			char[] msg = "<ESPACE> pour démarrer".toCharArray();
			Font font = new Font("Aria", Font.BOLD, 15);
			g.setColor(Color.white);
			g.setFont(font);
			graphics.drawChars(msg, 0, msg.length, 14, getHeight() / 2);
		}
	}

	public synchronized void actionPerformed(ActionEvent event) {
		if (event.getSource() == timerChute)
			grille.descendreBloc();
		repaint();
	}

	public synchronized void decalerBlocADroite() {
		grille.decalerBlocADroite();
	}

	public synchronized void decalerBlocAGauche() {
		grille.decalerBlocAGauche();
	}

	public synchronized void descendreBloc() {
		grille.descendreBloc();
	}

	public synchronized void effectuerRotationDirecteBloc() {
		grille.effectuerRotationDirecteBloc();
	}

	public synchronized void effectuerRotationIndirecteBloc() {
		grille.effectuerRotationIndirecteBloc();
	}

	public synchronized void faireChuterBloc() {
		grille.faireChuterBloc();
	}

	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
		initTimerChute();
		timerChute.start();
	}
	
	

}
