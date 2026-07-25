package org.javarush.module2Task;

import org.javarush.module2Task.clases.game.World;
import org.javarush.module2Task.data.Settings;
import org.javarush.module2Task.interfaces.CellConstructor;
import org.javarush.module2Task.interfaces.GameConstructor;
import org.javarush.module2Task.interfaces.GameField;
import org.javarush.module2Task.interfaces.View;
import org.javarush.module2Task.service.CellCreator;
import org.javarush.module2Task.View.ConsoleView;
import org.javarush.module2Task.service.GameCreator;
import org.javarush.module2Task.service.LifeCycle;
import org.javarush.module2Task.service.Statistic;

public class Main {
	public static void main(String[] args) {
		GameField gameField = new World();
		CellConstructor cellConstructor = new CellCreator();
		GameConstructor gameConstructor = new GameCreator(cellConstructor, gameField);
		gameConstructor.fillGameField(Settings.fieldWidth, Settings.fieldHeight);
		Statistic statistic = new Statistic(gameField);
		View consoleView = new ConsoleView();
		LifeCycle lifeCycle = new LifeCycle(gameField, statistic, consoleView);
		lifeCycle.start();
	}
}