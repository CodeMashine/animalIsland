package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.GameFiled;

public class Grass extends Plant {
	private final static double WEIGHT = 1.0;
	public Grass(int x, int y, GameFiled gameFiled , GameCell cell) {
		super(x, y, gameFiled , cell);
	}

	@Override
	public void dead() {
	}

	@Override
	public void multiply(GameFiled gameFiled) {

	}

	@Override
	public void multiply() {
		GameCell currentCell = gameFiled.getCell(x, y);
		currentCell.add(new Grass(x,y, gameFiled , cell));
		System.out.println(this +" + add child");
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
