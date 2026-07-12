package org.javarush_Module_2_Task.interfaces;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.Unit;

public interface World {
	public GameCell getCell(int x, int y);
	public boolean addUnit(Unit unit);
	public boolean removeUnit(Unit unit);

}
