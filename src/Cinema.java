import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * The Class Cinema.
 */

public class Cinema {

	/** Attributes */
	private String nome;					//nome del cinema
	
	/** Associations */
	private List<Cliente> clienti;			//elenco dei clienti registrati
	private List<Film> film;				//elenco dei film attualmente disponibili
	private GestoreCinema gestore;			//gestore del cinema
	private List<Proiezione> proiezioni;	//elenco delle proiezioni in programma
	private List<Sala> sale;				//elenco delle sale
	
	//costruttore
	public Cinema() {
		setNome("SWEngCinema");
		setClienti(new ArrayList<Cliente>());
		setFilm(new ArrayList<Film>());
		setProiezioni(new ArrayList<Proiezione>());
		setSale(new ArrayList<Sala>());
	}

	//metodi
	
	/**
     * Interfaccia grafica (a console) per l'interazione con l'utente
     *
     * @param input
     * @param args
     * 
     * @return boolean
     */
	public boolean userInterface(Scanner input, String[] args) {
		
		//variabile di ritorno
		boolean result = true;
		
		//variabili utili
		boolean l = false;
		boolean d = false;
		boolean r = false;
		int choice;
		Cliente cl = new Cliente();
		
		//fase di registrazione
		do {
			System.out.println("\nBenvenuto su "+ getNome()+"!");
			System.out.println("Sei già registrato? s/n");
			String s = input.next();
			switch(s) {
			case "s":
				//utente già registrato
				r = true;
				break;
			case "n":
				//utente non ancora registrato
				boolean ok = false;
				do {
					System.out.println("Vuoi registrarti? s/n");
					switch(input.next()) {
					case "s":
						//l'utente desidera registrarsi
						if(registraCliente(input,args)) {
							System.out.println("Registrazione avvenuta!");
							System.out.println("Reindirizzamento alla home in corso...");
						}
						ok = true;
						break;
					case "n":
						//l'utente non desidera registrarsi
						System.out.println("Grazie e arrivederci!");
						ok = true;
						break;
					default:
						System.out.println("ERRORE: valore inserito non valido, operazione annullata");
					}
				}while(!ok);
				break;
			default:
				System.out.println("Carattere/i inserito/i non valido/i");		
			}
		}while(!r);
		
		//fase di login
		do {
			System.out.println("\nLOGIN:");
			System.out.println("Inserire credenziali (nome, psw):");
			String id = input.next();
			String psw = input.next();
			
			int x = autenticazione(input, id, psw);
			
			if(x != 0) {
				//si è autenticato un cliente
				if(x == 1) {
					cl = trovaCliente(id);
					l = true;
				}
				//si è autenticato il gestore del cinema
				if(x == 2) {
					l = true;
					d = true;
				}
				//l'operazione è stata annullata
				if(x == 3) {
					return true;
				}
			}
			
		}while(!l);
		
		//fase di scelta delle operazioni da effettuare
		if(d) {
			//elenco operazioni che solo il gestore del cinema può effettuare
			do {
				System.out.println("\nSignor direttore, cosa desidera fare?");
				System.out.println("0-Tornare alla schermata iniziale");
				System.out.println("1-Spegnere il sistema");
				System.out.println("2-Visionare elenco proiezioni");
				System.out.println("3-Inserire proiezione");
				System.out.println("4-Rimuovere proiezione");
				System.out.println("5-Cambia orario proiezione");
				System.out.println("6-Cambia sala proiezione");
				
				while(!input.hasNextInt()) {
					System.out.println("ERRORE: il valore inserito deve essere un numero tra quelli indicati, riprovare");
					input.next();
				}
				choice = input.nextInt();
				
				switch(choice) {
					
				case 0:
					System.out.println("Grazie e arrivederci!");
					result = true;
					break;
				case 1:
					System.out.println("Arresto in corso...");
					result = false;
					break;
				case 2:
					richiediProiezioni();
					result = true;
					break;
				case 3:
					aggiungiProiezione(args, input);
					result = true;
					break;
				case 4:
					rimuoviProiezione(args, input);
					result = true;
					break;
				case 5:
					cambiaOrario(args, input);
					result = true;
					break;
				case 6:
					cambiaSala(args, input);
					result = true;
					break;
				default:
					System.out.println("ERRORE: opzione non presente");
				}
			}while(choice!=1&&choice!=0);
		}
		else {
			//elenco operazioni che solo il cliente può effettuare
			do {
				System.out.println("\nGentile cliente, cosa desidera fare?");
				System.out.println("0-Uscire");
				System.out.println("1-Prenotare biglietti");
				System.out.println("2-Visionare prenotazioni");
				System.out.println("3-Cancellare prenotazione");
				System.out.println("4-Cercare Proiezioni");
				System.out.println("5-Cancellare registrazione");
				
				while(!input.hasNextInt()) {
					System.out.println("ERRORE: il valore inserito deve essere un numero tra quelli indicati, riprovare");
					input.next();
				}
				choice = input.nextInt();
				
				switch(choice) {
					
				case 0:
					System.out.println("Grazie e arrivederci!");
					result = true;
					break;
				case 1:
					effettuaPrenotazione(cl, args, input);
					result = true;
					break;
				case 2:
					cl.visualizzaPrenotazioni();
					result = true;
					break;
				case 3:
					cancellaPrenotazione(cl, args, input);
					result = true;
					break;
				case 4:
					richiediProiezioni();
					result = true;
					break;
				case 5:
					rimuoviCliente(cl, args);
					result = true;
					break;
				default:
					System.out.println("ERRORE: opzione non presente");
				}
			}while(choice!=5&&choice!=0);	
		}
		return result;
	}
	
