public class Palabra {
    private String categoria;
    private String texto;
    private String pista;

    public Palabra(String categoria, String texto, String pista) {
        this.categoria = categoria;
        this.texto = texto;
        this.pista = pista;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTexto() {
        return texto;
    }

    public String getPista() {
        return pista;
    }
}
