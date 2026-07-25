package org.javarush_Module_2_Task.service;

import org.javarush_Module_2_Task.interfaces.GameField;
import org.javarush_Module_2_Task.interfaces.GameStatistic;

import java.util.concurrent.ConcurrentHashMap;

public class Statistic implements GameStatistic {
	private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

	private GameField gameField;

	public Statistic(GameField gameField) {
		this.gameField = gameField;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getStatistic() {
		return map;
	}

	public void put(String key) {
		if (map.containsKey(key)) {
			map.put(key, map.get(key) + 1);
		} else {
			map.put(key, 1);
		}
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