	/**
     * Funzione per l'autenticazione dell'utente
     * Il sistema riconosce le 4 situazioni possibili:
     * 0 - l'autenticazione non è andata a buon fine per un errore di sistema
     * 1 - si sta loggando un cliente
     * 2 - si sta loggando il gestore del cinema
     * 3 - l'utente annulla l'operazione (se non vuole effettuare un nuovo tentativo, in seguito ad errato inserimento delle credenziali)
     * Per ognuno di questi casi la funzione reistituisce il numero relativo.
     * 
     * @param input
     * @param id
     * @param psw
     * 
     * @return int
     */
	public int autenticazione(Scanner input, String id, String psw) {
		//primo check: controllo se le credenziali sono quelle del direttore
		if(!(id.equals(getGestore().getId()))||(!psw.contentEquals(getGestore().getPsw()))) {
			//in caso negativo
			//secondo check: controllo se le credenziali sono quelle di un cliente
			int i = 0;
			for(i = 0; i < getClienti().size(); i++) {
				if(id.equals(getClienti().get(i).getNome())&&psw.equals(getClienti().get(i).getPsw())) {
					//il cliente si logga
					System.out.println("Benvenuto "+id+"!");
					//cl = trovaCliente(id);
					return 1;
				}
				//se id e psw non corrispondono a nessun cliente viene restituito un messaggio d'errore
				if(i==getClienti().size()-1) {
					System.out.println("ERRORE: id o psw errati!");
					System.out.println("Inserire 0 per uscire");
					String k = input.next();
					if(k.equals("0")) {
						return 3;
					}
				}
			}
		}
		else {
			//in caso positivo è il direttore ad essersi autenticato
			System.out.println("\nBenvenuto direttore "+getGestore().getNome()+"!");
			return 2;
		}
		return 0;
	}
	
