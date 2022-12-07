package com.rlc.jtetris.factory;

import java.awt.Color;

import com.rlc.jtetris.bean.Bloc;

/**
 * Factory des blocs de type :
 * 
 *  X .
 *  X X
 *  . X
 * 
 * @author richard
 * @version 1.0.0
 * @since 1.0.0
 */
public class Type6BlocFactory implements BlocFactory {
	
	public Bloc nouveauBloc() {
		return new Bloc(new int[] {2, 3, 1}, 2, 3, Color.orange);
	}

}
