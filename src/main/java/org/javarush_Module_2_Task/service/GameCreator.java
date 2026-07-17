package org.javarush_Module_2_Task.service;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.CellConstructor;
import org.javarush_Module_2_Task.interfaces.GameConstructor;
import org.javarush_Module_2_Task.interfaces.GameField;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class GameCreator implements GameConstructor {
	private final CellConstructor cellConstructor;
	private final GameField gameField;

	public GameCreator(CellConstructor cellConstructor, GameField gameField) {
		this.cellConstructor = cellConstructor;
		this.gameField = gameField;
	}

	@Override
	public void fillGameField(int height, int width) {
		GameCell[][] GameCellsGrid = createGameCellsGrid(height, width);
		gameField.setGrid(GameCellsGrid);
	}

	private GameCell[][] createGameCellsGrid(int height, int width) {
		GameCell[][] gameCellsGrid = new GameCell[height][width];
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				gameCellsGrid[x][y] = cellConstructor.getCell(x, y);
			}
		}
		return gameCellsGrid;
	}
}
