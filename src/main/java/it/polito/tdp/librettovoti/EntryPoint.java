package it.polito.tdp.librettovoti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librettovoti.model.Libretto;


public class EntryPoint extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       // Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        //PASSO 2: creo il mio FXMLLoader mettendo nel costruttore l'indirizzo della Scene --> anziche chiamare un metodo statico della classe FXMLLoader, specificando QUALE FXML leggere (come nella riga sopra),
    	//creo un nuovo oggetto FXMLLoader, e nel costruttore specifico QUALE Scene creare, e poi chiamo il metodo load, che ora è un metodo d'istanza, non di classe, che attiva il caricamento --> faccio questo per avere un oggetto loader con un metodo getController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        Parent root = loader.load();
        FXMLController controllerCreatoDaLoader = loader.getController();
        //n.b. vai a vedere PASSO 3 su FXMLController
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        //PASSO 1, qui in mezzo devo creare la mia variabile Model, devo dichiararla qui (prima che il codice lanci la Scene)
        Libretto model = new Libretto(); 
        controllerCreatoDaLoader.setModel(model); //chiudo il cerchio, PASSO 4
        
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
