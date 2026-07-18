package org.javarush_Module_2_Task.service;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.GameField;
import org.javarush_Module_2_Task.interfaces.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class LifeCycle {
	private GameField gameField;
	private Statistic statistic;
	private View view;

	public LifeCycle(GameField gameField , Statistic statistic , View view) {
		this.gameField = gameField;
		this.statistic = statistic;
		this.view = view;
	}

	public void start(int days) {
		ExecutorService executorService = Executors.newFixedThreadPool(Settings.ThreadsAmount);

		for (int i = 0; i < days; i++) {
			System.out.println("Day " + i + "-----------------");
			executeEatActions(executorService);
			executeMulActions(executorService);
			executeMoveActions(executorService);
			executeStatisticAction(executorService);
			view.display(statistic);
			executeGetOlderActions(executorService);
//			executeUnitActions(executorService, Unit::multiply);
//			view.show();
		}
		executorService.shutdown();
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
