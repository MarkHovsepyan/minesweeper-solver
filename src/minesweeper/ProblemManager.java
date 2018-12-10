package minesweeper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import bean.Square;
import ui.MineSweeperUIFrame;

public class ProblemManager {

	private Square[][] arrSquares;
	private int max_rows = 0;
	private int max_cols = 0;
	private Set<Integer> mineLocations;
	public static int noOfMines;
	private MineSweeperUIFrame frame;
	private boolean gameOver;
	public static int count = 0;
	
	public boolean isGameOver(){
		return gameOver;
	}


	public void setFrame(MineSweeperUIFrame frame) {
		this.frame = frame;
	}

	public ProblemManager(int rows, int columns, int percent_mines){
		arrSquares = new Square[rows][columns];
		max_rows = rows;
		max_cols = columns;
		noOfMines = rows * columns * percent_mines / 100;
		mineLocations = getRandomMineLocations(noOfMines, rows, columns);
		for(int i = 0; i< rows; i++){
			for(int j=0; j<columns; j++){
				//arrSquares[i][j] = new Square(mineLocations.contains(i * 10 + j), i, j);
				arrSquares[i][j] = new Square(i, j);
			}
		}
	}

	public static int getNoOfMines() {
		return noOfMines;
	}

	public static void setNoOfMines(int noOfMines) {
		ProblemManager.noOfMines = noOfMines;
	}

	public Square[][] getSquares(){
		return arrSquares;
	}

	private Set<Integer> getRandomMineLocations(int noOfMines, int rows, int columns){
		Set<Integer> retSet = new HashSet<Integer>();
		Random rand = new Random();
		int maxRange = rows*columns;
		int count = 0;
		while(count < noOfMines){
		//for(int i = 0; i < noOfMines; i++){
			int randomIndex = rand.nextInt(maxRange - 1) + 1;
			if(retSet.contains(randomIndex) || randomIndex == (rows * columns / 2  + columns / 2  )){
				//i--;
				continue;
			}else{
				retSet.add(randomIndex);
				count++;
			}
		}
		
		System.out.println("RANDOM NUMBERS GENERATED : " +retSet);
		return retSet;
	}

	/*public boolean checkSquare(int x, int y){
		if(containsMine(x,y)){
			return false;
		}else{
			updateModel(x, y);
			return true;
		}

	}*/

	public boolean containsMine(int x, int y){
		return mineLocations.contains(x * max_cols+y);
	}
	public void updateModel(int x, int y){
		if(containsMine(x, y)){
			System.out.println("MINE AT : " +x +" - " +y);
			ReportUtil.minesAttackedCount++;
			count++;
			return;
		}
		if(arrSquares[x][y].getNoOfMinesAround() == -1){
			int noOfMinesAround = 0;
	
			Set<Square> neighbourSet =getAdjacentSquares(x, y); 
			for(Square neighbour :  neighbourSet){
				if(containsMine(neighbour.getLocX(),neighbour.getLocY())){
					noOfMinesAround++;
				}
			}
			arrSquares[x][y].setNoOfMinesAround(noOfMinesAround);
	
			if(noOfMinesAround ==0 &&  arrSquares[x][y].isEnabled() ){
				arrSquares[x][y].setEnabled(false);
				for(Square neighbour : neighbourSet){
					if(neighbour.isEnabled()){
						//Set<Square> safeSet = 
						updateModel(neighbour.getLocX(), neighbour.getLocY());
						//safeSquaresSet.addAll(safeSet);
					}else if (neighbour.isMarked()){
						//noOfMines--;
					}else{
						//do nothing
					}
				}
			}else{
				//do nothing.
			}
		}else{
			//do nothing.
		}
		//return safeSquaresSet;
	}

	private Set<Square> getAdjacentSquares(int x, int y){
		Set<Square> retSet = new HashSet<Square>();
		if(x-1 >= 0){
			if (y-1 >= 0 ) {
				retSet.add(arrSquares[x-1][y-1]) ;
				retSet.add(arrSquares[x][y-1]) ;
			}else{
				//do nothing. Go Ahead.
			} 
			if (y +1 < max_cols){
				retSet.add(arrSquares[x-1][y+1]);
			}
			retSet.add(arrSquares[x-1][y]);

		}else{
			//do nothing. Go On
		}
		if(x+1 < max_rows){
			retSet.add(arrSquares[x+1][y]);
			if(y+1 < max_cols){
				retSet.add(arrSquares[x+1][y+1]);
				retSet.add(arrSquares[x][y+1]);
				if(y-1 >= 0){
					retSet.add(arrSquares[x+1][y-1]);
				}else{
					//do ntohing. Go Ahead.
				}
			}

		}
		return retSet;
	}


}
