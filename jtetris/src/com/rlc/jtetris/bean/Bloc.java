package com.rlc.jtetris.bean;

import java.awt.Color;

import com.rlc.jtetris.util.BlocUtil;

/**
 * Représente un bloc
 * 
 * Un bloc est représenté sous la forme de 4 entiers.
 * La forme binaire de chaque entier représente une partie du bloc.
 * ex :
 * part1 = 0 0 1 0
 * part2 = 0 0 1 0
 * part3 = 0 1 1 0
 * part4 = 0 0 0 0
 * 
 * @author richard
 * @version 1.0.0
 * @since 1.0.0
 */
public class Bloc {
	
	/** Lignes constituant le bloc */
	private int[] parts;
	
	private int largeur;
	
	private int hauteur;
	
	private Color couleur;
	
	/**
	 * @param parts
	 */
	public Bloc(int[] parts, int largeur, int hauteur, Color couleur) {
		this.parts = parts;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.couleur = couleur;
	}

	/**
	 * Effectue une rotation directe d'un quart
	 * de tour sur le bloc
	 * @since 1.0.0
	 */
	public void rotationDirecte() {
		int[] newParts = new int[largeur];
		
		for (int i = 0; i < largeur; i++) {
			newParts[largeur - 1 - i] = BlocUtil.getColonne(parts, i, largeur);
		}
		
		synchronized (parts) {
			parts = newParts;
		}
		
		int tmp = largeur;
		largeur = hauteur;
		hauteur = tmp;
	}
	
	/**
	 * Effectue une rotation indirecte d'un quart
	 * de tour sur le bloc
	 * @since 1.0.0
	 */
	public void rotationIndirecte() {
		
		// TODO : améliorer
		for (int i=0; i < 3; i++) {
			rotationDirecte();
		}
	}

	/**
	 * Renvoie les parties du bloc
	 * @return
	 */
	public int[] getParts() {
		return parts;
	}

	public int getHauteur() {
		return hauteur;
	}

	public int getLargeur() {
		return largeur;
	}

	public Color getCouleur() {
		return couleur;
	}


	
}
