package model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Classe model che rappresenta un ordine effettuato da un utente.
 * 
 * Ogni oggetto Ordine corrisponde a un record della tabella ordini.
 * Contiene anche una lista di DettaglioOrdine, cioè i prodotti acquistati.
 */
public class Ordine {

    private int id;
    private int idUtente;
    private double totale;
    private String emailConsegna;
    private String indirizzoSpedizione;
    private String metodoPagamento;
    private String stato;
    private Timestamp dataOrdine;
    private ArrayList<DettaglioOrdine> dettagli;

    public Ordine() {
        dettagli = new ArrayList<DettaglioOrdine>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public String getEmailConsegna() {
        return emailConsegna;
    }

    public void setEmailConsegna(String emailConsegna) {
        this.emailConsegna = emailConsegna;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
        this.indirizzoSpedizione = indirizzoSpedizione;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getStato() {
        return stato;
    }


    public void setStato(String stato) {
        this.stato = stato;
    }

    public Timestamp getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Timestamp dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public ArrayList<DettaglioOrdine> getDettagli() {
        return dettagli;
    }
    
    public void setDettagli(ArrayList<DettaglioOrdine> dettagli) {
        this.dettagli = dettagli;
    }
}