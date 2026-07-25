package org.javarush_Module_2_Task.clases.game;

import org.javarush_Module_2_Task.clases.Animal;
import org.javarush_Module_2_Task.clases.Unit;
//import org.javarush_Module_2_Task.clases.herbivore.Horse;
//import org.javarush_Module_2_Task.clases.predators.Boa;
import org.javarush_Module_2_Task.clases.herbivore.Bison;
import org.javarush_Module_2_Task.clases.herbivore.Boar;
import org.javarush_Module_2_Task.clases.herbivore.Caterpillar;
import org.javarush_Module_2_Task.clases.herbivore.Deer;
import org.javarush_Module_2_Task.clases.herbivore.Duck;
import org.javarush_Module_2_Task.clases.herbivore.Goat;
import org.javarush_Module_2_Task.clases.herbivore.Horse;
import org.javarush_Module_2_Task.clases.herbivore.Mouse;
import org.javarush_Module_2_Task.clases.herbivore.Sheep;
import org.javarush_Module_2_Task.clases.predators.Bear;
import org.javarush_Module_2_Task.clases.predators.Boa;
import org.javarush_Module_2_Task.clases.predators.Eagle;
import org.javarush_Module_2_Task.clases.predators.Fox;
import org.javarush_Module_2_Task.clases.predators.Wolf;
//import org.javarush_Module_2_Task.clases.herbivore.Caterpillar;
//import org.javarush_Module_2_Task.clases.herbivore.Mouse;
import org.javarush_Module_2_Task.clases.herbivore.Rabbit;
import org.javarush_Module_2_Task.clases.plant.Grass;

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
		maxCountOfUnits.put(Mouse.class, 500);
		maxCountOfUnits.put(Caterpillar.class, 1000);
		maxCountOfUnits.put(Horse.class, 20);
		maxCountOfUnits.put(Boa.class, 30);
		maxCountOfUnits.put(Bear.class, 5);
		maxCountOfUnits.put(Deer.class, 20);
		maxCountOfUnits.put(Eagle.class, 20);
		maxCountOfUnits.put(Fox.class, 30);
		maxCountOfUnits.put(Bison.class, 10);
		maxCountOfUnits.put(Duck.class, 200);
		maxCountOfUnits.put(Sheep.class, 140);
		maxCountOfUnits.put(Boar.class, 50);
		maxCountOfUnits.put(Goat.class, 140);


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
		int currentCount = currentCountOfUnits.get(unitClass).get();

		int maxFlockCount =  maxCount / unit.getFlockSize();

//		System.out.println(unit + " try add in" + this);
		if (currentCount == maxFlockCount) {
			return false;
		} else {
//			System.out.println(unit + " add in " + this);
			units.add(unit);
			currentCountOfUnits.get(unitClass).incrementAndGet();
			return true;
		}
	}

	public synchronized boolean remove(Unit unit) {
		Class<? extends Unit> unitClass = unit.getClass();
//		System.out.println(unit + "try removed");
		if (units.remove(unit)) {
//			System.out.println(unit + " removed");
			currentCountOfUnits.get(unitClass).decrementAndGet();
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
		return new ArrayList<>(this.NEIGHBOURS);
	}


	private void fillStartValue() {
		for (Map.Entry<Class<? extends Unit>, Integer> entry : maxCountOfUnits.entrySet()) {
			currentCountOfUnits.put(entry.getKey(), new AtomicInteger(0));
		}
	}

	@Override
	public String toString() {

		StringBuilder sB = new StringBuilder();
		sB.append("GameCell{" + "x=" + x + ", y=" + y + "}\n");
		sB.append("Units : \n");
		for (var unit : units) {
			sB.append("flock "+ unit.getName() + " with " + unit.getCurrentFlockSize() + " members" + "\n");
		}
		return sB.toString();
	}
}
