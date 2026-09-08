# BookShop

BookShop simula un piccolo e-commerce dedicato alla vendita di libri.

Il sito permette agli utenti di consultare un catalogo prodotti, usare un carrello, registrarsi, effettuare il login, completare un ordine e visualizzare lo storico dei propri acquisti.

È inoltre presente un'area amministratore per la gestione dei prodotti e degli ordini.

---

## Tecnologie utilizzate

* Java
* Jakarta Servlet
* JSP
* JDBC
* DataSource JNDI
* MySQL
* HTML
* CSS
* JavaScript
* AJAX
* JSON
* Apache Tomcat

---

## Architettura generale

Il progetto segue il pattern MVC.

La suddivisione principale è la seguente:

* `model`: contiene le classi modello del progetto
* `dao`: contiene le classi che comunicano con il database
* `control`: contiene le Servlet pubbliche
* `control.admin`: contiene le Servlet dell'area amministratore
* `filtro`: contiene i filtri per proteggere le aree riservate
* `util`: contiene la classe per recuperare la connessione tramite DataSource
* `WEB-INF/view`: contiene le JSP non accessibili direttamente dal browser
* `scripts`: contiene i file JavaScript
* `styles`: contiene il CSS del sito
* `images/prodotti`: contiene le immagini dei prodotti

Il flusso generale è:

```text
Browser
→ Servlet
→ DAO
→ Database
→ Servlet
→ JSP
→ Browser
```

Le JSP si trovano dentro `WEB-INF/view`, quindi non vengono aperte direttamente dal browser, ma vengono raggiunte tramite forward dalle Servlet.

---

## Struttura principale del progetto

```text
src/main/java
├── control
│   ├── HomeServlet.java
│   ├── CatalogoServlet.java
│   ├── DettaglioProdottoServlet.java
│   ├── CarrelloServlet.java
│   ├── LoginServlet.java
│   ├── LogoutServlet.java
│   ├── RegistrazioneServlet.java
│   ├── VerificaEmailServlet.java
│   ├── CheckoutServlet.java
│   ├── StoricoOrdiniServlet.java
│   └── DettaglioOrdineServlet.java
│
├── control.admin
│   ├── AdminHomeServlet.java
│   ├── AdminProdottiServlet.java
│   ├── AdminNuovoProdottoServlet.java
│   ├── AdminModificaProdottoServlet.java
│   ├── AdminEliminaProdottoServlet.java
│   ├── AdminOrdiniServlet.java
│   ├── AdminDettaglioOrdineServlet.java
│   └── AdminAggiornaStatoOrdineServlet.java
│
├── dao
│   ├── ProdottoDAO.java
│   ├── UtenteDAO.java
│   └── OrdineDAO.java
│
├── model
│   ├── Prodotto.java
│   ├── Utente.java
│   ├── Carrello.java
│   ├── ElementoCarrello.java
│   ├── Ordine.java
│   └── DettaglioOrdine.java
│
├── filtro
│   ├── FiltroUtente.java
│   └── FiltroAdmin.java
│
└── util
    └── ConnessioneDatabase.java
```

```text
src/main/webapp
├── META-INF
│   └── context.xml
├── WEB-INF
│   ├── web.xml
│   ├── lib
│   └── view
│       ├── pagine
│       │   ├── home.jsp
│       │   ├── catalogo.jsp
│       │   ├── dettaglio-prodotto.jsp
│       │   ├── carrello.jsp
│       │   ├── checkout.jsp
│       │   ├── login.jsp
│       │   ├── registrazione.jsp
│       │   ├── storico-ordini.jsp
│       │   └── dettaglio-ordine.jsp
│       │
│       └── admin
│           ├── home.jsp
│           ├── prodotti.jsp
│           ├── nuovo-prodotto.jsp
│           ├── modifica-prodotto.jsp
│           ├── ordini.jsp
│           └── dettaglio-ordine.jsp
│
├── images
│   └── prodotti
├── scripts
└── styles
```

---

## Funzionalità per utente non registrato

Un utente non registrato può:

* visualizzare la homepage
* consultare il catalogo prodotti
* filtrare i prodotti per titolo, autore e categoria
* visualizzare il dettaglio di un prodotto disponibile
* aggiungere prodotti al carrello
* modificare le quantità nel carrello
* rimuovere prodotti dal carrello
* svuotare il carrello

L'utente non registrato può preparare il carrello, ma per completare l'acquisto deve effettuare il login.

---

## Funzionalità per utente registrato

Un utente registrato può:

* effettuare il login
* usare il carrello
* completare il checkout
* scegliere il metodo di pagamento
* visualizzare lo storico dei propri ordini
* visualizzare il dettaglio dei propri ordini

