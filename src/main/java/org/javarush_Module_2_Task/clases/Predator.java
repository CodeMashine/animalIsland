package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.GameField;

public abstract class Predator extends Animal {

	public Predator(GameCell cell,SEX sex) {
		super(cell,sex);
	}

	@Override
	protected int getHuntTry() {
		return Settings.HuntTryPredator;
	}
}
