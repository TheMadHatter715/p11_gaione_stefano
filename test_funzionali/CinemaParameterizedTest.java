import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * 
 * @author Gaione Stefano
 * 
 * Classe per i test parametrizzati
 * 
 * Viene usato questo insieme di test per effettuare principalmente i test funzionali, oltre che
 * per una serie di test funzionali principalmente legati alla correttezza degli input inseriti.
 * 
 * Ogni input è una simulazione della sequenza di comandi inseriti dall'utente per accedere ad una
 * data funzionalità.
 * 
 * Variando la sequenza di input è stato possibile inoltre considerare i casi in cui gli input
 * forniti dall'utente non siano corretti (errori di battitura, nome utente non disponibile e simili...).
 * Questo ha portato a far si che il coverage fosse già abbastanza elevato utilizzando questi test.
 * 
 * Per raggiungere il 100% del coverage del codice sorgente vengono effettuati i test delle altre
 * classi della TestSuite.
 * 
 */

@RunWith(Parameterized.class)
public class CinemaParameterizedTest {
	
	private static Cinema c = null;
	
	//file di input da usare nella fase di test
	private static String[] args = {"input/test_clienti.txt","input/test_film.txt","input/test_prenotazioni.txt","input/test_proiezioni.txt","input/test_sale.txt"};
	
	//parametri dei test
	@Parameter(0) public String simulatedUserInput; //simulazione della sequenza di input effettuata dall'utente
	@Parameter(1) public String expected;			//valore di ritorno atteso della funzione
	
	//insieme di input ricorrenti
	public static String choiceS  = "s" + System.getProperty("line.separator");
	public static String choiceN = "n" + System.getProperty("line.separator");
	public static String logGestore = "carlo 1" + System.getProperty("line.separator");
	public static String logGestoreErrato = "carlo 11" + System.getProperty("line.separator");
	public static String logCliente = "ste 3" + System.getProperty("line.separator");
	public static String logClienteErrato = "ste 34" + System.getProperty("line.separator");
	public static String idUsato = "ste" + System.getProperty("line.separator");
	public static String logNuovoCliente = "[] 4" + System.getProperty("line.separator");
	public static String nuovoCliente = "[] 4 mario@hotmail.com" + System.getProperty("line.separator");
	public static String notValidString = "%£é" + System.getProperty("line.separator");
	public static String choice0 = "0" + System.getProperty("line.separator");
	public static String choice1 = "1" + System.getProperty("line.separator");
	public static String choice2 = "2" + System.getProperty("line.separator");
	public static String choice3 = "3" + System.getProperty("line.separator");
	public static String choice4 = "4" + System.getProperty("line.separator");
	public static String choice5 = "5" + System.getProperty("line.separator");
	public static String choice6 = "6" + System.getProperty("line.separator");
	public static String choiceErrata = "999" + System.getProperty("line.separator");
	
	//insieme di comandi che permettono l'uscita dall'interfaccia grafica
	public static String exit = choiceS + logGestore + choice1;

