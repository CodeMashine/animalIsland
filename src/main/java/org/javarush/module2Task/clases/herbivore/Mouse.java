package org.javarush.module2Task.clases.herbivore;

import org.javarush.module2Task.clases.Animal;
import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.plant.Grass;
import org.javarush.module2Task.clases.Unit;

import java.util.Map;

public class Mouse extends Herbivore {

	private final static String NAME = "Mouse";
	private final static int SPEED = 1;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static double NEED_TO_EAT_ONE_UNIT = 0.01;
	private final static double WEIGHT_ONE_UNIT = 0.05;
	private final static int FLOCK_SIZE = 100;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	public Mouse(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	@Override
	public Animal getChild() {
		return new Mouse(cell);
	}
}
