package model;

/**
 * Classe model che rappresenta una singola riga del carrello.
 * 
 * Ogni elemento del carrello contiene un prodotto e la quantità scelta
 * dall'utente.
 */
public class ElementoCarrello {

    private Prodotto prodotto;
    private int quantita;

    public ElementoCarrello() {
    }

    public ElementoCarrello(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }


    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getTotaleRiga() {
        return prodotto.getPrezzo() * quantita;
    }
}