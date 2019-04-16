import java.io.ByteArrayInputStream;
import java.io.InputStream;

//import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author Gaione Stefano
 * 
 * Test usati per eseguire test strutturali sulla classe Main
 *
 */

public class TestMain {
	
	public static String alreadyReg  = "s" + System.getProperty("line.separator");
	public static String notReg = "n" + System.getProperty("line.separator");
	public static String logGestore = "carlo 1" + System.getProperty("line.separator");
	public static String notInt = "string" + System.getProperty("line.separator");
	public static String choice0 = "0" + System.getProperty("line.separator");
	public static String choice1 = "1" + System.getProperty("line.separator");
	
	@SuppressWarnings("static-access")
	@Test
	//viene testato il corretto funzionamento dell'interfaccia per input semplici
	public void testMain() {
		//setting variabili
		String[] args = {"input/test_clienti.txt","input/test_film.txt","input/test_prenotazioni.txt","input/test_proiezioni.txt","input/test_sale.txt"};
		
		String simulatedUserInput = alreadyReg + logGestore + choice0 + alreadyReg + logGestore + choice1;
		InputStream savedStandardInputStream = System.in;
		System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
	    
		//test
		Main m = new Main();
		m.main(args);
		
		System.setIn(savedStandardInputStream);
	}
	
	@SuppressWarnings("static-access")
	@Test
	//viene simulato il corretto funzionamento del main quando i file di input non esistono (diversa directory)
	public void testMainNoInput() {
		String[] args = {"clienti.txt","film.txt","prenotazioni.txt","proiezioni.txt","sale.txt"};
		
		String simulatedUserInput = alreadyReg + logGestore + choice0 + alreadyReg + logGestore + choice1;
		InputStream savedStandardInputStream = System.in;
		System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
	  
		Main m = new Main();
		m.main(args);
		
		System.setIn(savedStandardInputStream);
	}

}
