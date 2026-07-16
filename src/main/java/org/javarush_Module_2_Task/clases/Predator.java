package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.GameFiled;

public abstract class Predator extends Animal {

	public Predator(int x, int y, SEX sex, GameFiled gameFiled, GameCell cell) {
		super(x, y, sex, gameFiled, cell);
	}

	@Override
	protected int getHuntTry() {
		return Settings.HuntTryPredator;
	}
}
