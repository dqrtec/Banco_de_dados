package Controller;

import Model.Album;
import Model.Faixa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tibet Teixeira
 */
public class FaixaSQL extends ExecuteSQL {

    public FaixaSQL(Connection conn) {
        super(conn);
    }

    public List<Faixa> listarFaixas() {
        String sql = "SELECT TOP(20) f.num_faixa, f.id_album, f.descricao, f.tempo_duracao, a.descricao, c.nome "
                + "FROM faixa f "
                + "INNER JOIN album a ON (f.id_album = a.id_album) "
                + "INNER JOIN faixa_compositor fc ON (f.num_faixa = fc.num_faixa and f.id_album = fc.id_album) "
                + "INNER JOIN compositor c ON (c.id_compositor = fc.id_compositor) "
                + "ORDER BY id_album DESC";

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
                    f.setDescricaoAlbum(rs.getString(5));
                    f.setNomeCompositor(rs.getString(6));

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

    public Faixa listaFaixa(int idAlbum, int numFaixa) {
        String sql = "SELECT f.num_faixa, f.id_album, f.descricao, f.tempo_duracao, f.tipo_gravacao, f.id_tipo_composicao, tc.descricao, f.diretorio "
                + "FROM faixa f "
                + "INNER JOIN tipo_composicao tc ON (f.id_tipo_composicao = tc.id_tipo_composicao)"
                + "WHERE f.num_faixa = " + numFaixa + " and f.id_album = " + idAlbum;

        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                rs.next();
                Faixa faixa = new Faixa();

                faixa.setNumFaixa(rs.getInt(1));
                faixa.setIdAlbum(rs.getInt(2));
                faixa.setDescricao(rs.getString(3));
                faixa.setTempoDuracao(rs.getFloat(4));
                faixa.setTipoGravacao(rs.getString(5));
                faixa.setIdComposicao(rs.getInt(6));
                faixa.setDescricaoComposicao(rs.getString(7));
                faixa.setDiretorio(rs.getString(8));

                return faixa;
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Faixa> listarFaixasAlbum(int idAlbum) {

        String sql = "SELECT num_faixa, f.descricao, tempo_duracao, tipo_gravacao, tc.descricao "
                + "FROM faixa f "
                + "INNER JOIN tipo_composicao tc ON (f.id_tipo_composicao = tc.id_tipo_composicao) "
                + "WHERE f.id_album = " + idAlbum;

        List<Faixa> listaFaixa = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Faixa faixa = new Faixa();

                    faixa.setNumFaixa(rs.getInt(1));
                    faixa.setDescricao(rs.getString(2));
                    faixa.setTempoDuracao(rs.getFloat(3));
                    faixa.setTipoGravacao(rs.getString(4));
                    faixa.setDescricaoComposicao(rs.getString(5));

                    listaFaixa.add(faixa);
                }
                return listaFaixa;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public List<Faixa> listarFaixasAlbum(Album a) {
        String sql = "SELECT f.num_faixa, f.id_album, f.descricao, f.tempo_duracao, f.tipo_gravacao, f.id_tipo_composicao, tc.descricao "
                + "FROM faixa f "
                + "INNER JOIN tipo_composicao tc ON (f.id_tipo_composicao = tc.id_tipo_composicao) "
                + "WHERE f.id_album = " + a.getIdAlbum();

        List<Faixa> listaFaixa = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Faixa faixa = new Faixa();

                    faixa.setNumFaixa(rs.getInt(1));
                    faixa.setIdAlbum(rs.getInt(2));
                    faixa.setDescricao(rs.getString(3));
                    faixa.setTempoDuracao(rs.getFloat(4));
                    faixa.setTipoGravacao(rs.getString(5));
                    faixa.setIdComposicao(rs.getInt(6));
                    faixa.setDescricaoComposicao(rs.getString(7));

                    listaFaixa.add(faixa);
                }
                return listaFaixa;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

}
