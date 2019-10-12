/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daar.KMP;

/**
 *
 * @author sa3d
 */
public class TME2 {
    
    char[] fact;
    public TME2(String fact) {
        this.fact = fact.toCharArray();
    }
    
    public int[] getRetenue(){
       int[] retenue= new int[this.fact.length];
       int i=2, length =0;
       retenue[0]=-1; retenue[1]=0;
       while(i<fact.length){
           if(fact[i]==fact[0]){
               retenue[i]=-1;
               length++;i++;
           }else if(fact[i-1]==fact[length]){
               length++;
               retenue[i] = length;
               if(fact[length]==fact[i]){
                   retenue[i] = 0;
               }
               i++;
           }else if(length !=0){
               length=retenue[length];
           }else{
               retenue[i]=length;
               i++;
           }
       }
       return retenue;
    }
    
    
}
