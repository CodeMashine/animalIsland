package org.javarush.module2Task.clases;

import org.javarush.module2Task.clases.game.GameCell;
import org.javarush.module2Task.data.Settings;
import org.javarush.module2Task.interfaces.Aging;
import org.javarush.module2Task.interfaces.Eating;
import org.javarush.module2Task.interfaces.Moveble;
import org.javarush.module2Task.interfaces.Multiplyble;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Unit implements Moveble, Multiplyble, Eating, Aging {
	protected final int SPEED;
	protected final int MAX_DAYS_WO_EAT;
	protected final double NEED_TO_EAT_ONE_UNIT;
	protected final Map<Class<? extends Unit>, Integer> FOOD_LIST;

	protected int daysWOEat = 0;
	protected double eaten = 0;

	public Animal(GameCell cell, String name, double weightOneUnit, int flockSize, int speed, int maxDaysWOEat, double needToEatOneUnit, Map<Class<? extends Unit>, Integer> foodList) {
		super(cell, name, weightOneUnit, flockSize);
		FOOD_LIST = foodList;
		SPEED = speed;
		MAX_DAYS_WO_EAT = maxDaysWOEat;
		NEED_TO_EAT_ONE_UNIT = needToEatOneUnit;
	}

	@Override
	public void eat() {
		double needToEat = getFoodAmountNeedToEat();

		ConcurrentLinkedDeque<Unit> units = cell.getUnits();
		Unit[] food = units.stream().filter(u -> FOOD_LIST.containsKey(u.getClass())).toArray(size -> new Unit[size]);

		if (food.length == 0) {
			daysWOEat += 1;
			return;
		}

		for (Unit victim : food) {
			if (eaten >= needToEat) {
				break;
			}
			int chance = FOOD_LIST.get(victim.getClass());
			int fact = ThreadLocalRandom.current().nextInt(100);

			if (fact < chance && cell.getUnits().contains(victim)) {
				double victimWeight = victim.getWeight();
				if (victimWeight > needToEat) {
					eaten += needToEat;
					double delta = victimWeight - needToEat;
					victim.setWeight(delta);
				} else {
					eaten += victimWeight;
					victim.dead();
				}
			}
		}

		if (eaten <= needToEat / 2) {
			daysWOEat += 1;
		} else {
			daysWOEat = 0;
		}
	}

	@Override
	public void multiply() {
		if (daysWOEat > MAX_DAYS_WO_EAT) {
			return;
		}
		double nextWeight = currentWeight * 2;
		double maxWeight = getMaxWeight();

		if (nextWeight > maxWeight) {
			setWeight(maxWeight);
			Unit child = this.getChild();

			if (!cell.add(child)) {
				if (child instanceof Moveble moveble) {
					moveble.move();
				}
			}
		} else {
			setWeight(nextWeight);
		}
	}

	@Override
	public void move() {
		if (eaten >= getFoodAmountNeedToEat()) {
			return;
		}

		Set<GameCell> visitedCells = new HashSet<>();
		visitedCells.add(cell);

		List<GameCell> possibleDestCells = cell.getNeighbours();

		for (int i = 0; i < this.SPEED; i++) {
			if (possibleDestCells.isEmpty()) {
				return;
			}
			int possibleDestCellIndex = ThreadLocalRandom.current().nextInt(possibleDestCells.size());
			GameCell nextCell = possibleDestCells.remove(possibleDestCellIndex);
			if (visitedCells.contains(nextCell)) {
				i -= 1;
				continue;
			}

			if (nextCell.add(this)) {
				visitedCells.add(nextCell);
				this.cell.remove(this);
				this.cell = nextCell;

				for (Unit init : cell.getUnits()) {
					if (FOOD_LIST.containsKey(init.getClass())) {
						return;
					}
				}
				possibleDestCells = cell.getNeighbours();
			}
		}
	}

	@Override
	public void getOlder() {
		eaten = 0;
		if (daysWOEat >= MAX_DAYS_WO_EAT) {
			double nextWeight = currentWeight * Settings.weightLossCoefficient;
			setWeight(nextWeight);
		}
	}

	private double getFoodAmountNeedToEat() {
		return FLOCK_SIZE * NEED_TO_EAT_ONE_UNIT;
	}

	abstract public Unit getChild();
}
