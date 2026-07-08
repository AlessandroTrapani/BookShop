package util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Classe che gestisce il recupero delle connessioni al database.
 * 
 * I DAO usano questa classe per ottenere una connessione dal DataSource
 */
public class ConnessioneDatabase {


    private static DataSource dataSource;

    static {
        try {
            Context contesto = new InitialContext();
            dataSource = (DataSource) contesto.lookup("java:comp/env/jdbc/bookshop");
        } catch (NamingException e) {
            System.out.println("Errore nel recupero del DataSource jdbc/bookshop.");
            e.printStackTrace();
        }
    }

    public static Connection getConnessione() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource non inizializzato.");
        }

        return dataSource.getConnection();
    }
}