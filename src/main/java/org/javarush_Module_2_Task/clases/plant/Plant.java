package org.javarush_Module_2_Task.clases.plant;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.Unit;

public abstract class Plant extends Unit {
	public Plant(GameCell cell) {
		super(cell);
	}

//	@Override
//	public void multiply() {
//		cell.add(this);
//		double curWeight = this.getWeight();
//		this.setWeight(curWeight * 2);
//	}

	public void dead() {}
}
