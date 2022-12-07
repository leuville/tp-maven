package com.rlc.jtetris.bean;

import static com.rlc.jtetris.Constantes.NB_COLONNES;
import static com.rlc.jtetris.Constantes.NB_TOTAL_LIGNES;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.rlc.jtetris.factory.BlocFactory;
import com.rlc.jtetris.factory.RandomBlocFactory;
import com.rlc.jtetris.util.LigneUtil;

/**
 * Bean représentant une grille du tetris.
 * Une grille est composée de 13 colonnes et de 29 lignes.
 *  
 * @author richard
 * @version 1.0.0
 * @since 1.0.0
 */
public class Grille {
    
    /** Les lignes */
    private int[] lignes = new int[NB_TOTAL_LIGNES];
    
    private Color[][] couleurs = new Color[NB_TOTAL_LIGNES][NB_COLONNES];
    
    private List<GrilleListener> listeners = new ArrayList<GrilleListener>(2);
    
    /** Bloc en cours de chute */
    private Bloc blocCourant;
    
    private Bloc nextBloc;
    
    /** 
     * Position X du bloc courant 
     * (ie de sa partie la plus à gauche)
     * 0 = complètement à droite
     * */
    private int posXBloc;
    
    /** 
     * Position Y du bloc courant 
     * (ie de sa dernière part)
     * 0 = tout en haut
     * */
    private int posYBloc;
    
    private int posYGhost;
    
    private final BlocFactory blocFactory;
    
    /**
     * Constructeur
     */
    public Grille() {
    	reinit();
    	blocFactory = new RandomBlocFactory();
    }
    
    public void start() {
    	nouveauBloc();
    }
    
    public void reinit() {
    	for (int i=0; i < NB_TOTAL_LIGNES; i++) {
    		lignes[i] = 0;
    	}
    }
    
    public synchronized void nouveauBloc() {
    	if (nextBloc != null) {
    		blocCourant = nextBloc;
    	} else {
    		blocCourant = blocFactory.nouveauBloc();
    	}
    	
    	nextBloc = blocFactory.nouveauBloc();
    	
    	for (GrilleListener listener : listeners) {
    		listener.nouveauBloc();
    	}
    	
    	posYBloc = 0;
    	posXBloc = (NB_COLONNES - blocCourant.getLargeur()) / 2;
    	
    	updateGhost();
    	
    	if (!blocEstLibre()) {
    		gameOver();
    	}
    }
    
    private void gameOver() {
    	for (GrilleListener listener : listeners) {
    		listener.gameOver();
    	}
    }
    
    private boolean blocEstLibre() {
    	int[] parts = blocCourant.getParts();
    	final int hauteurBloc = blocCourant.getHauteur();
    	
    	for (int i=0; i<hauteurBloc; i++) {
    		int ligne = lignes[posYBloc + i];
    		int part = parts[hauteurBloc - 1 - i];
    		part = part << (posXBloc);
    		
    		if ((part & ligne) > 0)
    			return false;
    	}
    	
    	return true;
    }
    
    private void updateGhost() {
    	posYGhost = posYBloc;
    	
    	while(blocPeutDescendre(posYGhost)) {
    		posYGhost++;
    	}
    }
    
     /**
      * 
      * @param numeroLigne
      */
    private void supprimerLigne(int numeroLigne) {
    	
    	for (int i=numeroLigne; i > 0; i--) {
    		lignes[i] = lignes[i-1];
    		couleurs[i] = couleurs[i-1];
    	}
    	
    	lignes[0] = 0;
    	couleurs[0] = new Color[NB_COLONNES];
    }
    
    /**
     * 
     * @return
     */
    private boolean blocPeutDescendre(int posY) {
    	int[] parts = blocCourant.getParts();
    	final int hauteurBloc = blocCourant.getHauteur();
    	int indexLigne = 0;
    	
    	if (posY + hauteurBloc >= NB_TOTAL_LIGNES) {
    		return false;
    	}
    	
    	for (int i=0; i<hauteurBloc; i++) {
    		indexLigne = posY + i + 1;
	    	int ligne = lignes[indexLigne];
	    	int part = parts[i];
	    	part = part << posXBloc;
	    	
	    	if ((part & ligne) > 0)
	    		return false; 
    	}
    	
    	return true;
    }
    
    /**
     * 
     * @return
     */
    private boolean blocPeutAllerADroite() {
    	if (posXBloc - 1 < 0)
    		return false;
    	
    	int[] parts = blocCourant.getParts();
    	final int hauteurBloc = blocCourant.getHauteur();
    	
    	for (int i=0; i<hauteurBloc; i++) {
    		int ligne = lignes[posYBloc + i];
    		int part = parts[hauteurBloc - 1 - i];
    		part = part << (posXBloc - 1);
    		
    		if ((part & ligne) > 0)
    			return false;
    	}
    	
    	return true;
    }
    
