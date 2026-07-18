package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit extends Herbivore {

	private static AtomicInteger countRabbit = new AtomicInteger(1);
	private final static String NAME = "Rabbit";
	private final static int MAX_AGE = 5;
	private final static int MAX_SPEED = 2;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static int READY_TO_MULL_AFTER = 1;
	private final static double NEED_TO_EAT = 0.45;
	private final static double WEIGHT = 2;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	private final int id;

	private AtomicInteger age = new AtomicInteger(0);

	public Rabbit(GameCell cell,SEX sex) {
		super(cell,sex);
		this.id = countRabbit.getAndIncrement();
	}

	public int getAge() {
		return this.age.get();
	}

	public int getSpeed() {
		return MAX_SPEED;
	}

	@Override
	protected boolean isReadyToMul() {
		return this.daysSinceLastMull.get() >= READY_TO_MULL_AFTER;
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
				this.sex, cell.getX(), cell.getY());
	}
}