Dopo un ordine andato a buon fine, il carrello viene svuotato e le quantità dei prodotti vengono aggiornate nel database.

---

## Funzionalità amministratore

L'amministratore può:

* accedere all'area admin
* visualizzare la dashboard amministrativa
* visualizzare tutti i prodotti
* creare nuovi prodotti
* modificare prodotti esistenti
* eliminare logicamente prodotti
* visualizzare tutti gli ordini
* filtrare gli ordini per data iniziale, data finale e cliente
* visualizzare il dettaglio di un ordine
* aggiornare lo stato di un ordine

L'amministratore non può usare il flusso cliente.
Se prova ad accedere al carrello o alle pagine riservate agli utenti normali, viene reindirizzato all'area amministratore.

---

## Gestione prodotti

I prodotti sono salvati nella tabella `prodotti`.

Ogni prodotto contiene informazioni come:

* titolo
* autore
* categoria
* prezzo
* quantità
* immagine
* descrizione
* stato

Lo stato del prodotto può essere:

```text
DISPONIBILE
NON_DISPONIBILE
ELIMINATO
```

Il catalogo pubblico mostra solo prodotti con:

```text
stato = DISPONIBILE
quantità maggiore di zero
```

Quando un prodotto viene eliminato dall'amministratore, non viene cancellato fisicamente dal database, ma viene aggiornato il suo stato a `ELIMINATO`.

Questa scelta permette di mantenere corretti gli ordini già effettuati.

---

## Gestione carrello

Il carrello viene salvato nella sessione HTTP.

Questo permette all'utente di navigare tra le pagine mantenendo i prodotti selezionati.

Nel carrello è possibile:

* aggiungere un prodotto
* aggiornare la quantità
* rimuovere un prodotto
* svuotare il carrello

Il sistema controlla che la quantità richiesta non superi la quantità disponibile in magazzino.

---

## Gestione checkout

Il checkout è disponibile solo per utenti registrati.

Durante il checkout vengono richiesti:

* email di consegna
* indirizzo di spedizione
* metodo di pagamento

I metodi di pagamento previsti sono:

* Carta
* Contanti

Se viene scelto il pagamento con carta, vengono controllati anche:

* numero carta
* CVV

Simula il processo di conferma dell'ordine.

---

## Gestione ordini

Quando un utente conferma un ordine, il sistema:

1. crea un record nella tabella `ordini`
2. crea i record collegati nella tabella `dettagli_ordine`
3. salva il titolo e il prezzo storico dei prodotti acquistati
4. scala le quantità dei prodotti
5. aggiorna lo stato del prodotto a `NON_DISPONIBILE` se la quantità arriva a zero
6. svuota il carrello

Il salvataggio dell'ordine e l'aggiornamento delle quantità vengono gestiti tramite transazione.

In questo modo, se si verifica un errore durante il salvataggio dell'ordine, anche le modifiche alle quantità vengono annullate.

---

## Prezzo storico negli ordini

Nel dettaglio dell'ordine vengono salvati anche:

* titolo del prodotto acquistato
* prezzo del prodotto al momento dell'acquisto

Questo permette di mantenere corretto lo storico degli ordini anche se, in futuro, l'amministratore modifica il prezzo del prodotto nel catalogo.

---

## Area amministratore ordini

L'amministratore può visualizzare tutti gli ordini effettuati dagli utenti.

La lista ordini può essere filtrata per:

* data iniziale
* data finale
* id cliente

Dal dettaglio ordine l'amministratore può aggiornare lo stato dell'ordine.

Gli stati previsti sono:

```text
IN_ELABORAZIONE
SPEDITO
COMPLETATO
ANNULLATO
```

---

## Validazione dei form

Utilizza JavaScript per validare i principali form lato client.

Sono presenti validazioni per:

* login
* registrazione
* checkout
* inserimento prodotto admin
* modifica prodotto admin

Gli errori vengono mostrati direttamente nella pagina modificando il DOM, senza usare `alert`.

Il progetto mantiene anche controlli lato server nelle Servlet, perché la validazione JavaScript può essere disabilitata dal browser.

---

## AJAX e JSON

Nel form di registrazione viene utilizzata una chiamata AJAX per verificare se l'email inserita dall'utente è già presente nel database.

Il JavaScript chiama la Servlet dedicata alla verifica email.

La Servlet restituisce una risposta JSON che indica se l'email è valida o già registrata.

Il risultato viene mostrato direttamente nella pagina.

---

## Sicurezza e controllo accessi

Il progetto utilizza la sessione HTTP per gestire l'utente loggato.

Dopo il login vengono salvati in sessione:

* `utenteLoggato`
* `tokenAccesso`

Il token viene generato tramite `UUID`.

Sono presenti due filtri:

