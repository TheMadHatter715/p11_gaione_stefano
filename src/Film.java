
public class Film {

	/** Attributes */
	private String titolo;
	private int durata;
	private String genere;
	private boolean trid;
	
	//costruttore
	public Film(String titolo, int durata, String genere, boolean t) {
		setTitolo(titolo);
		setDurata(durata);
		setGenere(genere);
		setTrid(t);
	}
	
	//metodi
	
	/**
     * Override del metodo toString()
     *
     * @return String
     */
	@Override
	public String toString() {
		return getTitolo() + " " + getDurata() + " " + getGenere() + " " + isTrid();
	}

	//getter e setter degli attributi
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public int getDurata() {
		return durata;
	}
	public void setDurata(int durata) {
		this.durata = durata;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public boolean isTrid() {
		return trid;
	}
	public void setTrid(boolean trid) {
		this.trid = trid;
	}
	
	
}
