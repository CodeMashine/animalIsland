package org.javarush_Module_2_Task.clases.game;
import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.interfaces.GameField;
import java.util.ArrayList;
import java.util.List;

public class World implements GameField {
	private final List<GameCell> cells = new ArrayList<>();
	private GameCell[][] grid;
//	private final ConcurrentLinkedDeque<Unit> listOfAllUnits = new ConcurrentLinkedDeque<>();
//	private Map<Class<? extends Unit>, AtomicInteger> population = new ConcurrentHashMap<>();

	public World() {}

	@Override
	public void showCells() {
		for (int x = 0; x < grid[0].length; x++) {
			for (int y = 0; y < grid.length; y++) {
				System.out.println(grid[y][x]);
			}
		}
	}

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

	@Override
	public List<Animal> getAnimals() {
		List<Animal> animals = new ArrayList<>();
		for (GameCell cell : cells) {
			animals.addAll(List.of(cell.getAnimals()));
		}
		return animals;
	}

	public GameCell getCell(int x, int y) {
		return grid[x][y];
	}
}
