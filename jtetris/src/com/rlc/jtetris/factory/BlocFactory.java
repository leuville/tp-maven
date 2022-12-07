package com.rlc.jtetris.factory;

import com.rlc.jtetris.bean.Bloc;

/**
 * Interface commune aux fabriques de blocs
 * @author richard
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BlocFactory {

	/**
	 * Créé un nouveau bloc
	 * @return Un nouveau bloc
	 */
	Bloc nouveauBloc();
	
}
