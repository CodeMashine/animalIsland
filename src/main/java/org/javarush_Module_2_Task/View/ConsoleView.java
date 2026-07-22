package org.javarush_Module_2_Task.View;


import org.javarush_Module_2_Task.interfaces.View;
import org.javarush_Module_2_Task.service.Statistic;

import java.util.concurrent.ConcurrentHashMap;

public class ConsoleView implements View {

	@Override
	public void display(Statistic statistic) {
		final ConcurrentHashMap<String, Integer> stat = statistic.getStatistic();

		for (var entry : stat.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}


	}
}
