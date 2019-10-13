/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daar.Util;

import daar.Index.Coords;
import daar.KMP.KMP;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

/**
 *
 * @author sa3d
 */
public class Util {
    
    public static String[] removeElement(String[] table, String element){
        return Arrays.stream(table).filter(e -> !e.equals(element)).toArray(String[]::new);
    }
    
    public static void writeFile(Map<String, Coords> occur, String file) throws FileNotFoundException, UnsupportedEncodingException{
        PrintWriter writer = new PrintWriter(getFileName(file)+".index", "UTF-8");
        for(String s : occur.keySet()) {
                if(occur.get(s).getOccu() < 1000 && s.length() > 2) 
                    writer.println(s+" "+occur.get(s).toString());			
        }	
        writer.close();
    }
    
    public static String getFileName(String file){
        return file.substring(0, file.lastIndexOf("."));
    }
    
    public static BufferedReader readFile(String file) throws FileNotFoundException{
        InputStream ips  =  new FileInputStream(file);
        InputStreamReader ir = new InputStreamReader(ips);
        return new BufferedReader(ir);
    }
    
    public static String[] toWords(String line){
        String [] words = line.split("[^A-Za-z\\'\\-]");
        words = removeElement(words, "");
        return words;
    }
    
    public static int StringtoInt(String input){
        return Integer.parseInt(input);
    }
    
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map){
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        Map<K, V> result = new LinkedHashMap<>();
        for(Entry<K,V> entry: list){
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    public static boolean isRegex (String input){
        try {
          Pattern.compile(input);
          return !(!input.contains("+") && !input.contains("*") && !input.contains(".") && !input.contains("|"));
        } catch (PatternSyntaxException e) {
          return false;
        }
    }
    
    public static boolean isWord (String input){
        return toWords(input)[0].equals(input);
    }
    
    public static void displayAllLinesContaintWordInIndexFile(String file, String motif) throws FileNotFoundException, IOException{
        BufferedReader br = readFile(getFileName(file)+".index");
        List<String> points = new ArrayList<>();
        String line;
        while( (line = br.readLine()) != null) {
            String[] splitOfLine = line.split(" ");
            if(KMP.getIndexOfWord(motif, splitOfLine[0])!=-1) {
                for (int i = 1; i < splitOfLine.length; i++) {
                    points.add(splitOfLine[i]);
                }
            }
        }
        DisplayLines(PointToLine(points), file);
    }
    
    public static List<Integer> PointToLine(List<String> points){
        List<String> lines = new ArrayList<>();
        String[] point= points.toString().split("\\), \\(");
        String[] line;
        for (String p : point) {
            line= p.split(",");
            lines.add(line[1]);
        }
        lines.set(lines.size()-1, lines.get(lines.size()-1).substring(0, lines.get(lines.size()-1).length()-2));
        return lines.stream().map(l -> StringtoInt(l)).sorted().collect(Collectors.toList());
    }
    
    public static void DisplayLines(List<Integer> lines, String file) throws FileNotFoundException, IOException{
        BufferedReader br = Util.readFile(file);
        String line ; int l = 0;
        while( (line = br.readLine()) != null) { l++;
            if(lines.contains(l))   System.out.println(line);
        }
    }
    
    public static List<Point> listIntToListPoint(List<Integer> list, int line) {
    	return list.stream().map(i -> new Point(i, line)).collect(Collectors.toList());
    }
    
}
