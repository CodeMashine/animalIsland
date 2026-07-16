package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.Eating;
import org.javarush_Module_2_Task.interfaces.Moveble;
import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.GameFiled;

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


	public Animal(int x, int y, SEX sex, GameFiled gameFiled, GameCell cell) {
		super(x, y, gameFiled,cell);
		this.sex = sex;
	}

	@Override
	public void eat() {
		int currentHuntTry = 0;
		int huntTry = getHuntTry();
		double needToEat = getFoodNeedToEat();

		while (eaten < needToEat && huntTry > currentHuntTry) {
			ConcurrentLinkedDeque<Unit> units = cell.getUnits();
			Unit[] food = units.stream().filter(u -> getFoodList().containsKey(u.getClass())).toArray(
					size -> new Unit[size]);

			if (food.length == 0) {
				break;
			}

			for (Unit victim : food) {
				int chance = getFoodList().get(victim.getClass());
				int fact = ThreadLocalRandom.current().nextInt(100);

				if (fact < chance && cell.remove(victim)) {
					double victimWeight = victim.getWeight();
					eaten += victimWeight;
					System.out.println(this + " eat " + victim);
				}
				currentHuntTry += 1;
			}
		}
		if (eaten >= getFoodNeedToEat()) {
			daysWOEat = 0;
		} else {
			daysWOEat += 1;
		}
	}


	@Override
	public void multiply() {
//		GameCell currentCell = gameFiled.getCell(getX(), getY());
//		ConcurrentLinkedDeque<Unit> units = currentCell.getUnits();
//		System.out.println(this + " try add child");
//
//		Animal[] animalsAnotherSexReadyToMul = units.stream().filter(
//				u -> (this.getClass() == u.getClass() && this.getSex() != ((Animal) u).getSex())).filter(
//				u -> ((Animal) u).isReadyToMul()).toArray(Animal[]::new);
//
//		boolean haveChild = false;
//		for (Animal animal : animalsAnotherSexReadyToMul) {
//			Unit child = gameFiled.createUnit(this.getClass(), this.getX(), this.getY());
//			if (currentCell.add(child)) {
//				this.daysSinceLastMull.set(0);
//				animal.daysSinceLastMull.set(0);
//				haveChild = true;
//				System.out.println(this + " add child");
//			}
//			;
//		}
//
//		if (!haveChild) {
//			this.daysSinceLastMull.incrementAndGet();
//		}

	}


	abstract protected double getFoodNeedToEat();

	abstract protected Map<Class<? extends Unit>, Integer> getFoodList();

	abstract protected int getHuntTry();

	abstract protected boolean isReadyToMul();

	abstract protected <T extends Animal> T getChild();

	private SEX getSex() {
		return this.sex;
	}

	public void dead() {
		this.isDead = true;
	}


}
