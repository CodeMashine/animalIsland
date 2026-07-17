package org.javarush_Module_2_Task.service;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.Plant;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.errors.AnimalIslandException;
import org.javarush_Module_2_Task.interfaces.CellConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CellCreator implements CellConstructor {

	public GameCell getCell(int x, int y) {
		GameCell cell = new GameCell(x,y);
		fillCell(cell);
		return cell;
	}

	private void fillCell(GameCell cell) {
		final Map<Class<? extends Unit>, Integer> maxCountOfUnits = GameCell.getMaxCountOfUnits();

			for(Map.Entry<Class<? extends Unit>, Integer> entry : maxCountOfUnits.entrySet()) {
				int maxValue = entry.getValue();
				int randomValue = ThreadLocalRandom.current().nextInt(maxValue+1);
				for(int i = 0 ; i < randomValue ; i++){
					Unit unit = createUnit(cell ,entry.getKey());
					cell.add(unit);
				}
			}
	}

	private Unit createUnit(GameCell cell, Class<? extends Unit> unit) {
		Unit curUnit = null;
		if (Plant.class.isAssignableFrom(unit)) {
			try {
				curUnit = unit.getDeclaredConstructor(GameCell.class).newInstance(cell);
			} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
					 NoSuchMethodException e) {
				throw new AnimalIslandException(e.getMessage());
			}
		} else {
			SEX randomSex = ThreadLocalRandom.current().nextBoolean() ? SEX.MALE : SEX.FEMALE;
			try {
				curUnit = unit.getDeclaredConstructor(GameCell.class ,SEX.class).newInstance(cell,randomSex);
			} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
					 NoSuchMethodException e) {
				throw new AnimalIslandException(e.getMessage());
			}
		}
		return curUnit;
	}







}
