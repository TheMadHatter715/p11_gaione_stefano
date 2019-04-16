
public class Posto{
	
	/** Attributes */
    private int fila;
    private int numero;
    private boolean libero;
    private int costoAggiuntivo;
    
    //costruttore
    public Posto(int f, int n){
    	setFila(f);
    	setNumero(n);
    	setLibero(true);
        this.setCostoAggiuntivo(0);
    }

    //getter e setter degli attributi
	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}

	public int getCostoAggiuntivo() {
		return costoAggiuntivo;
	}

	public void setCostoAggiuntivo(int costoAggiuntivo) {
		this.costoAggiuntivo = costoAggiuntivo;
	}
    
}
