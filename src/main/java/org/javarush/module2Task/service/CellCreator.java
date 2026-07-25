package org.javarush.module2Task.service;

import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.errors.AnimalIslandException;
import org.javarush.module2Task.interfaces.CellConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CellCreator implements CellConstructor {

	public GameCell createCell(int x, int y) {
		GameCell cell = new GameCell(x, y);
		fillCell(cell);
		return cell;
	}

	private void fillCell(GameCell cell) {
		final Map<Class<? extends Unit>, Integer> maxCountOfUnits = GameCell.getMaxCountOfUnits();

		for (Map.Entry<Class<? extends Unit>, Integer> entry : maxCountOfUnits.entrySet()) {
			int maxValue = entry.getValue();
			int flockSize = ThreadLocalRandom.current().nextInt(maxValue);
			Unit unit = createUnit(cell, entry.getKey(), flockSize);
			cell.add(unit);
		}
	}

	private Unit createUnit(GameCell cell, Class<? extends Unit> unit, int flockSize) {
		try {
			Unit curUnit = unit.getDeclaredConstructor(GameCell.class).newInstance(cell); // каждый раз новый объект
			curUnit.setWeight(flockSize * curUnit.getWeightOneUnit());
			return curUnit;
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
				 NoSuchMethodException e) {
			throw new AnimalIslandException(e);
		}
	}
}
