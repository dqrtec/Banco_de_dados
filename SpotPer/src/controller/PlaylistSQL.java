/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Album;
import Model.Faixa;
import Model.FaixaPlaylist;
import Model.Playlist;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tibet Teixeira
 */
public class PlaylistSQL extends ExecuteSQL {

    public PlaylistSQL(Connection conn) {
        super(conn);
    }

    public String criarPlaylist(Playlist p) {
        String sql = "INSERT INTO playlist VALUES (? ? ? ?)";

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setInt(1, p.getIdPlaylist());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getDataCriacao());
            ps.setFloat(4, p.getTempoTotalExecucao());

            if (ps.executeUpdate() > 0) {
                return "Playlist criada com sucesso!";
            } else {
                return "Erro ao criar Playlist";
            }

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String adicionaFaixaPlaylist(Playlist p, Faixa f) {
        String sql = "INSERT INTO playlist_faixa VALUES (? ? ? ? ?)";

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setInt(1, p.getIdPlaylist());
            ps.setInt(2, f.getIdAlbum());
            ps.setInt(3, f.getNumFaixa());
            ps.setInt(4, 0); // Quantidade tocada
            ps.setString(5, null); // Data última vez reproduzida

            if (ps.executeUpdate() > 0) {
                alteraPlaylist(p, p.getTempoTotalExecucao() + f.getTempoDuracao());
                return "Faixa adicionada com sucesso!";
            } else {
                return "Erro ao adicionar Faixa";
            }

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String adicionaAlbumPlaylist(Playlist p, Album a) {
        // Fazer um while que percorre todas as musicas do album 
        // adicionando na playlist
        String sql = "INSERT INTO playlist_faixa VALUES (? ? ?)";

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setInt(1, p.getIdPlaylist());
            ps.setInt(2, a.getIdAlbum());
            // ps.setInt(3, a.getNumFaixa());

            if (ps.executeUpdate() > 0) {
                return "Faixa adicionada com sucesso!";
            } else {
                return "Erro ao adicionar Faixa";
            }

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public List<Playlist> listarPlaylist(String nome) {
        String sql = "SELECT id_playlist, nome, data_criacao, tempo_total_execucao "
                + "FROM playlist WHERE nome LIKE '%" + nome + "%'";
        List<Playlist> listaPlaylist = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Playlist p = new Playlist();

                    p.setIdPlaylist(rs.getInt(1));
                    p.setNome(rs.getString(2));
                    p.setDataCriacao(rs.getString(3));
                    p.setTempoTotalExecucao(rs.getFloat(4));

                    listaPlaylist.add(p);
                }
                return listaPlaylist;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public List<Faixa> listarFaixaPlaylist(int idPlaylist) {
        String sql = "SELECT f.num_faixa, f.id_album, f.descricao, f.tempo_duracao, f.id_composicao, f.tipo_gravacao "
                + "FROM faixa f "
                + "INNER JOIN faixa_playlist fp ON (f.num_faixa = fp.num_faixa) "
                + "WHERE fp.id_playlist = " + idPlaylist;

        List<Faixa> listaFaixa = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Faixa f = new Faixa();

                    f.setNumFaixa(rs.getInt(1));
                    f.setIdAlbum(rs.getInt(2));
                    f.setDescricao(rs.getString(3));
                    f.setTempoDuracao(rs.getFloat(4));
                    f.setIdComposicao(rs.getInt(5));
                    f.setTipoGravacao(rs.getString(6));

                    listaFaixa.add(f);
                }
                return listaFaixa;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public String alteraPlaylist(Playlist p, String nome) {
        String sql = "UPDATE playlist SET nome=? "
                + "WHERE id_playlist=?";
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setString(1, nome);
            ps.setInt(2, p.getIdPlaylist());

            if (ps.executeUpdate() > 0) {
                return "Playlist atualizada!";

            } else {
                return "Erro ao Atualizar";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String alteraPlaylist(Playlist p, Float tempoTotalExecucao) {
        String sql = "UPDATE playlist SET tempo_total_execucao=? "
                + "WHERE id_playlist=?";
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setFloat(1, tempoTotalExecucao);
            ps.setFloat(2, p.getIdPlaylist());

            if (ps.executeUpdate() > 0) {
                return "Playlist atualizada!";

            } else {
                return "Erro ao Atualizar";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String removerPlaylist(Playlist p) {
        String sql = "DELETE FROM playlist WHERE id_playlist = ?";

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ps.setInt(1, p.getIdPlaylist());

            if (ps.executeUpdate() > 0) {
                return "Playlist removida com sucesso!";
            } else {
                return "Erro ao remover playlist.";
            }

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String removerFaixaPlaylist(Playlist p, Faixa f) {
        String sql = "DELETE FROM faixa_playlist "
                + "WHERE id_playlist = ? and id_album = ? and num_faixa = ?";

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setInt(1, p.getIdPlaylist());
            ps.setInt(2, f.getIdAlbum());
            ps.setInt(3, f.getNumFaixa());

            if (ps.executeUpdate() > 0) {
                return "Faixa removida com sucesso!";
            } else {
                return "Erro ao remover faixa.";
            }

        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
