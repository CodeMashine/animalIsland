package org.javarush.module2Task.service;

import org.javarush.module2Task.interfaces.GameField;
import org.javarush.module2Task.interfaces.GameStatistic;

import java.util.concurrent.ConcurrentHashMap;

public class Statistic implements GameStatistic {
	private final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

	private GameField gameField;

	public Statistic(GameField gameField) {
		this.gameField = gameField;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getStatistic() {
		return map;
	}

	public void put(String key, int value) {
		if (map.containsKey(key)) {
			map.put(key, map.get(key) + value);
		} else {
			map.put(key, value);
		}
	}

	public void clear() {
		map.clear();
	}
}
