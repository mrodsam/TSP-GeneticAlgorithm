package tsp_ec;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Evaluation {

	public static Double computeSuccessRate(Double optimalSolution) {
		Double sr;

		File file = new File("times.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		Double runs = 0.0;
		Double successfulRuns = 0.0;
		while (sc.hasNextLine()) {
			if(!sc.hasNext())
				break;
			runs++;
			sc.next();
			if(Double.valueOf(sc.next()).equals(optimalSolution))
				successfulRuns++;
		}
		sr = (successfulRuns/runs);
		
		return sr;
	}
	
	public static Double computeMBF() {
		Double mbf;

		File file = new File("times.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		Double total = 0.0;
		Double runs = 0.0;
		while (sc.hasNextLine()) {
			if(!sc.hasNext())
				break;
			runs++;
			sc.next();
			total+=Double.valueOf(sc.next());
		}
		mbf = total/runs;
		
		return mbf;
	}
}
