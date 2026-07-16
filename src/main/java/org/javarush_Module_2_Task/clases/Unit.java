package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.GameFiled;

public abstract class Unit implements Multiplyble {
	protected int x;
	protected int y;
	protected GameFiled gameFiled;
	protected GameCell cell;
	protected double weight;
	protected boolean isDead = false;

	public Unit(int x, int y, GameFiled gameFiled, GameCell cell) {
		this.x = x;
		this.y = y;
		this.gameFiled = gameFiled;
		this.cell = cell;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWeight(double weight) {
		if (weight <= 0) {
			this.remove();
			return;
		}
		this.weight = weight;
	}

	public void remove() {
		GameCell cell = gameFiled.getCell(x, y);
		cell.remove(this);
	}

	public abstract double getWeight();

	@Override
	public void multiply() {
	}

	@Override
	public void multiply(GameFiled gameFiled) {

	}
}
