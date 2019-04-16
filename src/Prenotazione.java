import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class Prenotazione implements Comparable<Prenotazione>{
	
	/** Attributes */
	private int id;
	private double prezzo;
	private LocalDate dataPrenotazione;
	
	/** Associations */
	private Proiezione proiezione;
	private List<Posto> posti;
	private Cliente cliente;
	
	//costruttore
	public Prenotazione(int id, Proiezione proiezione, Cliente cliente, List<Posto> posti, LocalDate dataPrenotazione) {
		setId(id);
		setProiezione(proiezione);
		setCliente(cliente);
		setPosti(posti);
		setPrezzo(calcolaPrezzo(this.proiezione,this.posti));
		setDataPrenotazione(dataPrenotazione);
	}

	//metodi
	
	/**
     * Funzione per sistemare i dati in modo da visualizzarli poi a console
     * Override del metodo toString()
     *
     * @param args
     * @param input
     * 
     * @return boolean
     */
	@Override
	public String toString() {
		Iterator<Posto> i = posti.iterator();
		String sp = "";
		Posto p = null;
		while(i.hasNext()) {
			p = i.next();
			sp = sp.concat(" "+p.getFila() +"-"+p.getNumero());
		}
		return "ID PROIEZIONE:\t\t "+ proiezione.getId() + " -> film:" + proiezione.getFilm().getTitolo() +"; giorno: "+ proiezione.getInizio().toLocalDate()+"; ore: "+proiezione.getInizio().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
				+"\nID PRENOTAZIONE:\t "+ id
				+"\nDATI CLIENTE:\t\t nome - "+ cliente.getNome() +"; mail - "+ cliente.getMail() 
				+"\nPOSTI PRENOTATI:\t"+ sp
				+"\nDATA PRENOTAZIONE:\t "+ dataPrenotazione.toString()
				+"\nTOTALE $:\t\t "+ prezzo;
	}
	
	/**
     * Funzione per sistemare i dati in modo da scriverli poi nel file dedicato
     * 
     * @return String
     */
	public String print() {
		Iterator<Posto> i = getPosti().iterator();
		String sp = "";
		Posto p = null;
		while(i.hasNext()) {
			p = i.next();
			sp = sp.concat(" "+p.getFila() +"-"+p.getNumero());
		}
		return getProiezione().getId() +" "+ getId() +" "+ getCliente().getNome() +" "+ getCliente().getMail() 
		+ sp +" "+ getDataPrenotazione().toString() +" "+ getPrezzo();
	}

	/**
     * Funzione per stabilire il prezzo complessivo della prenotazione
     * 
     * @param p
     * @param posti
     * 
     * @return double costo totale della prenotazione
     */
	private double calcolaPrezzo(Proiezione p, List<Posto> posti) {
		Iterator<Posto> i = posti.iterator();
		double tot = 0;
		while(i.hasNext()) {
			tot = tot + p.getPrezzo() + i.next().getCostoAggiuntivo();
		}
		return tot;
	}
	
	/**
     * Funzione per il confronto tra due prenotazioni
     * (vengono ordinate in ordine crescente di id)
     *
     * @param o
     * 
     * @return int
     */
	public int compareTo(Prenotazione o)
	{
		return(id - o.id);
	}
	
	//getter e setter degli attributi
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Proiezione getProiezione() {
		return proiezione;
	}
	public void setProiezione(Proiezione proiezione) {
		this.proiezione = proiezione;
	}
	public List<Posto> getPosti() {
		return posti;
	}
	public void setPosti(List<Posto> posti) {
		this.posti = posti;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public LocalDate getDataPrenotazione() {
		return dataPrenotazione;
	}
	public void setDataPrenotazione(LocalDate dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
