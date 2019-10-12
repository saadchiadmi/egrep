package daar.Index;

import daar.Util.Point;
import java.util.ArrayList;
import java.util.List;


public class Coords implements Comparable<Coords>{
	
	
    int occu;
    public List<Point> points = new ArrayList<>();
    public Coords(int l) {
            occu = l;
    }

    public int getOccu() {
        return occu;
    }

    public void setOccu(int occu) {
        this.occu = occu;
    }

    @Override
    public String toString() {
        String result = "";
        for (Point point : points) {
            result+="("+point.getIndice()+","+point.getLigne()+") ";
        }
        return result;
    }

    @Override
    public int compareTo(Coords o) {
        return this.occu - o.occu;
    }
    
    
    
        

}
