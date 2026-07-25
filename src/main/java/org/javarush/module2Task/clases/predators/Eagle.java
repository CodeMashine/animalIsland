package org.javarush.module2Task.clases.predators;

import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.herbivore.Duck;
import org.javarush.module2Task.clases.herbivore.Mouse;
import org.javarush.module2Task.clases.herbivore.Rabbit;

import java.util.Map;

public class Eagle extends Predator {
	private final static String NAME = "Eagle";
	private final static int SPEED = 2;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static double NEED_TO_EAT_ONE_UNIT = 80;
	private final static double WEIGHT_ONE_UNIT = 500;
	private final static int FLOCK_SIZE = 5;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Fox.class , 10 ,Rabbit.class ,90 , Mouse.class ,90 , Duck.class , 80 );

	public Eagle(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	@Override
	public Unit getChild() {
		return new Eagle(cell);
	}
}
