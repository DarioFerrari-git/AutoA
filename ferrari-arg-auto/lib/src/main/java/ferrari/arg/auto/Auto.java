package ferrari.arg.auto;

public class Auto {
	String nome;
	String posizione;
	String tempo_arrivo;
	String direzione;
	int priorità;
	boolean strada_alternativa;

	public Auto(final String nome, final String posizione, final String tempo_arrivo, final String direzione,
			final int priorità, final boolean strada_alternativa) {
		this.direzione = direzione;
		this.nome = nome;
		this.posizione = posizione;
		this.tempo_arrivo = tempo_arrivo;
		this.priorità = priorità;
		this.strada_alternativa = strada_alternativa;
	}

	@Override
	public String toString() {
		return "(" + this.nome + "," + this.posizione + "," + this.tempo_arrivo + "," + this.direzione + ","
				+ this.priorità + "," + this.strada_alternativa + ")";
	}
}
