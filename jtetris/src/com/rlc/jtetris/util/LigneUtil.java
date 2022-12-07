package com.rlc.jtetris.util;

import com.rlc.jtetris.Constantes;

/**
 * Utilitaire de traitement des lignes
 * @author richard
 * @version 1.0.0
 * @since 1.0.0
 */
public final class LigneUtil {

    /**
     * Constructeur priv� pour classe utilitaire
     */
    private LigneUtil() {
    }
    
    /**
     * Indique si une ligne est compl�te
     * @param ligne La ligne � tester
     * @return true si la ligne est compl�te, false sinon
     */
    public static boolean isComplete(int ligne) {
        return ligne == ((1 << Constantes.NB_COLONNES) - 1);
    }
        
}
