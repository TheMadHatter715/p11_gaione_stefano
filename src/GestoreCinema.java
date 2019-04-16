
public class GestoreCinema {

	/** Attributes */
	private String nome;
	private String id;
	private String psw;
	
	//costruttore
	public GestoreCinema(String nome, String id, String psw) {
		setNome(nome);
		setId(id);
		setPsw(psw);
	}

	//getter e setter degli attributi
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

