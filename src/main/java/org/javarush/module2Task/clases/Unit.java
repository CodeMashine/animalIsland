package org.javarush.module2Task.clases;

import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.interfaces.Aging;
import org.javarush.module2Task.interfaces.Multiplyble;

public abstract class Unit implements Multiplyble, Aging {
	protected final String NAME;
	protected final double WEIGHT_ONE_UNIT;
	protected final int FLOCK_SIZE;
	public GameCell cell;
	protected double currentWeight;

	public Unit(GameCell cell, String name, double weightOneUnit, int FLOCK_SIZE) {
		this.cell = cell;
		this.NAME = name;
		this.WEIGHT_ONE_UNIT = weightOneUnit;
		this.FLOCK_SIZE = FLOCK_SIZE;
		currentWeight = WEIGHT_ONE_UNIT;
	}

	public double getWeight() {
		return currentWeight;
	}

	public double getWeightOneUnit() {
		return WEIGHT_ONE_UNIT;
	}

	public double getMaxWeight() {
		return FLOCK_SIZE * WEIGHT_ONE_UNIT;
	}

	public int getFlockSize() {
		return FLOCK_SIZE;
	}

	public String getName() {
		return NAME;
	}

	public synchronized int getCurrentFlockSize() {
		return (int) Math.round(currentWeight / WEIGHT_ONE_UNIT);
	}

	@Override
	abstract public void multiply();

	public synchronized void setWeight(double weight) {
		if (weight < WEIGHT_ONE_UNIT) {
			this.dead();
		} else {
			this.currentWeight = weight;
		}
	}

	public synchronized void dead() {
		this.cell.remove(this);
	}

	@Override
	public String toString() {
		return String.format("%s , in cell - x: %d , y : %d", this.getName(), cell.getX(), cell.getY());
	}
}
