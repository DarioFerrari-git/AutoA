
public class Sensore {
 String posizione;
 boolean funzionante;
 
 public Sensore(String posizione, boolean funzionante) {
	 this.posizione=posizione;
	 this.funzionante=funzionante;
 }
 
 public String toString() {
	 return "("+posizione+","+funzionante+")";
 }
 
}
