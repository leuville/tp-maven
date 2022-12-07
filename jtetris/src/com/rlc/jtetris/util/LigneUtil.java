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
     * Constructeur privé pour classe utilitaire
     */
    private LigneUtil() {
    }
    
    /**
     * Indique si une ligne est complète
     * @param ligne La ligne à tester
     * @return true si la ligne est complète, false sinon
     */
    public static boolean isComplete(int ligne) {
        return ligne == ((1 << Constantes.NB_COLONNES) - 1);
    }
        
}
