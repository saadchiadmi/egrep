package daar.Index;

import daar.Util.Point;
import daar.Util.Util;
import daar.KMP.KMP;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Indexing {
	//mettre mot <=2 et occur > 1000 dans une black list awk sed
	public static void forwardIndexing(String file) throws FileNotFoundException, IOException  {
            BufferedReader br = Util.readFile(file);
            String line ; int l = 0;
            Map<String, Coords> occur = new HashMap<String, Coords>();
            while( (line = br.readLine()) != null) { l++;
                String [] words = Util.toWords(line);
                for(String word: words) {
                    Coords coord;
                    if(occur.containsKey(word)) {
                        coord = occur.get(word);
                        Point point = coord.points.get(coord.points.size()-1);
                        Point newPoint;
                        if(point.getLigne() == l){
                            String subLine= line.substring(point.getIndice()+word.length());
                            newPoint = new Point(KMP.getIndexOfWord(word, subLine)+point.getIndice()+word.length(), l);
                        }else{
                            newPoint = new Point(KMP.getIndexOfWord(word, line), l);
                        }
                        coord.points.add(newPoint);
                        coord.setOccu(coord.getOccu()+1);
                        occur.put(word, coord);
                    }
                    else {
                        coord = new Coords(1);
                        Point point = new Point(KMP.getIndexOfWord(word, line), l);
                        coord.points.add(point);
                        occur.put(word,coord);
                    }
                }
            }
            occur = Util.sortByValue(occur);
            Util.writeFile(occur, file);
	}
        
	
	public static void main(String [] args) throws IOException {
		forwardIndexing("49345-0.txt");
	}
	
	
	
	

}
