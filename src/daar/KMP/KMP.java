package daar.KMP;

import daar.Util.Util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class KMP {
    	
	public static int getIndexOfWord(String word, String line) {
                line+=" ";
		return matchingAlgo(word.toCharArray(), retenue(word.toCharArray()), line.toCharArray());
	}
        
        public static List<Integer> getAllIndexOfWordInLine(String word, String line){
        	line+=" ";
            List<Integer> listOfIndex = new ArrayList<>();
            int index = getIndexOfWord(word, line);
            while(index!=-1){
                if(listOfIndex.size()>0){
                    int lastIndex = index + listOfIndex.get(listOfIndex.size()-1);
                    listOfIndex.add(lastIndex);
                    index = getIndexOfWord(word, line.substring(lastIndex+word.length()));
                }
                else {
                    listOfIndex.add(index);
                    index = getIndexOfWord(word, line.substring(index+word.length()));
                }
                
            }
            return listOfIndex;
        }
        
        public static void displayAllLinesContainsWordInFile(String word, String file) throws FileNotFoundException, IOException{
            BufferedReader br = Util.readFile(file);
            String line ;
            //List<String> points = new ArrayList<>();
            while( (line = br.readLine()) != null) { 
                List<Integer> AllIndexInLine = getAllIndexOfWordInLine(word, line);
                if(!AllIndexInLine.isEmpty()){
                    System.out.println(line);
                    /*for(int i : AllIndexInLine)
                        points.add(new Point(i, l).toString());*/
                }
            }
            //return points;
        }
	
	public static int matchingAlgo(char[] facteur, int [] retenue, char [] texte) {
		int i=0,j=0;
		while(i<texte.length) {
			if(j==facteur.length) return i-facteur.length + 1;
			if(texte[i] == facteur[j]) {
				i++;j++;
			}
			else {
				if(retenue[j] == -1) {
					j=0;i++;
				}
				else {
					j=retenue[j];
				}
			}
		}
		return -1;
	}
	
	public static int[] retenue(char [] facteur) {
            int[] ret = new int[facteur.length+1];
            for(int i=0;i<facteur.length;i++) { //parcours chaque facteur
                if(i < 2) {
                    if(i == 0) ret[i] = -1;  //cas première retenue
                    else {
                            if(facteur[i] != facteur[i-1] )ret[i] = 0; //deuxième retenue
                            else  ret[i] =-1;
                    }
                }
                else {
                    int max_pre = (int) (Math.ceil(i/2.0));
                    int mod2 = i%2;
                    int size_pre = 0;
                    for(int j=0; j<=max_pre-mod2;j++) {//decalage pour comparaison prefixe/suffixe
                        boolean ok = true;
                        for(int k=0;k<=max_pre-mod2-j;k++) { //comparaison lettre a lettre
                            if(facteur[0+k] != facteur[max_pre-mod2+k+j]) { //si 
                                    ok = false;
                                    break;
                            }
                        }
                        if(ok) { //si pre=suf trouvé
                            size_pre = max_pre -j;
                            if(facteur[i] != facteur[size_pre] ) ret[i] = size_pre; //si radical différent
                            else {
                                    if(facteur[i] != facteur[0]) {
                                            ret[i] = 0;
                                    }
                                    else {
                                            ret[i] = -1;
                                    }
                            }
                            break;
                        }
                        else {
                            if(j==max_pre-1) { //pas de pre=suf>0 trouvé
                                    if(facteur[i] == facteur[0]) {
                                            ret[i] =-1;
                                    }
                                    else {
                                            ret[i] = 0;
                                    }
                            }
                        }
                    }
                }
            }
            ret[facteur.length] = 0;
            return ret;
	}
	

}
