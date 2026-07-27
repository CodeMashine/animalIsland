package org.javarush.module2Task.interfaces;

import java.util.concurrent.ConcurrentHashMap;

public interface GameStatistic {
	 ConcurrentHashMap<String, Integer>  getStatistic();
}
