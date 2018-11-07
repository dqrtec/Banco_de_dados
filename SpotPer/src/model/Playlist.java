package Model;

/**
 *
 * @author Tibet Teixeira
 */
public class Playlist {
    int idPlaylist;
    float tempoTotalExecucao;
    String nome;
    String dataCriacao;

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
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
}
