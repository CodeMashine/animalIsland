package org.javarush_Module_2_Task.interfaces;

import org.javarush_Module_2_Task.clases.GameCell;
import org.javarush_Module_2_Task.clases.Unit;

public interface GameField {
	public void setGrid(GameCell[][] grid);
	public void showCells();
	public GameCell[][] getGrid();
	public GameCell getCell(int x, int y);
}
