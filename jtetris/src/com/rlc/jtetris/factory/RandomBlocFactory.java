package com.rlc.jtetris.factory;

import com.rlc.jtetris.bean.Bloc;

public class RandomBlocFactory implements BlocFactory {
	
	private static final BlocFactory[] FACTORIES = new BlocFactory[] {
		new Type1BlocFactory(),
		new Type2BlocFactory(),
		new Type3BlocFactory(),
		new Type4BlocFactory(),
		new Type5BlocFactory(),
		new Type6BlocFactory(),
		new Type7BlocFactory()
	};
	
	private static final int NB_BLOCS = FACTORIES.length;

	public Bloc nouveauBloc() {
		int index = (int) (Math.random() * NB_BLOCS);
		BlocFactory factory = FACTORIES[index];
		
		return factory.nouveauBloc();
	}
}
