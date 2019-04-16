

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author Gaione Stefano
 * 
 * Classe per effettuare singoli test strutturali non parametrizzati volti a
 * migliorare il coverage del codice.
 *
 */

public class CinemaSingleTests {
	
	//file di input regolari
	private static String[] args = {"input/test_clienti.txt","input/test_film.txt","input/test_prenotazioni.txt","input/test_proiezioni.txt","input/test_sale.txt"};
	//file di input con errori
	private static String[] argsBad = {"input/test_clienti.txt","input/test_film.txt","input/test_prenotazioni_badInput.txt","input/test_proiezioni_badInput.txt","input/test_sale.txt"};
	//file di input non presenti
	private static String[] argsNotValid = {"1.txt","2.txt","3.txt","4.txt","5.txt"};
	//file di input non specificati correttamente (directory), utili per generare degli IOException nei test che lo richiedono
	private static String[] argsDirectory = {"C:/","C:/","C:/","C:/","C:/"};
	
	//istanza oggetto Cinema
	private Cinema c = new Cinema();
	
	@Test
	//l'utente cerca un film il cui titolo non è tra quelli in programmazione
	public void testCercaFilm() {
		//setting variabili
		String f = "Nemo";
		//risultato atteso
		Film exp = null;
		//test
		Film found = c.cercaFilm(f);
		//check del risultato
		assertEquals("Film non trovato", found, exp);
	}
	
