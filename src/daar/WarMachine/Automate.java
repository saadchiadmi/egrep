/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daar.WarMachine;

/**
 *
 * @author sa3d
 */
public class Automate {
    
    protected int[][] transitions;
    protected boolean[] init;
    protected boolean[] fin;
    protected boolean[][] epsilon;

    public Automate(int nbState) {
            transitions = new int[nbState][256];
            init = new boolean[nbState];
            fin = new boolean[nbState];
            epsilon = new boolean[nbState][nbState];
            for(int i=0;i<transitions.length;i++) {
                    for(int j=0;j<transitions[i].length;j++) {
                            transitions[i][j] = -1;
                    }
            }

            for(int i=0;i<nbState;i++) {
                    for(int j=0;j<nbState;j++) {
                            epsilon[i][j] = false;
                    }
            }
            for(int i=0;i<init.length;i++) {
                    init[i] =false;
                    fin[i] =false;
            }



    }

    static Automate basicAuto(int r) {
            Automate aut = new Automate(2);
            aut.transitions[0][r] = 1;
            aut.init[0] = true;
            aut.fin[1] = true;		
            return aut;


    }

    static Automate etoileAuto(Automate aut) throws Exception {
            Automate etAut = new Automate(aut.init.length+2);
            int fin = -1;
            for(int i=0;i<aut.fin.length;i++) {
                    if(aut.fin[i]) { fin = i; break; }

            }

            if(fin ==-1) throw new Exception("Erreur etat final");
            etAut.epsilon[0][1] =true;
            etAut.epsilon[fin+1][aut.init.length+1] =true;
            etAut.epsilon[0][aut.init.length+1] =true;
            etAut.epsilon[fin+1][1] =true;



            etAut.fin[aut.init.length+1] = true;
            etAut.init[0] = true;
            for(int i=0;i<aut.epsilon.length;i++) {
                    for(int j=0;j<aut.epsilon[i].length;j++) {
                            if(aut.epsilon[i][j]) etAut.epsilon[i+1][j+1] = true;
                    }
            }
            for(int i=0;i<aut.transitions.length;i++) {
                    for(int j=0;j<aut.transitions[i].length;j++) {
                            if(aut.transitions[i][j] != -1) etAut.transitions[i+1][j] = aut.transitions[i][j]+1;
                    }
            }



            return etAut;
    }

    static Automate concatAuto(Automate a1,Automate a2) throws Exception {
            Automate concat = new Automate(a1.init.length+a2.init.length);
            int tailleAuto1 = a1.init.length;
            for(int i=0;i<a1.init.length;i++) {
                    if(a1.init[i]) concat.init[i] = true;
            }

            int fin = -1;
            for(int i=0;i<a1.fin.length;i++) {
                    if(a1.fin[i]) { fin = i; break; }

            }

            if(fin ==-1) throw new Exception("Erreur etat final concatenation");



            for(int i=0;i<a1.epsilon.length;i++) {
                    for(int j=0;j<a1.epsilon[i].length;j++){
                            concat.epsilon[i][j] = a1.epsilon[i][j];
                    }
            }

            concat.epsilon[fin][fin+1] =true;

            for(int i=0;i<a2.epsilon.length;i++) {
                    for(int j=0;j<a2.epsilon[i].length;j++){
                            if(a2.epsilon[i][j]) {
                                    concat.epsilon[i+tailleAuto1][j+tailleAuto1]=true;
                            }
                    }
            }


            for(int i=0;i<a1.transitions.length;i++) {
                    for(int j=0;j<a1.transitions[i].length;j++){
                            concat.transitions[i][j] = a1.transitions[i][j];
                    }
            }

            for(int i=0;i<a2.transitions.length;i++) {
                    for(int j=0;j<a2.transitions[i].length;j++){
                            if(a2.transitions[i][j] != -1) {
                                    concat.transitions[i+tailleAuto1][j] = a2.transitions[i][j]+tailleAuto1;
                            }
                    }
            }

            concat.init[0] = true;
            concat.fin[concat.fin.length-1] = true;

            return concat;
    }

