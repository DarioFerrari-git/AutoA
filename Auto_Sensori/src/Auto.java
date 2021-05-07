
public class Auto {
 String nome;
 String posizione;
 String tempo_arrivo;
 String direzione;
 int priorità;
 boolean strada_alternativa;
 
 public Auto(String nome,String posizione,String tempo_arrivo,String direzione,int priorità,boolean strada_alternativa){
	 this.direzione=direzione;
	 this.nome=nome;
	 this.posizione=posizione;	
	 this.tempo_arrivo=tempo_arrivo;
	 this.priorità=priorità;
	 this.strada_alternativa=strada_alternativa;
 }
 
 public String toString() {
		return "("+nome+","+posizione+","+tempo_arrivo+","+direzione+","+priorità+","+strada_alternativa+")";
	}
}
