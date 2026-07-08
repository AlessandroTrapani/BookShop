package model;

/**
 * Classe model che rappresenta una riga di dettaglio di un ordine.
 * 
 * Ogni oggetto DettaglioOrdine corrisponde a un record della tabella
 * dettagli_ordine e rappresenta un prodotto acquistato in un ordine.
 */
public class DettaglioOrdine {

    private int id;
    private int idOrdine;
    private int idProdotto;
    private String nomeProdotto;
    private double prezzo;
    private int quantita;

    public DettaglioOrdine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }


    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public double getPrezzo() {
        return prezzo;
    }


    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getTotaleRiga() {
        return prezzo * quantita;
    }
}