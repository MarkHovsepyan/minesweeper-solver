package minesweeper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import jpl.Atom;
import jpl.Query;
import jpl.Term;


public class PrologWrapper {

	public static boolean loadFile(String fileName){

		Query q1 = 
				new Query( 
						"consult", 
						new Term[] {new Atom(fileName)} 
						);
		return q1.query();
	}
	
	public boolean reload(){
		return loadFile(this.fileName);
	}


	private String fileName;

	public PrologWrapper(String fileName) {
		this.fileName = fileName;
		reload();
	}
	
	public boolean assertNewFact(String predicateString){
		List<String>  lines = new ArrayList<String>();
		lines.add(predicateString);
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			while(line != null){
				lines.add(line);
				line = br.readLine();
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			File fout = new File(fileName);
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for(String line : lines){
				bw.write(line);
				bw.newLine();
			}
			bw.close();
		}catch(Exception e){
			
		}
		return reload();
	}
	
	Hashtable[] getAllSolutions(Term goal){
		Query objQuery = new Query(goal);
		return objQuery.allSolutions();
	}
	
	

}
