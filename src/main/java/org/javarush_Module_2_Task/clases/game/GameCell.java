package org.javarush_Module_2_Task.clases.game;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.Unit;
import org.javarush_Module_2_Task.clases.herbivore.Horse;
import org.javarush_Module_2_Task.clases.predators.Boa;
import org.javarush_Module_2_Task.clases.predators.Wolf;
import org.javarush_Module_2_Task.clases.herbivore.Caterpillar;
import org.javarush_Module_2_Task.clases.herbivore.Mouse;
import org.javarush_Module_2_Task.clases.herbivore.Rabbit;
import org.javarush_Module_2_Task.clases.plant.Grass;
import org.javarush_Module_2_Task.data.Settings;
import org.javarush_Module_2_Task.interfaces.Massive;

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
//		maxCountOfUnits.put(Mouse.class, 500/Settings.groupCoef);
//		maxCountOfUnits.put(Caterpillar.class, 1000/Settings.groupCoef);
//		maxCountOfUnits.put(Horse.class, 20/Settings.groupCoef);
//		maxCountOfUnits.put(Boa.class, 30/Settings.groupCoef);
//		maxCountOfUnits.put(Bear.class, 5/Settings.groupCoef);
//		maxCountOfUnits.put(Deer.class, 20/Settings.groupCoef);
//		maxCountOfUnits.put(Eagle.class, 20/Settings.groupCoef);
//		maxCountOfUnits.put(Fox.class, 30/Settings.groupCoef);
//		maxCountOfUnits.put(Bison.class, 10/Settings.groupCoef);
//		maxCountOfUnits.put(Duck.class, 200/Settings.groupCoef);
//		maxCountOfUnits.put(Sheep.class, 140/Settings.groupCoef);
//		maxCountOfUnits.put(Boar.class, 50/Settings.groupCoef);
//		maxCountOfUnits.put(Goat.class, 140/Settings.groupCoef);



	}

	public GameCell(int x, int y) {
		this.x = x;
		this.y = y;
		fillStartValue();
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
		AtomicInteger current = currentCountOfUnits.get(unitClass);

		if (current.get() < maxCount) {
			current.incrementAndGet();
//			if (unit instanceof Massive) {
//				for (Unit currUnit : units) {
//					if (currUnit.getClass().equals(unit.getClass())) {
//						currUnit.multiply();
//						return false;
//					}
//				}
//			}
			units.add(unit);
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

	public Map<Class<? extends Unit>, AtomicInteger> getCurrentCountOfUnits() {
		return currentCountOfUnits;
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


	private void fillStartValue() {
		for(Map.Entry<Class<? extends Unit>, Integer> entry : maxCountOfUnits.entrySet()) {
			currentCountOfUnits.put(entry.getKey() ,new AtomicInteger(0) );
		}
	}

	@Override
	public String toString() {

		StringBuilder sB = new StringBuilder();
		sB.append("GameCell{" + "x=" + x + ", y=" + y + "}\n");
//		sB.append("Units : \n");
//		for (Unit unit : units) {
//				sB.append(unit.toString()+"\n");
//		}
		sB.append("Units : \n");
		for (var unit : currentCountOfUnits.entrySet()) {
			sB.append(unit.getKey() + "   " + unit.getValue() + "\n");
		}
		return sB.toString();
	}
}
