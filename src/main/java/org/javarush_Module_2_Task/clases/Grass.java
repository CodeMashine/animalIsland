package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.World;

public class Grass extends Plant {
	private final static double WEIGHT = 1.0;
	public Grass(int x, int y, World world) {
		super(x, y, world);
	}

	@Override
	public void multiply() {
		GameCell currentCell = world.getCell(x, y);
		currentCell.add(new Grass(x,y,world));
	}

	@Override
	public  double getWeight() {
		return WEIGHT;
	}


	@Override
	public String toString() {
		return "Grass{" + "x=" + x + ", y=" + y + '}';
	}
}
