package org.javarush_Module_2_Task.data;

public interface Settings {
	int unitAmountCoef = 3 ;
	int HuntTryHerbivore = Integer.MAX_VALUE ;
	int HuntTryPredator = 7 ;
	int ThreadsAmount = Runtime.getRuntime().availableProcessors();;
}
