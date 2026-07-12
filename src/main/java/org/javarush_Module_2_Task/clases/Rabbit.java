package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.World;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit extends Herbivore {

	private static AtomicInteger count = new AtomicInteger(0);
	private final static String NAME = "Rabbit";
	private final static int MAX_AGE = 5;
	private final static int MAX_SPEED = 2;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static int READY_TO_MULL_AFTER = 1;
	private final static double NEED_TO_EAT = 0.45;
	private final static double WEIGHT = 2;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	private final int id;

//	private AtomicInteger age = new AtomicInteger(0);

	public Rabbit(int x, int y, SEX sex, World world) {
		super(x, y, sex, world );
		this.id = count.incrementAndGet();


		System.out.println("create " + this);
	}

	public int getAge() {
		return this.age.get();
	}

	@Override
	public void eat() {
		GameCell currentCell = world.getCell(getX(), getY());

		ConcurrentLinkedDeque<Unit> units = currentCell.getUnits();
		Unit[] food = units.stream()
				.filter(u -> FOOD_LIST.containsKey(u.getClass()))
				.toArray(Unit[]::new);

		while (this.eaten < NEED_TO_EAT) {

			if(food.length == 0) {
				this.daysWOEat +=1;
				break;
			}

			for(Unit u : food) {
				if(units.remove(u)) {
					eaten++;
				}
			}
		}
	}

	@Override
	public void move() {

	}

	@Override
	public void multiply() {
		GameCell currentCell = world.getCell(getX(), getY());
//		CopyOnWriteArrayList<Unit> units = currentCell.getUnits();


	}

	@Override
	public double getFoodNeedToEat() {
		return NEED_TO_EAT;
	}

	@Override
	public  Map<Class<? extends Unit>, Integer> getFoodList() {
		return FOOD_LIST;
	}

	@Override
	public double getWeight() {
		return WEIGHT;
	}

	@Override
	public String toString() {
		return String.format("Rabbit id - %d , age %d , sex - %s , in cell - x: %d , y : %d", this.id, this.age.get(),
				this.sex, this.getX(), this.getY());
	}
}
