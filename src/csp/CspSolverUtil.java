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

import minesweeper.MinesweepingAgent;
import minesweeper.ProblemManager;
import minesweeper.ReportUtil;
import bean.Square;


public class CspSolverUtil {

	private Store objStore;

	public CspSolverUtil() {
		this.objStore = new Store();
	}


	public Set<Square> solve(Map<Square, Set<Square>> input){
		Map<Square, IntVar> varMap = new HashMap<Square, IntVar>();
		for(Square exploredSquare : input.keySet()){
			Set<Square> vars = input.get(exploredSquare);
			IntVar[] arrVar = new IntVar[vars.size()];
			int index = 0;
			for(Square objSquare : vars){
				if(varMap.containsKey(objSquare)){
					arrVar[index++] = varMap.get(objSquare);
				}else{
					if(objSquare.isMarked()){
						arrVar[index] = new IntVar(objStore, objSquare.getLocX() +"," +objSquare.getLocY(), 1 ,1);
					}else{
						arrVar[index] = new IntVar(objStore, objSquare.getLocX() +"," +objSquare.getLocY(), 0 ,1);
					}
					varMap.put(objSquare, arrVar[index++]);
				}
			}

			int val = exploredSquare.getNoOfMinesAround();
			Constraint objConstraint = new Sum(arrVar, new IntVar(objStore, val, val));
			objStore.impose(objConstraint);
		}

		IntVar[] vars = new IntVar[varMap.values().size()];

		Map<Integer, Square> squareMap = new HashMap<Integer, Square>();
		Map<Square, Set<Integer>> solutionMap = new HashMap<Square, Set<Integer>>();
		int index = 0;
		for(Square objSquare : varMap.keySet()){
			vars[index] = varMap.get(objSquare);
			squareMap.put(index++, objSquare);
			solutionMap.put(objSquare, new HashSet<Integer>());
		}

		Search<IntVar> label = new DepthFirstSearch<IntVar>();
		SelectChoicePoint<IntVar> select = new InputOrderSelect<IntVar>(objStore, vars, new IndomainMin<IntVar>() );
		label.getSolutionListener().searchAll(true);
		label.getSolutionListener().recordSolutions(true);
		boolean result = label.labeling(objStore, select);
		Set<Square> safeSet = new HashSet<Square>();
		if(result){
			for (int i=1; i<=label.getSolutionListener().solutionsNo(); i++){
				for (int j=0; j<label.getSolution(i).length; j++){
					Square objSquare = squareMap.get(j);
					solutionMap.get(objSquare).add(Integer.parseInt(label.getSolution(i)[j].toString()));
				}
			}

			//Getting the ones with single solution
			for(Square objSquare : solutionMap.keySet()){
				int count = solutionMap.get(objSquare).size();
				if(count == 1){
					int sol = (Integer)solutionMap.get(objSquare).toArray()[0];
					if(sol== 0){
						safeSet.add(objSquare);
						ReportUtil.solutionFound++;
					}else{
						objSquare.setMarked(true);
						objSquare.setEnabled(false);
						ProblemManager.noOfMines--;
						MinesweepingAgent.noOfMines--;
						ReportUtil.solutionFound++;
					}
					
				}else{
					ReportUtil.ambiguous++;
				}
			}
		}else{
			ReportUtil.noSolutionFound++;
		}
		return safeSet;

	}

}
