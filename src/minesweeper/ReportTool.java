package minesweeper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportTool {

	public static int guessCount = 0;
	public static int minesAttackedCount = 0;
	public static int solutionFound = 0;
	public static int noSolutionFound = 0;
	public static int ambiguous = 0;
	
	
	public static void writeToFile(int cardinality, long time, String mode){
		try {
			if(!new File("results.csv").exists()){
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("results.csv", true)));
			    out.println("CARDINALITY, TIME TAKEN, MODE, GUESS COUNT, MINES ATTACKED COUNT, SOLUTION FOUND, NO SOLUTION FOUND, AMBIGUOUS SOLUTION FOUND");
			    out.close();
			}
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("results.csv", true)));

		    String line = cardinality +", " +time +", " +mode +", " +guessCount +", " +minesAttackedCount +", "+solutionFound+", " +noSolutionFound +", " +ambiguous;
		    out.println(line);
		    out.close();
		} catch (IOException e) {
		    //add exception later
		}
	}
	public static void print(int cardinality, long time, String mode){
		System.out.println("CARDINALITY, TIME TAKEN, MODE,GUESS COUNT, MINES ATTACKED COUNT, SOLUTION FOUND, NO SOLUTION FOUND, AMBIGUOUS SOLUTION FOUND");
		String line = cardinality +", " +time +", " +mode +", " +guessCount +", " +minesAttackedCount +", "+solutionFound+", " +noSolutionFound +", " +ambiguous;
	    System.out.println(line);
	}

}
