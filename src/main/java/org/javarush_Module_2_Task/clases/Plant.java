package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.GameField;

public abstract class Plant extends Unit{
	public Plant(GameCell cell) {
		super(cell);
	}

	public void dead() {}
}
