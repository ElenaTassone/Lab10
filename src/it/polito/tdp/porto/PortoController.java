package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private Button btnTrova;

    @FXML
    private Button btnSequenza;

    @FXML
    private TextArea txtResult;

    @FXML
    void doPopola(ActionEvent event) {
    	Author a = boxPrimo.getValue() ;
    	List<Author> coautori = model.getCoautori(a) ;
    	List<Author> nonCoautori = model.getNonCoautori(coautori, a);
		boxSecondo.getItems().addAll(nonCoautori) ;
    }
    @FXML
    void handleCoautori(ActionEvent event) {
    	txtResult.clear();
    	Author a = boxPrimo.getValue() ;
    	List<Author> coautori = model.getCoautori(a) ;
    	if(coautori != null){
    		for(Author c : coautori){
    			txtResult.appendText(c.toString());
    		}	
    	}else{
    		txtResult.setText("Nessuna collaborazione");
    	}
     }

    @FXML
    void handleSequenza(ActionEvent event) {
    	Author primo = boxPrimo.getValue() ;
    	Author secondo = boxSecondo.getValue() ;
    	List<Paper> papers = model.getPubb(primo, secondo) ;
    	txtResult.clear();
    	for(Paper p : papers)
    		txtResult.appendText(p.toString());

    }
    
    public void setModel(Model m){
    	this.model= m ;
    	List<Author> autori = model.addAutori() ;
    	boxPrimo.getItems().addAll(model.addAutori()) ;
    	m.creaGrafo();
    	
    	
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnTrova != null : "fx:id=\"btnTrova\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnSequenza != null : "fx:id=\"btnSequenza\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

        
        
        
    }
}

