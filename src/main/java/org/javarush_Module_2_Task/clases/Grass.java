package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.GameField;

public class Grass extends Plant {
	private final static String NAME = "Grass";
	private final static double WEIGHT = 1.0;
	public Grass(GameCell cell) {
		super(cell);
	}

	@Override
	public void dead() {
	}

	@Override
	public String getName() {
		return this.NAME;
	}


	@Override
	public  double getWeight() {
		return WEIGHT;
	}


	@Override
	public String toString() {
		return "Grass{" + "x=" + cell.getX() + ", y=" + cell.getY() + '}';
	}

}
