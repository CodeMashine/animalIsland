package org.javarush_Module_2_Task.data;

public interface Settings {
	int unitAmountCoef = 4 ;
	int HuntTryHerbivore = Integer.MAX_VALUE ;
	int HuntTryPredator = 10 ;
	int ThreadsAmount = Runtime.getRuntime().availableProcessors();
	int FieldWidth = 100;
	int FieldHeight = 20;


	int extraLargeGroupCoef = 100 ;
	int largeGroupCoef = 50 ;
	int middleGroupCoef = 10 ;
	int groupCoef = 5 ;
}
