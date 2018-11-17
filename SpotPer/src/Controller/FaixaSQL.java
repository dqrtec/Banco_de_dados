package Controller;

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
        String sql = "SELECT num_faixa, id_album FROM faixa";

        List<Faixa> listaFaixa = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Faixa f = new Faixa();

                    f.setNumFaixa(rs.getInt(1));
                    f.setIdAlbum(rs.getInt(2));

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
                    Faixa f = new Faixa();

                    f.setNumFaixa(rs.getInt(1));
                    f.setDescricao(rs.getString(2));
                    f.setTempoDuracao(rs.getFloat(3));
                    f.setTipoGravacao(rs.getString(4));
                    f.setDescricaoComposicao(rs.getString(5));

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

}
