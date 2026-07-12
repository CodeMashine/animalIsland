package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.interfaces.Eating;
import org.javarush_Module_2_Task.interfaces.Moveble;
import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.World;

public abstract class Animal extends Unit implements Moveble, Multiplyble, Eating {
	protected final SEX sex;

	public Animal(int x, int y, SEX sex , World island) {
		super(x, y , island);
		this.sex = sex;
	}

}
