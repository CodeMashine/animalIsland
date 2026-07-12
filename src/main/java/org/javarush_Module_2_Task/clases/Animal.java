package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.Eating;
import org.javarush_Module_2_Task.interfaces.Moveble;
import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.World;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Animal extends Unit implements Moveble, Multiplyble, Eating {
	protected final SEX sex;
	protected int daysSinceLastMull = 0;
	protected int daysWOEat = 0;
	protected double eaten = 0;
	protected AtomicInteger age = new AtomicInteger(0);

	public Animal(int x, int y, SEX sex, World world) {
		super(x, y, world);
		this.sex = sex;
	}

	@Override
	public void eat() {
		GameCell currentCell = world.getCell(getX(), getY());
		ConcurrentLinkedDeque<Unit> units = currentCell.getUnits();
		Unit[] food = units.stream().filter(u -> getFoodList().containsKey(u.getClass())).toArray(Unit[]::new);
		if (food.length == 0) {
			this.daysWOEat += 1;
			return;
		}

		int huntTry = 0;

		while (eaten < getFoodNeedToEat() && getHuntTry() > huntTry) {
			for (Unit unit : food) {
				int chance = getFoodList().get(unit.getClass());
				int fact = ThreadLocalRandom.current().nextInt(100);

				if (fact < chance) {
					if (units.remove(unit)) {
						eaten += unit.getWeight();
					}
				}
				huntTry += 1;
			}
		}
	}

	abstract public double getFoodNeedToEat();

	abstract public Map<Class<? extends Unit>, Integer> getFoodList();

	abstract public int getHuntTry();


}
