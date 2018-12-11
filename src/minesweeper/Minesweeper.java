package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ui.GameUI;

public class Minesweeper {

	private static final int MAX_ROWS = 20;
	private static final int MAX_COLS = 20;

	private static final int NAIVE = 10;
	private static final int EXPERT = 20;
	private static final int MEDIUM = 15;

	public static void main(String[] args) {
		if(args.length == 0){
			createReport();
			System.exit(3);
		}
		
		int max_rows = MAX_ROWS;
		int max_cols = MAX_COLS;
		int mode = MEDIUM;
		if (args.length == 3) {
			max_rows = Integer.parseInt(args[0]);
			max_cols = Integer.parseInt(args[1]);
			mode = Integer.parseInt(args[2]);
		}else{
			System.out.println("USAGE: Minesweeper <max_rows> <max_cols> <mode>\n"
					+ "\tMODE VALUES: 0 - NAIVE, 1 - MEDIUM, 2 - EXPERT");
			System.exit(3);
		}

		int minesPercentage = -1;
		switch(mode){
		case 0:
            minesPercentage = NAIVE;
			break;
		case 1:
			minesPercentage = MEDIUM;
			break;
		case 2:
            minesPercentage = EXPERT;
			break;
		default:
			System.out.println("USAGE: Minesweeper <max_rows> <max_cols> <mode>\n"
					+ "\tMODE VALUES: 0 - NAIVE, 1 - MEDIUM, 2 - EXPERT");
			System.exit(3);
		}

		final levelCreator problem = new levelCreator(max_rows, max_cols, minesPercentage);

        final GameUI frame = new GameUI(problem);
		problem.setFrame(frame);
		final SolverAgent agent = new SolverAgent(problem.getTiles(), levelCreator.getNoOfMines());
		frame.setVisible(true);
		frame.startTimer();

		problem.updateModel(max_rows / 2,  max_cols/2);
		frame.repaint();
		Random objRandom = new Random();
		while(levelCreator.noOfMines != 0 && !problem.isGameOver()){
			Set<Tile> safeTiles =  agent.solve();
			if(levelCreator.noOfMines == 0){
				frame.repaint();
				frame.gameEnded(true);
				break;
			}
			if(safeTiles == null){
				ReportTool.guessCount++;
				Tile[][] tileArray = problem.getTiles();
				List<Tile> listOfTiles = new ArrayList<>();
                for (Tile[] aTileArray : tileArray) {
                    for (int j = 0; j < tileArray.length; j++) {
                        if (aTileArray[j].isEnabled()
                                && !aTileArray[j].isMarked()) {
                            listOfTiles.add(aTileArray[j]);
                        }
                    }
                }
				if(listOfTiles.size() != 0) {
					int index = objRandom.nextInt(listOfTiles.size());
					Tile randTile = listOfTiles.get(index);
					System.out.println("GUESSED: " +randTile.getLocX() +" , " +randTile.getLocY());
					problem.updateModel(randTile.getLocX(), randTile.getLocY());
				}
				frame.repaint();
			}else{
				for(Tile objTile : safeTiles){
					problem.updateModel(objTile.getLocX(), objTile.getLocY());
					frame.repaint();
				}
			}
		}
		System.out.println("Count: " + levelCreator.count);
		if(levelCreator.noOfMines == 0){
			frame.gameEnded(true);
		}else{
			frame.gameEnded(false);
		}
		ReportTool.print(max_cols, 0, "MODE");
	}
	
	private static void createReport(){
		int[] cardinality = new int[]{5, 10, 15, 20, 25};
		int[] modes = new int[]{0,1,2};
		long startTime;
		long endTime;
		for(int i : modes){
			for(int j : cardinality){
				startTime = System.currentTimeMillis();
				gameOn(j, j, i);
				endTime = System.currentTimeMillis() - startTime;
				ReportTool.writeToFile(j, endTime, getMode(i));
			}
			
		}
	}
	
	private static String getMode(int mode){
		switch(mode){
		case 0:
			return "NAIVE";
		case 1:
			return "MEDIUM";
		case 2:
			return "EXPERT";
		}
		return "";
	}
	
	private static void gameOn(int max_rows, int max_cols, int mode){
	
		int minesPercentage = -1;
		switch(mode){
		case 2:
			minesPercentage = EXPERT;
			break;
		case 1:
			minesPercentage = MEDIUM;
			break;
		case 0:
			minesPercentage = NAIVE;
			break;
		default:
			System.out.println("USAGE : Minesweeper <max_rows> <max_cols> <mode>\n"
					+ "\tMODE VALUES : 0 - NAIVE, 1 - MEDIUM, 2 - EXPERT");
			System.exit(3);
		}

		final levelCreator problem = new levelCreator(max_rows, max_cols, minesPercentage);

        final GameUI frame = new GameUI(problem);
		problem.setFrame(frame);
		final SolverAgent agent = new SolverAgent(problem.getTiles(), levelCreator.getNoOfMines());
		frame.setVisible(true);

		frame.startTimer();
		problem.updateModel(0, 0);
		frame.repaint();
		Random objRandom = new Random();
		while(levelCreator.noOfMines != 0 && !problem.isGameOver()){
			Set<Tile> safeTiles =  agent.solve();
			if(levelCreator.noOfMines == 0){
				frame.repaint();
				frame.gameEnded(true);
				break;
			}
			if(safeTiles == null){
				ReportTool.guessCount++;
				Tile[][] tileArray = problem.getTiles();
				List<Tile> listOfTiles = new ArrayList<>();
                for (Tile[] aTileArray : tileArray) {
                    for (int j = 0; j < tileArray.length; j++) {
                        if (aTileArray[j].isEnabled()
                                && !aTileArray[j].isMarked()) {
                            listOfTiles.add(aTileArray[j]);
                        }
                    }
                }
				if(listOfTiles.size() != 0) {
					int index = objRandom.nextInt(listOfTiles.size());
					Tile randTile = listOfTiles.get(index);
					System.out.println("GUESSED: " +randTile.getLocX() +" , " +randTile.getLocY());
					problem.updateModel(randTile.getLocX(), randTile.getLocY());
				}
				frame.repaint();
			}else{
				for(Tile objTile : safeTiles){
					problem.updateModel(objTile.getLocX(), objTile.getLocY());
					frame.repaint();
				}
			}
		}
		System.out.println("Count: " + levelCreator.count);
		if(levelCreator.noOfMines == 0){
			frame.gameEnded(true);
		}else{
			frame.gameEnded(false);
		}
		frame.dispose();
		ReportTool.print(max_cols, 0, "MODE");

	}

}
