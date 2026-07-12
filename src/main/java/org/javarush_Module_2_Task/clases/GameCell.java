package org.javarush_Module_2_Task.clases;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameCell {
	private final int x;
	private final int y;
//	private final CopyOnWriteArrayList<Unit> units = new CopyOnWriteArrayList<>();
	private final ConcurrentLinkedDeque<Unit> units = new ConcurrentLinkedDeque<>();

	private static final Map<Class<? extends Unit>, Integer> maxCountOfUnits = new HashMap<>();
	private final Map<Class<? extends Unit>, AtomicInteger> currentCountOfUnits = new ConcurrentHashMap<>();
//	private final Map<Class<? extends Unit>, Integer> maxCountOfUnits = Map.of(Wolf.class, 30 , Plant.class, 500,Rabbit.class, 150);

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
		AtomicInteger current = currentCountOfUnits.get(unitClass);
		if (current != null) {
			current.decrementAndGet();
			return units.remove(unit);
		}
		return false;
	}

	public Map<Class<? extends Unit> , Integer> getMaxCountOfUnits() {
		return maxCountOfUnits ;
	}

	public ConcurrentLinkedDeque<Unit> getUnits() {
		return units;
	}


	@Override
	public String toString() {
		for (Unit unit : units) {
			if (unit instanceof Wolf) {
				System.out.println(unit.toString());
			}
		}
		return "GameCell{" + "x=" + x + ", y=" + y + '}';
	}
}
