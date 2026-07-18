package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.GameField;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Wolf extends Predator {


	private static AtomicInteger count = new AtomicInteger(0);
	private final static String NAME = "Wolf";
	private final static int MAX_AGE = 12;
	private final static int SPEED = 3;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static int READY_TO_MULL_AFTER = 3;
	private final static double NEED_TO_EAT = 3;
	private final static double WEIGHT = 50;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Rabbit.class, 60);
//	private final static int HUNT_ATTEMPTS = 3;

	private final int id;
	private AtomicInteger age = new AtomicInteger(0);


	public Wolf(GameCell cell, SEX sex) {
		super(cell, sex);
		this.id = count.incrementAndGet();
	}

	public String getName() {
		return this.NAME;
	}


	@Override
	protected int getSpeed() {
		return SPEED;
	}

	public int getAge() {
		return this.age.get();
	}

	@Override
	public double getFoodNeedToEat() {
		return NEED_TO_EAT;
	}

	@Override
	public Map<Class<? extends Unit>, Integer> getFoodList() {
		return FOOD_LIST;
	}

	@Override
	protected boolean isReadyToMul() {
		return this.daysSinceLastMull.get() >= READY_TO_MULL_AFTER;
	}

	@Override
	public double getWeight() {
		return WEIGHT;
	}

	@Override
	public String toString() {
		return String.format("Wolf id - %d , age %d , sex - %s , in cell - x: %d , y : %d", this.id, this.age.get(),
				this.sex, cell.getX(), cell.getY());
	}
}
