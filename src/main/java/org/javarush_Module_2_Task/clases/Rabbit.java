package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.World;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit extends Animal{

	private static AtomicInteger count = new AtomicInteger(0);
	private final int id;
	private final static String NAME = "Rabbit";
	private final static int MAX_AGE = 5;
	private final static int MAX_SPEED = 1;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static int READY_TO_MULL_AFTER = 1;
	private int daysSinceLastMull = 0 ;
	private final static double NEED_TO_EAT = 0.45;
	private final static double WEIGHT = 8;


	private final static Map<Class<? extends Unit> , Integer> FOODLIST = Map.of(Grass.class, 100);
	private AtomicInteger age = new AtomicInteger(0);

	public Rabbit(int x, int y, SEX sex, World island) {
		super(x, y, sex,island);
		this.id = count.incrementAndGet();
		System.out.println("create " + this);
	}

	public int getAge() {
		return this.age.get();
	}

	@Override
	public void eat() {

	}

	@Override
	public void move() {

	}

	@Override
	public void multiply() {
		GameCell currentCell = world.getCell(getX(), getY());
		CopyOnWriteArrayList<Unit> units = currentCell.getUnits();



	}


	@Override
	public String toString() {
		return String.format("Rabbit id - %d , age %d , sex - %s , in cell - x: %d , y : %d", this.id, this.age.get(),
				this.sex, this.getX(), this.getY());
	}
}
