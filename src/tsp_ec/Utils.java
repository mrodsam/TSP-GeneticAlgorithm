package tsp_ec;

import java.util.LinkedHashMap;

public class Utils {

	public static String arrayToString(Object[] strArray) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < strArray.length; i++) {
			stringBuilder.append(strArray[i]);
			stringBuilder.append(",");
		}
		return stringBuilder.toString();
	}

	public static LinkedHashMap<Integer, City> arrayToMap(Integer[] array) {

		LinkedHashMap<Integer, City> individual = new LinkedHashMap<Integer, City>();

		for (int j = 0; j < array.length; j++) {
			individual.put((Integer) array[j], Main.cities.get(array[j]));
		}

		return individual;
	}

	public static LinkedHashMap<Integer, City> arrayToMap(String[] array) {

		LinkedHashMap<Integer, City> individual = new LinkedHashMap<Integer, City>();

		for (int j = 0; j < array.length; j++) {
			individual.put((Integer.parseInt(array[j])), Main.cities.get(Integer.parseInt(array[j])));
		}

		return individual;
	}

}
