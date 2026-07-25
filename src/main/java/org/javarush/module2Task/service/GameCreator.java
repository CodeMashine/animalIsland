package org.javarush.module2Task.service;

import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.interfaces.CellConstructor;
import org.javarush.module2Task.interfaces.GameConstructor;
import org.javarush.module2Task.interfaces.GameField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
				gameCellsGrid[x][y] = cellConstructor.createCell(x, y);
			}
		}

		Arrays.stream(gameCellsGrid).flatMap(Arrays::stream).forEach(cell -> {
			List<GameCell> neighbours = getNeighbours(cell.getX(), cell.getY(), height, width, gameCellsGrid);
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
}
