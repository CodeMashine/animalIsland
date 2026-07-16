package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.errors.AnimalIslandException;
import org.javarush_Module_2_Task.interfaces.GameFiled;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class World implements GameFiled {
	private final int height;
	private final int width;

	private final List<GameCell> cells = new ArrayList<>();
	private final GameCell[][] grid;
	//	private CopyOnWriteArrayList<Unit> listOfAllUnits = new CopyOnWriteArrayList<>();
	private final ConcurrentLinkedDeque<Unit> listOfAllUnits = new ConcurrentLinkedDeque<>();
	private Map<Class<? extends Unit>, AtomicInteger> population = new ConcurrentHashMap<>();


	public World(int height, int width) {
		this.height = height;
		this.width = width;
		grid = new GameCell[height][width];
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
			int currentCountOfUnit = ThreadLocalRandom.current().nextInt(maxValueOfUnits + 1) / Settings.unitAmountCoef;
//			int currentCountOfUnit = ThreadLocalRandom.current().nextInt(maxValueOfUnits + 1);
			int x = cell.getX();
			int y = cell.getY();
			for (int i = 0; i < currentCountOfUnit; i++) {
				Unit curUnit = createUnit(cell, unit, x, y);
//				addUnit(curUnit);
				threadPool.execute(() -> cell.add(curUnit));
			}
			threadPool.shutdown();
		}
	}

	public Unit createUnit(GameCell cell, Class<? extends Unit> unit, int x, int y) {
		Unit curUnit = null;
		if (Plant.class.isAssignableFrom(unit)) {
			try {
				curUnit = unit.getDeclaredConstructor(int.class, int.class,
						org.javarush_Module_2_Task.interfaces.GameFiled.class, GameCell.class).newInstance(x, y, this,
						cell);

			} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
					 NoSuchMethodException e) {
				throw new AnimalIslandException(e.getMessage());
			}
		} else {
			SEX randomSex = ThreadLocalRandom.current().nextBoolean() ? SEX.MALE : SEX.FEMALE;
			try {
				curUnit = unit.getDeclaredConstructor(int.class, int.class, SEX.class, GameFiled.class,
						GameCell.class).newInstance(x, y, randomSex, this, cell);
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
				grid[x][y] = cell;
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
		return listOfAllUnits.add(unit);
	}

	@Override
	public GameCell getCell(int x, int y) {
		return grid[x][y];
	}

	@Override
	public boolean removeUnit(Unit unit) {
		return false;
	}

	@Override
	public Unit createUnit(Class<? extends Unit> unit, int x, int y) {
		return null;
	}


	@Override
	public void lifeSimulation() {

	}

	@Override
	public void lifeSimulation(int days) {
		ExecutorService threadPool = Executors.newFixedThreadPool(4);

		for (int x = 0; x < days; x++) {
			System.out.println("day " + x + "-------------------------------------------------");

			List<Callable<Void>> tasks = new ArrayList<>();
			for (GameCell cell : cells) {
				Animal[] animalsInCell = cell.getAnimals();
				for (Animal animal : animalsInCell) {
					tasks.add(() -> {
						animal.eat();
						return null;
					});
				}
			}
			try {
				threadPool.invokeAll(tasks);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tasks.clear();

//			for (GameCell cell : cells) {
//				final ConcurrentLinkedDeque<Unit> units = cell.getUnits();
//				for (Unit unit : units) {
//					tasks.add(()->{unit.multiply(); return null;});
//				}
//			}
//			try{
//				threadPool.invokeAll(tasks);
//			}catch (InterruptedException e) {
//				e.printStackTrace();
//			}


//			for (GameCell cell : cells) {
//				Animal[] animalsInCell = cell.getAnimals();
//				for (Animal animal : animalsInCell) {
//					if(cell.getUnits().contains(animal)) {
//						threadPool.execute(() -> {animal.eat();});
//					}
//				}
//			}
//
//			for (GameCell cell : cells) {
//
//				for (Unit unit : cell.getUnits()) {
//					threadPool.execute(() -> {unit.multiply();});
//				}
//			}

		}
		threadPool.shutdown();
//		try {
//			threadPool.awaitTermination(60, TimeUnit.SECONDS);
//		} catch (InterruptedException e) {
//			threadPool.shutdownNow();
//			throw new RuntimeException(e);
//		}
	}

	private List<Animal> getAnimals() {
		List<Animal> animals = new ArrayList<>();
		for (GameCell cell : cells) {
			animals.addAll(List.of(cell.getAnimals()));
		}
		return animals;
	}


	private CountDownLatch allEat(ExecutorService threadPool) {
		List<Animal> animals = getAnimals();
		CountDownLatch latch = new CountDownLatch(animals.size());
		for (Animal animal : animals) {
			threadPool.execute(() -> {
				try {
					animal.eat();

				} finally {
					latch.countDown();
				}
			});
		}
		return latch;
	}

	private void allMul(ExecutorService threadPool) {
		CountDownLatch latch = new CountDownLatch(cells.size());
		for (GameCell cell : cells) {
			threadPool.execute(() -> {cell.unitMul();});
			latch.countDown();
		}
		try {
			latch.wait();
		} catch (InterruptedException e) {
			throw new AnimalIslandException(e);
		}


//		threadPool.shutdown();
	}


}
