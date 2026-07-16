//package org.javarush_Module_2_Task.clases;
//
//import org.javarush_Module_2_Task.data.SEX;
//import org.javarush_Module_2_Task.errors.AnimalIslandException;
//import org.javarush_Module_2_Task.interfaces.World;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class UnitFactory implements UnitFactory {
//
//	@Override
//	public Unit createUnit(Class<? extends Unit> unit, int x, int y) {
//		Unit curUnit = null;
//		if (Plant.class.isAssignableFrom(unit)) {
//			try {
//				curUnit = unit.getDeclaredConstructor(int.class, int.class, World.class).newInstance(x, y, this);
//
//			} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
//					 NoSuchMethodException e) {
//				throw new AnimalIslandException(e.getMessage());
//			}
//
//		} else {
//			SEX randomSex = ThreadLocalRandom.current().nextBoolean() ? SEX.MALE : SEX.FEMALE;
//			try {
//				curUnit = unit.getDeclaredConstructor(int.class, int.class, SEX.class, World.class).newInstance(x, y,
//						randomSex, this);
//			} catch (InvocationTargetException | InstantiationException | IllegalAccessException |
//					 NoSuchMethodException e) {
//				throw new AnimalIslandException(e.getMessage());
//			}
//		}
//		return curUnit;
//	}
//}
