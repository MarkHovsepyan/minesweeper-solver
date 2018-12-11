package csp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jacop.constraints.Constraint;
import org.jacop.constraints.Sum;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;

import minesweeper.SolverAgent;
import minesweeper.levelCreator;
import minesweeper.ReportTool;
import minesweeper.Tile;


public class CSPSolver {

	private Store objStore;

	public CSPSolver() {
		this.objStore = new Store();
	}


	public Set<Tile> solve(Map<Tile, Set<Tile>> input){
		Map<Tile, IntVar> varMap = new HashMap<>();
		for(Tile exploredTile : input.keySet()){
			Set<Tile> vars = input.get(exploredTile);
			IntVar[] arrVar = new IntVar[vars.size()];
			int index = 0;
			for(Tile objTile : vars){
				if(varMap.containsKey(objTile)){
					arrVar[index++] = varMap.get(objTile);
				}else{
					if(objTile.isMarked()){
						arrVar[index] = new IntVar(objStore, objTile.getLocX() +"," +objTile.getLocY(), 1 ,1);
					}else{
						arrVar[index] = new IntVar(objStore, objTile.getLocX() +"," +objTile.getLocY(), 0 ,1);
					}
					varMap.put(objTile, arrVar[index++]);
				}
			}

			int val = exploredTile.getNoOfMinesAround();
			Constraint objConstraint = new Sum(arrVar, new IntVar(objStore, val, val));
			objStore.impose(objConstraint);
		}

		IntVar[] vars = new IntVar[varMap.values().size()];

		Map<Integer, Tile> TileMap = new HashMap<>();
		Map<Tile, Set<Integer>> solutionMap = new HashMap<>();
		int index = 0;
		for(Tile objTile : varMap.keySet()){
			vars[index] = varMap.get(objTile);
			TileMap.put(index++, objTile);
			solutionMap.put(objTile, new HashSet<>());
		}

		Search<IntVar> label = new DepthFirstSearch<>();
		SelectChoicePoint<IntVar> select = new InputOrderSelect<>(objStore, vars, new IndomainMin<IntVar>());
		label.getSolutionListener().searchAll(true);
		label.getSolutionListener().recordSolutions(true);
		boolean result = label.labeling(objStore, select);
		Set<Tile> safeSet = new HashSet<>();
		if(result){
			for (int i=1; i<=label.getSolutionListener().solutionsNo(); i++){
				for (int j=0; j<label.getSolution(i).length; j++){
					Tile objTile = TileMap.get(j);
					solutionMap.get(objTile).add(Integer.parseInt(label.getSolution(i)[j].toString()));
				}
			}

			//Getting the ones with single solution
			for(Tile objTile : solutionMap.keySet()){
				int count = solutionMap.get(objTile).size();
				if(count == 1){
					int sol = (Integer)solutionMap.get(objTile).toArray()[0];
					if(sol== 0){
						safeSet.add(objTile);
						ReportTool.solutionFound++;
					}else{
						objTile.setMarked(true);
						objTile.setEnabled(false);
						levelCreator.noOfMines--;
						SolverAgent.noOfMines--;
						ReportTool.solutionFound++;
					}
					
				}else{
					ReportTool.ambiguous++;
				}
			}
		}else{
			ReportTool.noSolutionFound++;
		}

		return safeSet;
	}
}
