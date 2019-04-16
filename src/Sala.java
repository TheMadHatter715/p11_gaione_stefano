
public class Sala {

	/** Attributes */
	private int id;
	private boolean trid;
	private int numeroFile;
	private int capienza;
	
	/** Associations */
	private Posto[][] posti;
	
	//costruttore
	public Sala(int id, int numerofile, int capienza, boolean t) {
		setId(id);
		setNumeroFile(numerofile);
		setCapienza(capienza);
		Posto[][] posti = new Posto[this.numeroFile][this.capienza/this.numeroFile];
		setPosti(posti); 
		fillPosti();
		setTrid(t);
	}
	
	//metodi
	
	/**
     * Funzione per impostare la corretta tipologia di posto
     *
     */
	private void fillPosti(){
		
		for(int i = 0; i<this.numeroFile; i++){
			for(int j = 0; j<this.capienza/this.numeroFile; j++) {
				if(i==this.numeroFile-3){
	                posti[i][j] = new PostoMigliore(i+1,j+1);
	            }
	            else{
	            	posti[i][j] = new Posto(i+1,j+1);
	            }
			} 
        }
	}

	//Getter e setter degli attributi
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isTrid() {
		return trid;
	}
	public void setTrid(boolean trid) {
		this.trid = trid;
	}
	public Posto[][] getPosti(){
		return posti;
	}
	public void setPosti(Posto[][] posti) {
		this.posti = posti;
	}
	public int getNumeroFile() {
		return numeroFile;
	}
	public void setNumeroFile(int numeroFile) {
		this.numeroFile = numeroFile;
	}
	public int getCapienza() {
		return capienza;
	}
	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}
}
