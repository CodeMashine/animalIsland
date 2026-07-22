package org.javarush_Module_2_Task.service;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.GameField;
import org.javarush_Module_2_Task.interfaces.Massive;
import org.javarush_Module_2_Task.interfaces.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class LifeCycle {
	private GameField gameField;
	private Statistic statistic;
	private View view;
	private int days;

	public LifeCycle(GameField gameField, Statistic statistic, View view) {
		this.gameField = gameField;
		this.statistic = statistic;
		this.view = view;
	}

	public void start() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(() -> {
			if (isGameOver()) {
				scheduler.shutdown();
			}
			;
			process();
		}, 0, 1, TimeUnit.SECONDS);

	}


	private void process() {
		ExecutorService executorService = Executors.newFixedThreadPool(Settings.ThreadsAmount);
		System.out.println("Day " + ++days + "-----------------");
//			executeStatisticAction(executorService);
//			executeStatisticAction2(executorService);
		executeStatisticAction2();
		view.display(statistic);
		executeEatActions(executorService);
		executeMulActions(executorService);
		executeMoveActions(executorService);
		executeGetOlderActions(executorService);
		statistic.clear();

		executorService.shutdown();
	}

	private boolean isGameOver() {
		return gameField.getAnimals().isEmpty();
	}

	private void executeGetOlderActions(ExecutorService executorService) {
		executeUnitActions(executorService, Unit::getOlder);
	}


	private void executeEatActions(ExecutorService executorService) {
		executeAnimalActions(executorService, Animal::eat);
	}

	private void executeMulActions(ExecutorService executorService) {
		executeUnitActions(executorService, Unit::multiply);
	}

	private void executeMoveActions(ExecutorService executorService) {
		executeAnimalActions(executorService, Animal::move);
	}

	private void executeStatisticAction(ExecutorService executorService) {
		Consumer<Unit> statAction = u -> statistic.put(u.getName());
		executeUnitActions(executorService, statAction);
	}


//	private void executeStatisticAction2(ExecutorService executorService) {
//		GameCell[][] grid = gameField.getGrid();
//		List<Callable<Void>> taskList = new ArrayList<>();
//		for (int x = 0; x < grid.length; x++) {
//			for (int y = 0; y < grid[0].length; y++) {
//				grid[x][y].getUnits().forEach(u -> {
//					statistic.put(u.getName());
//				});
//

	//			grid[x][y].getCurrentCountOfUnits().forEach((u, c) -> {
	//					taskList.add(() -> {
	//						statistic.put(u.getSimpleName(), c.get());
	//						return null;
	//					});
	//				});
//				final Map<Class<? extends Unit>, AtomicInteger> currentCountOfUnits = grid[x][y].getCurrentCountOfUnits();
//
//				taskList.add(() -> {
//					for (Map.Entry<Class<? extends Unit>, AtomicInteger> unitClass : currentCountOfUnits.entrySet()) {
//						int amount = unitClass.getValue().get();
//						String name = unitClass.getKey().getSimpleName();
//						statistic.put(name, amount);
//					}
//					return null;
//				});
//
//				executeActions(executorService, taskList);
//			}
//		}
//	}
	private void executeStatisticAction2() {
		GameCell[][] grid = gameField.getGrid();
		List<Callable<Void>> taskList = new ArrayList<>();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				final Map<Class<? extends Unit>, AtomicInteger> currentCountOfUnits = grid[x][y].getCurrentCountOfUnits();
				for (Map.Entry<Class<? extends Unit>, AtomicInteger> unitClass : currentCountOfUnits.entrySet()) {
					int amount = unitClass.getValue().get();
					Class<? extends Unit> currentUnit = unitClass.getKey();
					String name = currentUnit.getSimpleName();
					if (Massive.class.isAssignableFrom(currentUnit)) {
//						currentUnit.
					}
					statistic.put(name, amount);
				}
			}
		}
	}


	private void executeAnimalActions(ExecutorService executorService, Consumer<Animal> action) {
		GameCell[][] grid = gameField.getGrid();
		List<Callable<Void>> taskList = new ArrayList<>();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				taskList.clear();
				grid[x][y].getUnits().stream().filter(u -> u instanceof Animal).map(u -> (Animal) u).forEach(animal -> {
					taskList.add(() -> {
						action.accept(animal);
						return null;
					});
				});
				executeActions(executorService, taskList);
			}
		}
	}

	private void executeUnitActions(ExecutorService executorService, Consumer<Unit> action) {
		GameCell[][] grid = gameField.getGrid();
		List<Callable<Void>> taskList = new ArrayList<>();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				taskList.clear();
				grid[x][y].getUnits().forEach(u -> {
					taskList.add(() -> {
						action.accept(u);
//						System.out.println(u.cell);
						return null;
					});
				});
				executeActions(executorService, taskList);
			}
		}

	}


	private void executeActions(ExecutorService executorService, List<Callable<Void>> taskList) {
		try {
			executorService.invokeAll(taskList);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

}
