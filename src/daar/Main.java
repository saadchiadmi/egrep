/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daar;

import daar.Util.Util;
import daar.Index.Indexing;
import daar.KMP.KMP;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		long start = System.currentTimeMillis();
		String motif = "word";
		String file = "files/oneHundredMegaOctet.txt";

		if (Util.isRegex(motif)) {
			System.out.println("TME 1");

		} else if (!Util.isWord(motif)) {
			KMP.displayAllLinesContainsWordInFile(motif, file);

		} else {
			if (!new File(Util.getFileName(file) + ".index").isFile()) {
				Indexing.forwardIndexing(file);
			}
			Util.displayAllLinesContaintWordInIndexFile(file, motif);
		}
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.println(elapsedTimeMillis);

	}

}
