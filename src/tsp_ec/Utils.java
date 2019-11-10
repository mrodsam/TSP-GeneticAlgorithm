package tsp_ec;

import java.util.LinkedHashMap;

/**
 * Clase con métodos de conversión entre tipos de variables
 * 
 * @author Marta Rodríguez Sampayo
 *
 */
public class Utils {

	/**
	 * Conversión de un Array a una cadena de texto
	 * 
	 * @param strArray Array a convertir
	 * @return Cadena de texto con los valores del Array separados por comas
	 */
	public static String arrayToString(Object[] strArray) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < strArray.length; i++) {
			stringBuilder.append(strArray[i]);
			stringBuilder.append(",");
		}
		return stringBuilder.toString();
	}

	/**
	 * Conversión de un Array de enteros a un LinkedHashMap
	 * 
	 * @param array Array de enteros
	 * @return LinkedHashMap - Clave: entero del Array, Valor: objeto City asociado
	 *         a dicho entero
	 */
	public static LinkedHashMap<Integer, City> arrayToMap(Integer[] array) {

		LinkedHashMap<Integer, City> individual = new LinkedHashMap<Integer, City>();

		for (int j = 0; j < array.length; j++) {
			individual.put((Integer) array[j], Main.cities.get(array[j]));
		}

		return individual;
	}

	/**
	 * Conversión de un Array de texto a un LinkedHashMap
	 * 
	 * @param array Array de cadenas de texto
	 * @return LinkedHashMap - Clave: cadena de texto del Array convertida a entero,
	 *         Valor: objeto City asociado a dicho entero
	 */
	public static LinkedHashMap<Integer, City> arrayToMap(String[] array) {

		LinkedHashMap<Integer, City> individual = new LinkedHashMap<Integer, City>();

		for (int j = 0; j < array.length; j++) {
			individual.put((Integer.parseInt(array[j])), Main.cities.get(Integer.parseInt(array[j])));
		}

		return individual;
	}

}
