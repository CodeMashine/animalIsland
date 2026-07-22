package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.clases.game.GameCell;
import org.javarush_Module_2_Task.interfaces.Aging;
import org.javarush_Module_2_Task.interfaces.Multiplyble;

public abstract class Unit implements Multiplyble, Aging {
	public GameCell cell;
	protected double currentWeight;
	protected int age;
	protected boolean isDead = false;

	public Unit(GameCell cell) {
		this.cell = cell;
		this.currentWeight = getWeightClass();
	}

	public  double getWeight(){
		return currentWeight;
	};

	public abstract double getWeightClass();

	public abstract String getName();

	@Override
	abstract public void multiply();

	@Override
	public void getOlder() {
		this.age += 1;
	}

	public synchronized void setWeight(double weight) {
		this.currentWeight = weight;
	}

	public void dead() {
//		System.out.println(this + " dead day with out food " + this.daysWOEat );
		this.isDead = true;
		this.cell.remove(this);
	}
}
