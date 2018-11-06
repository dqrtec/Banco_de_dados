package Model;

/**
 *
 * @author Tibet Teixeira
 */
public class Playlist {
    int idPlaylist;
    int quantidadeTocada;
    float tempoTotalExecucao;
    String nome;
    String dataCriacao;
    String dataUltimaVezTocada;

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public int getQuantidadeTocada() {
        return quantidadeTocada;
    }

    public void setQuantidadeTocada(int quantidadeTocada) {
        this.quantidadeTocada = quantidadeTocada;
    }

    public float getTempoTotalExecucao() {
        return tempoTotalExecucao;
    }

    public void setTempoTotalExecucao(float tempoTotalExecucao) {
        this.tempoTotalExecucao = tempoTotalExecucao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataUltimaVezTocada() {
        return dataUltimaVezTocada;
    }

    public void setDataUltimaVezTocada(String dataUltimaVezTocada) {
        this.dataUltimaVezTocada = dataUltimaVezTocada;
    }
    
    
}
