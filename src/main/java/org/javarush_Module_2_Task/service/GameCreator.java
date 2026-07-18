package org.javarush_Module_2_Task.service;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.CellConstructor;
import org.javarush_Module_2_Task.interfaces.GameConstructor;
import org.javarush_Module_2_Task.interfaces.GameField;

import java.util.ArrayList;
import java.util.Arrays;
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
	public void fillGameField(int width, int height) {
		GameCell[][] GameCellsGrid = createGameCellsGrid(width, height);
		gameField.setGrid(GameCellsGrid);
	}

	private GameCell[][] createGameCellsGrid(int width, int height) {
		GameCell[][] gameCellsGrid = new GameCell[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				gameCellsGrid[x][y] = cellConstructor.getCell(x, y);
			}
		}

		Arrays.stream(gameCellsGrid).flatMap(row -> Arrays.stream(row)).forEach(cell -> {
			List<GameCell> neighbours = getNeighbours(cell.getX(), cell.getY(), height, width, gameCellsGrid)
			;
			cell.setNeighbours(neighbours);
		});
		return gameCellsGrid;
	}

	private List<GameCell> getNeighbours(int x, int y, int height, int width, GameCell[][] gameCellsGrid) {
		List<GameCell> neighbours = new ArrayList<>();
		if (x - 1 >= 0) {
			neighbours.add(gameCellsGrid[x - 1][y]);
		}
		if (x + 1 < width) {
			neighbours.add(gameCellsGrid[x + 1][y]);
		}
		if (y - 1 >= 0) {
			neighbours.add(gameCellsGrid[x][y - 1]);
		}
		if (y + 1 < height) {
			neighbours.add(gameCellsGrid[x][y + 1]);
		}
		if (y + 1 < height && x + 1 < width) {
			neighbours.add(gameCellsGrid[x + 1][y + 1]);
		}
		if (x - 1 >= width && y + 1 < height) {
			neighbours.add(gameCellsGrid[x - 1][y + 1]);
		}
		if (x - 1 >= 0 && y - 1 >= 0) {
			neighbours.add(gameCellsGrid[x - 1][y - 1]);
		}
		if (x + 1 < width && y - 1 >= 0) {
			neighbours.add(gameCellsGrid[x + 1][y - 1]);
		}
		return neighbours;
	}

	;


}
