package org.javarush_Module_2_Task.clases.herbivore;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.plant.Grass;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.data.SEX;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Mouse extends Herbivore {

	private static final AtomicInteger countRabbit = new AtomicInteger(1);
	private final static String NAME = "Mouse";
	private final static int MAX_AGE = 3;
	private final static int MAX_SPEED = 1;
	private final static int MAX_DAYS_WO_EAT = 2;
	private final static int READY_TO_MULL_AFTER = 1;
	private final static double NEED_TO_EAT = 0.01;
	private final static double WEIGHT = 0.05;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100 , Caterpillar.class , 90);

	private final int id;

	private AtomicInteger age = new AtomicInteger(0);

	public Mouse(GameCell cell, SEX sex) {
		super(cell,sex);
		this.id = countRabbit.getAndIncrement();
	}

	@Override
	public String getName() {
		return this.NAME;
	}

	public int getAge() {
		return this.age.get();
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
		return new Mouse(cell , sex);
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
		return String.format("%s id - %d , age %d , sex - %s , in cell - x: %d , y : %d",this.getName(), this.id, this.age.get(),
				this.sex, cell.getX(), cell.getY());
	}
}
