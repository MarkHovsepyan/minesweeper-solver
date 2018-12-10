package minesweeper;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bean.Square;
import csp.CspSolverUtil;

public class MinesweepingAgent {

	private Square[][] arrSquares;
	public static int noOfMines;

	public MinesweepingAgent(Square[][] arrSquares, int noOfMines){
		this.arrSquares = arrSquares;
		this.noOfMines = noOfMines;
	}

	public Set<Square> solve(){
		CspSolverUtil objCsp = new CspSolverUtil();
		Map<Square, Set<Square>> inputMap = generateInputForCsp();
		if(inputMap == null){
			//Probably all the squares equals no of mines
			for(int i = 0 ; i < arrSquares.length; i++){
				for(int j=0; j < arrSquares[0].length; j++){
					if(arrSquares[i][j].isEnabled()){
						arrSquares[i][j].setMarked(true);
						noOfMines--;
						ProblemManager.noOfMines--;
					}
				}
			}
			return null;
		}else{

		}
		Set<Square> safeSquares = objCsp.solve(inputMap);
		return safeSquares.isEmpty()? null : safeSquares;
	}

	private Map<Square, Set<Square>> generateInputForCsp(){

		Map<Square, Set<Square>> retMap = new HashMap<Square, Set<Square>>();
		Set<Square> exploredSquares = getExploredSquares();
		int noOfUnexploredMines = 0;
		Set<Square> setUnexplored = new HashSet<Square>();
		for(Square exploredSquare : exploredSquares){
			Set<Square> unexploredNeighbours = getUnexploredNeighbours(exploredSquare);
			retMap.put(exploredSquare, unexploredNeighbours);
			setUnexplored.addAll(unexploredNeighbours);
		}

		if(setUnexplored.size() == noOfMines){
			return null;
		}else{
			return retMap;
		}

	}

	private Set<Square> getExploredSquares(){
		Set<Square> retSet = new HashSet<Square>();

		for(int i = 0 ; i < arrSquares.length; i++){
			for(int j=0 ; j < arrSquares[i].length; j++){
				if(arrSquares[i][j].getNoOfMinesAround() > 0 && !arrSquares[i][j].isEnabled()){
					retSet.add(arrSquares[i][j]);
				}
			}
		}

		return retSet;
	}


	private Set<Square> getUnexploredNeighbours(Square objSquare){
		int x = objSquare.getLocX();
		int y = objSquare.getLocY();
		Set<Square> retSet = new HashSet<Square>();
		if (y-1 >= 0 ) {
			if(arrSquares[x][y-1].isEnabled()){
				retSet.add(arrSquares[x][y-1]) ;
			}else{
				//do nothing
			}
		}
		if(x-1 >= 0){
			if (y-1 >= 0 ) {
				if(arrSquares[x-1][y-1].isEnabled()){
					retSet.add(arrSquares[x-1][y-1]) ;
				}else{
					//do nothing
				} 
			}
			if (y +1 < arrSquares[0].length){
				if(arrSquares[x-1][y+1].isEnabled()){
					retSet.add(arrSquares[x-1][y+1]);
				}else{
					//do nothing
				}
			}
			if(arrSquares[x-1][y].isEnabled()){
				retSet.add(arrSquares[x-1][y]);
			}else{
				//do nothing
			}

		}else{
			//do nothing. Go On
		}
		if(y+1 < arrSquares[0].length){
			if(arrSquares[x][y+1].isEnabled()){
				retSet.add(arrSquares[x][y+1]);
			}else{
				//do nothing
			}
		}else{
			// do nothing
		}

		if(x+1 < arrSquares.length){
			if(arrSquares[x+1][y].isEnabled()){
				retSet.add(arrSquares[x+1][y]);
			}else{
				//do nothing
			}
			if(y+1 < arrSquares[0].length){
				if(arrSquares[x+1][y+1].isEnabled()){
					retSet.add(arrSquares[x+1][y+1]);
				}else{
					//do nothing
				}
			}
			if(y-1 >= 0){
				if(arrSquares[x+1][y-1].isEnabled()){
					retSet.add(arrSquares[x+1][y-1]);
				}else{
					//do nothing
				}
			}else{
				//do ntohing. Go Ahead.
			}
		}


		return retSet;
	}
}
