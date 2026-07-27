package org.javarush.module2Task.View;


import org.javarush.module2Task.interfaces.View;
import org.javarush.module2Task.service.Statistic;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleView implements View {

	@Override
	public void display(Statistic statistic) {
		final ConcurrentHashMap<String, Integer> stat = statistic.getStatistic();
		stat.entrySet().stream()
				.sorted((a, b) -> b.getValue().compareTo(a.getValue()))
				.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

	}

}