    static Automate alterAuto(Automate a1, Automate a2) throws Exception {
            int tailleAuto1 = a1.init.length;
            int tailleAuto2 = a2.init.length;
            Automate alt = new Automate(tailleAuto1+tailleAuto2+2);
            alt.init[0] = true;
            alt.fin[tailleAuto1+1] = true;

            alt.epsilon[0][1] = true;
            int fin =-1;
            for(int i=0;i<a1.fin.length;i++) {
                    if(a1.fin[i]) fin = i;
            }
            if(fin ==-1) throw new Exception("Erreur etat final alter");
            alt.epsilon[fin+1][tailleAuto1+1]=true;
            alt.epsilon[tailleAuto1+tailleAuto2+1][tailleAuto1+1]=true;


            for(int i=0;i<a1.transitions.length;i++) {
                    for(int j=0;j<a1.transitions[i].length;j++){
                            if(a1.transitions[i][j] != -1) {
                                    alt.transitions[i+1][j] = a1.transitions[i][j]+1;
                            }
                    }
            }

            alt.epsilon[0][tailleAuto1+2] = true;

            for(int i=0;i<a2.transitions.length;i++) {
                    for(int j=0;j<a2.transitions[i].length;j++){
                            if(a2.transitions[i][j] != -1) {
                                    alt.transitions[i+tailleAuto1+2][j] = a2.transitions[i][j]+tailleAuto1+2;
                            }
                    }
            }

            for(int i=0;i<a1.epsilon.length;i++) {
                    for(int j=0;j<a1.epsilon[i].length;j++){
                            if(a1.epsilon[i][j]) {
                                    alt.epsilon[i+1][j+1]=true;
                            }
                    }
            }

            for(int i=0;i<a2.epsilon.length;i++) {
                    for(int j=0;j<a2.epsilon[i].length;j++){
                            if(a2.epsilon[i][j]) {
                                    alt.epsilon[i+tailleAuto1+2][j+tailleAuto1+2]=true;
                            }
                    }
            }

            return alt;
    }

    static public Automate deterAuto(Automate aut) {
            Automate deter = new Automate(aut.init.length);
            boolean [][] reach = new boolean[aut.init.length][aut.init.length];
            for(int i=0;i<aut.init.length;i++) {
                    reach[i][i] = true;
            }

            for(int i=0;i<aut.epsilon.length;i++) {
                    for(int j=0;j<aut.epsilon[i].length;j++) {

                    }
            }

            return deter;
    }

    public String toString() {
            String s="\t";
            for(int i=0;i<256;i++) {
                    s+=i+"\t";
            }
            s+="\n";
            for(int i=0;i<transitions.length;i++) {
                    s+=i+"\t";
                    for(int j=0;j<transitions[i].length;j++) {
                            if(transitions[i][j] !=-1) s+= transitions[i][j];
                            else s+="_";
                            s+="\t";
                    }
                    s+="\n";
            }
            s+="_______________________\n\n";
            s+="transitions epsilon :\n\t";
            for(int i=0;i<epsilon.length;i++) {
                    s+=i+"\t";
            }
            s+="\n";
            for(int i=0;i<epsilon.length;i++) {
                    s+=i+"\t";
                    for(int j=0;j<epsilon[i].length;j++) {
                            if(epsilon[i][j]) s+= "O";
                            else s+="_";
                            s+="\t";
                    }
                    s+="\n";
            }
            s+="\nétats initiaux [ ";
            for(int i=0;i<init.length;i++) {
                    if(init[i]) s+=i+" ";
            }
            s+="]\n";
            s+="\nétats finaux [ ";
            for(int i=0;i<fin.length;i++) {
                    if(fin[i]) s+=i+" ";
            }
            s+="]\n";
            return s;
    }
    
}
