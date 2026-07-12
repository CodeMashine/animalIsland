package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.data.SEX;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.World;

public abstract class Herbivore extends Animal {
	public Herbivore(int x, int y, SEX sex, World island) {
		super(x, y, sex, island);
	}

	@Override
	public int getHuntTry(){
		return Settings.HuntTryHerbivore;
	}
}
