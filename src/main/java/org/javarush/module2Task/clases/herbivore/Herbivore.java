package org.javarush.module2Task.clases.herbivore;

import org.javarush.module2Task.clases.Animal;
import org.javarush.module2Task.clases.Unit;
import org.javarush.module2Task.clases.game.GameCell;

import java.util.Map;

public abstract class Herbivore extends Animal {
	public Herbivore(GameCell cell, String name, double weightOneUnit, int flockSize, int speed, int maxDaysWOEat, double needToEatOneUnit, Map<Class<? extends Unit>, Integer> foodList) {
		super(cell, name, weightOneUnit, flockSize, speed, maxDaysWOEat, needToEatOneUnit, foodList);
	}
}
