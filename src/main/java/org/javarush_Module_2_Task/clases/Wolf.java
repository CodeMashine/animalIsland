package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.World;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Wolf extends Animal {

	private static AtomicInteger count = new AtomicInteger(0);
	private final static String NAME = "Wolf";
	private final static int MAX_AGE = 12;
	private final static int SPEED = 3;
	private final static int MAX_DAYS_WO_EAT = 3;
	private final static int READY_TO_MULL_AFTER = 3;
	private final static double NEED_TO_EAT = 3;
	private final static double WEIGHT = 50;
	private final static Map<Class<? extends Unit> , Integer> FOODLIST = Map.of(Rabbit.class, 60);
	private final static int HUNT_ATTEMPTS = 3 ;

	private final int id;
	private AtomicInteger age = new AtomicInteger(0);
	private double eaten = 3;


	public Wolf(int x, int y, SEX sex, World island) {
		super(x, y, sex,island);
		this.id = count.incrementAndGet();
//		System.out.println("create " + this);
	}

	public int getAge() {
		return this.age.get();
	}

	@Override
	public void eat() {

	}

	@Override
	public void move() {
		if (this.eaten >= NEED_TO_EAT) { return; }
		int currentSpeed = ThreadLocalRandom.current().nextInt(Wolf.SPEED+1);

		int x = this.x;
		int y = this.y;

		if(x > currentSpeed) {
			
		}

	}

	@Override
	public void multiply() {
	}


	@Override
	public String toString() {
		return String.format("Wolf id - %d , age %d , sex - %s , in cell - x: %d , y : %d", this.id, this.age.get(),
				this.sex, this.getX(), this.getY());
	}
}
