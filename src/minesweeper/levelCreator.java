package minesweeper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ui.GameUI;

public class levelCreator {

	private Tile[][] tileArray;
	private int max_rows = 0;
	private int max_cols = 0;
	private Set<Integer> mineLocations;
	public static int noOfMines;
	private GameUI frame;
	private boolean gameOver;
	public static int count = 0;
	
	public boolean isGameOver(){
		return gameOver;
	}


	public void setFrame(GameUI frame) {
		this.frame = frame;
	}

	public levelCreator(int rows, int columns, int percent_mines){
		tileArray = new Tile[rows][columns];
		max_rows = rows;
		max_cols = columns;
		noOfMines = rows * columns * percent_mines / 100;
		mineLocations = getRandomMineLocations(noOfMines, rows, columns);
		for(int i = 0; i< rows; i++){
			for(int j=0; j<columns; j++){
				//tileArray[i][j] = new Tile(mineLocations.contains(i * 10 + j), i, j);
				tileArray[i][j] = new Tile(i, j);
			}
		}
	}

	public static int getNoOfMines() {
		return noOfMines;
	}

	public static void setNoOfMines(int noOfMines) {
		levelCreator.noOfMines = noOfMines;
	}

	public Tile[][] getTiles(){
		return tileArray;
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

	/*public boolean checkTile(int x, int y){
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
			ReportTool.minesAttackedCount++;
			count++;
			return;
		}
		if(tileArray[x][y].getNoOfMinesAround() == -1){
			int noOfMinesAround = 0;
	
			Set<Tile> neighbourSet =getAdjacentTiles(x, y); 
			for(Tile neighbour :  neighbourSet){
				if(containsMine(neighbour.getLocX(),neighbour.getLocY())){
					noOfMinesAround++;
				}
			}
			tileArray[x][y].setNoOfMinesAround(noOfMinesAround);
	
			if(noOfMinesAround ==0 &&  tileArray[x][y].isEnabled() ){
				tileArray[x][y].setEnabled(false);
				for(Tile neighbour : neighbourSet){
					if(neighbour.isEnabled()){
	
						updateModel(neighbour.getLocX(), neighbour.getLocY());
			
					}else if (neighbour.isMarked()){
			
					}else{

					}
				}
			}else{

			}
		}else{
		
		}

	}

	private Set<Tile> getAdjacentTiles(int x, int y){
		Set<Tile> retSet = new HashSet<Tile>();
		if(x-1 >= 0){
			if (y-1 >= 0 ) {
				retSet.add(tileArray[x-1][y-1]) ;
				retSet.add(tileArray[x][y-1]) ;
			}else{
				//do nothing. Go Ahead.
			} 
			if (y +1 < max_cols){
				retSet.add(tileArray[x-1][y+1]);
			}
			retSet.add(tileArray[x-1][y]);

		}else{
			//do nothing. Go On
		}
		if(x+1 < max_rows){
			retSet.add(tileArray[x+1][y]);
			if(y+1 < max_cols){
				retSet.add(tileArray[x+1][y+1]);
				retSet.add(tileArray[x][y+1]);
				if(y-1 >= 0){
					retSet.add(tileArray[x+1][y-1]);
				}else{
					//do ntohing. Go Ahead.
				}
			}

		}
		return retSet;
	}


}
