package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.GameField;

public abstract class Unit implements Multiplyble {
	protected GameCell cell;
	protected double weight;
	protected boolean isDead = false;

	public Unit(GameCell cell) {
		this.cell = cell;
	}

	public void setWeight(double weight) {
		if (weight <= 0) {
			this.remove();
			return;
		}
		this.weight = weight;
	}

	public void remove() {
		cell.remove(this);
	}

	public abstract double getWeight();

	@Override
	public void multiply() {
	}


}
