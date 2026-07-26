package org.javarush.module2Task.service;

import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.errors.AnimalIslandException;
import org.javarush.module2Task.interfaces.CellConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CellCreator implements CellConstructor {

	public GameCell createCell(int x, int y) {
		GameCell cell = new GameCell(x, y);
		fillCell(cell);
		return cell;
	}

	private void fillCell(GameCell cell){
		final Map<Class<? extends Unit>, Integer> maxCountOfUnits = GameCell.getMaxCountOfUnits();

		for (Map.Entry<Class<? extends Unit>, Integer> entry : maxCountOfUnits.entrySet()) {
			int maxValue = entry.getValue();

			Class<? extends Unit> unitClass = entry.getKey();
			try {
				Field flockSizeField = unitClass.getDeclaredField("FLOCK_SIZE");
				flockSizeField.setAccessible(true);
				int flockSize = flockSizeField.getInt(null);
				int unitAmount = ThreadLocalRandom.current().nextInt(maxValue);
				int flockAmount = (unitAmount + flockSize - 1) / flockSize;

				for (int i = 0; i < flockAmount; i++) {
					Unit unit = createUnit(cell, entry.getKey());
					cell.add(unit);
				}
			}catch (NoSuchFieldException | IllegalAccessException e) {
				throw new AnimalIslandException(e);
			}
		}
	}

	private Unit createUnit(GameCell cell, Class<? extends Unit> unit) {
		try {
			Unit curUnit = unit.getDeclaredConstructor(GameCell.class).newInstance(cell);
			int randomFlockSize = ThreadLocalRandom.current().nextInt(curUnit.getFlockSize());
			curUnit.setWeight(randomFlockSize * curUnit.getWeightOneUnit());
			return curUnit;
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
				 NoSuchMethodException e) {
			throw new AnimalIslandException(e);
		}
	}
}
