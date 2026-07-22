package org.javarush_Module_2_Task.clases.predators;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.data.Settings;

public abstract class Predator extends Animal {

	public Predator(GameCell cell, SEX sex) {
		super(cell,sex);
	}

	@Override
	protected int getHuntTry() {
		return Settings.HuntTryPredator;
	}
}
