package org.javarush_Module_2_Task;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.World;
import org.javarush_Module_2_Task.interfaces.GameFiled;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static int height = 3;
	private static int width = 3;

	public static GameCell[][] grid = new GameCell[height][width];

	public static List<GameCell> listOfCells = new ArrayList<>();



	public static void main(String[] args) {
		GameFiled gameFiled = new World(height, width);
		gameFiled.lifeSimulation(3);

	}



}