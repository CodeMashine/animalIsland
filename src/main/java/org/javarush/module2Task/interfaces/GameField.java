package org.javarush.module2Task.interfaces;

import org.javarush.module2Task.clases.game.GameCell;

public interface GameField {
	 void setGrid(GameCell[][] grid);
	 GameCell[][] getGrid();
}
