/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daar.WarMachine;

import java.util.ArrayList;

/**
 *
 * @author sa3d
 */
public class RegExTree {
    protected int root;
    protected ArrayList<RegExTree> subTrees;
    
    public RegExTree(int root, ArrayList<RegExTree> subTrees) {
      this.root = root;
      this.subTrees = subTrees;
    }
    
    //FROM TREE TO PARENTHESIS
    public String toString() {
      if (subTrees.isEmpty()) return rootToString();
      String result = rootToString()+"("+subTrees.get(0).toString();
      for (int i=1;i<subTrees.size();i++) result+=","+subTrees.get(i).toString();
      return result+")";
    }
    
    private String rootToString() {
      if (root==RegEx.CONCAT) return ".";
      if (root==RegEx.ETOILE) return "*";
      if (root==RegEx.ALTERN) return "|";
      if (root==RegEx.DOT) return ".";
      return Character.toString((char)root);
    }
}
