package org.javarush_Module_2_Task;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.Island;
import org.javarush_Module_2_Task.interfaces.World;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static int height = 100;
	private static int width = 20;

	public static GameCell[][] grid = new GameCell[height][width];

	public static List<GameCell> listOfCells = new ArrayList<>();



	public static void main(String[] args) {
		World island = new Island(height, width);

	}



}