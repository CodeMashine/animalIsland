package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.Eating;
import org.javarush_Module_2_Task.interfaces.Moveble;
import org.javarush_Module_2_Task.interfaces.Multiplyble;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Animal extends Unit implements Moveble, Multiplyble, Eating {
	protected final SEX sex;
	protected AtomicInteger daysSinceLastMull = new AtomicInteger(0);
	protected int daysWOEat = 0;
	protected double eaten = 0;
	protected boolean isDead = false;


	public Animal(GameCell cell, SEX sex) {
		super(cell);
		this.sex = sex;
	}

	@Override
	public void eat() {
		int currentHuntTry = 0;
		int huntTry = getHuntTry();
		double needToEat = getFoodNeedToEat();

		ConcurrentLinkedDeque<Unit> units = cell.getUnits();
		Unit[] food = units.stream().filter(u -> getFoodList().containsKey(u.getClass())).toArray(
				size -> new Unit[size]);

		if (food.length == 0) {
			daysWOEat += 1;
			return;
		}

		for (Unit victim : food) {
			if (eaten >= needToEat || huntTry <= currentHuntTry) {
				break;
			}
			int chance = getFoodList().get(victim.getClass());
			int fact = ThreadLocalRandom.current().nextInt(100);

			if (fact < chance && cell.getUnits().contains(victim)) {
				double victimWeight = victim.getWeight();
				victimWeight -= needToEat;
				if (victimWeight > needToEat) {
					eaten += needToEat;
					double delta = victimWeight - needToEat;
					victim.setWeight(delta);
				}else{
					victim.dead();

				}
//				System.out.println(this + " eat " + victim +" eaten " +  eaten + " and need " + this.getFoodNeedToEat());
			}

			if (eaten >= needToEat) {
				break;
			}
			currentHuntTry += 1;
		}

		if (eaten >= getFoodNeedToEat()) {
			daysWOEat = 0;
		} else if (eaten == 0) {
			daysWOEat += 1;
		}
	}

	@Override
	public void multiply() {
		if (daysWOEat > 2) {
			return;
		}
		ConcurrentLinkedDeque<Unit> units = cell.getUnits();
//		System.out.println(this + " try add child");

		Animal[] animalsAnotherSexReadyToMul = units.stream().filter(
				u -> (this.getClass() == u.getClass() && this.getSex() != ((Animal) u).getSex())).filter(
				u -> ((Animal) u).isReadyToMul()).toArray(size -> new Animal[size]);

		boolean haveChild = false;
		for (Animal animal : animalsAnotherSexReadyToMul) {
			if (haveChild) {
				return;
			}
			Unit child = this.getChild();
			if (cell.add(child)) {
				this.daysSinceLastMull.set(0);
				animal.daysSinceLastMull.set(0);
				haveChild = true;
//				System.out.println(this + " add child");
			}
		}

		if (!haveChild) {
			this.daysSinceLastMull.incrementAndGet();
		}
	}

	@Override
	public void move() {
		if (eaten >= getFoodNeedToEat()) {
			return;
		}

		for (int i = 0; i < this.getSpeed(); i++) {
			List<GameCell> posibleDestCells = cell.getNeighbours();
			int possibleDestCellIndex = ThreadLocalRandom.current().nextInt(posibleDestCells.size());
			GameCell nextCell = posibleDestCells.get(possibleDestCellIndex);

			if (nextCell.add(this)) {
				this.cell.remove(this);
				this.cell = nextCell;
//			System.out.println(this + " go from " + previousCell +  " to " + currentCell);
			}
		}
	}

	abstract protected int getSpeed();

	abstract protected double getFoodNeedToEat();

	abstract protected Map<Class<? extends Unit>, Integer> getFoodList();

	abstract protected int getHuntTry();

	abstract protected boolean isReadyToMul();

	abstract protected Unit getChild();

	private SEX getSex() {
		return this.sex;
	}

	abstract public int getMaxDaysWOEat();

	abstract public int getMaxAge();

	@Override
	public void getOlder() {
		super.getOlder();
		this.eaten = 0;
		this.daysSinceLastMull.incrementAndGet();
		if (this.daysWOEat == this.getMaxDaysWOEat() || this.age == this.getMaxAge()) {
			this.dead();
		}
	}





}
