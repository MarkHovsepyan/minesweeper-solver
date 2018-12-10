package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import minesweeper.bean.Square;
import ui.MineSweeperUIFrame;

public class Minesweeper {

	public static final int MAX_ROWS = 20;
	public static final int MAX_COLS = 20;

	public static final int EXPERT = 20;
	public static final int MEDIUM = 15;
	public static final int NAIIVE = 10;

	//
	/**
	 *
	 */
	public Minesweeper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 0){
			generateReport();
			System.exit(3);
		}else{
			//do nothing.
		}
		
		int max_rows = MAX_ROWS;
		int max_cols = MAX_COLS;
		int mode = MEDIUM;
		if (args.length == 3) {
			max_rows = Integer.parseInt(args[0]);
			max_cols = Integer.parseInt(args[1]);
			mode = Integer.parseInt(args[2]);
		}else{
			System.out.println("USAGE : Minesweeper <max_rows> <max_cols> <mode>\n"
					+ "\tMODE VALUES : 0 - NAIIVE, 1 - MEDIUM, 2 - EXPERT");
			System.exit(3);
		}
		int percentMines = -1;
		switch(mode){
		case 2:
			percentMines = EXPERT;
			break;
		case 1:
			percentMines = MEDIUM;
			break;
		case 0:
			percentMines = NAIIVE;
			break;
		default:
			System.out.println("USAGE : Minesweeper <max_rows> <max_cols> <mode>\n"
					+ "\tMODE VALUES : 0 - NAIIVE, 1 - MEDIUM, 2 - EXPERT");
			System.exit(3);
		}

		final ProblemManager problem = new ProblemManager(max_rows, max_cols, percentMines);

		final int rows = max_rows;
		final int cols = max_cols;
		final MineSweeperUIFrame frame = new MineSweeperUIFrame(problem);
		problem.setFrame(frame);
		final MinesweepingAgent agent = new MinesweepingAgent(problem.getSquares(), problem.getNoOfMines());
		frame.setVisible(true);
		frame.startTimer();
		
		problem.updateModel(max_rows / 2,  max_cols/2);
		frame.repaint();
		Random objRandom = new Random();
		while(ProblemManager.noOfMines != 0 && !problem.isGameOver()){
			Set<Square> safeSquares =  agent.solve();
			if(ProblemManager.noOfMines == 0){
				frame.repaint();
				frame.gameEnded(true);
				break;
			}
			if(safeSquares == null){
				ReportUtil.guessCount++;
				Square[][] arrSquares = problem.getSquares();
				List<Square> lstSquares = new ArrayList<Square>();
				for(int i=0; i< arrSquares.length; i++){
					for(int j=0; j<arrSquares.length; j++){
						if(arrSquares[i][j].isEnabled()
								&& !arrSquares[i][j].isMarked()){
							lstSquares.add(arrSquares[i][j]);
						}
					}
				}
				if(lstSquares.size() == 0){
					//do nothing.
				}else{
					int index = objRandom.nextInt(lstSquares.size());
					Square randSquare = lstSquares.get(index);
					System.out.println("GUESSED : " +randSquare.getLocX() +" , " +randSquare.getLocY());
					problem.updateModel(randSquare.getLocX(), randSquare.getLocY());
				}
				frame.repaint();
			}else{
				for(Square objSquare : safeSquares){
					problem.updateModel(objSquare.getLocX(), objSquare.getLocY());
					frame.repaint();
				}
			}
		}
		System.out.println("Count : " +ProblemManager.count);
		if(ProblemManager.noOfMines == 0){
			frame.gameEnded(true);
		}else{
			frame.gameEnded(false);
		}
		ReportUtil.print(max_cols, 0, "MODE");
	}
	
	private static void generateReport(){
		int[] cardinality = new int[]{5, 10, 15, 20, 25};
		int[] modes = new int[]{0,1,2};
		long startTime = 0L;
		long endTime = 0L;
		for(int i : modes){
			for(int j : cardinality){
				startTime = System.currentTimeMillis();
				gameOn(j, j, i);
				endTime = System.currentTimeMillis() - startTime;
				ReportUtil.writeToFile(j, endTime, getMode(i));
			}
			
		}
	}
	
	private static String getMode(int mode){
		switch(mode){
		case 0:
			return "NAIIVE";
		case 1:
			return "MEDIUM";
		case 2:
			return "EXPERT";
		}
		return "";
	}
	
	private static void gameOn(int max_rows, int max_cols, int mode){
	
		int percentMines = -1;
		switch(mode){
		case 2:
			percentMines = EXPERT;
			break;
		case 1:
			percentMines = MEDIUM;
			break;
		case 0:
			percentMines = NAIIVE;
			break;
		default:
			System.out.println("USAGE : Minesweeper <max_rows> <max_cols> <mode>\n"
					+ "\tMODE VALUES : 0 - NAIIVE, 1 - MEDIUM, 2 - EXPERT");
			System.exit(3);
		}

		final ProblemManager problem = new ProblemManager(max_rows, max_cols, percentMines);

		final int rows = max_rows;
		final int cols = max_cols;
		final MineSweeperUIFrame frame = new MineSweeperUIFrame(problem);
		problem.setFrame(frame);
		final MinesweepingAgent agent = new MinesweepingAgent(problem.getSquares(), problem.getNoOfMines());
		frame.setVisible(true);

		frame.startTimer();
		problem.updateModel(0, 0);
		frame.repaint();
		Random objRandom = new Random();
		while(ProblemManager.noOfMines != 0 && !problem.isGameOver()){
			Set<Square> safeSquares =  agent.solve();
			if(ProblemManager.noOfMines == 0){
				frame.repaint();
				frame.gameEnded(true);
				break;
			}
			if(safeSquares == null){
				ReportUtil.guessCount++;
				Square[][] arrSquares = problem.getSquares();
				List<Square> lstSquares = new ArrayList<Square>();
				for(int i=0; i< arrSquares.length; i++){
					for(int j=0; j<arrSquares.length; j++){
						if(arrSquares[i][j].isEnabled()
								&& !arrSquares[i][j].isMarked()){
							lstSquares.add(arrSquares[i][j]);
						}
					}
				}
				if(lstSquares.size() == 0){
					//do nothing.
				}else{
					int index = objRandom.nextInt(lstSquares.size());
					Square randSquare = lstSquares.get(index);
					System.out.println("GUESSED : " +randSquare.getLocX() +" , " +randSquare.getLocY());
					problem.updateModel(randSquare.getLocX(), randSquare.getLocY());
				}
				frame.repaint();
			}else{
				for(Square objSquare : safeSquares){
					problem.updateModel(objSquare.getLocX(), objSquare.getLocY());
					frame.repaint();
				}
			}
		}
		System.out.println("Count : " +ProblemManager.count);
		if(ProblemManager.noOfMines == 0){
			frame.gameEnded(true);
		}else{
			frame.gameEnded(false);
		}
		frame.dispose();
		ReportUtil.print(max_cols, 0, "MODE");

	}

}
