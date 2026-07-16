package org.javarush_Module_2_Task.interfaces;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.Unit;

public interface GameFiled {
	public GameCell getCell(int x, int y);
	public boolean addUnit(Unit unit);
	public boolean removeUnit(Unit unit);
	public Unit createUnit(Class<? extends Unit> unit, int x, int y);
	public Unit createUnit(GameCell cell, Class<? extends Unit> unit, int x, int y);
	public void lifeSimulation();
	public void lifeSimulation(int days);

}
