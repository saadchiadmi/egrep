/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daar.Util;

/**
 *
 * @author sa3d
 */
public class Point {
    
    int indice; 
    int ligne;

    public Point(int indice, int ligne) {
        this.indice = indice;
        this.ligne = ligne;
    }

    public int getIndice() {
        return indice;
    }

    public int getLigne() {
        return ligne;
    }

    @Override
    public String toString() {
        return "("+indice+","+ligne+")";
    }
    
    
    
    
    
    
}