* `FiltroUtente`
* `FiltroAdmin`

`FiltroUtente` protegge le pagine riservate agli utenti registrati.

`FiltroAdmin` protegge l'area amministratore.

In questo modo:

* un utente non loggato non può accedere al checkout
* un utente normale non può accedere all'area admin
* un admin non può usare il flusso cliente

---

## DataSource e connessione al database

La connessione al database viene gestita tramite DataSource JNDI.

Il file di configurazione si trova in:

```text
src/main/webapp/META-INF/context.xml
```

La risorsa configurata si chiama:

```text
jdbc/bookshop
```

La classe `ConnessioneDatabase` recupera la connessione tramite JNDI.

Il riferimento alla risorsa è dichiarato anche nel file:

```text
src/main/webapp/WEB-INF/web.xml
```

---

## Database

Il database utilizzato è MySQL.

Nome database:

```text
bookshop
```

Le tabelle principali sono:

* `utenti`
* `prodotti`
* `ordini`
* `dettagli_ordine`

---

## Configurazione locale del database

Nel file `context.xml` è presente una configurazione locale del DataSource.

Esempio:

```xml
<Resource
    name="jdbc/bookshop"
    auth="Container"
    type="javax.sql.DataSource"
    maxTotal="20"
    maxIdle="10"
    maxWaitMillis="-1"
    username="root"
    password="root"
    driverClassName="com.mysql.cj.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/bookshop?useSSL=false&amp;serverTimezone=UTC" />
```

---

## Utenti di test

Per provare il progetto sono disponibili due utenti di test.

### Utente normale

Email:

```text
utente@bookshop.it
```

Password:

```text
utente
```

### Amministratore

Email:

```text
admin@bookshop.it
```

Password:

```text
admin
```

---

## Avvio in locale

Per eseguire il progetto in locale:

1. creare il database MySQL `bookshop`
2. importare lo script SQL del database
3. verificare la configurazione del DataSource in `context.xml`
4. avviare Apache Tomcat
5. deployare il progetto su Tomcat
6. aprire il browser

URL principale:

```text
http://localhost:8080/BookShop/
```

Oppure:

```text
http://localhost:8080/BookShop/home
```

---

## Deploy per la presentazione

Per la presentazione il progetto può essere esportato come file `.war` e inserito in un Tomcat standalone.

Procedura:

1. esportare il progetto come `BookShop.war`
2. copiare il file `.war` nella cartella `webapps` di Tomcat
3. assicurarsi che MySQL sia avviato
4. assicurarsi che il database `bookshop` sia presente
5. assicurarsi che il driver MySQL sia disponibile
6. avviare Tomcat standalone
7. aprire il progetto dal browser

URL:

```text
http://localhost:8080/BookShop/
```

Se il deploy è corretto, Tomcat estrae automaticamente il file `.war` nella cartella `webapps`.

---

## File principali

### Servlet pubbliche

* `HomeServlet.java`: mostra la homepage
* `CatalogoServlet.java`: mostra il catalogo prodotti
* `DettaglioProdottoServlet.java`: mostra il dettaglio di un prodotto
* `CarrelloServlet.java`: gestisce il carrello
* `LoginServlet.java`: gestisce il login
* `LogoutServlet.java`: gestisce il logout
* `RegistrazioneServlet.java`: gestisce la registrazione
* `VerificaEmailServlet.java`: verifica l'email tramite AJAX
* `CheckoutServlet.java`: gestisce il checkout
* `StoricoOrdiniServlet.java`: mostra lo storico ordini dell'utente
* `DettaglioOrdineServlet.java`: mostra il dettaglio di un ordine utente

### Servlet admin

* `AdminHomeServlet.java`: mostra la dashboard admin
* `AdminProdottiServlet.java`: mostra i prodotti admin
* `AdminNuovoProdottoServlet.java`: crea un nuovo prodotto
* `AdminModificaProdottoServlet.java`: modifica un prodotto esistente
* `AdminEliminaProdottoServlet.java`: elimina logicamente un prodotto
* `AdminOrdiniServlet.java`: mostra e filtra gli ordini
* `AdminDettaglioOrdineServlet.java`: mostra il dettaglio ordine admin
* `AdminAggiornaStatoOrdineServlet.java`: aggiorna lo stato dell'ordine

### DAO

* `ProdottoDAO.java`: gestisce le query sui prodotti
* `UtenteDAO.java`: gestisce le query sugli utenti
* `OrdineDAO.java`: gestisce le query sugli ordini

### Model

* `Prodotto.java`
* `Utente.java`
* `Carrello.java`
* `ElementoCarrello.java`
* `Ordine.java`
* `DettaglioOrdine.java`

### Filtri

* `FiltroUtente.java`
* `FiltroAdmin.java`

---

