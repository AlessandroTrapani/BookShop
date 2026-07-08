package model;

import java.util.ArrayList;

/**
 * Classe model che rappresenta il carrello dell'utente.
 * 
 * Il carrello viene salvato in sessione e contiene una lista di elementi.
 * Ogni elemento contiene un prodotto e la relativa quantità.
 */
public class Carrello {

    private ArrayList<ElementoCarrello> elementi;

    public Carrello() {
        elementi = new ArrayList<ElementoCarrello>();
    }

    public ArrayList<ElementoCarrello> getElementi() {
        return elementi;
    }

    public void setElementi(ArrayList<ElementoCarrello> elementi) {
        this.elementi = elementi;
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita) {
        ElementoCarrello elementoEsistente = trovaElemento(prodotto.getId());

        int quantitaDisponibile = prodotto.getQuantita();

        if (elementoEsistente != null) {
            int nuovaQuantita = elementoEsistente.getQuantita() + quantita;

            if (nuovaQuantita > quantitaDisponibile) {
                nuovaQuantita = quantitaDisponibile;
            }

            elementoEsistente.setQuantita(nuovaQuantita);
        } else {

            if (quantita > quantitaDisponibile) {
                quantita = quantitaDisponibile;
            }

            ElementoCarrello nuovoElemento = new ElementoCarrello(prodotto, quantita);
            elementi.add(nuovoElemento);
        }
    }

    public void aggiornaQuantita(int idProdotto, int quantita) {
        ElementoCarrello elemento = trovaElemento(idProdotto);

        if (elemento != null) {
            if (quantita <= 0) {
                rimuoviProdotto(idProdotto);
            } else {
                int quantitaDisponibile = elemento.getProdotto().getQuantita();

                if (quantita > quantitaDisponibile) {
                    quantita = quantitaDisponibile;
                }

                elemento.setQuantita(quantita);
            }
        }
    }

    public void rimuoviProdotto(int idProdotto) {
        ElementoCarrello elementoDaRimuovere = trovaElemento(idProdotto);

        if (elementoDaRimuovere != null) {
            elementi.remove(elementoDaRimuovere);
        }
    }


    public void svuota() {
        elementi.clear();
    }

    public boolean isVuoto() {
        return elementi.isEmpty();
    }

    public double getTotale() {
        double totale = 0;

        for (ElementoCarrello elemento : elementi) {
            totale += elemento.getTotaleRiga();
        }

        return totale;
    }
    
    public int getQuantitaProdotto(int idProdotto) {
        ElementoCarrello elemento = trovaElemento(idProdotto);

        if (elemento != null) {
            return elemento.getQuantita();
        }

        return 0;
    }

    private ElementoCarrello trovaElemento(int idProdotto) {
        for (ElementoCarrello elemento : elementi) {
            if (elemento.getProdotto().getId() == idProdotto) {
                return elemento;
            }
        }

        return null;
    }
}