package org.javarush_Module_2_Task.clases;

import org.javarush_Module_2_Task.errors.AnimalIslandException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
		if (units.remove(unit)) {
			AtomicInteger current = currentCountOfUnits.get(unitClass);
			current.decrementAndGet();
			return true;
		}
		return false;
	}

//	public synchronized boolean remove(Unit unit) {
//		Class<? extends Unit> unitClass = unit.getClass();
//		AtomicInteger current = currentCountOfUnits.get(unitClass);
//		int cur = current.get();
//		System.out.println(cur + " " + unit);
//		if (cur == 0) {
//			return false;
//		}
//
//		current.decrementAndGet();
//		return units.remove(unit);
//	}




		public Map<Class<? extends Unit>, Integer> getMaxCountOfUnits() {
		return maxCountOfUnits;
	}

	public ConcurrentLinkedDeque<Unit> getUnits() {
		return units;
	}

	public Animal[] getAnimals() {
		return units.stream()
				.filter(u->u instanceof Animal)
				.map(u->(Animal)u)
				.toArray(size -> new Animal[size]);
	}

	public void unitEats(ExecutorService threadPool ) {
		showUnits();
		for (Unit unit : units) {
			if (unit instanceof Animal animal)
				threadPool.execute(() -> animal.eat());
		}

	}


	public void unitMul() {
//		showUnits();
		final ExecutorService threadPool = Executors.newWorkStealingPool();

		for (Unit unit : units) {
				threadPool.execute(() -> unit.multiply());
		}

		threadPool.shutdown();
		try {
			threadPool.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new AnimalIslandException("");
		}
	}

	public void showUnits() {
		System.out.printf("----------units in cell x: %d, y: %d-----------\n", this.getX(), this.getY());
		final Set<Map.Entry<Class<? extends Unit>, AtomicInteger>> entries = currentCountOfUnits.entrySet();

		for (var entry : entries) {
			System.out.println(entry.getKey().getName() + " " + entry.getValue());
		}
		System.out.println("---------------------");
	}

	@Override
	public String toString() {

		StringBuilder sB = new StringBuilder();
		sB.append("GameCell{" + "x=" + x + ", y=" + y + "}\n");
		sB.append("Units : \n");
		for (Unit unit : units) {
			if (unit instanceof Wolf) {
				sB.append(unit.toString());
			}
		}
		return sB.toString();
	}
}
