package org.javarush_Module_2_Task.clases.herbivore;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.plant.Grass;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.data.SEX;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Horse extends Herbivore {

	private static final AtomicInteger countHorse = new AtomicInteger(1);
	private final static String NAME = "Horse";
	private final static int MAX_AGE = 20;
	private final static int MAX_SPEED = 4;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static int READY_TO_MULL_AFTER = 2;
	private final static double NEED_TO_EAT = 60;
	private final static double WEIGHT = 400;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	private final int id;

	public Horse(GameCell cell, SEX sex) {
		super(cell,sex);
		this.id = countHorse.getAndIncrement();
	}

	@Override
	public String getName() {
		return this.NAME;
	}

	public int getSpeed() {
		return MAX_SPEED;
	}

	@Override
	public  int getMaxAge() {
		return MAX_AGE;
	}


	@Override
	protected Unit getChild() {
		SEX sex = ThreadLocalRandom.current().nextBoolean() ? SEX.MALE : SEX.FEMALE;
		return new Horse(cell , sex);
	}

	@Override
	public int getMaxDaysWOEat() {
		return MAX_DAYS_WO_EAT;
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
	protected boolean isReadyToMul() {
		return this.daysSinceLastMull.get() >= READY_TO_MULL_AFTER;
	}

	@Override
	public double getWeightClass(){
		return WEIGHT;
	}

	@Override
	public String toString() {
		return String.format("%s id - %d , age %d , sex - %s , in cell - x: %d , y : %d",this.getName(), this.id, this.age,
				this.sex, cell.getX(), cell.getY());
	}
}
