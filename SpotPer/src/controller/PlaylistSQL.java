/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import Model.Playlist;
import java.sql.Connection;

/**
 *
 * @author Tibet Teixeira
 */
public class PlaylistSQL extends ExecuteSQL{

    public PlaylistSQL(Connection conn) {
        super(conn);
    }
    
    public String criarPlaylist(Playlist p) {
        
    }

}
