package Controller;

import Model.Faixa;
import Model.Gravadora;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tibet Teixeira
 */
public class GravadoraSQL extends ExecuteSQL {

    public GravadoraSQL(Connection conn) {
        super(conn);
    }
    
    public List<Gravadora> listarGravadoras() {
        String sql = "SELECT cod_gravadora, nome FROM gravadora";

        List<Gravadora> listaGravadora = new ArrayList();
        try {
            PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Gravadora g = new Gravadora();

                    g.setCodigoGravadora(rs.getInt(1));
                    g.setNome(rs.getString(2));

                    listaGravadora.add(g);
                }
                return listaGravadora;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
}
