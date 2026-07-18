package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.Aging;
import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.GameField;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Unit implements Multiplyble, Aging {
	protected GameCell cell;
	protected String name;
	protected double weight;
	protected int age;
	protected boolean isDead = false;

	public Unit(GameCell cell) {
		this.cell = cell;
	}

	public abstract double getWeight();

	public abstract String getName();

	@Override
	public void multiply() {
		try {
			Unit unit = this.getClass().getDeclaredConstructor(GameCell.class).newInstance(cell);
			cell.add(unit);
		} catch (RuntimeException | NoSuchMethodException | InvocationTargetException | InstantiationException |
				 IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void getOlder() {
		this.age += 1;
	}
}
