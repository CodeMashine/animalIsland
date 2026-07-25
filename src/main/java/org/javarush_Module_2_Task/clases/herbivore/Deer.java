package org.javarush_Module_2_Task.clases.herbivore;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.plant.Grass;
import org.javarush_Module_2_Task.clases.Unit;

import java.util.Map;

public class Deer extends Herbivore {

	private final static String NAME = "Deer";
	private final static int SPEED = 4;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static double NEED_TO_EAT_ONE_UNIT = 50;
	private final static double WEIGHT_ONE_UNIT = 300;
	private final static int FLOCK_SIZE = 5;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	public Deer(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	@Override
	public Unit getChild() {
		return new Deer(cell);
	}
}
