import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cliente {

	/** Attributes */
	private String nome;
	private String psw;
	private String mail;
	private List<Prenotazione> pr;
	
	//costruttori
	public Cliente(String nome, String psw, String mail) {
		setNome(nome);
		setPsw(psw);
		setMail(mail);
		setPr(new ArrayList<Prenotazione>());
	}

	public Cliente() {
		this.nome = null;
		this.psw = null;
		this.mail = null;
		this.pr = new ArrayList<Prenotazione>();
	}
	
	//metodi
	
	/**
     * Override del metodo toString()
     *
     * @return String
     */
	@Override
	public String toString() {
		return nome +" "+ psw +" "+ mail;
	}
	
	/**
     * Funzione per mostrare all'utente le prenotazioni effettuate
     * 
     * @return boolean
     */
	public boolean visualizzaPrenotazioni() {
		Iterator<Prenotazione> i = pr.iterator();
		if(pr.isEmpty()) {
			System.out.println("Non vi sono prenotazioni registrate al momento");
			return false;
		}
		while(i.hasNext()) {
			System.out.println();
			System.out.println(i.next().toString());
		}
		return true;
	}
	
	/**
     * Funzione che restituisce una determinata prenotazione
     * una prenotazione è definita univocamente dagli id della prenotazione e della proiezione associata
     *
     * @param idPrenotazione
     * @param idProiezione
     * 
     * @return boolean
     */
	public Prenotazione getPrenotazione(int idPrenotazione, int idProiezione) {
		Iterator<Prenotazione> i = pr.iterator();
		Prenotazione pr;
		while(i.hasNext()) {
			pr = i.next();
			if(pr.getId()==idPrenotazione && pr.getProiezione().getId() == idProiezione) {
				return pr;
			}
		}
		return null;
	}

	//Getter e Setter degli attributi
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public List<Prenotazione> getPr() {
		return pr;
	}
	public void setPr(List<Prenotazione> pr) {
		this.pr = pr;
	}
}
