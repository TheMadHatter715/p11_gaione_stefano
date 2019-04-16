import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//inizializzazione sistema
		Scanner input = new Scanner(System.in);
		Cinema c = new Cinema();
		
		//caricamento dei dati dai file
		if(c.loadData(args)) {
			//se va a buon fine viene chiamata la "GUI"
			while(c.userInterface(input,args));
		}
		else {
			//altrimenti viene visualizzato il messaggio d'errore
			System.out.println("ERRORE: caricamento dei dati fallito, possibili file corrotti");
		}
		
		//chiusura del sistema
		input.close();
		System.out.println("Sistema spento.");
	}
}