	@Test
	//viene chiamata la funzione di caricamento dei dati
	//i file in input non esistono ed il sistema dovrà mostrare i messaggi d'errore relativi
	public void testArgsNotValid() {
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.loadData(argsNotValid);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//il cliente cancella la propria registrazione
	//caso particolare in cui il cliente non ha prenotazioni a suo nome
	public void cancellaPrenotazioniNoInput(){
		//set variabili
		Cliente cl = new Cliente("mario","40","mail");
		c.getClienti().add(cl);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.rimuoviCliente(cl,argsDirectory);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//il cliente vuole cancellare una prenotazione
	//caso particolare in cui il cliente non ha prenotazioni a suo nome
	public void cancellaPrenotazioneNoInput(){
		//set variabili
		InputStream savedStandardInputStream = System.in;
		String text = "";
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.cancellaPrenotazione(new Cliente(), args, input);
		
		System.setIn(savedStandardInputStream);
		
		//check del risultato
		assertEquals("",r,exp);
		
	}
	
	@Test
	//la funzione che controlla se un input è un intero viene valutata
	//ponendole in ingresso prima una stringa e poi un intero
	public void testCheckForInt() {
		InputStream savedStandardInputStream = System.in;
		String text = "string 1";
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		c.checkForInt(input);
		
		System.setIn(savedStandardInputStream);
	}
	
	@Test
	//test della funzione cambiaSala() quando i file in input non sono presenti
	public void testCambiaSalaNoData() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "";
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.cambiaSala(argsNotValid, input);
		
		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}

	@Test
	//test della funzione cambiaOrario() quando i file in input non sono presenti
	public void testCambiaOrarioNoData() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "";
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.cambiaOrario(argsNotValid, input);
		
		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//test della funzione newID() quando l'elenco delle proiezioni è vuoto
	//viene simulato non caricando nessun dato dai file
	public void testNewIDNoData() {
		//test
		int i = c.newID();
		//check del risultato
		assertEquals("",i,1);
		
	}
	
	@Test
	//il cliente vuole cancellare la registrazione al portale ma i file di input non esistono
	//viene simulato un errore nel salvataggio dei dati
	public void testRimuoviClienteNoData() {
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.rimuoviCliente(new Cliente(), argsNotValid);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene testato il corretto funzionamento della funzione checkForID()
	public void testCheckForID() {
		//setting variabili
		Film f = new Film("titolo", 0, "genere", false);
		Sala s = new Sala(10, 10, 100, false);
		Proiezione p = new Proiezione(1, f, s, LocalDateTime.now(), 10);
		List<Posto> l = new ArrayList<Posto>();
		l.add(s.getPosti()[1][1]);
		Prenotazione pr = new Prenotazione(2, p, new Cliente("ste", "3", "@mail"), l, LocalDate.now());
		p.aggiungiPrenotazione(pr);
		
		//risultato atteso
		int exp = 1;
		//test
		int r = p.checkForId();
		//check del risultato
		assertEquals("",r,exp);
		
	}
	
	@Test
	//viene provocata una IOException nella funzione registraCliente()
	public void testIOExRegistraClienti() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "[] 4 mario@hotmail.com";
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		c.loadData(args);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.registraCliente(input, argsDirectory);

		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene provocata una IOException nella funzione loadData()
	public void testIOExLoadData() {
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.loadData(argsDirectory);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene provocata una IOException nella funzione aggiungiProiezione()
	public void testIOExAggiungiProiezione() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "1 2019-04-26 21:00 10 1";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		c.loadData(args);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.aggiungiProiezione(argsDirectory, input);
		
		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene provocata una IOException nella funzione cambiaOrario()
	public void testIOExCambiaOrario() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "11 05:00";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		c.loadData(args);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.cambiaOrario(argsDirectory, input);
		
		System.setIn(savedStandardInputStream);
		
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene provocata una IOException nella funzione cambiaSala()
	public void testIOExCambiaSala() {
		InputStream savedStandardInputStream = System.in;
		String text = "11 4";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		c.loadData(args);
		
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.cambiaSala(argsDirectory, input);
		
		System.setIn(savedStandardInputStream);
		
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene provocata una IOException nella funzione effettuaPrenotazione()
	public void testIOExEffettuaPrenotazione() {
		//set variabili
		InputStream savedStandardInputStream = System.in;
		String text = "3 11 1 1 1 s";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		Cliente cl = new Cliente("ste","3","mail");
		c.loadData(args);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.effettuaPrenotazione(cl, argsDirectory, input);
		
		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene provocata una IOException nella funzione rimuoviProiezione()
	public void testIOExRimuoviProiezione() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "11 1 1 1 s";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		c.loadData(args);
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.rimuoviProiezione(argsDirectory, input);
		
		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene provocata una IOException nella funzione cancellaPrenotazione()
	public void testIOEXCancellaPrenotazione() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "1 3";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		c.loadData(args);
		Cliente cl = c.getClienti().get(1);
		System.out.print(cl.getPr().size());
		
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.cancellaPrenotazione(cl, argsDirectory, input);
		
		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//viene testata la funzione loadData() quando i file in ingresso non hanno i dati nel formato corretto
	public void testLoadDataParseEx() {
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.loadData(argsBad);
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//il cliente vuole cancellare una prenotazione ma mancano meno di 3 ore all'inizio della proiezione
	public void testCancellaPrenotazioneTroppoTardi() {
		//setting variabili
		c.loadData(args);
		Film f = c.cercaFilm("Captain Marvel");
		Sala s = c.getSale().get(0);
		Proiezione p = new Proiezione(1000, f, s, LocalDateTime.now().plusHours(2), 10);
		c.getProiezioni().add(p);
		
		Cliente cl = c.getClienti().get(0);
		List<Posto> posti = new ArrayList<Posto>();
		posti.add(p.getSala().getPosti()[0][0]);
		Prenotazione pr = new Prenotazione(1, p, cl, posti, LocalDate.now().minusDays(2));
		
		cl.getPr().add(pr);
		p.aggiungiPrenotazione(pr);
		
		InputStream savedStandardInputStream = System.in;
		String text = "1 1000";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.cancellaPrenotazione(cl, args, input);
		
		System.setIn(savedStandardInputStream);
		
		//check del risultato
		assertEquals("",r,exp);
	}
	
	@Test
	//si testano le funzioni storeClienti, storePrenotazioni e storeProiezioni
	//quando i file di input non esistono
	public void testUpdateFileNotExists() {
		c.update(argsNotValid);
	}
	
	@Test
	//test di errore durante la registrazione di un cliente dovuto a file di input non validi
	public void testRegistrazioneFallita() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		String text = "n s nome password mail s carlo 1 1";
		
		System.setIn(new ByteArrayInputStream(text.getBytes()));
		Scanner input = new Scanner(System.in);
		
		c.loadData(args);
		
		//risultato atteso
		boolean exp = false;
		//test
		boolean r = c.userInterface(input, argsDirectory);
		
		System.setIn(savedStandardInputStream);
		//check del risultato
		assertEquals("",r,exp);
	}
}
