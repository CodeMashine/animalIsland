package org.javarush_Module_2_Task.clases.herbivore;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.clases.plant.Grass;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.data.SEX;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit extends Herbivore {

	private static final AtomicInteger countRabbit = new AtomicInteger(1);
	private final static String NAME = "Rabbit";
	private final static int MAX_AGE = 5;
	private final static int MAX_SPEED = 2;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static int READY_TO_MULL_AFTER = 1;
	private final static double NEED_TO_EAT = 0.45;
	private final static double WEIGHT_ONE_UNIT = 2;
	private final static double FLOCK_SIZE = 50 ;
	private final static Map<Class<? extends Unit>, Integer> FOOD_LIST = Map.of(Grass.class, 100);

	private final int id;

//	private AtomicInteger age = new AtomicInteger(0);

	public Rabbit(GameCell cell) {
		super(cell);
		this.id = countRabbit.getAndIncrement();
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
		return new Rabbit(cell);
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
	public double getWeightOneUnitClass(){
		return WEIGHT;
	}

	@Override
	public String toString() {
		return String.format("%s id - %d , age %d , sex - %s , in cell - x: %d , y : %d",this.getName(), this.id, this.age,
				this.sex, cell.getX(), cell.getY());
	}
}
