package Controller;

import Model.Album;
import Model.Faixa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tibet Teixeira
 */
public class AlbumSQL extends ExecuteSQL {

    public AlbumSQL(Connection conn) {
        super(conn);
    }

    public List<Album> listarAlbuns() {
        String sql = "SELECT id_album, descricao, data_gravacao, nome "
                + "FROM album a "
                + "INNER JOIN gravadora g ON (a.cod_gravadora = g.cod_gravadora)";

        List<Album> listaAlbuns = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Album a = new Album();

                    a.setIdAlbum(rs.getInt(1));
                    a.setDescricao(rs.getString(2));
                    a.setDataGravacao(rs.getString(3));
                    a.setNomeGravadora(rs.getString(4));

                    listaAlbuns.add(a);
                }
                return listaAlbuns;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
}
