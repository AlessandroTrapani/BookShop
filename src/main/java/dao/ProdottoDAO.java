package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Prodotto;
import util.ConnessioneDatabase;

public class ProdottoDAO {

    public ArrayList<Prodotto> cercaProdotti(String ricerca, String autore, String categoria) {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        String sql = "SELECT * FROM prodotti "
                + "WHERE stato = 'DISPONIBILE' "
                + "AND quantita > 0 "
                + "AND (? IS NULL OR titolo LIKE ?) "
                + "AND (? IS NULL OR autore = ?) "
                + "AND (? IS NULL OR categoria = ?) "
                + "ORDER BY titolo";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            if (ricerca == null || ricerca.trim().equals("")) {
                statement.setString(1, null);
                statement.setString(2, null);
            } else {
                statement.setString(1, ricerca.trim());
                statement.setString(2, "%" + ricerca.trim() + "%");
            }

            if (autore == null || autore.trim().equals("")) {
                statement.setString(3, null);
                statement.setString(4, null);
            } else {
                statement.setString(3, autore.trim());
                statement.setString(4, autore.trim());
            }

            if (categoria == null || categoria.trim().equals("")) {
                statement.setString(5, null);
                statement.setString(6, null);
            } else {
                statement.setString(5, categoria.trim());
                statement.setString(6, categoria.trim());
            }

            try (ResultSet risultato = statement.executeQuery()) {
                while (risultato.next()) {
                    Prodotto prodotto = creaProdotto(risultato);
                    prodotti.add(prodotto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }

    public Prodotto trovaPerId(int id) {
        Prodotto prodotto = null;

        String sql = "SELECT * FROM prodotti WHERE id = ?";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            try (ResultSet risultato = statement.executeQuery()) {
                if (risultato.next()) {
                    prodotto = creaProdotto(risultato);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotto;
    }

    public ArrayList<Prodotto> trovaTuttiAdmin() {
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        String sql = "SELECT * FROM prodotti ORDER BY data_inserimento DESC";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql);
            ResultSet risultato = statement.executeQuery()
        ) {
            while (risultato.next()) {
                Prodotto prodotto = creaProdotto(risultato);
                prodotti.add(prodotto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }

    public boolean inserisciProdotto(Prodotto prodotto) {
        boolean inserito = false;

        String sql = "INSERT INTO prodotti "
                + "(titolo, autore, categoria, prezzo, quantita, immagine, descrizione, stato) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setString(1, prodotto.getTitolo());
            statement.setString(2, prodotto.getAutore());
            statement.setString(3, prodotto.getCategoria());
            statement.setDouble(4, prodotto.getPrezzo());
            statement.setInt(5, prodotto.getQuantita());
            statement.setString(6, prodotto.getImmagine());
            statement.setString(7, prodotto.getDescrizione());
            statement.setString(8, prodotto.getStato());

            int righeInserite = statement.executeUpdate();

            if (righeInserite > 0) {
                inserito = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inserito;
    }

    public boolean aggiornaProdotto(Prodotto prodotto) {
        boolean aggiornato = false;

        String sql = "UPDATE prodotti "
                + "SET titolo = ?, autore = ?, categoria = ?, prezzo = ?, "
                + "quantita = ?, immagine = ?, descrizione = ?, stato = ? "
                + "WHERE id = ?";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setString(1, prodotto.getTitolo());
            statement.setString(2, prodotto.getAutore());
            statement.setString(3, prodotto.getCategoria());
            statement.setDouble(4, prodotto.getPrezzo());
            statement.setInt(5, prodotto.getQuantita());
            statement.setString(6, prodotto.getImmagine());
            statement.setString(7, prodotto.getDescrizione());
            statement.setString(8, prodotto.getStato());
            statement.setInt(9, prodotto.getId());

            int righeAggiornate = statement.executeUpdate();

            if (righeAggiornate > 0) {
                aggiornato = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aggiornato;
    }

    public boolean eliminaLogicamente(int idProdotto) {
        boolean eliminato = false;

        String sql = "UPDATE prodotti SET stato = ? WHERE id = ?";

        try (
            Connection connessione = ConnessioneDatabase.getConnessione();
            PreparedStatement statement = connessione.prepareStatement(sql)
        ) {
            statement.setString(1, "ELIMINATO");
            statement.setInt(2, idProdotto);

            int righeAggiornate = statement.executeUpdate();

            if (righeAggiornate > 0) {
                eliminato = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminato;
    }

    private Prodotto creaProdotto(ResultSet risultato) throws SQLException {
        Prodotto prodotto = new Prodotto();

        prodotto.setId(risultato.getInt("id"));
        prodotto.setTitolo(risultato.getString("titolo"));
        prodotto.setAutore(risultato.getString("autore"));
        prodotto.setCategoria(risultato.getString("categoria"));
        prodotto.setPrezzo(risultato.getDouble("prezzo"));
        prodotto.setQuantita(risultato.getInt("quantita"));
        prodotto.setImmagine(risultato.getString("immagine"));
        prodotto.setDescrizione(risultato.getString("descrizione"));
        prodotto.setStato(risultato.getString("stato"));
        prodotto.setDataInserimento(risultato.getTimestamp("data_inserimento"));

        return prodotto;
    }
}