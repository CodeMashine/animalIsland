package org.javarush_Module_2_Task.clases.herbivore;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.plant.Grass;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.data.Settings;

import java.util.Map;

public class Caterpillar extends Herbivore {

	private final static String NAME = "Caterpillar";
	private final static int SPEED = 0;
	private final static int MAX_DAYS_WO_EAT = 2;
	private final static double NEED_TO_EAT_ONE_UNIT = 0.01;
	private final static double WEIGHT_ONE_UNIT = 0.01;
	private final static int FLOCK_SIZE = 1000;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	public Caterpillar(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	public Unit getChild() {
		return new Caterpillar(cell);
	}
}
