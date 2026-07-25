package org.javarush.module2Task.clases.predators;

import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.clases.game.GameCell;
//import org.javarush_Module_2_Task.clases.herbivore.Mouse;
import org.javarush.module2Task.clases.herbivore.Duck;
import org.javarush.module2Task.clases.herbivore.Mouse;
import org.javarush.module2Task.clases.herbivore.Rabbit;

import java.util.Map;

public class Boa extends Predator {
	private final static String NAME = "Boa";
	private final static int SPEED = 2;
	private final static int MAX_DAYS_WO_EAT = 7;
	private final static double NEED_TO_EAT_ONE_UNIT = 3;
	private final static double WEIGHT_ONE_UNIT = 15;
	private final static int FLOCK_SIZE = 10;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Fox.class , 15 , Mouse.class,40 ,
			Duck.class , 10 , Rabbit.class , 20 );

	public Boa(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	@Override
	public Unit getChild() {
		return new Boa(cell);
	}
}
