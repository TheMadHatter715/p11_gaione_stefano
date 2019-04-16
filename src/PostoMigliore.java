//classe derivata della classe Posto
public class PostoMigliore extends Posto{
    
	//costruttore
    public PostoMigliore(int f, int n){
        super(f,n);
        setCostoAggiuntivo(1);
    }
    
}