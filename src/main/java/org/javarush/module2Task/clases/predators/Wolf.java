package org.javarush.module2Task.clases.predators;

import org.javarush.module2Task.clases.Animal;
import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.clases.herbivore.Bison;
import org.javarush.module2Task.clases.herbivore.Boar;
import org.javarush.module2Task.clases.herbivore.Deer;
import org.javarush.module2Task.clases.herbivore.Duck;
import org.javarush.module2Task.clases.herbivore.Goat;
import org.javarush.module2Task.clases.herbivore.Horse;
import org.javarush.module2Task.clases.herbivore.Mouse;
import org.javarush.module2Task.clases.herbivore.Rabbit;
import org.javarush.module2Task.clases.herbivore.Sheep;

import java.util.Map;

public class Wolf extends Predator {
	private final static String NAME = "Wolf";
	private final static int SPEED = 3;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static double NEED_TO_EAT_ONE_UNIT = 3;
	private final static double WEIGHT_ONE_UNIT = 50;
	private final static int FLOCK_SIZE = 10;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Rabbit.class , 60 , Mouse.class,80 ,
			Horse.class , 10 ,Sheep.class , 70 ,
			Goat.class ,60 , Deer.class , 15 , Boar.class , 15 , Bison.class , 10 , Duck.class , 40  );

	public Wolf(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	@Override
	public Animal getChild() {
		return new Wolf(cell);
	}
}