	@Parameters
	public static Collection<String[]> data(){
		return Arrays.asList(new String[][]{
				//0 - GESTORE ESEGUE LOGIN ED ESCE (+ ERRORE NELL'IMMETTERE I DATI)
				{choiceS + logGestoreErrato + choice1 + 
				logGestore + choice0, "true"},
				//1 - GESTORE ESEGUE LOGIN E SPEGNE IL SISTEMA (+ CREDENZIALI INIZIALI NON VALIDE)
				{notValidString + exit, "false"},
				//2 - GESTORE ESEGUE LOGIN E SPEGNE IL SISTEMA (+ INPUT ERRATO -> STRINGA E NON INTERO)
				{choiceS + logGestore + notValidString + choice1, "false"},
				//3 - GESTORE ESEGUE IL LOGIN E VISIONA LE PROIEZIONI PRESENTI (+ ERRATA SELEZIONE OPERAZIONE)
				{choiceS + logGestore + choiceErrata + choice2 + choice1, "false"},
				//4 - GESTORE ESEGUE IL LOGIN E CREA PROIEZIONE #1 (SI TESTANO ANCHE ALCUNI INPUT ERRATI)
				{choiceS + logGestore + choice3 + //scelta opzione di aggiunta proiezione
				choiceErrata + //inserito film errato
				choice1 + //inserimento film corretto
				"2019/02/24" + System.getProperty("line.separator") + //formato data scorretto
				"2019-02-24" + System.getProperty("line.separator") + //data già trascorsa
				"2019-05-25" + System.getProperty("line.separator") + //data corretta
				"21" + System.getProperty("line.separator") + //formato ora scorretto
				"21:00" + System.getProperty("line.separator") + //ora corretta
				notValidString + //inserimento prezzo non valido, stringa invece di numero
				"-1" + System.getProperty("line.separator") + //prezzo errato (negativo)
				"10" + System.getProperty("line.separator") + //prezzo corretto
				choiceErrata + //sala non presente
				choice2 + //sala non valida
				"ok" + System.getProperty("line.separator") + //comando per proseguire
				choice1 + //sala occupata
				"ok" + System.getProperty("line.separator") + //comando per proseguire
				choice3 + //sala corretta
				choice1, "false"},//termine
				//5 - GESTORE ESEGUE IL LOGIN E CREA PROIEZIONE #2 (DIVERSA DALLA PRECEDENTE, setup per test successivo)
				{choiceS + logGestore + choice3 + //scelta opzione di aggiunta proiezione
				choiceErrata + choice1 + //compilazione proiezione
				"2019-05-24" + System.getProperty("line.separator") + //data corretta
				"18:00" + System.getProperty("line.separator") + //ora corretta
				"10" + System.getProperty("line.separator") + //prezzo corretto
				choice3 + //sala corretta
				choice1, "false"}, //termine
				//6 - GESTORE ESEGUE IL LOGIN E CREA PROIEZIONE #3 (DIVERSA DALLA PRECEDENTE + OPERAZIONE ANNULLATA)
				{choiceS + logGestore + choice3 + //scelta opzione di aggiunta proiezione
				choiceErrata + choice1 + //compilazione proiezione
				"2019-05-24" + System.getProperty("line.separator") + //data corretta
				"18:00" + System.getProperty("line.separator") + //ora corretta
				"10" + System.getProperty("line.separator") + //prezzo corretto
				choice3 + //sala occupata
				"0" + System.getProperty("line.separator") + //comando per annullare
				choice1, "false"}, //termine
				//7 - UTENTE NON REGISTRATO DECIDE DI NON REGISTRARSI (+ INPUT ERRATO)
				{choiceN + notValidString + choiceN + exit, "false"},
				//8 - REGISTRAZIONE NUOVO CLIENTE (+ ID GIA' USATO)
				{choiceN + choiceS + idUsato + nuovoCliente + exit, "false"},
				//9 - IL NUOVO CLIENTE SI REGISTRA E VISIONA LE SUE PRENOTAZIONI (ELENCO VUOTO)
				{choiceS + logNuovoCliente + choice2 + choice0, "true"},
				//10 - IL NUOVO CLIENTE ESEGUE IL LOGIN E VISIONA LE PROIEZIONI IN PROGRAMMA (+ ERRATA SELEZIONE OPERAZIONE)
				{choiceS + logNuovoCliente + choiceErrata + choice4 + choice0, "true"},
				//11 - IL NUOVO CLIENTE EFFETTUA UNA PRENOTAZIONE (NON RIESCE PERCHè NON VI SONO PROIEZIONI PER IL FILM SCELTO)
				{choiceS + logNuovoCliente + choice1 + //scelta operazione
				choice4 + //film senza proiezioni
				choice0, "true"},//termine
				//12 - IL NUOVO CLIENTE EFFETTUA UNA PRENOTAZIONE (NON RIESCE PERCHè LA PROIEZIONE SCELTA è SOLD OUT)
				{choiceS + logNuovoCliente + choice1 + //scelta operazione
				choice1 + //film corretto
				choice2 + //proiezione sold out
				choice0, "true"},//termine
				//13 - IL NUOVO CLIENTE EFFETTUA UNA PRENOTAZIONE (SI TESTANO ANCHE ALCUNI INPUT ERRATI)
				{choiceS + logNuovoCliente + choice1 + //scelta operazione
				choiceErrata + //numero film non presente
				choice5 + //film senza proiezioni
				choice1 + //film corretto
				choiceErrata + //numero proiezione non presente
				choice1 + //proiezione corretta
				choiceErrata + //input numero posti da prenotare errato (troppo grande)
				"-1" + System.getProperty("line.separator") + //input numero posti da prenotare errato (negativo)
				choice2 + //numero corretto posti (2)
				"100 100" + System.getProperty("line.separator") + //posto inserito errato
				"1 1" + System.getProperty("line.separator") + //prenotazione posto corretto
				"1 1" + System.getProperty("line.separator") + //posto già inseriti
				"1 2" + System.getProperty("line.separator") + choiceS + choice0, "true"},//prenotazione posto corretto
				//14 - IL NUOVO CLIENTE EFFETTUA UNA PRENOTAZIONE (DIVERSA DALLA PRECEDENTE, setup per test successivo)
				{choiceS + logNuovoCliente + choice1 + //scelta operazione
				choice1 + choice1 + choice2 + //compilazione prenotazione
				"1 1" + System.getProperty("line.separator") + //posto occupato
				"2 1" + System.getProperty("line.separator") + //posto libero
				"2 2" + System.getProperty("line.separator") + //posto libero
				choiceS + //conferma
				choice0, "true"},
				//15 - IL NUOVO CLIENTE EFFETTUA UNA PRENOTAZIONE MA LA ANNULLA
				{choiceS + logNuovoCliente + choice1 + //scelta operazione
				choice1 + choice1 + choice3 + //compilazione prenotazione
				"3 1" + System.getProperty("line.separator") + //posto occupato
				"3 2" + System.getProperty("line.separator") + //posto libero
				"3 3" + System.getProperty("line.separator") + //posto libero
				notValidString + //input errato (diverso da s e n)
				choiceN + //conferma
				choice0, "true"},
				//16 - cliente cancella una prenotazione
				{choiceS + logNuovoCliente + choice3 +	//scelta operazione
				"100 100" + System.getProperty("line.separator") + //parametri errati
				"1 1" + System.getProperty("line.separator") + choice0, "true"}, //parametri corretti
				//17 - IL NUOVO UTENTE CANCELLA LA SUA REGISTRAZIONE (+ INPUT ERRATO)
				{choiceS + logNuovoCliente + notValidString + choice5, "true"},
				//18 - TENTATIVO DI LOGIN NON VALIDO
				{choiceS + logNuovoCliente + choice1 + logGestore + choice1, "false"},
				//19 - CLIENTE EFFETTUA LOGIN (test su errore di input e annullamento operazione)
				{choiceS + logClienteErrato + choice0, "true"},
				//20 - GESTORE ESEGUE IL LOGIN E RIMUOVE PROIEZIONE #1 (+ INPUT ERRATI)
				{choiceS + logGestore + choice4 + choice2 + choice1 + choiceErrata + choice1 + choice1, "false"},
				//21 - GESTORE ESEGUE IL LOGIN E RIMUOVE UNA PROIEZIONE (PROIEZIONE NON ELIMINABILE + USCITA ANTICIPATA)
				{choiceS + logGestore + choice4 + choice2 + choice0 + choice1, "false"},
				//22 - GESTORE ESEGUE IL LOGIN E CAMBIA ORARIO AD UNA PROIEZIONE
				{choiceS + logGestore + choice5 + //scelta operazione
				choiceErrata + //numero di proiezione errato
				choice4 + //proiezione da modificare
				"21" + System.getProperty("line.separator") + //formato ora scorretto
				"22:00" + System.getProperty("line.separator") + //sala occupata in questo orario
				"18:00" + System.getProperty("line.separator") + //sala libera in questo orario
				choice1, "false"},
				//23 - GESTORE ESEGUE IL LOGIN E CAMBIA LA SALA DI PROIEZIONE
				{choiceS + logGestore + choice6 + //scelta operazione
				choiceErrata + //numero di proiezione errato
				choice2 + //proiezione con prenotazioni
				choice4 + //proiezione da modificare
				choice2 + //sala non compatibile
				choiceErrata + //sala inesistente
				choice4 + //sala occupata
				choice1 + //sala libera
				choice1, "false"},
				//24 - GESTORE ESEGUE IL LOGIN E RIMUOVE PROIEZIONE #2	
				{choiceS + logGestore + choice4 + choice4 + choice1 + choice1, "false"}
		});
	}
	
	@BeforeClass
	public static void setUp(){
		//SETUP - vengono caricati i dati dai file di input specificati nell'array 'args'
		c = new Cinema();
		c.loadData(args);
		
	}
	
	@Test
	public void testUserInterface() {
		//setting variabili
		InputStream savedStandardInputStream = System.in;
		System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
		Scanner sc = new Scanner(System.in);
		assertNotNull("Oggetto Cinema non istanziato",c);
		
		//test e verifica
		assertEquals(c.userInterface(sc, args),Boolean.parseBoolean(expected));
		
		System.setIn(savedStandardInputStream);
	}
}
