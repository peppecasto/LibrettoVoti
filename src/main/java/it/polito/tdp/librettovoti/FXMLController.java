
	//non posso mettere qui la gestione della logica perchè qui sta la gestione dell'interfaccia utente (UI)
	//ho bisogno di testare la logica separatamente, altrimenti ogni test devo interagire con l'UI
    //pattern: come organizzare il codice (combinando gli elementi offerti da java) per risolvere un problema?
	//pattern MVC: View, Controller, Model; 
	//View(UI -buttons, label ecc..- + visualizzazione grafica delle informazioni rilevanti del Model) --> FXML, SceneBuilder 
	//Controller(tramite tra View e Model, prende gli input dell'utente che interagisce con UI e li fa realizzare a livello logico nel Model --> se qualcosa cambia in Model fa in modo di aggiornare View e viceversa) --> FXMLController,  
	//Model (tutti i dati, stato, calcoli, application logic - algoritmi di logica)
    //1-Quando FXML Loader ha finito di creare i nodi della Scene, crea il Controller, collegando gli ID dei nodi alle variabili del Controller -vedi su EntryPoint FXML Loader.load, lo fa da solo in automatico-
	//2-Il Model --> creato direttamente all'interno del main (Application) -deve sopravvivere anche se ci sono Controller diversi, ovvero più finestre grafiche diverse tra loro che funzionano in modo differente-, Model non richiama MAI metodi nel Controller o nella Scene --> N.B. un Controller lavora per UNA Scena, quella di cui contiene le variabili collegati ai nodi della stessa
	//3-Dopo che il main crea Model, devo scrivere una variabile locale Model e un metodo setModel nel controller
    //-------------------------------------------
	//Per prima cosa creo UI da SceneBuilder: ricorda di dare fx:id a ciò di cui voglio leggere/scrivere proprietà, e #onAction ai button che devo legare ai metodi per scatenare azioni
    //Poi copio lo skeleton su FXMLController
	
/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.librettovoti;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import librettovoti.model.Libretto;
import librettovoti.model.Voto;

public class FXMLController {
	
	private Libretto model; // PASSO 3: dichiaro qui una variabile model e creo il metodo setModel
	public void setModel(Libretto model) {this.model=model;} //serve che il main(EntryPoint) chiami questo metodo passando il parametro opportuno del libretto che ha appena creato
    //N.B. setModel è un metodo della classe FXMLController, quindi nel main sarà richiamabile tramite il Loader, che crea il Controller (Loader ha metodo getController)
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbPunti"
    private ComboBox<Integer> cmbPunti; //ha tra <> il tipo di oggetto che voglio che contenga, in questo caso voglio numeri interi --> -INTEGER- è la classe per numeri interi

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtVoti"
    private TextArea txtVoti; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtStatus"
    private Label txtStatus; // Value injected by FXMLLoader

    @FXML //Per quanto possibile Controller e model devono parlarsi usando oggetti (è bene che anche l'interfaccia sia pensata a oggetti)
    void handleNuovoVoto(ActionEvent event) { //N.B. alla fine ogni metodo handleAction avrà pressochè la stessa struttura (A - B - C)..
    	
    	String nomeEsame = txtNome.getText();
    	Integer punti = cmbPunti.getValue(); //value = item selezionato (def. da Java)
    	if(nomeEsame.equals("")|| punti == null) {
    		txtStatus.setText("ERRORE: Occorre inserire un Nome del Corso e un Punteggio valido.\n"); 
    		return;
    	}
    	//A - queste prime righe prendono input utente e fanno controlli di validità - acquisizione e controllo dati
       
    	
    	boolean ok = model.add(new Voto (nomeEsame,punti)); //B - chiedo al model di eseguire l'operazione
        
    	if(ok==true) {
        List<Voto> voti = model.getVoti(); //C - aggiorno l'interfaccia, chiedo al model i risultati e li metto su interfaccia
        txtVoti.clear();
        txtVoti.appendText("Hai superato "+voti.size()+" esami:\n"); //prima riga disponibile = append
        for(Voto v : voti) {
        	txtVoti.appendText(v.toString()+"\n");
        }
        
        txtNome.clear(); cmbPunti.setValue(null); txtStatus.setText("");
    	} else {
    		txtStatus.setText("ERRORE: Il Nome del Corso è già presente nel Libretto.");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    //questa parte di code viene eseguita per prima, una volta che tutte le variabili sono state inizializzate --> viene eseguito prima di passare al controllo di UI, per questo mi trovo già la cmbPunti popolata 
    void initialize() {
        assert cmbPunti != null : "fx:id=\"cmbPunti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVoti != null : "fx:id=\"txtVoti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStatus != null : "fx:id=\"txtStatus\" was not injected: check your FXML file 'Scene.fxml'.";
        //posso popolare qui la mia combobox con i numeri da 18 a 30 --> getItems per combobox
        cmbPunti.getItems().clear(); //la pulisco per sicurezza
        
        for(int i=18; i<=30; i++) {
        	cmbPunti.getItems().add(i);
        }
    }

}



