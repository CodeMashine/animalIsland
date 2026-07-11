package org.javarush_Module_2_Task.clases;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameCell {
	private final int x;
	private final int y;
	private final CopyOnWriteArrayList<Unit> units = new CopyOnWriteArrayList<>();
	public GameCell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
