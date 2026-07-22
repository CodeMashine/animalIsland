package org.javarush_Module_2_Task.clases.plant;

import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.interfaces.Massive;

public class Grass extends Plant implements Massive {
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
	public double getWeightClass(){
		return WEIGHT;
	}

	@Override
	public void multiply() {
		Unit child = new Grass(cell);
		cell.add(child);
	}

	@Override
	public void getOlder() {
		return;
	}

	@Override
	public String toString() {
		return "Grass{" + "x=" + cell.getX() + ", y=" + cell.getY() + '}';
	}

}
