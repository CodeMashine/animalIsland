package org.javarush_Module_2_Task.interfaces;

import org.javarush_Module_2_Task.clases.Unit;

public interface UnitFactory {
	public Unit createUnit(Class<? extends Unit> unit, int x, int y);
}
