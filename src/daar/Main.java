/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daar;

import daar.Util.Util;
import daar.WarMachine.RegEx;
import daar.Index.Indexing;
import daar.KMP.KMP;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author sa3d
 */
public class Main {

	/**
	 * @param args the command line arguments
	 * @throws java.io.FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String motif;
		String file;
		long start=0;
		if (args.length ==2) {
		      motif = args[0];
		      file = args[1];
		      
		    } else {
			      Scanner scanner = new Scanner(System.in);
			      System.out.print("  >> Please enter a regEx: ");
			      motif = scanner.next();
			      System.out.print("  >> Please enter a filename: ");
			      file = scanner.next();
		      
		    }
		    if (motif.length()<1) {
		    	System.err.println("  >> ERROR: empty regEx.");
		      
		    } else {
	    		start = System.currentTimeMillis();
			      if(Util.isRegex(motif)) {
			    	  new RegEx(motif, file);
			      }else if(!Util.isWord(motif)){
			          KMP.displayAllLinesContainsWordInFile(motif, file);
			      }else{
			          if(!new File(Util.getFileName(file)+".index").isFile()){
			              Indexing.forwardIndexing(file);
			          }
			          Util.displayAllLinesContaintWordInIndexFile(file, motif);
			      }
		    }
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.println(elapsedTimeMillis);

	}

}
