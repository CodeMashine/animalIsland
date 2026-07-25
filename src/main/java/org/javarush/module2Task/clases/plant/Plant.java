package org.javarush.module2Task.clases.plant;

import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.Unit;

public abstract class Plant extends Unit {
	public Plant(GameCell cell ,String name , double weightOneUnit , int flockSize) {
		super(cell, name, weightOneUnit, flockSize);
	}
	public void dead() {}

	@Override
	public void getOlder() {

	}
}
