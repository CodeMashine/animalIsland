package org.javarush.module2Task.clases.game;
import org.javarush.module2Task.interfaces.GameField;
import java.util.ArrayList;
import java.util.List;

public class World implements GameField {
	private final List<GameCell> cells = new ArrayList<>();
	private GameCell[][] grid;

	public World() {}

	@Override
	public void setGrid(GameCell[][] grid) {
		this.grid = grid;
		for (GameCell[] gameCells : grid) {
			cells.addAll(List.of(gameCells));
		}
	}

	@Override
	public GameCell[][] getGrid() {
		return grid;
	}

	public GameCell getCell(int x, int y) {
		return grid[x][y];
	}
}
