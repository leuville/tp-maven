package com.rlc.jtetris.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.rlc.jtetris.Constantes;
import com.rlc.jtetris.bean.Grille;
import com.rlc.jtetris.bean.GrilleListener;

public class JTetrisFrame extends JFrame implements KeyListener, GrilleListener {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	
	private GrillePanel grillePanel;
	private NextBlocComponent nextBlocComponent;
	private NbLignesComponent nbLignesComponent;
	private ScoreComponent scoreComponent;
	
	private Grille grille;
	
	private boolean gameStarted;

	/**
	 * This is the default constructor
	 */
	public JTetrisFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(450, 490);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("JTetris");
		
		grille = new Grille();
		
		add(getGrillePanel());
		add(getNextBlocComponent());
		add(getNbLignesComponent());
		add(getScoreComponent());
		
		addKeyListener(this);
		grille.addGrilleListener(this);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.BLACK);
		}
		return jContentPane;
	}
	
	public static void main(String[] args) {
		JTetrisFrame frame = new JTetrisFrame();
		frame.setVisible(true);
	}

	public GrillePanel getGrillePanel() {
		if (grillePanel == null) {
			grillePanel = new GrillePanel(grille);
			grillePanel.setBounds(0, 0, (Constantes.NB_COLONNES + 2) * 15, (Constantes.NB_TOTAL_LIGNES + 1) * 15);
		}
		
		return grillePanel;
	}
	
	public NextBlocComponent getNextBlocComponent() {
		if (nextBlocComponent == null) {
			nextBlocComponent = new NextBlocComponent(grille);
			nextBlocComponent.setBounds(getGrillePanel().getWidth() + 5, 5, 100, 100);
		}
		
		return nextBlocComponent;
	}
	
	public NbLignesComponent getNbLignesComponent() {
		if (nbLignesComponent == null) {
			nbLignesComponent = new NbLignesComponent();
			nbLignesComponent.setBounds(getNextBlocComponent().getX() + getNextBlocComponent().getWidth() + 5, 5, 100, 100);
		}
		
		return nbLignesComponent;
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !gameStarted) {
			nbLignesComponent.setNbLignes(0);
			grillePanel.startGame();
			gameStarted = true;
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_UP && gameStarted)
			grillePanel.effectuerRotationDirecteBloc();
		else if (e.getKeyCode() == KeyEvent.VK_LEFT && gameStarted)
			grillePanel.decalerBlocAGauche();
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT && gameStarted)
			grillePanel.decalerBlocADroite();
		else if (e.getKeyCode() == KeyEvent.VK_SPACE && gameStarted)
			grillePanel.faireChuterBloc();
		else if (e.getKeyCode() == KeyEvent.VK_DOWN && gameStarted)
			grillePanel.descendreBloc();
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void nouveauBloc() {
		getNextBlocComponent().repaint();
	}

	public void gameOver() {
		getGrillePanel().stopGame();
		getGrillePanel().repaint();
		gameStarted = false;
	}

	public void lignesCompletes(int nbLignes) {
		int nbLignesActuel = getNbLignesComponent().getNbLignes() + nbLignes;
		getNbLignesComponent().setNbLignes(nbLignesActuel);
		
		int level = (nbLignesActuel / 10) + 1;
		
		grillePanel.setDifficulte(level);
		
		int points = 
			nbLignes == 1 ? 100 :
			nbLignes == 2 ? 250 :
			nbLignes == 3 ? 500 :
			1000;
		
		points *= level;
		
		scoreComponent.addToScore(points);
	}

	public ScoreComponent getScoreComponent() {
		if (scoreComponent == null) {
			scoreComponent = new ScoreComponent();
			scoreComponent.setBounds(getNextBlocComponent().getX() , getNextBlocComponent().getY() + + getNextBlocComponent().getHeight() + 5, 200, 100);
		}
		
		return scoreComponent;
	}
}
