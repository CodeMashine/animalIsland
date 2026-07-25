package org.javarush_Module_2_Task.clases.plant;

import org.javarush_Module_2_Task.clases.game.GameCell;

public class Grass extends Plant {

	private final static String NAME = "Grass";
	private final static double WEIGHT_ONE_UNIT = 1.0;
	private final static int FLOCK_SIZE = 500;

	public Grass(GameCell cell) {
		super(cell, NAME, WEIGHT_ONE_UNIT, FLOCK_SIZE);
	}

	@Override
	public void multiply() {
		double nextWeight = currentWeight * 2;
		double maxWeight = getMaxWeight();

		if (currentWeight == maxWeight) {
			return;
		}

		if (nextWeight > maxWeight) {
			setWeight(maxWeight);
		} else {
			setWeight(nextWeight);
		}
	}
}
