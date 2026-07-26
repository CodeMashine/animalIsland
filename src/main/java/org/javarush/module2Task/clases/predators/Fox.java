package org.javarush.module2Task.clases.predators;

import org.javarush.module2Task.clases.Animal;
import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.herbivore.Caterpillar;
import org.javarush.module2Task.clases.herbivore.Duck;
import org.javarush.module2Task.clases.herbivore.Mouse;
import org.javarush.module2Task.clases.herbivore.Rabbit;

import java.util.Map;

public class Fox extends Predator {
	private final static String NAME = "Fox";
	private final static int SPEED = 2;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static double NEED_TO_EAT_ONE_UNIT = 2;
	private final static double WEIGHT_ONE_UNIT = 8;
	private final static int FLOCK_SIZE = 10;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Rabbit.class, 70, Mouse.class, 90,
			Duck.class, 60, Caterpillar.class, 40);

	public Fox(GameCell cell) {
		super(cell, NAME, WEIGHT_ONE_UNIT, FLOCK_SIZE, SPEED, MAX_DAYS_WO_EAT, NEED_TO_EAT_ONE_UNIT, FOOD_LIST);
	}

	@Override
	public Animal getChild() {
		return new Fox(cell);
	}
}
