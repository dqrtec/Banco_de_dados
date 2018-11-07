/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Model.Album;
import Model.Faixa;
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
        String sql = "INSERT INTO playlist VALUES (? ? ? ? ? ?)";

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setInt(1, p.getIdPlaylist());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getDataCriacao());
            ps.setInt(4, p.getQuantidadeTocada());
            ps.setFloat(5, p.getTempoTotalExecucao());
            ps.setString(6, p.getDataUltimaVezTocada());

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
        String sql = "INSERT INTO playlist_faixa VALUES (? ? ?)";

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);

            ps.setInt(1, p.getIdPlaylist());
            ps.setInt(2, f.getIdAlbum());
            ps.setInt(3, f.getNumFaixa());

            if (ps.executeUpdate() > 0) {
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
        String sql = "SELECT id_playlist, nome, data_criacao, data_ultima_vez_tocada, quantidade_tocada, tempo_total_execucao "
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
                    p.setDataUltimaVezTocada(rs.getString(4));
                    p.setQuantidadeTocada(rs.getInt(5));
                    p.setTempoTotalExecucao(rs.getFloat(6));

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
        String sql = "SELECT id_album, num_faixa FROM album_faixa_playlist WHERE id_playlist = " + idPlaylist;
        List<Faixa> listaFaixa = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Faixa f = new Faixa();
                    f.setIdAlbum(rs.getInt(1));
                    f.setNumFaixa(rs.getInt(2));
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

    public String alteraPlaylist(Playlist p) {
        String sql = "UPDATE playlist SET nome=?, data_criacao=?,"
                + "quantidade_tocada=?, tempo_total_execucao=?, data_ultima_vez_tocada=?"
                + " WHERE id_playlist=?";
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDataCriacao());
            ps.setInt(3, p.getQuantidadeTocada());
            ps.setFloat(4, p.getTempoTotalExecucao());
            ps.setString(5, p.getDataUltimaVezTocada());

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
        String sql = "DELETE FROM album_faixa_playlist "
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
