package org.javarush_Module_2_Task;

import org.javarush_Module_2_Task.clases.game.World;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.CellConstructor;
import org.javarush_Module_2_Task.interfaces.GameConstructor;
import org.javarush_Module_2_Task.interfaces.GameField;
import org.javarush_Module_2_Task.interfaces.View;
import org.javarush_Module_2_Task.service.CellCreator;
import org.javarush_Module_2_Task.View.ConsoleView;
import org.javarush_Module_2_Task.service.GameCreator;
import org.javarush_Module_2_Task.service.LifeCycle;
import org.javarush_Module_2_Task.service.Statistic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
	private static int width = Settings.FieldWidth;
	private static int height = Settings.FieldHeight;

	public static void main(String[] args) {
		GameField gameField = new World() ;
		CellConstructor cellConstructor = new CellCreator();
		GameConstructor gameConstructor = new GameCreator(cellConstructor, gameField);
		gameConstructor.fillGameField(width,height);

		Statistic statistic = new Statistic(gameField);
		View consoleView = new ConsoleView();
		LifeCycle lifeCycle = new LifeCycle(gameField , statistic , consoleView);

		lifeCycle.start();

//		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Settings.ThreadsAmount);
//		scheduler.scheduleWithFixedDelay(()-> lifeCycle.start() , 0 , 1, TimeUnit.SECONDS);
//		scheduler.shutdown();

//		gameField.showCells();


	}



}