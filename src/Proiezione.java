import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Proiezione implements Comparable<Proiezione>{
	
	/** Attributes */
	private int id;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	private double prezzo;
	private int totPostiPrenotati;
	
	/** Associations */
	private Film film;
	private Sala sala;
	private List<Prenotazione> prenotazioni;
	
	//costruttore
	public Proiezione(int id, Film f, Sala s, LocalDateTime inizio, double prezzo) {
		setId(id);
		setFilm(f);
		setSala(new Sala(s.getId(), s.getNumeroFile(), s.getCapienza(), s.isTrid()));
		setInizio(inizio);
		setFine(calcolaFine());
		setPrezzo(prezzo);
		setPrenotazioni(new ArrayList<Prenotazione>());
		setTotPostiPrenotati(0);
	}
	
	//metodi
	
	/**
     * Funzione per stabilire giorno e ora in cui termina la proiezione
     * 
     * @return LocalDateTime ora di termine della proiezione
     */
	public LocalDateTime calcolaFine() {
		return this.inizio.plusMinutes(this.film.getDurata());
	}

	/**
     * Funzione per sistemare i dati in modo da visualizzarli poi a console
     * Override del metodo toString()
     * 
     * @return String
     */
	@Override
	public String toString() {
		return id + " - \nFilm:\t" + film.getTitolo() + "\nSala:\t" + sala.getId() 
		+ "\nInizio:\t" + inizio.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
		+ " " + inizio.toLocalDate()
		+ "\nFine:\t" + fine.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
		+ " " + fine.toLocalDate()
		+ "\n$:\t" + prezzo;
	}
	
	/**
     * Funzione per sistemare i dati in modo da scriverli poi nel file dedicato
     * 
     * @return boolean
     */
	public String print() {
		return id + " " + String.join("_", film.getTitolo().split(" ")) + " " + sala.getId() 
		+ " " + inizio.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
		+ " " + inizio.toLocalDate()
		+ " " + prezzo;
	}

	/**
     * Funzione per aggiungere una prenotazione all'elenco
     *
     * @param p
     * 
     */
	public void aggiungiPrenotazione(Prenotazione p) {
		Iterator<Posto> i = p.getPosti().iterator();
		Posto posto = null;
		
		this.totPostiPrenotati = this.totPostiPrenotati+p.getPosti().size();
		//ogni posto indicato nella prenotazione viene impostato come occupato
		while(i.hasNext()) {
			posto = i.next();
			this.sala.getPosti()[posto.getFila()-1][posto.getNumero()-1].setLibero(false);
		}
		//aggiunta alla lista
		this.prenotazioni.add(p);
	}
	
	/**
     * Funzione per confrontare due proiezioni
     * le proiezioni vengono ordinate per titolo
     * se il titolo è uguale si ordinano per istante di inizio (crescente)
     *
     * @param o
     * 
     * @return boolean
     */
	public int compareTo(Proiezione o)
	{
		if(film.equals(o.film))
			return inizio.compareTo(o.inizio);
		return film.getTitolo().compareTo(o.film.getTitolo());
	}
	
	/**
     * Funzione che restituisce il primo id libero
     * 
     * @return int
     */
	public int checkForId() {
		int i = 1;
		Iterator<Prenotazione> it = prenotazioni.iterator();
		while(it.hasNext()) {
			if(it.next().getId()!=i) {
				return i;
			}else {
				i++;
			}
		}
		return i;
	}
	
	//getter e setter degli attributi
	public int getTotPostiPrenotati() {
		return totPostiPrenotati;
	}
	public void setTotPostiPrenotati(int totPostiPrenotati) {
		this.totPostiPrenotati = totPostiPrenotati;
	}
	public Film getFilm() {
		return film;
	}
	public void setFilm(Film film) {
		this.film = film;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(List<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}
	public LocalDateTime getFine() {
		return fine;
	}
	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
}
