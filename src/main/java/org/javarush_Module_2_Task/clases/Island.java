package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.data.StartSettings;
import org.javarush_Module_2_Task.errors.AnimalIslandException;
import org.javarush_Module_2_Task.interfaces.World;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Island implements World {
	private final int height;
	private final int width;


	private final List<GameCell> cells = new ArrayList<>();
	private CopyOnWriteArrayList<Unit> listOfAllUnits = new CopyOnWriteArrayList<>();
	private Map<Class<? extends Unit>, AtomicInteger> population = new ConcurrentHashMap<>();


	public Island(int height, int width) {
		this.height = height;
		this.width = width;
		createCells();
		fillCells();
	}

	private void fillCells() {
		try (ExecutorService threadPool = Executors.newWorkStealingPool()) {

			Map<Class<? extends Unit>, Integer> maxCountOfUnits = cells.getFirst().getMaxCountOfUnits();
			Set<Class<? extends Unit>> units = maxCountOfUnits.keySet();

			for (Class<? extends Unit> unit : units) {
				int maxValueOfUnits = maxCountOfUnits.get(unit);
				for (int i = 0; i < cells.size(); i++) {
					GameCell current = cells.get(i);
					threadPool.execute(() -> fillCell(current, maxValueOfUnits, unit));
				}
			}
			threadPool.shutdown();
			try {
				threadPool.awaitTermination(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				threadPool.shutdownNow();
			}
		}

	}

	private void fillCell(GameCell cell, int maxValueOfUnits, Class<? extends Unit> unit) {
		try (ExecutorService threadPool = Executors.newWorkStealingPool()) {
			int currentCountOfUnit = ThreadLocalRandom.current().nextInt(
					maxValueOfUnits + 1) / StartSettings.unitAmountCoef;
			int x = cell.getX();
			int y = cell.getY();
			for (int i = 0; i < currentCountOfUnit; i++) {
				Unit curUnit = getCurentUnit(unit, x, y);
				threadPool.execute(() -> cell.add(curUnit));
			}
			threadPool.shutdown();
		}
	}

	private Unit getCurentUnit(Class<? extends Unit> unit, int x, int y) {
		Unit curUnit = null;
		if (Plant.class.isAssignableFrom(unit)) {
			try {
				curUnit = unit.getDeclaredConstructor(int.class, int.class, World.class).newInstance(x, y, this);

			} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
					 NoSuchMethodException e) {
				throw new AnimalIslandException(e.getMessage());
			}

		} else {
			SEX randomSex = ThreadLocalRandom.current().nextBoolean() ? SEX.MALE : SEX.FEMALE;
			try {
				curUnit = unit.getDeclaredConstructor(int.class, int.class, SEX.class, World.class).newInstance(x, y,
						randomSex, this);
			} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
					 NoSuchMethodException e) {
				throw new AnimalIslandException(e.getMessage());
			}
		}

		return curUnit;

	}


	public void createCells() {
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				GameCell cell = new GameCell(x, y);
				cells.add(cell);
			}
		}

	}

	private void showCells() {
		for (int x = 0; x < cells.size(); x++) {
			System.out.println(cells.get(x));
			System.out.println("---------");
		}
	}

	@Override
	public boolean addUnit(Unit unit) {
		return false;
	}

	@Override
	public GameCell getCell(int x, int y) {
		return null;
	}

	@Override
	public boolean removeUnit(Unit unit) {
		return false;
	}


}
