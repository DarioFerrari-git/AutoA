package ferrari.arg.auto;

public class Sensore {
	String posizione;
	boolean funzionante;

	public Sensore(final String posizione, final boolean funzionante) {
		this.posizione = posizione;
		this.funzionante = funzionante;
	}

	@Override
	public String toString() {
		return "(" + this.posizione + "," + this.funzionante + ")";
	}

}
