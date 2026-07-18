package org.javarush_Module_2_Task;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.World;
import org.javarush_Module_2_Task.interfaces.CellConstructor;
import org.javarush_Module_2_Task.interfaces.GameConstructor;
import org.javarush_Module_2_Task.interfaces.GameField;
import org.javarush_Module_2_Task.service.CellCreator;
import org.javarush_Module_2_Task.service.GameCreator;
import org.javarush_Module_2_Task.service.LifeCycle;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static int width = 100;
	private static int height = 20;

	public static GameCell[][] grid = new GameCell[width][height];

	public static List<GameCell> listOfCells = new ArrayList<>();



	public static void main(String[] args) {
		GameField gameField = new World() ;
		CellConstructor cellConstructor = new CellCreator();
		GameConstructor gameConstructor = new GameCreator(cellConstructor, gameField);
		gameConstructor.fillGameField(width,height);

		LifeCycle lifeCycle = new LifeCycle(gameField);
		lifeCycle.start(3);
	}



}