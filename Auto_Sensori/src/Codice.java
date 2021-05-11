import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Codice {
	public static ArrayList<Auto> creAuto(){
		ArrayList<Auto>Incrocio=new ArrayList<Auto>();
		String posOccupata="";
		String nomOccupato="";
		String valOccupato="";
		String tempOccupato="";
for(int x=0; x<2;x++) {	
 String lettere="A,B,C,D,E,F,G,H,I,L,M,N,O,P,Q,R,S,T,U,V,Z,X,Y,K,J";
 String nome="";
 String posizione="";
 String direzione="";
 String priorità="";
 String tempo_arrivo="";
 boolean strada_alternativa=(Math.random()<0.5);
 
 int f=0;{
	 while(f<1) {
 String valore=""+(int)Math.floor(Math.random()*100);
 if(!valOccupato.contains(valore)) {priorità=valore;f++;}
 }
 }
 
 

 int i=0;{ 
 
     while(i<1){
	 int valore=(int)Math.floor(Math.random()*100);
	 if(valore<25&&!nomOccupato.contains(valore+"")) {
		 i++;
	 String[]s=lettere.split(",");
	 nome=s[valore];
	 nomOccupato+= valore+" ";
	 }	
}
}
 int j=0;{
 
     while(j<1) {
	 int valore=(int)Math.floor(Math.random()*10);
	 if(valore==0&&!posOccupata.contains("sini")) {posizione="sinistra";j++;}
	 if(valore==1&&!posOccupata.contains("bass")) {posizione="basso";j++;}
	 if(valore==2&&!posOccupata.contains("dest")) {posizione="destra";j++;}
	 if(valore==3&&!posOccupata.contains("alto")) {posizione="alto";j++;}
 }
 }
 int k=0;{
	 while(k<1) {
	 int valore=(int)Math.floor(Math.random()*10);
	 if(valore==0) {direzione="sinistra";k++;}
	 if(valore==1) {direzione="diritto";k++;}
	 if(valore==2) {direzione="destra";k++;}
 }
 }
    int int_priorità=Integer.parseInt(priorità);
    Auto A=new Auto(nome,posizione,tempo_arrivo,direzione,int_priorità,strada_alternativa);
    Incrocio.add(A);
    posOccupata+=posizione+" ";
}

    return(Incrocio);
    }
	
	public static ArrayList<Sensore> creaSensori(){
		ArrayList<Sensore>Sens=new ArrayList<Sensore>();
		String posOccupata="";
		
		for(int x=0; x<4;x++) {	
			String posizione="";
			boolean funzionante=(Math.random()>0.02);
			int j=0;{
				 
			     while(j<1) {
				 int valore=(int)Math.floor(Math.random()*10);
				 if(valore==0&&!posOccupata.contains("sini")) {posizione="sinistra";j++;}
				 if(valore==1&&!posOccupata.contains("bass")) {posizione="basso";j++;}
				 if(valore==2&&!posOccupata.contains("dest")) {posizione="destra";j++;}
				 if(valore==3&&!posOccupata.contains("alto")) {posizione="alto";j++;}
			 }
			 }
			Sensore S=new Sensore(posizione,funzionante);
		    Sens.add(S);
		    posOccupata+=posizione+" ";
		}
		return(Sens);
	}
	
public void Caricadati(String file,ArrayList<Auto>Incrocio,ArrayList<Sensore>Sens) {
		
		try {
			PrintWriter out= new PrintWriter(new FileWriter(file));
			int k=0;
			boolean giàScrittoF=false;
			boolean giàScrittoT=false;
			HashMap<String,Boolean>Rilevazione=new HashMap<String,Boolean>();
			boolean rilevate=true;
			boolean Incidente=true;
			ArrayList<String> incidenteOno=new ArrayList<String>();
			ArrayList<String> chiedeOnoParz=new ArrayList<String>();
			ArrayList<String> chiedeOno=new ArrayList<String>();
			
			ArrayList<String> regole=new ArrayList<String>();
			
				for(int i=0; i<Incrocio.size();i++) {
					out.println("=> macchina_"+Incrocio.get(i).nome);
					for(int w=0; w<Sens.size();w++) {
						if(Sens.get(w).posizione==Incrocio.get(i).posizione) {
						out.println("=> sensore_"+Sens.get(w).posizione+"_"+Sens.get(w).funzionante);
					      if(Sens.get(w).funzionante) {out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",sensore_"+Sens.get(w).posizione+"_"+Sens.get(w).funzionante+" => macchina_"+Incrocio.get(i).nome+"_rilevata");k++;  
					    	  Rilevazione.put(Incrocio.get(i).nome, true);
					      if(Incrocio.get(i).priorità>60) {
									if(!giàScrittoF)out.println("=> DiFretta_");
									out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,DiFretta => InoltraSegnaleUrgenza_"+Incrocio.get(i).nome);
									chiedeOnoParz.add("d"+k+" "+Incrocio.get(i).nome);k++;
									giàScrittoF=true;}
								if(Incrocio.get(i).priorità<60) {
									if(!giàScrittoT)out.println("=> Tranquilla");
									out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,Tranquilla => !InoltraSegnaleUrgenza_"+Incrocio.get(i).nome);k++;
									giàScrittoT=true;}    
					      } 
					    else {out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",sensore_"+Sens.get(w).posizione+"_"+Sens.get(w).funzionante+" => macchina_"+Incrocio.get(i).nome+"_Nonrilevata");
					    Rilevazione.put(Incrocio.get(i).nome, false);;k++;
					    }
					}	
			
			}
					for(int j=0; j<Incrocio.size();j++) {
						if(i!=j) {	
						if(Incrocio.get(i).posizione=="destra"&&Incrocio.get(j).posizione=="sinistra"&&Incrocio.get(i).direzione=="dritto"&&Incrocio.get(j).direzione=="dritto"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="alto"&&Incrocio.get(j).posizione=="basso"&&Incrocio.get(i).direzione=="dritto"&&Incrocio.get(j).direzione=="dritto"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="destra"&&Incrocio.get(j).posizione=="alto"&&Incrocio.get(i).direzione=="destra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="alto"&&Incrocio.get(j).posizione=="sinistra"&&Incrocio.get(i).direzione=="destra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="sinistra"&&Incrocio.get(j).posizione=="basso"&&Incrocio.get(i).direzione=="destra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="basso"&&Incrocio.get(j).posizione=="destra"&&Incrocio.get(i).direzione=="destra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="basso"&&Incrocio.get(j).posizione=="alto"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione!="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="alto"&&Incrocio.get(j).posizione=="basso"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione!="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="sinistra"&&Incrocio.get(j).posizione=="destra"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione!="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="destra"&&Incrocio.get(j).posizione=="sinistra"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione!="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="basso"&&Incrocio.get(j).posizione=="sinistra"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione=="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="destra"&&Incrocio.get(j).posizione=="basso"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione=="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="alto"&&Incrocio.get(j).posizione=="destra"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione=="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						if(Incrocio.get(i).posizione=="sinistra"&&Incrocio.get(j).posizione=="alto"&&Incrocio.get(i).direzione=="destra"&&Incrocio.get(j).direzione=="sinistra"&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0")) {
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+",macchina_"+Incrocio.get(j).nome+" => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"0");
							regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"0");
							k++;
							Incidente=false;
							out.println("d"+k+": NonSiOstacolano"+" => !Incidente");k++;	
						}
						}
					}
				}
				
			if(Incidente) {
				for(String a:Rilevazione.keySet()) {
					for(String b:Rilevazione.keySet()) {
						if(a!=b&&Rilevazione.get(a)==false&&Rilevazione.get(b)!=false) {out.println("d"+k+": macchina_"+a+",macchina_"+b+" => !NonSiOstacolano");rilevate=false;k++;out.println("d"+k+": !NonSiOstacolano,macchina_"+a+"_Nonrilevata => Incidente");k++;}
						if(a!=b&&Rilevazione.get(a)==false&&Rilevazione.get(b)==false&&!regole.contains(a+","+b+"X")) {out.println("d"+k+": macchina_"+a+",macchina_"+b+" => !NonSiOstacolano");k++;out.println("d"+k+": !NonSiOstacolano,macchina_"+a+"_Nonrilevata,macchina_"+b+"_Nonrilevata => Incidente");k++;
						regole.add(b+","+a+"X");rilevate=false;
						}
					}
				}
				if(rilevate) {
					for(int i=0; i<Incrocio.size();i++) {
						for(int j=0; j<Incrocio.size();j++) {
							if(i!=j&&(Integer.parseInt(Incrocio.get(i).tempo_arrivo)-Integer.parseInt(Incrocio.get(j).tempo_arrivo)<15)&&(Integer.parseInt(Incrocio.get(i).tempo_arrivo)-Integer.parseInt(Incrocio.get(j).tempo_arrivo)>-15)) {	
								 if(Incrocio.get(i).priorità>60&&Incrocio.get(j).priorità<60) {
									    out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");k++;
										out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");
								    	incidenteOno.add("d"+k);
										k++;
										
								    	out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,"+"InoltraSegnaleUrgenza_"+Incrocio.get(i).nome+" => PassaPerPrima_"+Incrocio.get(i).nome);k++;
								    	out.println("d"+k+": macchina_"+Incrocio.get(j).nome+"_rilevata,"+"!InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+" => PassaPerSeconda_"+Incrocio.get(j).nome);k++;
								    	out.println("d"+k+": PassaPerSeconda_"+Incrocio.get(j).nome+",PassaPerPrima_"+Incrocio.get(i).nome+" => !ApparenteIncidente");
								    	incidenteOno.add("d"+k);
								    	k++;
								        out.println();
								    	out.println(incidenteOno.get(0)+"<"+incidenteOno.get(1));
										 }
								 if(Incrocio.get(j).priorità<60&&Incrocio.get(i).priorità<60) {
									   if(Incrocio.get(i).priorità>Incrocio.get(j).priorità) {
									   out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");k++;
									   out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");
									   incidenteOno.add("d"+k);
									   k++;
										   
										out.println("=> MoltoPropensoAspettare_"+Incrocio.get(j).nome);
								    	out.println("d"+k+": !InoltraSegnaleUrgenza_"+Incrocio.get(i).nome+",!InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+",MoltoPropensoAspettare_"+Incrocio.get(j).nome+" => PassaPerPrima_"+Incrocio.get(i).nome);k++;
								    	out.println("d"+k+": PassaPerPrima_"+Incrocio.get(i).nome+",!InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+" => PassaPerSeconda_"+Incrocio.get(j).nome);k++;
								    	out.println("d"+k+": PassaPerSeconda_"+Incrocio.get(j).nome+",PassaPerPrima_"+Incrocio.get(i).nome+" => !ApparenteIncidente");
								    	incidenteOno.add("d"+k);
								    	k++;
								        out.println();
								    	out.println(incidenteOno.get(0)+"<"+incidenteOno.get(1));
										 }
								 }
								 if(Incrocio.get(j).priorità>60&&Incrocio.get(i).priorità>60) {
									 if(Incrocio.get(i).strada_alternativa==true&&Incrocio.get(j).strada_alternativa!=true) {
										 out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");k++;
										 out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");
										 incidenteOno.add("d"+k);
										 k++;
										 
										 out.println("=>StradaAlternativa_"+Incrocio.get(i).nome);
										 out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,StradaAlternativa_"+Incrocio.get(i).nome+" => !InoltraSegnaleUrgenza_"+Incrocio.get(i).nome);
										 if(chiedeOnoParz.get(0).contains(Incrocio.get(i).nome))chiedeOno.add(""+chiedeOnoParz.get(0).replace(" "+Incrocio.get(i).nome, ""));
										 if(chiedeOnoParz.get(1).contains(Incrocio.get(i).nome))chiedeOno.add(""+chiedeOnoParz.get(1).replace(" "+Incrocio.get(i).nome, ""));
										 chiedeOno.add("d"+k); 
										 k++;
										 out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,"+"!InoltraSegnaleUrgenza_"+Incrocio.get(i).nome+" => PassaPerSeconda_"+Incrocio.get(i).nome);k++;
										 out.println("d"+k+": macchina_"+Incrocio.get(j).nome+"_rilevata,InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+" => PassaPerPrima_"+Incrocio.get(j).nome);k++;
										 out.println("d"+k+": PassaPerSeconda_"+Incrocio.get(i).nome+",PassaPerPrima_"+Incrocio.get(j).nome+" => !ApparenteIncidente");
									     out.println();
								    	 incidenteOno.add("d"+k);
										 out.println(chiedeOno.get(0)+"<"+chiedeOno.get(1)+"<"+incidenteOno.get(0)+"<"+incidenteOno.get(1));
										 k++;
		                              }
									 if(Incrocio.get(j).strada_alternativa==true&&Incrocio.get(i).strada_alternativa==true&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"X")) {
										 if((Math.random()<0.5)) {
											  out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");k++;
											  out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");
										      incidenteOno.add("d"+k);
											  k++;
											 
											  out.println("=>StradaAlternativa_"+Incrocio.get(i).nome);
											  out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,StradaAlternativa_"+Incrocio.get(i).nome+" => !InoltraSegnaleUrgenza_"+Incrocio.get(i).nome);
											  if(chiedeOnoParz.get(0).contains(Incrocio.get(i).nome))chiedeOno.add(""+chiedeOnoParz.get(0).replace(" "+Incrocio.get(i).nome, ""));
											  if(chiedeOnoParz.get(1).contains(Incrocio.get(i).nome))chiedeOno.add(""+chiedeOnoParz.get(1).replace(" "+Incrocio.get(i).nome, ""));
											  chiedeOno.add("d"+k); 
											  k++;
											  out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,"+"!InoltraSegnaleUrgenza_"+Incrocio.get(i).nome+" => PassaPerSeconda_"+Incrocio.get(i).nome);k++;
											  out.println("d"+k+": macchina_"+Incrocio.get(j).nome+"_rilevata,InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+" => PassaPerPrima_"+Incrocio.get(j).nome);k++;
											  out.println("d"+k+": PassaPerSeconda_"+Incrocio.get(i).nome+",PassaPerPrima_"+Incrocio.get(j).nome+" => !ApparenteIncidente");
											  out.println();
											  incidenteOno.add("d"+k);
											  out.println(chiedeOno.get(0)+"<"+chiedeOno.get(1)+"<"+incidenteOno.get(0)+"<"+incidenteOno.get(1));
											  k++;
											  regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"X");
											  }
											  else {
											  out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");k++;
										      out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");
										      incidenteOno.add("d"+k);
											  k++;
												  
											  out.println("=>StradaAlternativa_"+Incrocio.get(j).nome);
											  out.println("d"+k+": macchina_"+Incrocio.get(j).nome+"_rilevata,StradaAlternativa_"+Incrocio.get(j).nome+" => !InoltraSegnaleUrgenza_"+Incrocio.get(j).nome);
											  if(chiedeOnoParz.get(0).contains(Incrocio.get(j).nome))chiedeOno.add(""+chiedeOnoParz.get(0).replace(" "+Incrocio.get(j).nome, ""));
											  if(chiedeOnoParz.get(1).contains(Incrocio.get(j).nome))chiedeOno.add(""+chiedeOnoParz.get(1).replace(" "+Incrocio.get(j).nome, ""));
											  chiedeOno.add("d"+k); 
											  k++;
											  out.println("d"+k+": macchina_"+Incrocio.get(j).nome+"_rilevata,"+"!InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+" => PassaPerSeconda_"+Incrocio.get(j).nome);k++;
											  out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,InoltraSegnaleUrgenza_"+Incrocio.get(i).nome+" => PassaPerPrima_"+Incrocio.get(i).nome);k++;
											  out.println("d"+k+": PassaPerSeconda_"+Incrocio.get(j).nome+",PassaPerPrima_"+Incrocio.get(i).nome+" => !ApparenteIncidente");
											  out.println();
											  incidenteOno.add("d"+k);
											  out.println(chiedeOno.get(0)+"<"+chiedeOno.get(1)+"<"+incidenteOno.get(0)+"<"+incidenteOno.get(1));
											  k++;
											  regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"X");  
											  } 		 				 
									   }
									 if(Incrocio.get(j).strada_alternativa==false&&Incrocio.get(i).strada_alternativa==false&&!regole.contains(Incrocio.get(i).nome+","+Incrocio.get(j).nome+"X")) {
										 if(Math.random()<0.5) {
											    out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");k++;
											    out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");
											    incidenteOno.add("d"+k);
											    k++;
											 
												out.println("=> Spazientita_"+Incrocio.get(j).nome);
										    	out.println("d"+k+": InoltraSegnaleUrgenza_"+Incrocio.get(i).nome+",InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+",Spazientita_"+Incrocio.get(j).nome+" => PassaPerPrima_"+Incrocio.get(j).nome);k++;
										    	out.println("d"+k+": PassaPerPrima_"+Incrocio.get(j).nome+" => PassaPerSeconda_"+Incrocio.get(i).nome);k++;
										    	out.println("d"+k+": PassaPerSeconda_"+Incrocio.get(i).nome+",PassaPerPrima_"+Incrocio.get(j).nome+" => !ApparenteIncidente");
										    	incidenteOno.add("d"+k);
										    	k++;
										    	out.println();
										    	out.println(incidenteOno.get(0)+"<"+incidenteOno.get(1));
										    	regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"X");  		  
												}
										 else {
											    out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");k++;
											    out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");
											    incidenteOno.add("d"+k);
											    k++;
											 
												out.println("=> Spazientita_"+Incrocio.get(i).nome);
											    out.println("d"+k+": InoltraSegnaleUrgenza_"+Incrocio.get(i).nome+",InoltraSegnaleUrgenza_"+Incrocio.get(j).nome+",Spazientita_"+Incrocio.get(i).nome+" => PassaPerPrima_"+Incrocio.get(i).nome);k++;
											    out.println("d"+k+": PassaPerPrima_"+Incrocio.get(i).nome+" => PassaPerSeconda_"+Incrocio.get(j).nome);k++;	
											    out.println("d"+k+": PassaPerSeconda_"+Incrocio.get(j).nome+",PassaPerPrima_"+Incrocio.get(i).nome+" => !ApparenteIncidente");
											    incidenteOno.add("d"+k);
											    k++;
											    out.println();
											    out.println(incidenteOno.get(0)+"<"+incidenteOno.get(1));
											    regole.add(Incrocio.get(j).nome+","+Incrocio.get(i).nome+"X");  
									    		}				 
									 }			 
								}
								}
						    
						if(i!=j&&(Integer.parseInt(Incrocio.get(i).tempo_arrivo)-Integer.parseInt(Incrocio.get(j).tempo_arrivo)>15)&&(Integer.parseInt(Incrocio.get(i).tempo_arrivo)-Integer.parseInt(Incrocio.get(j).tempo_arrivo)>-15)) {	
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata => !NonSiOstacolano");
							incidenteOno.add("d"+k);k++;
							out.println("d"+k+": !NonSiOstacolano => ApparenteIncidente");k++;
							
						    out.println("=>PiùDi15secDiDifferenza");
							out.println("d"+k+": macchina_"+Incrocio.get(i).nome+"_rilevata,macchina_"+Incrocio.get(j).nome+"_rilevata,PiùDi15secDiDifferenza => NonSiOstacolano");
							incidenteOno.add("d"+k);
							k++;
							out.println("d"+k+": NonSiOstacolano => !ApparenteIncidente");k++;
							out.println();
						    out.println(incidenteOno.get(0)+"<"+incidenteOno.get(1));
						    
						}
							}
							}
					        }
				}
					
					
					
					
					
			
			
	
			out.close();
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}
}
