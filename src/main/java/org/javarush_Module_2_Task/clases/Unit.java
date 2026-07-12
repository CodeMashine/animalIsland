package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.interfaces.Multiplyble;
import org.javarush_Module_2_Task.interfaces.World;

public abstract class Unit implements Multiplyble {
	protected int x;
	protected int y;
	protected World world ;

	public Unit(int x, int y , World island) {
		this.x = x;
		this.y = y;
		this.world = island ;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
