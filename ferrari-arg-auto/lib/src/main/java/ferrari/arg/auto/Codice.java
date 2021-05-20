package ferrari.arg.auto;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Codice {
	public static String creaParametri() {
		final ArrayList<Auto> Incrocio = new ArrayList<>();
		String posOccupata = "";
		String nomOccupato = "";
		final String valOccupato = "";
		final String tempOccupato = "";
		String parametri="";
		String separatore="%";
		for (int x = 0; x < 2; x++) {
			final String lettere = "A,B,C,D,E,F,G,H,I,L,M,N,O,P,Q,R,S,T,U,V,Z,X,Y,K,J";
			String nome = "";
			String posizione = "";
			String priorità = "";
			String tempo_arrivo = "";
			
			int f = 0;
			while (f < 1) {
				final String valore = "" + (int) Math.floor(Math.random() * 100);
				if (!valOccupato.contains(valore)) {
					priorità = valore;
					f++;
				}
			}

			int g = 0;
			while (g < 1) {
				final String tempo = "" + (int) Math.floor(Math.random() * 100);
				if (!tempOccupato.contains(tempo) && Integer.parseInt(tempo) < 30) {
					tempo_arrivo = tempo;
					g++;
				}
			}

			int i = 0;

			while (i < 1) {
				final int valore = (int) Math.floor(Math.random() * 100);
				if (valore < 25 && !nomOccupato.contains(valore + "")) {
					i++;
					final String[] s = lettere.split(",");
					nome = s[valore];
					nomOccupato += valore + " ";
				}
			}
			int j = 0;

			while (j < 1) {
				final int valore = (int) Math.floor(Math.random() * 10);
				if (valore == 0 && !posOccupata.contains("sini")) {
					posizione = "sinistra";
					j++;
				}
				if (valore == 1 && !posOccupata.contains("bass")) {
					posizione = "basso";
					j++;
				}
				if (valore == 2 && !posOccupata.contains("dest")) {
					posizione = "destra";
					j++;
				}
				if (valore == 3 && !posOccupata.contains("alto")) {
					posizione = "alto";
					j++;
				}
			}
		
		if(x==1)separatore="";
			parametri += nome+"/"+posizione+"/"+tempo_arrivo+"/"+priorità+separatore;
			posOccupata+=posizione+" ";
		}

		return parametri;
	}

public static ArrayList<Auto> creaConflittoNoSA(){
	final ArrayList<Auto> Inc=new ArrayList<Auto>();
	String parametri=creaParametri();
	String[]entità=parametri.split("%");

	String[]valori=entità[0].split("/");
    String nome1=valori[0];
    String posizione1=valori[1];
    String tempo_arrivo1=valori[2];
    int priorità1=Integer.parseInt(valori[3]);
    String direzione1="";
    String[]valori2=entità[1].split("/");
    String nome2=valori2[0];
    String posizione2=valori2[1];
    String tempo_arrivo2=valori2[2];
    int priorità2=Integer.parseInt(valori2[3]);
    String direzione2="";
    if(posizione1.equals("alto")&&posizione2.equals("basso")||posizione2.equals("alto")&&posizione1.equals("basso")) {
    	if(Math.random()<0.5) {direzione1="destra";direzione2="sinistra";}
    	else {direzione2="destra";direzione1="sinistra";}
    }
    if(posizione1.equals("destra")&&posizione2.equals("sinistra")||posizione2.equals("destra")&&posizione1.equals("sinistra")) {
    	if(Math.random()<0.5) {direzione1="destra";direzione2="sinistra";}
    	else {direzione2="destra";direzione1="sinistra";}
    }
    if(posizione1.equals("alto")&&posizione2.equals("sinistra")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("sinistra")&&posizione2.equals("basso")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("basso")&&posizione2.equals("destra")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("destra")&&posizione2.equals("alto")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("alto")&&posizione2.equals("destra")) {
    	double a=Math.random();
       if(a<=0.33) {direzione1="diritto";
          if(Math.random()<0.5) direzione2="diritto";
          else direzione2="sinistra";
       }
  	   if(a>=0.66) {direzione1="sinistra";
  	      if(Math.random()<0.5) direzione2="diritto";
          else direzione2="sinistra";
  	   }
  	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";} 
    }
    if(posizione1.equals("destra")&&posizione2.equals("basso")) {
    	double a=Math.random();
        if(a<=0.33) {direzione1="diritto";
           if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
        }
   	   if(a>=0.66) {direzione1="sinistra";
   	      if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
   	   }
   	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";}
     }
    if(posizione1.equals("basso")&&posizione2.equals("sinistra")) {
    	double a=Math.random();
        if(a<=0.33) {direzione1="diritto";
           if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
        }
   	   if(a>=0.66) {direzione1="sinistra";
   	      if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
   	   }
   	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";}
     }
    if(posizione1.equals("sinistra")&&posizione2.equals("alto")) {
    	double a=Math.random();
        if(a<=0.33) {direzione1="diritto";
           if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
        }
   	   if(a>=0.66) {direzione1="sinistra";
   	      if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
   	   }
   	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";}
     }
    
    
    Auto A=new Auto(nome1,posizione1,tempo_arrivo1,direzione1,priorità1,false);
    Auto B=new Auto(nome2,posizione2,tempo_arrivo2,direzione2,priorità2,false);
	Inc.add(A);
	Inc.add(B);
    return Inc;
}	
public static ArrayList<Auto> creaConflittoSA(){
	final ArrayList<Auto> Inc=new ArrayList<Auto>();
	String parametri=creaParametri();
	String[]entità=parametri.split("%");

	String[]valori=entità[0].split("/");
    String nome1=valori[0];
    String posizione1=valori[1];
    String tempo_arrivo1=valori[2];
    int priorità1=Integer.parseInt(valori[3]);
    String direzione1="";
    String[]valori2=entità[1].split("/");
    String nome2=valori2[0];
    String posizione2=valori2[1];
    String tempo_arrivo2=valori2[2];
    int priorità2=Integer.parseInt(valori2[3]);
    String direzione2="";
    if(posizione1.equals("alto")&&posizione2.equals("basso")||posizione2.equals("alto")&&posizione1.equals("basso")) {
    	if(Math.random()<0.5) {direzione1="destra";direzione2="sinistra";}
    	else {direzione2="destra";direzione1="sinistra";}
    }
    if(posizione1.equals("destra")&&posizione2.equals("sinistra")||posizione2.equals("destra")&&posizione1.equals("sinistra")) {
    	if(Math.random()<0.5) {direzione1="destra";direzione2="sinistra";}
    	else {direzione2="destra";direzione1="sinistra";}
    }
    if(posizione1.equals("alto")&&posizione2.equals("sinistra")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("sinistra")&&posizione2.equals("basso")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("basso")&&posizione2.equals("destra")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("destra")&&posizione2.equals("alto")) {
    	if(Math.random()<0.5) {direzione1="diritto";
    	   double a=Math.random();
    	   if(a<=0.33) {direzione2="diritto";}
    	   if(a>=0.66) {direzione2="sinistra";}
    	   if(a<0.66&&a>0.33) {direzione2="destra";}
    	}
    	else {direzione1="sinsitra";
    	   double a=Math.random();
    	   if(a<0.5) {direzione2="diritto";}	
    	   else direzione2="sinistra";
    	}
    }
    if(posizione1.equals("alto")&&posizione2.equals("destra")) {
    	double a=Math.random();
       if(a<=0.33) {direzione1="diritto";
          if(Math.random()<0.5) direzione2="diritto";
          else direzione2="sinistra";
       }
  	   if(a>=0.66) {direzione1="sinistra";
  	      if(Math.random()<0.5) direzione2="diritto";
          else direzione2="sinistra";
  	   }
  	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";} 
    }
    if(posizione1.equals("destra")&&posizione2.equals("basso")) {
    	double a=Math.random();
        if(a<=0.33) {direzione1="diritto";
           if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
        }
   	   if(a>=0.66) {direzione1="sinistra";
   	      if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
   	   }
   	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";}
     }
    if(posizione1.equals("basso")&&posizione2.equals("sinistra")) {
    	double a=Math.random();
        if(a<=0.33) {direzione1="diritto";
           if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
        }
   	   if(a>=0.66) {direzione1="sinistra";
   	      if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
   	   }
   	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";}
     }
    if(posizione1.equals("sinistra")&&posizione2.equals("alto")) {
    	double a=Math.random();
        if(a<=0.33) {direzione1="diritto";
           if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
        }
   	   if(a>=0.66) {direzione1="sinistra";
   	      if(Math.random()<0.5) direzione2="diritto";
           else direzione2="sinistra";
   	   }
   	   if(a<0.66&&a>0.33) {direzione1="destra"; direzione2="diritto";}
     }
    boolean strada1=false;
    boolean strada2=false;
    double b=Math.random();
    if(b<0.33)strada1=true;
    if(b>0.66)strada2=true;
    if(b<0.66&&b>0.33) {strada1=true;strada2=true;}
    
    
    Auto A=new Auto(nome1,posizione1,tempo_arrivo1,direzione1,priorità1,strada1);
    Auto B=new Auto(nome2,posizione2,tempo_arrivo2,direzione2,priorità2,strada2);
	Inc.add(A);
	Inc.add(B);
    return Inc;
}		

	
	public static ArrayList<Sensore> creaSensori() {
		final ArrayList<Sensore> Sens = new ArrayList<>();
		String posOccupata = "";

		for (int x = 0; x < 4; x++) {
			String posizione = "";
			final boolean funzionante = Math.random() > 0.02;
			int j = 0;
			{

				while (j < 1) {
					final int valore = (int) Math.floor(Math.random() * 10);
					if (valore == 0 && !posOccupata.contains("sini")) {
						posizione = "sinistra";
						j++;
					}
					if (valore == 1 && !posOccupata.contains("bass")) {
						posizione = "basso";
						j++;
					}
					if (valore == 2 && !posOccupata.contains("dest")) {
						posizione = "destra";
						j++;
					}
					if (valore == 3 && !posOccupata.contains("alto")) {
						posizione = "alto";
						j++;
					}
				}
			}
			final Sensore S = new Sensore(posizione, funzionante);
			Sens.add(S);
			posOccupata += posizione + " ";
		}
		return Sens;
	}

	public void Caricadati(final String file, final ArrayList<Auto> Incrocio, final ArrayList<Sensore> Sens) {

		try {
			final PrintWriter out = new PrintWriter(new FileWriter(file));
			int k = 0;
			boolean giàScrittoF = false;
			boolean giàScrittoT = false;
			final HashMap<String, Boolean> Rilevazione = new HashMap<>();
			boolean rilevate = true;
			boolean Incidente = true;
			final ArrayList<String> incidenteOno = new ArrayList<>();
			final ArrayList<String> chiedeOnoParz = new ArrayList<>();
			final ArrayList<String> chiedeOno = new ArrayList<>();

			final ArrayList<String> regole = new ArrayList<>();

			for (int i = 0; i < Incrocio.size(); i++) {
				out.println("=> macchina_" + Incrocio.get(i).nome);
				for (final Sensore sen : Sens) {
					if (sen.posizione == Incrocio.get(i).posizione) {
						out.println("=> sensore_" + sen.posizione + "_" + sen.funzionante);
						if (sen.funzionante) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",sensore_" + sen.posizione
									+ "_" + sen.funzionante + " => macchina_" + Incrocio.get(i).nome + "_rilevata");
							k++;
							Rilevazione.put(Incrocio.get(i).nome, true);
							if (Incrocio.get(i).priorità > 60) {
								if (!giàScrittoF) {
									out.println("=> DiFretta_");
								}
								out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
										+ "_rilevata,DiFretta => InoltraSegnaleUrgenza_" + Incrocio.get(i).nome);
								chiedeOnoParz.add("d" + k + " " + Incrocio.get(i).nome);
								k++;
								giàScrittoF = true;
							}
							if (Incrocio.get(i).priorità < 60) {
								if (!giàScrittoT) {
									out.println("=> Tranquilla");
								}
								out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
										+ "_rilevata,Tranquilla => !InoltraSegnaleUrgenza_" + Incrocio.get(i).nome);
								k++;
								giàScrittoT = true;
							}
						} else {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",sensore_" + sen.posizione
									+ "_" + sen.funzionante + " => macchina_" + Incrocio.get(i).nome + "_Nonrilevata");
							Rilevazione.put(Incrocio.get(i).nome, false);

							k++;
						}
					}

				}
				for (int j = 0; j < Incrocio.size(); j++) {
					if (i != j) {
						if (Incrocio.get(i).posizione == "destra" && Incrocio.get(j).posizione == "sinistra"
								&& Incrocio.get(i).direzione == "dritto" && Incrocio.get(j).direzione == "dritto"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "alto" && Incrocio.get(j).posizione == "basso"
								&& Incrocio.get(i).direzione == "dritto" && Incrocio.get(j).direzione == "dritto"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "destra" && Incrocio.get(j).posizione == "alto"
								&& Incrocio.get(i).direzione == "destra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "alto" && Incrocio.get(j).posizione == "sinistra"
								&& Incrocio.get(i).direzione == "destra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "sinistra" && Incrocio.get(j).posizione == "basso"
								&& Incrocio.get(i).direzione == "destra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "basso" && Incrocio.get(j).posizione == "destra"
								&& Incrocio.get(i).direzione == "destra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "basso" && Incrocio.get(j).posizione == "alto"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione != "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "alto" && Incrocio.get(j).posizione == "basso"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione != "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "sinistra" && Incrocio.get(j).posizione == "destra"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione != "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "destra" && Incrocio.get(j).posizione == "sinistra"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione != "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "basso" && Incrocio.get(j).posizione == "sinistra"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione == "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "destra" && Incrocio.get(j).posizione == "basso"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione == "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "alto" && Incrocio.get(j).posizione == "destra"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione == "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
						if (Incrocio.get(i).posizione == "sinistra" && Incrocio.get(j).posizione == "alto"
								&& Incrocio.get(i).direzione == "destra" && Incrocio.get(j).direzione == "sinistra"
								&& !regole.contains(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0")) {
							out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + ",macchina_"
									+ Incrocio.get(j).nome + " => NonSiOstacolano");
							regole.add(Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "0");
							regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "0");
							k++;
							Incidente = false;
							out.println("d" + k + ": NonSiOstacolano" + " => !Incidente");
							k++;
						}
					}
				}
			}

			if (Incidente) {
				for (final String a : Rilevazione.keySet()) {
					for (final String b : Rilevazione.keySet()) {
						if (a != b && Rilevazione.get(a) == false && Rilevazione.get(b) != false) {
							out.println("d" + k + ": macchina_" + a + ",macchina_" + b + " => !NonSiOstacolano");
							rilevate = false;
							k++;
							out.println("d" + k + ": !NonSiOstacolano,macchina_" + a + "_Nonrilevata => Incidente");
							k++;
						}
						if (a != b && Rilevazione.get(a) == false && Rilevazione.get(b) == false
								&& !regole.contains(a + "," + b + "X")) {
							out.println("d" + k + ": macchina_" + a + ",macchina_" + b + " => !NonSiOstacolano");
							k++;
							out.println("d" + k + ": !NonSiOstacolano,macchina_" + a + "_Nonrilevata,macchina_" + b
									+ "_Nonrilevata => Incidente");
							k++;
							regole.add(b + "," + a + "X");
							rilevate = false;
						}
					}
				}
				if (rilevate) {
					for (int i = 0; i < Incrocio.size(); i++) {
						for (int j = 0; j < Incrocio.size(); j++) {
							if (i != j
									&& Integer.parseInt(Incrocio.get(i).tempo_arrivo)
											- Integer.parseInt(Incrocio.get(j).tempo_arrivo) < 15
									&& Integer.parseInt(Incrocio.get(i).tempo_arrivo)
											- Integer.parseInt(Incrocio.get(j).tempo_arrivo) > -15) {
								if (Incrocio.get(i).priorità > 60 && Incrocio.get(j).priorità < 60) {
									out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,macchina_"
											+ Incrocio.get(j).nome + "_rilevata => !NonSiOstacolano");
									k++;
									out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
									incidenteOno.add("d" + k);
									k++;

									out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,"
											+ "InoltraSegnaleUrgenza_" + Incrocio.get(i).nome + " => PassaPerPrima_"
											+ Incrocio.get(i).nome);
									k++;
									out.println("d" + k + ": macchina_" + Incrocio.get(j).nome + "_rilevata,"
											+ "!InoltraSegnaleUrgenza_" + Incrocio.get(j).nome + " => PassaPerSeconda_"
											+ Incrocio.get(j).nome);
									k++;
									out.println("d" + k + ": PassaPerSeconda_" + Incrocio.get(j).nome
											+ ",PassaPerPrima_" + Incrocio.get(i).nome + " => !ApparenteIncidente");
									incidenteOno.add("d" + k);
									k++;
									out.println();
									out.println(incidenteOno.get(0) + "<" + incidenteOno.get(1));
								}
								if (Incrocio.get(j).priorità < 60 && Incrocio.get(i).priorità < 60) {
									if (Incrocio.get(i).priorità > Incrocio.get(j).priorità) {
										out.println(
												"d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,macchina_"
														+ Incrocio.get(j).nome + "_rilevata => !NonSiOstacolano");
										k++;
										out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
										incidenteOno.add("d" + k);
										k++;

										out.println("=> MoltoPropensoAspettare_" + Incrocio.get(j).nome);
										out.println("d" + k + ": !InoltraSegnaleUrgenza_" + Incrocio.get(i).nome
												+ ",!InoltraSegnaleUrgenza_" + Incrocio.get(j).nome
												+ ",MoltoPropensoAspettare_" + Incrocio.get(j).nome
												+ " => PassaPerPrima_" + Incrocio.get(i).nome);
										k++;
										out.println("d" + k + ": PassaPerPrima_" + Incrocio.get(i).nome
												+ ",!InoltraSegnaleUrgenza_" + Incrocio.get(j).nome
												+ " => PassaPerSeconda_" + Incrocio.get(j).nome);
										k++;
										out.println("d" + k + ": PassaPerSeconda_" + Incrocio.get(j).nome
												+ ",PassaPerPrima_" + Incrocio.get(i).nome + " => !ApparenteIncidente");
										incidenteOno.add("d" + k);
										k++;
										out.println();
										out.println(incidenteOno.get(0) + "<" + incidenteOno.get(1));
									}
								}
								if (Incrocio.get(j).priorità > 60 && Incrocio.get(i).priorità > 60) {
									if (Incrocio.get(i).strada_alternativa == true
											&& Incrocio.get(j).strada_alternativa != true) {
										out.println(
												"d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,macchina_"
														+ Incrocio.get(j).nome + "_rilevata => !NonSiOstacolano");
										k++;
										out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
										incidenteOno.add("d" + k);
										k++;

										out.println("=>StradaAlternativa_" + Incrocio.get(i).nome);
										out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
												+ "_rilevata,StradaAlternativa_" + Incrocio.get(i).nome
												+ " => !InoltraSegnaleUrgenza_" + Incrocio.get(i).nome);
										if (chiedeOnoParz.get(0).contains(Incrocio.get(i).nome)) {
											chiedeOno.add(
													"" + chiedeOnoParz.get(0).replace(" " + Incrocio.get(i).nome, ""));
										}
										if (chiedeOnoParz.get(1).contains(Incrocio.get(i).nome)) {
											chiedeOno.add(
													"" + chiedeOnoParz.get(1).replace(" " + Incrocio.get(i).nome, ""));
										}
										chiedeOno.add("d" + k);
										k++;
										out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,"
												+ "!InoltraSegnaleUrgenza_" + Incrocio.get(i).nome
												+ " => PassaPerSeconda_" + Incrocio.get(i).nome);
										k++;
										out.println("d" + k + ": macchina_" + Incrocio.get(j).nome
												+ "_rilevata,InoltraSegnaleUrgenza_" + Incrocio.get(j).nome
												+ " => PassaPerPrima_" + Incrocio.get(j).nome);
										k++;
										out.println("d" + k + ": PassaPerSeconda_" + Incrocio.get(i).nome
												+ ",PassaPerPrima_" + Incrocio.get(j).nome + " => !ApparenteIncidente");
										out.println();
										incidenteOno.add("d" + k);
										out.println(chiedeOno.get(0) + "<" + chiedeOno.get(1) + "<"
												+ incidenteOno.get(0) + "<" + incidenteOno.get(1));
										k++;
									}
									if (Incrocio.get(j).strada_alternativa == true
											&& Incrocio.get(i).strada_alternativa == true && !regole.contains(
													Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "X")) {
										if (Math.random() < 0.5) {
											out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
													+ "_rilevata,macchina_" + Incrocio.get(j).nome
													+ "_rilevata => !NonSiOstacolano");
											k++;
											out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
											incidenteOno.add("d" + k);
											k++;

											out.println("=>StradaAlternativa_" + Incrocio.get(i).nome);
											out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
													+ "_rilevata,StradaAlternativa_" + Incrocio.get(i).nome
													+ " => !InoltraSegnaleUrgenza_" + Incrocio.get(i).nome);
											if (chiedeOnoParz.get(0).contains(Incrocio.get(i).nome)) {
												chiedeOno.add(""
														+ chiedeOnoParz.get(0).replace(" " + Incrocio.get(i).nome, ""));
											}
											if (chiedeOnoParz.get(1).contains(Incrocio.get(i).nome)) {
												chiedeOno.add(""
														+ chiedeOnoParz.get(1).replace(" " + Incrocio.get(i).nome, ""));
											}
											chiedeOno.add("d" + k);
											k++;
											out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,"
													+ "!InoltraSegnaleUrgenza_" + Incrocio.get(i).nome
													+ " => PassaPerSeconda_" + Incrocio.get(i).nome);
											k++;
											out.println("d" + k + ": macchina_" + Incrocio.get(j).nome
													+ "_rilevata,InoltraSegnaleUrgenza_" + Incrocio.get(j).nome
													+ " => PassaPerPrima_" + Incrocio.get(j).nome);
											k++;
											out.println("d" + k + ": PassaPerSeconda_" + Incrocio.get(i).nome
													+ ",PassaPerPrima_" + Incrocio.get(j).nome
													+ " => !ApparenteIncidente");
											out.println();
											incidenteOno.add("d" + k);
											out.println(chiedeOno.get(0) + "<" + chiedeOno.get(1) + "<"
													+ incidenteOno.get(0) + "<" + incidenteOno.get(1));
											k++;
											regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "X");
										} else {
											out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
													+ "_rilevata,macchina_" + Incrocio.get(j).nome
													+ "_rilevata => !NonSiOstacolano");
											k++;
											out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
											incidenteOno.add("d" + k);
											k++;

											out.println("=>StradaAlternativa_" + Incrocio.get(j).nome);
											out.println("d" + k + ": macchina_" + Incrocio.get(j).nome
													+ "_rilevata,StradaAlternativa_" + Incrocio.get(j).nome
													+ " => !InoltraSegnaleUrgenza_" + Incrocio.get(j).nome);
											if (chiedeOnoParz.get(0).contains(Incrocio.get(j).nome)) {
												chiedeOno.add(""
														+ chiedeOnoParz.get(0).replace(" " + Incrocio.get(j).nome, ""));
											}
											if (chiedeOnoParz.get(1).contains(Incrocio.get(j).nome)) {
												chiedeOno.add(""
														+ chiedeOnoParz.get(1).replace(" " + Incrocio.get(j).nome, ""));
											}
											chiedeOno.add("d" + k);
											k++;
											out.println("d" + k + ": macchina_" + Incrocio.get(j).nome + "_rilevata,"
													+ "!InoltraSegnaleUrgenza_" + Incrocio.get(j).nome
													+ " => PassaPerSeconda_" + Incrocio.get(j).nome);
											k++;
											out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
													+ "_rilevata,InoltraSegnaleUrgenza_" + Incrocio.get(i).nome
													+ " => PassaPerPrima_" + Incrocio.get(i).nome);
											k++;
											out.println("d" + k + ": PassaPerSeconda_" + Incrocio.get(j).nome
													+ ",PassaPerPrima_" + Incrocio.get(i).nome
													+ " => !ApparenteIncidente");
											out.println();
											incidenteOno.add("d" + k);
											out.println(chiedeOno.get(0) + "<" + chiedeOno.get(1) + "<"
													+ incidenteOno.get(0) + "<" + incidenteOno.get(1));
											k++;
											regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "X");
										}
									}
									if (Incrocio.get(j).strada_alternativa == false
											&& Incrocio.get(i).strada_alternativa == false && !regole.contains(
													Incrocio.get(i).nome + "," + Incrocio.get(j).nome + "X")) {
										if (Math.random() < 0.5) {
											out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
													+ "_rilevata,macchina_" + Incrocio.get(j).nome
													+ "_rilevata => !NonSiOstacolano");
											k++;
											out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
											incidenteOno.add("d" + k);
											k++;

											out.println("=> Spazientita_" + Incrocio.get(j).nome);
											out.println("d" + k + ": InoltraSegnaleUrgenza_" + Incrocio.get(i).nome
													+ ",InoltraSegnaleUrgenza_" + Incrocio.get(j).nome + ",Spazientita_"
													+ Incrocio.get(j).nome + " => PassaPerPrima_"
													+ Incrocio.get(j).nome);
											k++;
											out.println("d" + k + ": PassaPerPrima_" + Incrocio.get(j).nome
													+ " => PassaPerSeconda_" + Incrocio.get(i).nome);
											k++;
											out.println("d" + k + ": PassaPerSeconda_" + Incrocio.get(i).nome
													+ ",PassaPerPrima_" + Incrocio.get(j).nome
													+ " => !ApparenteIncidente");
											incidenteOno.add("d" + k);
											k++;
											out.println();
											out.println(incidenteOno.get(0) + "<" + incidenteOno.get(1));
											regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "X");
										} else {
											out.println("d" + k + ": macchina_" + Incrocio.get(i).nome
													+ "_rilevata,macchina_" + Incrocio.get(j).nome
													+ "_rilevata => !NonSiOstacolano");
											k++;
											out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
											incidenteOno.add("d" + k);
											k++;

											out.println("=> Spazientita_" + Incrocio.get(i).nome);
											out.println("d" + k + ": InoltraSegnaleUrgenza_" + Incrocio.get(i).nome
													+ ",InoltraSegnaleUrgenza_" + Incrocio.get(j).nome + ",Spazientita_"
													+ Incrocio.get(i).nome + " => PassaPerPrima_"
													+ Incrocio.get(i).nome);
											k++;
											out.println("d" + k + ": PassaPerPrima_" + Incrocio.get(i).nome
													+ " => PassaPerSeconda_" + Incrocio.get(j).nome);
											k++;
											out.println("d" + k + ": PassaPerSeconda_" + Incrocio.get(j).nome
													+ ",PassaPerPrima_" + Incrocio.get(i).nome
													+ " => !ApparenteIncidente");
											incidenteOno.add("d" + k);
											k++;
											out.println();
											out.println(incidenteOno.get(0) + "<" + incidenteOno.get(1));
											regole.add(Incrocio.get(j).nome + "," + Incrocio.get(i).nome + "X");
										}
									}
								}
							}

							if (i != j
									&& Integer.parseInt(Incrocio.get(i).tempo_arrivo)
											- Integer.parseInt(Incrocio.get(j).tempo_arrivo) > 15
									&& Integer.parseInt(Incrocio.get(i).tempo_arrivo)
											- Integer.parseInt(Incrocio.get(j).tempo_arrivo) > -15) {
								out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,macchina_"
										+ Incrocio.get(j).nome + "_rilevata => !NonSiOstacolano");
								incidenteOno.add("d" + k);
								k++;
								out.println("d" + k + ": !NonSiOstacolano => ApparenteIncidente");
								k++;

								out.println("=>PiùDi15secDiDifferenza");
								out.println("d" + k + ": macchina_" + Incrocio.get(i).nome + "_rilevata,macchina_"
										+ Incrocio.get(j).nome + "_rilevata,PiùDi15secDiDifferenza => NonSiOstacolano");
								incidenteOno.add("d" + k);
								k++;
								out.println("d" + k + ": NonSiOstacolano => !ApparenteIncidente");
								k++;
								out.println();
								out.println(incidenteOno.get(0) + "<" + incidenteOno.get(1));

							}
						}
					}
				}
			}

			out.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
