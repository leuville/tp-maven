package com.rlc.jtetris.util;

/**
 * Utilitaire de gestion des blocs
 * 
 * @author richard
 * @version 1.0.0
 * @since 1.0.0
 */
public final class BlocUtil {
	
	/**
	 * Constructeur privé pour classe utilitaire
	 * @since 1.0.0
	 */
	private BlocUtil() {
	}
	
	/**
	 * Renvoie la colonne à l'index donné.
	 * 
	 * ex :
	 * Pour le bloc
	 * part1 = 0 0 1 0
	 * part2 = 0 0 1 0
	 * part3 = 0 1 1 0
	 * part4 = 0 0 0 0
	 * 
	 * La colonne 2 a pour valeur binaire : 1 1 1 0
	 * La colonne 3 a pour valeur binaire : 0 0 1 0
	 * 
	 * @param parts Un bloc
	 * @param index Index de la colonne recherchée
	 * @return Valeur de la colonne
	 * @since 1.0.0
	 */
	public static int getColonne(int[] parts, int index, int largeur) {
		final int hauteur = parts.length;
		
		int val = 0;
		
		for (int i=0; i<hauteur; i++) {
			int part = parts[i];
			val += (part & 1 << (largeur - index - 1)) == 0 ? 0 : 1 << (hauteur - 1 - i); 
		}
		
		return val;
	}

}
