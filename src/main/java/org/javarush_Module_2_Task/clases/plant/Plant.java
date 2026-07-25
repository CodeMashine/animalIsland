package org.javarush_Module_2_Task.clases.plant;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.Unit;

public abstract class Plant extends Unit {
	public Plant(GameCell cell ,String name , double weightOneUnit , int flockSize) {
		super(cell, name, weightOneUnit, flockSize);
	}
	public void dead() {}

	@Override
	public void getOlder() {

	}
}