    /**
     * 
     * @return
     */
    private boolean blocPeutAllerAGauche() {
    	if (posXBloc + blocCourant.getLargeur() >= NB_COLONNES)
    		return false;
    	
    	int[] parts = blocCourant.getParts();
    	final int hauteurBloc = blocCourant.getHauteur();
    	
    	for (int i=0; i<hauteurBloc; i++) {
    		int ligne = lignes[posYBloc + i];
    		int part = parts[hauteurBloc - 1 - i];
    		part = part << (posXBloc + 1);
    		
    		if ((part & ligne) > 0)
    			return false;
    	}
    	
    	return true;
    }
    
    private boolean blocPeutTournerDirect() {
    	Bloc bloc = new Bloc(blocCourant.getParts(), 
    						blocCourant.getLargeur(), 
    						blocCourant.getHauteur(), 
    						blocCourant.getCouleur());
    	bloc.rotationDirecte();
    	
    	int[] parts = bloc.getParts();
    	final int hauteurBloc = bloc.getHauteur();
    	
    	for (int i=0; i<hauteurBloc; i++) {
    		int ligne = lignes[posYBloc + i];
    		int part = parts[hauteurBloc - 1 - i];
    		part = part << posXBloc;
    		
    		if ((part & ligne) > 0 || part >= (1 << NB_COLONNES))
    			return false;
    	}
    	
    	return true;
    }
    
    private boolean blocPeutTournerIndirect() {
    	Bloc bloc = new Bloc(blocCourant.getParts(), 
    						blocCourant.getLargeur(), 
    						blocCourant.getHauteur(), 
    						blocCourant.getCouleur());
    	bloc.rotationIndirecte();
    	
    	int[] parts = bloc.getParts();
    	final int hauteurBloc = bloc.getHauteur();
    	
    	for (int i=0; i<hauteurBloc; i++) {
    		int ligne = lignes[posYBloc + i];
    		int part = parts[hauteurBloc - 1 - i];
    		part = part << posXBloc;
    		
    		if ((part & ligne) > 0 || part >= (1 << NB_COLONNES))
    			return false;
    	}
    	
    	return true;
    }
    
    public synchronized void decalerBlocAGauche() {
    	if (blocPeutAllerAGauche()) {
    		posXBloc++;
    		updateGhost();
    	}
    }
    
    public synchronized void decalerBlocADroite() {
    	if (blocPeutAllerADroite()) {
    		posXBloc--;
    		updateGhost();
    	}
    }
    
    public synchronized void effectuerRotationDirecteBloc() {
    	if (blocPeutTournerDirect()) {
    		blocCourant.rotationDirecte();
    		updateGhost();
    	}
    }
    
    public synchronized void effectuerRotationIndirecteBloc() {
    	if (blocPeutTournerIndirect()) {
    		blocCourant.rotationIndirecte();
    		updateGhost();
    	}
    }
    
    public synchronized void descendreBloc() {
    	if (blocPeutDescendre(posYBloc))
    		posYBloc++;
    	else {
    		associerBloc();
    		nouveauBloc();
    		updateGhost();
    	}
    }
    
    public synchronized void faireChuterBloc() {
    	while (blocPeutDescendre(posYBloc)) {
    		posYBloc++;
    	}
    	associerBloc();
    	nouveauBloc();
    }
    
    private void associerBloc() {
    	int[] parts = blocCourant.getParts();
    	final int length = parts.length;
    	
    	for (int i=0; i<length; i++) {
    		int indexLigne = posYBloc + i;
    		if (indexLigne < NB_TOTAL_LIGNES) {
    			int ligne = lignes[indexLigne];
	    		int part = parts[i] << posXBloc;
	    		
	    		lignes[indexLigne] = (ligne | part );
	    		
	    		for (int t = 0; t < NB_COLONNES; t++) {
	    			if ((part & (1 << t)) > 0 ) {
	    				couleurs[indexLigne][t] = blocCourant.getCouleur();
	    			}
	    		}
    		}
    	}
    	
    	verifierLignesCompletes();
    	
    	blocCourant = null;
    }
    
    private void verifierLignesCompletes() {
    	int nbLignes = 0;
    	
    	for (int i=NB_TOTAL_LIGNES-1; i >= 0; i--) {
    		if (LigneUtil.isComplete(lignes[i])) {
    			supprimerLigne(i);
    			i++;
    			nbLignes++;
    		}
    	}
    	
    	if (nbLignes > 0) {
    		for (GrilleListener listener : listeners) {
    			listener.lignesCompletes(nbLignes);
    		}
    	}
    }
    
	public synchronized int[] getLignes() {
		return lignes;
	}
	public synchronized Bloc getBlocCourant() {
		return blocCourant;
	}
	public synchronized int getPosXBloc() {
		return posXBloc;
	}
	public synchronized int getPosYBloc() {
		return posYBloc;
	}

	public Color[][] getCouleurs() {
		return couleurs;
	}

	public Bloc getNextBloc() {
		return nextBloc;
	}

	public int getPosYGhost() {
		return posYGhost;
	}
	
	public void addGrilleListener(GrilleListener listener) {
		listeners.add(listener);
	}
    
}
