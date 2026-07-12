package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.World;

public abstract class Plant extends Unit{
	public Plant(int x, int y, World island) {
		super(x, y, island);
	}
}
