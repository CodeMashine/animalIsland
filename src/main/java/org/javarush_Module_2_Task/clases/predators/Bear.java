package org.javarush_Module_2_Task.clases.predators;

import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.herbivore.Bison;
import org.javarush_Module_2_Task.clases.herbivore.Boar;
import org.javarush_Module_2_Task.clases.herbivore.Deer;
import org.javarush_Module_2_Task.clases.herbivore.Duck;
import org.javarush_Module_2_Task.clases.herbivore.Goat;
import org.javarush_Module_2_Task.clases.herbivore.Horse;
import org.javarush_Module_2_Task.clases.herbivore.Mouse;
import org.javarush_Module_2_Task.clases.herbivore.Rabbit;
import org.javarush_Module_2_Task.clases.herbivore.Sheep;

import java.util.Map;

public class Bear extends Predator {
	private final static String NAME = "Bear";
	private final static int SPEED = 2;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static double NEED_TO_EAT_ONE_UNIT = 80;
	private final static double WEIGHT_ONE_UNIT = 500;
	private final static int FLOCK_SIZE = 1;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Boa.class,80,  Rabbit.class , 80 , Mouse.class,90 ,
			Horse.class , 40 ,Sheep.class , 70 ,
			Goat.class ,70 , Deer.class , 80 , Boar.class , 50 , Bison.class , 20 , Duck.class , 10  );


	public Bear(GameCell cell) {
		super(cell,NAME , WEIGHT_ONE_UNIT ,FLOCK_SIZE ,SPEED, MAX_DAYS_WO_EAT ,NEED_TO_EAT_ONE_UNIT ,FOOD_LIST );
	}

	@Override
	public Unit getChild() {
		return new Bear(cell);
	}
}
