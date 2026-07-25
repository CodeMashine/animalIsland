package org.javarush.module2Task.service;

import org.javarush.module2Task.clases.Animal;
import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.data.Settings;
import org.javarush.module2Task.interfaces.GameField;
import org.javarush.module2Task.interfaces.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class LifeCycle {
	private final GameField gameField;
	private final Statistic statistic;
	private final View view;
	private int days;

	public LifeCycle(GameField gameField, Statistic statistic, View view) {
		this.gameField = gameField;
		this.statistic = statistic;
		this.view = view;
	}

	public void start() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		ExecutorService executorService = Executors.newFixedThreadPool(Settings.threadsAmount);
		scheduler.scheduleWithFixedDelay(() -> {
			if (isGameOver()) {
				executorService.shutdown();
				scheduler.shutdown();
				return;
			}
			process(executorService);
		}, 0, 1, TimeUnit.SECONDS);

	}


	private void process(ExecutorService executorService) {
		System.out.println("Day " + ++days + "-----------------");
		executeStatisticAction(executorService);
		view.display(statistic);
		executeEatActions(executorService);
		executeMulActions(executorService);
		executeMoveActions(executorService);
		executeGetOlderActions(executorService);
		statistic.clear();
	}

	private boolean isGameOver() {
		Set<Class<? extends Unit>> units = new HashSet<>();

		GameCell[][] grid = gameField.getGrid();

		for (GameCell[] cells : grid) {
			for (GameCell cell : cells) {
				for (Unit unit : cell.getUnits()) {
					units.add(unit.getClass());
				}
			}
		}

		if (units.size() <= 2) {
			System.out.println("Game over");
			return true;
		}
		return false;
	}

	private void executeEatActions(ExecutorService executorService) {
		executeAnimalActions(executorService, Animal::eat);
	}

	private void executeGetOlderActions(ExecutorService executorService) {
		executeUnitActions(executorService, Unit::getOlder);
	}

	private void executeMulActions(ExecutorService executorService) {
		executeUnitActions(executorService, Unit::multiply);
	}

	private void executeMoveActions(ExecutorService executorService) {
		executeAnimalActions(executorService, Animal::move);
	}

	private void executeStatisticAction(ExecutorService executorService) {
		Consumer<Unit> statAction = u -> {
			statistic.put(u.getName(), u.getCurrentFlockSize());
		};
		executeUnitActions(executorService, statAction);
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
