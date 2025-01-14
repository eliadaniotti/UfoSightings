/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Anno> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    public void setModel(Model model) {
    	this.model=model;
    	boxAnno.getItems().addAll(model.getAnni());
    }
    @FXML
    void handleAnalizza(ActionEvent event) {
    	txtResult.clear();
    	String stato = boxStato.getValue();
    	
    	if(stato == null)
    		txtResult.setText("Scegli uno stato");
    	
    	txtResult.appendText("Elenco vicini di " + stato + ": ");
    	for(String v: model.getVicini(stato))
    		txtResult.appendText(v+" ");
    	
    	txtResult.appendText("\nElenco stati raggiungibili da " + stato + ": ");
    	for(String v: model.getRaggiungibili(stato))
    		txtResult.appendText(v+" ");
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	Anno anno = boxAnno.getValue();
    	if(anno==null) {
    		txtResult.setText("Selezionare un anno dal menu a tendina");
    		return;
    	}
    	
    	model.creaGrafo(anno.getYear());
    	
    	boxStato.getItems().addAll(model.getGrafo().vertexSet());

    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }
}
