package daar.WarMachine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import daar.Util.Util;

class Automate{
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
	
	static Automate dotAuto() throws Exception {
		Automate dotAut = new Automate(2);
		for(int i=0; i<256;i++) {
			dotAut.transitions[0][i] = 1;
			dotAut.init[0] = true;
			dotAut.fin[1] = true;	
		}
		return dotAut;
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
		Automate deter; //nouvel automate déterminisé
		boolean [][] reach = new boolean[aut.init.length][aut.init.length]; //tableau d'accessibilité
		for(int i=0;i<aut.init.length;i++) {
			reach[i][i] = true;
		}
		//calcul accessibilité
		List<Integer> toSee = new ArrayList<Integer>();
		for(int i=0;i<aut.epsilon.length;i++) {
			toSee.clear();
			for(int j=0;j<aut.epsilon[i].length;j++) {
				if(aut.epsilon[i][j]) {
					reach[i][j] = true;
					toSee.add(j);
				}
			}
			while(!toSee.isEmpty()) {
				int tmp = toSee.remove(0);
				for(int k=0;k<aut.epsilon[tmp].length;k++) {
					if(aut.epsilon[tmp][k]) {
						toSee.add(k);
						reach[i][k]=true;
						
					}
				}
			}
		}
		//calcul états importants
		toSee.clear();
		//états initiaux
		for(int i=0;i<aut.init.length;i++) {
			if(aut.init[i]) toSee.add(i);
		}
		//calcul états entrés par des arcs
		for(int i=0;i<aut.transitions.length;i++) {
			for(int j=0;j<aut.transitions[i].length;j++) {
				int tmp2 = aut.transitions[i][j];
				if(tmp2!=-1) {
					if(!toSee.contains(tmp2)) toSee.add(tmp2);
				}
			}
		}
		
		deter = new Automate(toSee.size());
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>(); //association ancien à un nouvel état
		for(int i=0;i<toSee.size();i++) {
			map.put(toSee.get(i), i);
		}
		
		//calcul nouvel automate
		for(Integer i : toSee) { //parcours états importants
			int v1 = map.get(i); //nouvel état de i
			if(aut.init[i]) {deter.init[v1] = true; }//si état initial
			for(int j=0; j<reach[i].length;j++) { 
				if(reach[i][j]) { //si j état atteignable
					if(aut.init[j] && !deter.init[v1]) {deter.init[v1] = true;}
					if(aut.fin[j] && !deter.fin[v1]) deter.fin[v1] = true;
					
					for(int k=0;k<aut.transitions[j].length;k++) {//parcours transitions de i
						int stateArc = aut.transitions[j][k]; 
						if(stateArc != -1) deter.transitions[v1][k] =map.get(stateArc); //etat entré par la transition k
					}
				}
				
				
			}
		}
		
		/*
		
		//affichage accessiblité
		for(int i=0;i<reach.length;i++) {
			for(int j=0;j<reach[i].length;j++) {
				System.out.print(reach[i][j]+"\t");
			}
			System.out.println();
		}
		
		//affichage états importants
		for(Integer i : toSee) {
			System.out.print(i+"\t");
		}
		System.out.println();*/
		return deter;
	}
	
	static Automate minAuto(Automate aut) {
		Automate min;
		
		//partition initiale
		
		int [] partition = new int[aut.init.length];
		
		for(int i=0;i<aut.fin.length;i++) {
			if(aut.fin[i]) {
				partition[i] = 0;
			}
			else {
				partition[i] = 1;
			}
		}
		
		//symboles
		List<Integer> s = new ArrayList<Integer>();
		for(int i=0;i<256;i++) {
			for(int j=0;j<partition.length;j++) {
				if(aut.transitions[j][i] != -1 ) {
					s.add(i);
					break;
				}
			}
		}
		int nb_p = 0;
		boolean has_changed = true;
		while(has_changed) {
			int[] part_tmp = new int[partition.length];
			Map<Integer,List<Integer>> map_partition = new HashMap<Integer, List<Integer>>();
			nb_p = 0;
			for(int i=0;i<partition.length;i++) {
				List<Integer> bilan = new ArrayList<Integer>();
				for(Integer symb : s) {
					int p = aut.transitions[i][symb];
					if(p != -1) {
						bilan.add(partition[p]);
					}
					else {
						bilan.add(-1);
					}
				}
				map_partition.put(i, bilan);
				boolean eq=false;
				for(int j=0;j<i;j++) {
					if(map_partition.get(i).equals(map_partition.get(j))) {part_tmp[i] = part_tmp[j];eq=true; break;}
					
				}
				if(!eq) part_tmp[i] = nb_p++;
				
				
				
			}
			has_changed = false;
			for(int i=0;i<part_tmp.length;i++) {
				if(part_tmp[i] != partition[i]) { has_changed =true; break;}
			}
			partition = part_tmp;
		}
		
		min = new Automate(nb_p);
	
			for(int i=0;i<aut.init.length;i++) {
				for(Integer symbole: s) {
					if(aut.transitions[i][symbole] != -1) {
						min.transitions[partition[i]][symbole] = partition[aut.transitions[i][symbole]];
					}
				}
				if(aut.init[i]) min.init[partition[i]] =true;
				if(aut.fin[i]) min.fin[partition[i]] =true;
			}
		
		
		
		
		return min;
	}
	
	public String searchPattern(String line) {	
		int state_init=0;
		int state;
		List<Integer> finalStates = new ArrayList<Integer>();
		for(int i=0; i<init.length;i++) {
			if(init[i]) {state_init = i;break;}
		}
		for(int i=0; i<fin.length;i++) {
			if(fin[i]){
				finalStates.add(i);
			}
		}
		state=state_init;
		for(int i=0;i<line.length();i++) {
			if(state == -1) {
				state = transition(state_init,line.charAt(i));
			}
			else {
				state = transition(state,line.charAt(i));
			}
			if(finalStates.contains(state)) return line;
			
		}
		
		
		return "";
		
	}
	
	public void searchInFile(String file) throws IOException {
		BufferedReader br = Util.readFile(file);
		String res;
		String line;
		while(! ((line = br.readLine()) == null)) {
            res = searchPattern(line);
            if(res != "") {
            	System.out.println(res);
            }
        }
	}
	
	
	
	private int transition(int state, char c) {
		if(((int)c)>= 256)return -1;
		return transitions[state][(int) c];
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

