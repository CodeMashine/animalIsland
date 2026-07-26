package org.javarush.module2Task.clases.herbivore;

import org.javarush.module2Task.clases.Animal;
import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.plant.Grass;
import org.javarush.module2Task.clases.Unit;

import java.util.Map;

public class Horse extends Herbivore {

	private final static String NAME = "Horse";
	private final static int SPEED = 4;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static double NEED_TO_EAT_ONE_UNIT = 60;
	private final static double WEIGHT_ONE_UNIT = 400;
	private final static int FLOCK_SIZE = 5;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	public Horse(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	@Override
	public Animal getChild() {
		return new Horse(cell);
	}
}
