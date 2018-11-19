package Model;

/**
 *
 * @author Tibet Teixeira
 */
public class Gravadora {
  
    int codigoGravadora;
    String nome;
    String homePage;
    String rua;
    String cidade;
    String pais;

    public int getCodigoGravadora() {
        return codigoGravadora;
    }

    public void setCodigoGravadora(int codigoGravadora) {
        this.codigoGravadora = codigoGravadora;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
