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
    
    public List<Faixa> listarFaixas(){
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
    
}
