package org.javarush_Module_2_Task.clases.predators;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.clases.game.GameCell;

import java.util.Map;

public abstract class Predator extends Animal {
	public Predator(GameCell cell, String name, double weightOneUnit, int flockSize, int speed, int maxDaysWOEat, double needToEatOneUnit, Map<Class<? extends Unit>, Integer> foodList) {
		super(cell, name, weightOneUnit, flockSize, speed, maxDaysWOEat, needToEatOneUnit, foodList);
	}
}
