package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.World;

public class Grass extends Plant {
	private static double weight = 1.0;
	public Grass(int x, int y, World island) {
		super(x, y, island);
	}

	@Override
	public void multiply() {
		GameCell currentCell = world.getCell(x, y);
		currentCell.add(new Grass(x,y,world));
	}
}
