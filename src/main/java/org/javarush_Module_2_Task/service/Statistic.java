package org.javarush_Module_2_Task.service;

import org.javarush_Module_2_Task.interfaces.GameField;
import org.javarush_Module_2_Task.interfaces.GameStatistic;

import java.util.concurrent.ConcurrentHashMap;

public class Statistic implements GameStatistic {
	private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

	private GameField gameField;

	public Statistic (GameField gameField) {
		this.gameField = gameField;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getStatistic() {
		return map;
	}

	public void put (String key) {
		map.merge(key, 1, Integer::sum);
	}

	public void clear () {
		map.clear();
	}
}
