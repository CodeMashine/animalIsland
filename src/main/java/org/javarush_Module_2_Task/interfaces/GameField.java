package org.javarush_Module_2_Task.interfaces;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.game.GameCell;

import java.util.List;

public interface GameField {
	public void setGrid(GameCell[][] grid);
	public void showCells();
	public GameCell[][] getGrid();
	public GameCell getCell(int x, int y);
	public List<Animal> getAnimals();
}
