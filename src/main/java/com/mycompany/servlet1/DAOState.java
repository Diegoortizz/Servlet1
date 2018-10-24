/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import simplejdbc.DAO;

/**
 *
 * @author Diego
 */
public class DAOState extends DAO {

    public DAOState(DataSource dataSource) {
        super(dataSource);
    }

    public ArrayList<String> EveryState() throws SQLException {
        
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT DISTINCT APP.CUSTOMER.STATE FROM APP.CUSTOMER";
        

        // Syntaxe "try with resources" 
        // cf. https://stackoverflow.com/questions/22671697/try-try-with-resources-and-connection-statement-and-resultset-closing
        try (Connection connection = myDataSource.getConnection(); // Ouvrir une connexion
                Statement stmt = connection.createStatement(); // On crée un statement pour exécuter une requête
                ResultSet rs = stmt.executeQuery(sql) // Un ResultSet pour parcourir les enregistrements du résultat
                ) {
            while (rs.next()) { // Pas la peine de faire while, il y a 1 seul enregistrement
                // On récupère le champ NUMBER de l'enregistrement courant
                result.add(rs.getString("STATE"));
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }

        return result;
    }

}
