package Model;

/**
 *
 * @author Tibet Teixeira
 */
public class FaixaPlaylist {
    int idPlaylist;
    int numFaixa;
    int idAlbum;
    int quantidadeTocada;
    String dataUltimaVezTocada;

    public int getQuantidadeTocada() {
        return quantidadeTocada;
    }

    public void setQuantidadeTocada(int quantidadeTocada) {
        this.quantidadeTocada = quantidadeTocada;
    }

    public String getDataUltimaVezTocada() {
        return dataUltimaVezTocada;
    }

    public void setDataUltimaVezTocada(String dataUltimaVezTocada) {
        this.dataUltimaVezTocada = dataUltimaVezTocada;
    }

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public int getNumFaixa() {
        return numFaixa;
    }

    public void setNumFaixa(int numFaixa) {
        this.numFaixa = numFaixa;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }
    
    
}
