package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.GameFiled;

public abstract class Plant extends Unit{
	public Plant(int x, int y, GameFiled gameFiled , GameCell cell) {
		super(x, y, gameFiled , cell);
	}

	public void dead() {}
}
