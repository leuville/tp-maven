package com.rlc.jtetris.bean;

public interface GrilleListener {
	
	public void nouveauBloc();
	public void gameOver();
	public void lignesCompletes(int nbLignes);

}