	/**
     * Creazione di una nuova proiezione ed inserimento nella lista delle proiezioni in programma
     *
     * @param args
     * @param input
     * 
     * @return boolean
     */
	public boolean aggiungiProiezione(String[] args, Scanner input) {
		boolean correctInput; //bit per la verifica del corretto inserimento dei dati da parte dell'utente
		
		//scelta del film da proiettare
		System.out.println("Di quale film si vuole aggiungere una proiezione?");
		showFilm();
		
		Film f = null;
		correctInput = false;
		do {
			checkForInt(input);
			try{
				f = film.get(input.nextInt()-1);
				correctInput = true;
			}catch(Exception e) {
				System.out.println("ERRORE: il valore inserito deve essere tra quelli indicati nell'elenco, riprovare");
			}
		}while(!correctInput);
		
		//inserimento data di proiezione
		System.out.println("Inserire la data di proiezione (formato yyyy-MM-dd)");
		LocalDate data = null;
		correctInput = false;
		do {
			try{
				data = LocalDate.parse(input.next(),
		            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				//La data inserita deve essere successiva a quella corrente
				if(data.isAfter(LocalDate.now())) {
					correctInput = true;
				}else {
					System.out.println("ERRORE: la data deve essere successiva a quella odierna, riprovare");
				}
			}catch (DateTimeParseException e) {
				System.out.println("ERRORE: inserire la data nel formato indicato, riprovare");
			}
		}while(!correctInput);
		
		//inserimento ora di proiezione
		System.out.println("Inserire l'ora di proiezione (formato HH:mm)");
		LocalTime orain = null;
		correctInput = false;
		do {
			try{
				orain = LocalTime.parse(input.next()+":00",
		            DateTimeFormatter.ofPattern("HH:mm:ss"));
				correctInput = true;
			}catch (DateTimeParseException e) {
			    System.out.println("ERRORE: inserire l'ora nel formato indicato, riprovare");
			}
		}while(!correctInput);
		
		//inserimento prezzo
		correctInput = false;
		System.out.println("Inserire il prezzo (usare la virgola per la parte decimale)");
		double prezzo;
		do {
			while(!input.hasNextDouble()) {
				System.out.println("ERRORE: il valore inserito deve essere un numero positivo, riprovare");
				input.next();
			}
			prezzo = input.nextDouble();
			if(prezzo>0) {
				correctInput = true;
			}
			else {
				System.out.println("ERRORE: il valore inserito deve essere un numero positivo, riprovare");
			}
		}while(!correctInput);
		
		
		//scelta sala di proiezione
		System.out.println("Scegliere la sala di proiezione");
		showSale();
		
		Sala s = null;
		correctInput = false;
		Proiezione p = null;
		LocalDateTime inizio = LocalDateTime.of(data,orain);
		do {
			checkForInt(input);
			try {
				s = sale.get(input.nextInt()-1);
				p = new Proiezione(newID(), f, s, inizio, prezzo);
				
				//la sala deve essere compatibile con il film e allo stesso tempo
				//la sala indicata deve essere libera nell'orario precedentemente inserito
				if(s.isTrid()==f.isTrid()) {
					if(salaLibera(p)) {
						correctInput = true;
					}else {
						p = null;
						System.out.println("ERRORE: sala occupata nell'orario indicato, riprovare");
						System.out.println("Premere un tasto per riprovare, 0 per uscire");
						String c = input.next();
						if(c.equals("0")) {
							return false;
						}
					}
				}
				else {
					System.out.println("ERRORE: sala non adatta alla proiezione del film scelto, riprovare");
				}
			}catch(Exception e) {
				System.out.println("ERRORE: il valore inserito deve essere tra quelli indicati nell'elenco, riprovare");
			}
		}while(!correctInput);
		
		//aggiunta della proiezione all'elenco delle proiezioni prenotabili
		proiezioni.add(p);
		Collections.sort(proiezioni);
		if(!storeProiezioni(args)) {
			proiezioni.remove(p);
			System.out.println("ERRORE: problema con il salvataggio della proiezione, effettuare nuovamente l'operazione");
			return false;
		}
		else {
			System.out.println("Proiezione aggiunta");
		}
		return correctInput;
	}

	/**
     * Ordina l'elenco delle proiezioni in ordine di id crescente
     * Restituisce il primo id non utilizzato
     *
     * @return int
     */
	protected int newID() {
		int id = 1;
		
		Collections.sort(proiezioni, new Comparator<Proiezione>(){
            public int compare(Proiezione p1, Proiezione p2){
				return p1.getId()-p2.getId();
          }});
		
		Iterator<Proiezione> it = proiezioni.iterator();
		Proiezione p;
		while(it.hasNext()) {
			p = it.next();
			if(p.getId()!=id) {
				return id;
			}
			else {
				id++;
			}
		}
		
		return id;
	}

	/**
     * Funzione per cambiare la sala di una proiezione già in programma
     *
     * @param args
     * @param input
     * 
     * @return boolean (true se l'operazione va a buon fine, false altrimenti)
     */
	public boolean cambiaSala(String[] args, Scanner input) {
		boolean correctInput = false;
		
		//selezione proiezione da modificare
		System.out.println("Quale proiezione si intende modificare?");
		if(!richiediProiezioni()) {
			//se non ci sono proiezioni in programma si esce dalla funzione
			return false;
		}
		
		Proiezione p = null;
		do {
			checkForInt(input);
			p = trovaProiezione(input.nextInt());
			if(p!=null) {
				if(p.getTotPostiPrenotati()==0)
					correctInput = true;
				else {
					System.out.println("ERRORE: vi sono delle prenotazioni per la proiezione inserita, riprovare");
				}
			}
			else {
				System.out.println("ERRORE: il valore inserito deve essere tra quelli indicati nell'elenco, riprovare");
			}
			
		}while(!correctInput);
		
		correctInput = false;
		Sala s = null;
		do {
			//scelta della nuova sala di proiezione
			System.out.println("Scegliere la nuova sala di proiezione");
			System.out.println("(inserire la stessa sala per procedere senza modificare lo stato attuale)");
			showSale();
			
			checkForInt(input);
			try {
				s = sale.get(input.nextInt()-1);
				
				//la sala deve essere compatibile con il film
				if(s.isTrid()==p.getFilm().isTrid()) {
					Sala old_s = p.getSala();
					p.setSala(s);
					//la sala deve essere libera nell'orario indicato dalla proiezione
					if(salaLibera(p)) {
						System.out.println("Sala aggiornata");
						correctInput = true;
						if(!storeProiezioni(args)) {
							System.out.println("ERRORE: problema con il salvataggio della proiezione, effettuare nuovamente l'operazione");
							return false;
						}
					}
					else {
						System.out.println("ERRORE: sala occupata nell'orario della proiezione");
						p.setSala(old_s);
					}
				}
				else {
					System.out.println("ERRORE: sala non adatta alla proiezione del film indicato dalla proiezione, riprovare");
				}
			}catch(Exception e) {
				System.out.println("ERRORE: il valore inserito deve essere tra quelli indicati nell'elenco, riprovare");
			}
		}while(!correctInput);
		return correctInput;
	}
	
	/**
     * Funzione per cambiare l'orario di una proiezione già in programma
     *
     * @param args
     * @param input
     * 
     * @return boolean
     */
	public boolean cambiaOrario(String[] args, Scanner input) {

		boolean correctInput = false;	//bit per verificare il corretto inserimento dei dati da parte dell'utente
		boolean ok = false;				//bit per verificare che l'operazione sia andata a buon fine
		
		//selezione proiezione da modificare
		System.out.println("Quale proiezione si intende modificare?");
		if(!richiediProiezioni()) {
			//se non ci sono proiezioni in programma si esce dalla funzione
			return false;
		}
		
		Proiezione p = null;
		do {
			checkForInt(input);
			p = trovaProiezione(input.nextInt());
			if(p!=null) {
				correctInput = true;
			}
			else {
				System.out.println("ERRORE: il valore inserito deve essere tra quelli indicati nell'elenco, riprovare");
			}
		}while(!correctInput);
		
		//inserimento nuovo orario di proiezione
		System.out.println("Inserire la nuova ora di inizio proiezione (formato HH:mm)");
		System.out.println("(inserire lo stesso orario per procedere senza modificare lo stato attuale)");
		
		LocalTime orain = null;
		correctInput = false;
		do {
			do {
				try{
					orain = LocalTime.parse(input.next()+":00",
			            DateTimeFormatter.ofPattern("HH:mm:ss"));
					correctInput = true;
				}catch (DateTimeParseException e) {
				    System.out.println("ERRORE: inserire l'ora nel formato indicato, riprovare");
				}
			}while(!correctInput);
			
			//salvataggio degli orari di inizio e fine precedenti la modifica
			LocalTime old_in = p.getInizio().toLocalTime();
			LocalDateTime old_fin = p.getFine();
			
			//modifica degli orari e controlli di coerenza
			p.setInizio(p.getInizio().toLocalDate().atTime(orain));
			p.setFine(p.calcolaFine());
			
			//la sala deve essere libera nel nuovo orario indicato
			if(salaLibera(p)) {
				if(!storeProiezioni(args)) {
					ok = true;
					System.out.println("ERRORE: problema con il salvataggio della priezione, eseguire nuovamente l'operazione");
					//se la modifica non va a buon fine si ripristinano i valori iniziali
					p.setInizio(p.getInizio().toLocalDate().atTime(old_in));
					p.setFine(old_fin);
					return false;
				}
				else {
					System.out.println("Ora aggiornata");
					ok = true;
				}
			}
			else {
				System.out.println("ERRORE: sala occupata nell'orario indicato, riprovare");
				//se la modifica non va a buon fine si ripristinano i valori iniziali
				p.setInizio(p.getInizio().toLocalDate().atTime(old_in));
				p.setFine(old_fin);
			}
		}while(!ok);
		return ok;
	}
	
	/**
     * Funzione per eliminare una prenotazione
     *
     * @param cl cliente che sta effettuando l'operazione
     * @param args
     * @param input
     * 
     * @return boolean
     */
	public boolean cancellaPrenotazione(Cliente cl, String[] args, Scanner input) {

		//scelta prenotazione da eliminare
		System.out.println("Quale prenotazione si intende cancellare?");
		if(!cl.visualizzaPrenotazioni()) {
			//se non vi sono prenotazioni registrate si esce
			return false;
		}
		
		Prenotazione pr = null;
		Proiezione p = null;
		boolean ok = false;
		int idPr;
		int idP;
		
		do {
			System.out.println("\nInserire id proiezione");
			checkForInt(input);
			idPr = input.nextInt();
			System.out.println("Inserire id prenotazione");
			checkForInt(input);
			idP = input.nextInt();
			
			try {
				pr = cl.getPrenotazione(idPr,idP);
				p = pr.getProiezione();
				
				//verifica limite di tempo per rimozione prenotazione
				if(LocalDateTime.now().isAfter(p.getInizio().minusHours(3))) {
					//caso in cui il limite di tempo è scaduto
					System.out.println("Le prenotazioni possono essere cancellate solo entro 3 ore dall'inizio della proiezione");
					ok = true;
					return false;
				}
				else {
					//caso in cui si è in tempo
					
					//i posti relativi alla prenotazione da eliminare vengono subito resi disponibili
					Iterator<Posto> posti = pr.getPosti().iterator();
					while(posti.hasNext()) {
						posti.next().setLibero(true);
					}
					
					//eliminazione prenotazione
					p.setTotPostiPrenotati(p.getTotPostiPrenotati()-pr.getPosti().size());
					p.getPrenotazioni().remove(pr);
					cl.getPr().remove(pr);
					if(!storePrenotazioni(args)) {
						System.out.println("ERRORE: problema con la rimozione della prenotazione, effettuare nuovamente l'operazione");
						p.aggiungiPrenotazione(pr);
						cl.getPr().add(pr);
						return false;
					}
					else {
						System.out.println("Prenotazione rimossa");
					}
					ok = true;
				}
			}catch(Exception e) {
				System.out.println("ERRORE: i valori devono essere quelli indicati nell'elenco, riprovare");
			}
		}while(!ok);
		return ok;
	}
	
	/**
     * Funzione per eliminare tutte le prenotazione di un cliente
     * 
     * @param cl cliente del quale si intendono cancellare le prenotazioni
     * @param args
     * @param input
     * 
     * @return boolean
     */
	private boolean cancellaPrenotazioni(Cliente cl) {
	
		if(cl.getPr().isEmpty()) {
			return false;
		}
		Iterator<Prenotazione> it = cl.getPr().iterator();
		Prenotazione pr = null;
		Proiezione p = null;

		while(it.hasNext()) {
			pr = it.next();
			p = pr.getProiezione();
			//i posti relativi alla prenotazione da eliminare vengono subito resi disponibili
			Iterator<Posto> posti = pr.getPosti().iterator();
			while(posti.hasNext()) {
				posti.next().setLibero(true);
			}
			
			//eliminazione prenotazione
			p.setTotPostiPrenotati(p.getTotPostiPrenotati()-pr.getPosti().size());
			p.getPrenotazioni().remove(pr);
			it.remove();
		}
		System.out.println("Prenotazioni rimosse");
		return true;
	}
	
	/**
     * Funzione che ritorna l'oggetto Film corrispondente al titolo selezionato (esso è univoco)
     * Funzione utilizzata in fase di lettura dei file di input
     *
     * @param titolo
     * 
     * @return boolean
     */
	protected Film cercaFilm(String titolo) {
		Iterator<Film> i = film.iterator();
		Film f;
		while(i.hasNext()) {
			f = i.next();
			if(f.getTitolo().equals(titolo)) {
				return f;
			}
		}
		return null;
	}
	
	/**
     * Funzione per permettere all'utente di prenotare dei posti per una proiezione
     *
     * @param cl cliente che effettua l'operazione
     * @param args
     * @param input
     * 
     * @return boolean
     */
	public boolean effettuaPrenotazione(Cliente cl, String[] args, Scanner input) {
		
		boolean ok = false;
		
		//scelta del film
		System.out.println("Quale film desidera guardare?");
		showFilm();
		
		Film f = null;
		do{
			checkForInt(input);
			try{
				f = film.get(input.nextInt()-1);
				ok = true;
			}catch(Exception e) {
				System.out.println("ERRORE: valore inserito non tra quelli indicati, riprovare");
			}
		}while(!ok);
		
		//scelta della proiezione
		ok = false;
		System.out.println("A quale proiezione si vuole assistere?");
		Proiezione p = null;
		
		if(showProiezioni(f)) {
			do {
				checkForInt(input);
				p = trovaProiezione(input.nextInt());
				if(p!=null){
					if(p.getTotPostiPrenotati()==p.getSala().getCapienza()) {
						System.out.println("Proiezione SOLD OUT");
						return false;
					}
					else {
						ok = true;
					}
				}else{
					System.out.println("ERRORE: valore inserito non tra quelli indicati, riprovare");
				}
			}while(!ok);
		}
		else {
			System.out.println("Nessuna proiezione per il film scelto");
			return false;
		}
		
		//inserimento numero di posti da prenotare
		ok = false;
		System.out.println("Quanti posti si vogliono prenotare?");
		int n = 0;
		
		do {
			checkForInt(input);
			n = input.nextInt();
			int liberi = p.getSala().getCapienza()-p.getTotPostiPrenotati();
			
			//controllo sul numero di posti prenotabili
			if(n<=liberi && n>0) {
				ok = true;
			}else {
				System.out.println("ERRORE: controllare di aver inserito un numero intero positivo\ne che sia inferiore (al limite uguale) ai posti liberi rimanenti, riprovare");
				System.out.println("Posti liberi rimanenti = "+liberi);
			}
		}while(!ok);
		
		//scelta del/dei posto/i da prenotare
		ok = false;
		int fila, numero = 0;
		List<Posto> ps = new ArrayList<Posto>();
		showPosti(p);
		System.out.println("Che posti si vogliono prenotare?");
		
		while(n>0) {
			ok = false;
			do {
				int i = 1;
				checkForInt(input);
				try {
					System.out.println("Inserire fila e numero del "+i+"° posto:");
					fila = input.nextInt();
					numero = input.nextInt();
					Posto x = p.getSala().getPosti()[fila-1][numero-1];
					//il posto inserito deve essere libero
					if(x.isLibero()) {
						if(!ps.contains(x)) {
							ps.add(p.getSala().getPosti()[fila-1][numero-1]);
							n--;
							i++;
							ok = true;
						}
						else {
							System.out.println("ERRORE: posto già inserito, riprovare");
						}
					}
					else {
						System.out.println("ERRORE: posto già occupato, riprovare");
					}
					
				}catch(Exception e) {
					System.out.println("ERRORE: fila e numero devono essere all'interno dei range specificati dalla griglia, riprovare");
				}
			}while(!ok);
		}
		ok = false;
		
		//creazione prenotazione
		Prenotazione pr = new Prenotazione(p.checkForId(), p, cl, ps, LocalDate.now());
		
		//richiesta di conferma della creazione appena creata
		System.out.println(pr);
		do {
			System.out.println("Confermare? s/n");
			switch(input.next()) {
			case "s":
				System.out.println("Prenotazione confermata");
				ok = true;
				//aggiunta prenotazione all'elenco delle prenotazioni eseguite dal cliente
				cl.getPr().add(pr);
				//aggiunta prenotazione all'elenco delle prenotazioni per la proiezione indicata
				p.aggiungiPrenotazione(pr);
				if(!storePrenotazioni(args)) {
					System.out.println("ERRORE: problema con il salvataggio della prenotazione, effettuare nuovamente l'operazione");
					p.getPrenotazioni().remove(pr);
					cl.getPr().remove(pr);
					return false;
				}
				break;
			case "n":
				ok = true;
				System.out.println("Prenotazione annullata");
				break;
			default:
				System.out.println("ERRORE: immettere s per confermare la prenotazione, n per annullarla");
				break;
			}
		}while(!ok);
		return ok;
	}
	
	/**
     * Funzione per leggere i dati dai file di testo e creare così le istanze appropriate
     *
     * @param args
     * 
     * @return boolean
     */
	protected boolean loadData(String[] args) {
		
		System.out.println("CARCAMENTO DATI IN CORSO...");
		boolean ok = true;
		//assegnamento gestore del cinema
		GestoreCinema gc = new GestoreCinema("Carlo Verdi","carlo","1");
		setGestore(gc);
		
		//creazione elenco clienti attualmente registrati
		File inputFile = new File(args[0]);
		Scanner scan = null;
		String line = null;
		String[] splitted = null;
		Cliente cl = null;
		try {
			scan = new Scanner(inputFile);
			while(scan.hasNextLine()) {
				
				line = scan.nextLine();
				splitted = line.split(" ");
				cl = new Cliente(splitted[0],splitted[1],splitted[2]);
				getClienti().add(cl);
				
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("file clienti non trovato");
			ok = false;
		}
		
		//creazione elenco film da mettere in programmazione
		inputFile = new File(args[1]);
		Film f = null;
		try {
			scan = new Scanner(inputFile);
			while(scan.hasNextLine()) {
				
				line = scan.nextLine();
				splitted = line.split(" ");
				f = new Film(String.join(" ", splitted[0].split("_")),Integer.parseInt(splitted[1]),
						splitted[2],Boolean.parseBoolean(splitted[3]));
				getFilm().add(f);
				System.out.println("film aggiunto");
				
			}
			scan.close();
		}catch (FileNotFoundException e) {
			System.out.println("file clienti non trovato");
			ok = false;
		}
		
		//creazione sale cinematografiche
		inputFile = new File(args[4]);
		Sala s = null;
		try {
			scan = new Scanner(inputFile);
			while(scan.hasNextLine()) {
				
				line = scan.nextLine();
				splitted = line.split(" ");
				s = new Sala(Integer.parseInt(splitted[0]),Integer.parseInt(splitted[1]),
						Integer.parseInt(splitted[2]),Boolean.parseBoolean(splitted[3]));
				getSale().add(s);
				System.out.println("sala aggiunta");
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("file clienti non trovato");
			ok = false;
		}
		
		//creazione elenco proiezioni attualmente in programmazione
		inputFile = new File(args[3]);
		Proiezione p = null;
		try {
			scan = new Scanner(inputFile);
			while(scan.hasNextLine()) {
				line = scan.nextLine();
				splitted = line.split(" ");
				f = cercaFilm(String.join(" ", splitted[1].split("_")));
				s = getSale().get(Integer.parseInt(splitted[2])-1);
				LocalTime orain = null;
				try{
					orain = LocalTime.parse(splitted[3]+":00",
			            DateTimeFormatter.ofPattern("HH:mm:ss"));
				}catch (DateTimeParseException exc) {
				    System.out.println("ERRORE: ora nel formato scorretto");
				    ok = false;
				}
				LocalDate data = null;
				try{
					data = LocalDate.parse(splitted[4],
			            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				}catch (DateTimeParseException exc) {
					System.out.println("ERRORE: data non inserita nel formato corretto");
					ok = false;
				}
				LocalDateTime inizio;
				if(data==null || orain==null) {
					System.out.println("ERRORE: possibile file corrotto, proiezione non aggiunta");
					ok = false;
				}
				else {
					inizio = LocalDateTime.of(data,orain);
					p = new Proiezione(Integer.parseInt(splitted[0]),f,s,inizio,Double.parseDouble(splitted[5]));
					getProiezioni().add(p);
					System.out.println("Proiezione aggiunta");
				}
			}
			scan.close();
		}catch (FileNotFoundException e) {
			System.out.println("file proiezioni non trovato");
			ok = false;
		}
		
		//loading delle prenotazioni attive
		inputFile = new File(args[2]);
		Prenotazione pr = null;
		try {
			scan = new Scanner(inputFile);
			while(scan.hasNextLine()) {
				line = scan.nextLine();
				splitted = line.split(" ");
				
				Cliente clp = trovaCliente(splitted[2]);
				Proiezione pp = trovaProiezione(Integer.parseInt(splitted[0]));
				if(pp!=null) {
					List<Posto> posti = new ArrayList<Posto>();
					String[] splitted_posti = null;
					for(int i = 4; i<=splitted.length-3;i++) {
						splitted_posti = splitted[i].split("-");
						int fila = Integer.parseInt(splitted_posti[0]);
						int numero = Integer.parseInt(splitted_posti[1]);
						posti.add(pp.getSala().getPosti()[fila-1][numero-1]);

					}
					
					LocalDate data = null;
					try{
						data = LocalDate.parse(splitted[splitted.length-2],
				            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					}catch (DateTimeParseException exc) {
						System.out.printf("data non inserita nel formato corretto");
						ok = false;
					}
					
					pr = new Prenotazione(Integer.parseInt(splitted[1]),
							pp, 
							clp, 
							posti,
							data);
					
					pp.aggiungiPrenotazione(pr);
					clp.getPr().add(pr);
					System.out.println("prenotazione aggiunta");
				}
				else {
					System.out.println("ERRORE: proiezione non trovata");
					ok = false;
				}
			}
			scan.close();
		}catch (FileNotFoundException e) {
			System.out.println("file proiezioni non trovato");
			ok = false;
		}
		
		if(ok)
			update(args);
		System.out.println("CARICAMENTO DATI TERMINATO.");
		return ok;
	}
	
	/**
     * Funzione per la registrazione di un nuovo cliente
     *
     * @param input
     * @param args
     * 
     * @return boolean
     */
	public boolean registraCliente(Scanner input, String[] args) {
		boolean correctInput = false;
		boolean result = true;
		//inserimento dati
		String id;
		do {
			System.out.println("Inserire username");
			id = input.next();
			if(trovaCliente(id)==null) {
				correctInput = true;
			}
			else {
				System.out.println("ERRORE: username inserito già utilizzato, inserirne un altro");
			}
		}while(!correctInput);
		
		System.out.println("Inserire password");
		String psw = input.next();
		System.out.println("Inserire indirizzo mail");
		String mail = input.next();
		
		//creazione del nuovo cliente e aggiunta all'elenco dei clienti registrati
		Cliente cl = new Cliente(id,psw,mail);
		clienti.add(cl);
		
		if(!storeClienti(args)) {
			System.out.println("ERRORE: problema con la registrazione, effettuare nuovamente l'operazione");
			result = false;
		}
		
		return result;
	}
	
	/**
     * Funzione per mostrare all'utente l'elenco delle proiezioni in programma
     * 
     * @return boolean
     */
	public boolean richiediProiezioni() {
		Iterator<Proiezione> i = proiezioni.iterator();
		Proiezione pr;
		if(i.hasNext()) {
			while(i.hasNext()) {
				pr = i.next();
				System.out.println(pr.toString());
				System.out.println();
			}
			return true;
		}
		else {
			System.out.println("Non ci sono proiezioni registrate");
			return false;
		}
		
	}

	/**
     * Funzione per eliminare un cliente dall'elenco dei clienti registrati
     *
     * @param cl cliente che si desidera rimuovere dall'elenco
     * @param args
     * 
     * @return boolean
     */
	public boolean rimuoviCliente(Cliente cl, String[] args) {
		boolean result = true;
		cancellaPrenotazioni(cl);
		if(clienti.remove(cl)) {
			if(!storeClienti(args)) {
				System.out.println("ERRORE: problema con l'aggiornamento dei clienti registrati, effettuare nuovamente l'operazione");
				clienti.add(cl);
				result = false;
			}
			if(!storePrenotazioni(args)) {
				System.out.println("ERRORE: problema con l'aggiornamento delle prenotazioni, effettuare nuovamente l'operazione");
				clienti.add(cl);
				result = false;
			}
			
			else {
				System.out.println("Grazie di essere stati con noi");
			}
		}else
		{
			System.out.println("ERRORE: problema con la cancellazione dell'account, effettuare nuovamente l'operazione");
			result = false;
		}
		return result;
	}
	
	/**
     * Funzione per eliminare una proiezione in programma
     *
     * @param args
     * @param input
     * 
     * @return boolean
     */
	public boolean rimuoviProiezione(String[] args, Scanner input) {

		boolean ok = false;
		
		do {
			//scelta proiezione da eliminare
			System.out.println("Quale proiezione si intende eliminare?");
			Iterator<Proiezione> it = proiezioni.iterator();
			while(it.hasNext()) {
				System.out.println();
				System.out.println(it.next().toString());
			}
			
			checkForInt(input);
			try {
				Proiezione p = trovaProiezione(input.nextInt());
				if(p.getTotPostiPrenotati()==0) {
					proiezioni.remove(p);
					System.out.println("Proiezione eliminata");
					if(!storeProiezioni(args)) {
						System.out.println("ERRORE: problema con la rimozione della proiezione, effettuare nuovamente l'operazione");
						proiezioni.add(p);
						return false;
					}
					ok = true;
				}
				else {
					System.out.println("ERRORE: vi sono delle prenotazioni per la proiezione selezionata, impossibile eliminare");
					System.out.println("Premere un tasto per riprovare, 0 per uscire");
					String s = input.next();
					if(s.equals("0")) {
						return false;
					}
				}
			}catch(Exception e) {
				System.out.println("ERRORE: il valore inserito deve essere tra quelli indicati nell'elenco, riprovare");
			}
		}while(!ok);
		return ok;
	}

	/**
     * Funzione che restituisce il cliente con un dato nome (assicurarsi che sia univoco)
     *
     * @param nome user-id del cliente da cercare
     * 
     * @return Cliente
     */
	public Cliente trovaCliente(String nome) {
		Iterator<Cliente> i = clienti.iterator();
		Cliente cl;
		while(i.hasNext()) {
			cl = i.next();
			if(cl.getNome().equals(nome)) {
				return cl;
			}
		}
		return null;
	}
	
	/**
     * Funzione che restituisce la proiezione con un dato id
     *
     * @param id della proiezione da cercare
     * 
     * @return Proiezione
     */
	public Proiezione trovaProiezione(int id) {
		Iterator<Proiezione> i = proiezioni.iterator();
		Proiezione p;
		while(i.hasNext()) {
			p = i.next();
			if(p.getId()==id) {
				return p;
			}
		}
		return null;
	}
	
	/**
     * Funzione per controllare che l'utente inserisca un numero intero
     *
     * @param input
     * 
     */
	protected void checkForInt(Scanner input) {

		while(!input.hasNextInt()) {
			System.out.println("ERRORE: il valore inserito deve essere un numero intero, riprovare");
			input.next();
		}
	}
	
	/**
     * Funzione per controllare la disponibilità di una sala
     * si controlla che non vi siano altre proiezioni i cui orari si sovrappongano a quelli
     * della proiezione p passata come parametro
     * restituisce false se ve ne sono, true altrimenti
     *
     * @param p proiezione da controllare
     * 
     * @return boolean
     */
	private boolean salaLibera(Proiezione p) {
		
		Iterator<Proiezione> i = proiezioni.iterator();
		Proiezione next;
		while(i.hasNext()){
			next = i.next();
			if(!next.equals(p) && next.getSala().getId()==p.getSala().getId() && next.getInizio().toLocalDate().equals(p.getInizio().toLocalDate())) {
				if((!p.getInizio().isAfter(next.getInizio())&&!p.getFine().isBefore(next.getInizio()))
						||(!p.getInizio().isAfter(next.getFine())&&!p.getFine().isBefore(next.getFine()))) {
					return false;
				}
				
			}
		}
		return true;
	}
	
	/**
     * Funzione per mostrare all'utente l'elenco dei film disponibili
     * 
     * @return
     */
	private void showFilm() {
		int i = 1;
		Iterator<Film> fl = film.iterator();
		while(fl.hasNext()) {
			System.out.println(i+" - "+fl.next());
			i++;
		}
	}
	
	/**
	 * Funzione per mostrare all'utente lo stato dei posti per una data proiezione
     * 
     *
     * @param p proiezione di cui si vuole mostrare la disponibilità di posti
     * 
     * @return
     */
	private void showPosti(Proiezione p) {
		
		Posto[][] ps = p.getSala().getPosti();
		//legenda
		System.out.println("Righe = file (F), Colonne = numeri (N)");
		System.out.println("O = posto libero, $ = posto migliore libero, # = posto occupato");
		
		//stampa angolo in alto a sinistra della matrice
		System.out.print("F"+"\\"+"N"+"\t");
		
		//stampa prima riga, indica il numero del posto
		for(int k = 0; k<p.getSala().getCapienza()/p.getSala().getNumeroFile(); k++) {
			System.out.print(" "+(k+1));
		}
		
		//stampa delle righe successive
		for(int i = 0; i<p.getSala().getNumeroFile();i++) {
			//numero di fila
			System.out.print("\n"+ (i+1) +"\t");
			//stampa di un simbolo per ogni posto della fila
			for(int j = 0; j<p.getSala().getCapienza()/p.getSala().getNumeroFile(); j++) {
				if(ps[i][j].isLibero()) {
					if(ps[i][j].getCostoAggiuntivo()==0)
						System.out.print(" O");
					else
						System.out.print(" $");
				}
				else {
					System.out.print(" #");
				}
			}
		}
		System.out.print("\n");
	}
	
	/**
     * Funzione per mostrare all'utente le proiezioni di un dato film
     *
     * @param f
     * 
     * @return boolean
     */
	private boolean showProiezioni(Film f) {
		
		boolean b = false;
		Iterator<Proiezione> i = this.proiezioni.iterator();
		Proiezione pr;
		
		while(i.hasNext()) {
			pr = i.next();
			if(pr.getFilm().equals(f)) {
				System.out.println(pr.toString());
				b = true;
			}
		}
		
		return b;
	}
	
	/**
     * Funzione per mostrare all'utente l'elenco delle sale disponibili
     *
     * @return
     */
	private void showSale() {
		Iterator<Sala> sl = sale.iterator();
		Sala next;
		String l;
		while(sl.hasNext()) {
			next = sl.next();
			if(next.isTrid()) {
				l = " (sala 3D)";
			}
			else {
				l = "";
			}
			System.out.println(next.getId()+l);
		}
	}
	
	/**
     * Funzione per salvare lo stato dei clienti registrati
     *
     * @param args
     * 
     * @return boolean
     */
	private boolean storeClienti(String[] args){
		
		boolean result = false;
		File f;
		FileWriter fw;
		BufferedWriter wr;
		String s;
		f = new File(args[0]);
		
		if(f.exists()) {
			//scrittura lista clienti
			try {
				fw = new FileWriter(f,false);
				wr = new BufferedWriter(fw);
				Iterator<Cliente> i = clienti.iterator();
				while(i.hasNext()) {
					s = i.next().toString();
					wr.write(s);
					wr.newLine();
				}
				wr.close();
				result = true;
				
			} catch (IOException e) {
				System.out.println("ERRORE: problema nella scrittura del file clienti.txt");
			}
		}
		else {
			System.out.println("ERRORE: file clienti.txt non trovato");
		}
		
		return result;
	}
	
	/**
     * Funzione per salvare lo stato delle prenotazioni effettuate
     *
     * @param args
     * 
     * @return boolean
     */
	private boolean storePrenotazioni(String[] args){
		
		boolean result = false;
		File f;
		FileWriter fw;
		BufferedWriter wr;
		String s;
		
		//scrittura lista clienti
		f = new File(args[2]);
		
		if(f.exists()) {
			try {
				fw = new FileWriter(f,false);
				wr = new BufferedWriter(fw);
				Iterator<Proiezione> i = proiezioni.iterator();
				Proiezione p = null;
				while(i.hasNext()) {
					p = i.next();
					Collections.sort(p.getPrenotazioni());
					Iterator<Prenotazione> j = p.getPrenotazioni().iterator();
					Prenotazione pr = null;
					while(j.hasNext()) {
						pr = j.next();
						s = pr.print();
						wr.write(s);
						wr.newLine();
					}
				}
				wr.close();
				result = true;
			}catch(IOException e) {
				System.out.println("ERRORE: problema nella scrittura del file prenotazioni.txt");
			}
		}
		else {
			System.out.println("ERRORE: file prenotazioni.txt non trovato");
		}
		return result;
	}
	
	/**
     * Funzione per salvare lo stato delle proiezioni in programma
     *
     * @param args
     * @param input
     * 
     * @return boolean
     */
	private boolean storeProiezioni(String[] args){
		boolean result = false;
		File f;
		FileWriter fw;
		BufferedWriter wr;
		String s;
		
		//scrittura lista clienti
		f = new File(args[3]);
		if(f.exists()) {
			try {
				fw = new FileWriter(f,false);
				wr = new BufferedWriter(fw);
				Collections.sort(proiezioni);
				Iterator<Proiezione> i = proiezioni.iterator();
				Proiezione p = null;
				while(i.hasNext()) {
					p = i.next();
					s = p.print();
					wr.write(s);
					wr.newLine();
				}
				wr.close();
				result = true;
			} catch (IOException e) {
				System.out.println("ERRORE: problema nella scrittura del file proiezioni.txt");
			}
		}
		else {
			System.out.println("ERRORE: file prenotazioni.txt non trovato");
		}
		return result;
	}

	/**
     * Funzione per aggiornare lo stato del sistema quando viene lanciato il programma
     * in automatico vengono eliminate le proiezioni la cui data è precedente quella odierna
     * questa funzione viene chiamata dopo la lettura dei file di input
     *
     * @param args
     * 
     */
	protected void update(String[] args){
		
		storeClienti(args);
		storePrenotazioni(args);
		storeProiezioni(args);
	}
	
	//Getters and Setters
	
	public List<Cliente> getClienti() {
		return clienti;
	}
	public List<Film> getFilm() {
		return film;
	}
	public GestoreCinema getGestore() {
		return gestore;
	}
	public String getNome() {
		return nome;
	}
	public List<Proiezione> getProiezioni() {
		return proiezioni;
	}
	public List<Sala> getSale() {
		return sale;
	}
	public void setClienti(List<Cliente> clienti) {
		this.clienti = clienti;
	}
	public void setFilm(List<Film> film) {
		this.film = film;
	}
	public void setGestore(GestoreCinema gestore) {
		this.gestore = gestore;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setProiezioni(List<Proiezione> proiezioni) {
		this.proiezioni = proiezioni;
	}
	public void setSale(List<Sala> sale) {
		this.sale = sale;
	}
}
