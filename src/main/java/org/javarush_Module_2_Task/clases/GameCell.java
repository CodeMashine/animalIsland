package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.errors.AnimalIslandException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class GameCell {
	private final int x;
	private final int y;
	private final ConcurrentLinkedDeque<Unit> units = new ConcurrentLinkedDeque<>();
	private List<GameCell> NEIGHBOURS;

	private static final Map<Class<? extends Unit>, Integer> maxCountOfUnits = new HashMap<>();
	private final Map<Class<? extends Unit>, AtomicInteger> currentCountOfUnits = new ConcurrentHashMap<>();

	static {
		maxCountOfUnits.put(Wolf.class, 30);
		maxCountOfUnits.put(Grass.class, 500);
		maxCountOfUnits.put(Rabbit.class, 150);
	}

	public GameCell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public synchronized boolean add(Unit unit) {
		Class<? extends Unit> unitClass = unit.getClass();
		Integer maxCount = maxCountOfUnits.get(unitClass);
		AtomicInteger current = currentCountOfUnits.computeIfAbsent(unitClass, _ -> new AtomicInteger(0));

		if (current.get() < maxCount) {
			units.add(unit);
			current.incrementAndGet();
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean remove(Unit unit) {
		Class<? extends Unit> unitClass = unit.getClass();
		if (units.remove(unit)) {
			AtomicInteger current = currentCountOfUnits.get(unitClass);
			current.decrementAndGet();
			return true;
		}
		return false;
	}

	public static Map<Class<? extends Unit>, Integer> getMaxCountOfUnits() {
		return maxCountOfUnits;
	}

	public ConcurrentLinkedDeque<Unit> getUnits() {
		return units;
	}

	public Animal[] getAnimals() {
		return units.stream().filter(u -> u instanceof Animal).map(u -> (Animal) u).toArray(size -> new Animal[size]);
	}


	public void setNeighbours(List<GameCell> neighbours) {
		this.NEIGHBOURS = neighbours;
	}

	public List<GameCell> getNeighbours() {
		return this.NEIGHBOURS;
	}

	@Override
	public String toString() {

		StringBuilder sB = new StringBuilder();
		sB.append("GameCell{" + "x=" + x + ", y=" + y + "}\n");
		sB.append("Units : \n");
		for (Unit unit : units) {
				sB.append(unit.toString()+"\n");
		}
		return sB.toString();
	}
}
