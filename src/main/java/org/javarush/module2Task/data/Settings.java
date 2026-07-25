package org.javarush.module2Task.data;

public interface Settings {
	int threadsAmount = Runtime.getRuntime().availableProcessors();
	int fieldWidth = 100;
	int fieldHeight = 20;
	double weightLossCoefficient = 0.3 ;
}
