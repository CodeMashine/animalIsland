package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.Eating;
import org.javarush_Module_2_Task.interfaces.Moveble;
import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.GameField;

import java.lang.reflect.InvocationTargetException;
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
	protected AtomicInteger age = new AtomicInteger(0);
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

			if (fact < chance && cell.remove(victim)) {
				double victimWeight = victim.getWeight();
				eaten += victimWeight;
//				System.out.println(this + " eat " + victim +" eaten " +  eaten + " and need " + this.getFoodNeedToEat());
			}

			if (eaten >= needToEat) {
				break;
			}
			currentHuntTry += 1;
		}

		if (eaten >= getFoodNeedToEat()) {
			daysWOEat = 0;
		} else {
			daysWOEat += 1;
		}
	}

	@Override
	public void multiply() {
		ConcurrentLinkedDeque<Unit> units = cell.getUnits();
//		System.out.println(this + " try add child");

		Animal[] animalsAnotherSexReadyToMul = units.stream().filter(
				u -> (this.getClass() == u.getClass() && this.getSex() != ((Animal) u).getSex())).filter(
				u -> ((Animal) u).isReadyToMul()).toArray(size -> new Animal[size]);

		boolean haveChild = false;
		for (Animal animal : animalsAnotherSexReadyToMul) {
			if (haveChild)
				return;
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
		if(eaten >= getFoodNeedToEat()) {
			return;
		};

		List<GameCell> posibleDestCell = cell.getNeighbours();
		GameCell currentCell = cell;
		GameCell previousCell = cell;

		for (int i = 0 ; i < this.getSpeed() ; i++ ){
			int possibleDestCellIndex = ThreadLocalRandom.current().nextInt(posibleDestCell.size());
			currentCell = posibleDestCell.get(possibleDestCellIndex);
		}
		if(currentCell.add(this)){
			this.cell = currentCell;
			previousCell.remove(this);
//			System.out.println(this + " go from " + previousCell +  " to " + currentCell);
		}
	}

	abstract protected int getSpeed();

	abstract protected double getFoodNeedToEat();

	abstract protected Map<Class<? extends Unit>, Integer> getFoodList();

	abstract protected int getHuntTry();

	abstract protected boolean isReadyToMul();

	protected Unit getChild() {
		try {
			SEX randomSex = ThreadLocalRandom.current().nextBoolean() ? SEX.MALE : SEX.FEMALE;
			Unit unit = this.getClass().getDeclaredConstructor(GameCell.class, SEX.class).newInstance(cell, randomSex);
			return unit;
		} catch (RuntimeException | NoSuchMethodException | InvocationTargetException | InstantiationException |
				 IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private SEX getSex() {
		return this.sex;
	}

	@Override
	public void getOlder() {
		super.getOlder();
		this.eaten = 0;
		this.daysSinceLastMull.incrementAndGet();
	}

	public void dead() {
		this.isDead = true;
	}


}
