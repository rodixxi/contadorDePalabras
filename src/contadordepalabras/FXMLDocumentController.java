/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contadordepalabras;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import clases.TextFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author jpaganel
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    private TextFile libro, libro2;
    @FXML
    private Button btnBrowser;
    @FXML
    private TextField filepath;
    @FXML
    private TextArea txtTabla;
    @FXML
    private Button btnCargar;
    @FXML
    private TextField textToSearch;
    @FXML
    private TextField ocurrencias;
    @FXML
    private Button btnBuscar;
    
    
    
    
     @FXML
    private void handleButtonCargar(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir archivo de texto");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            filepath.setText(file.getAbsolutePath());
            libro = new TextFile(file);
            
        }
    }
    
        
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException{
        /*File file = new File("16082-8.txt");
        TextFile libro = new TextFile(file);*/
        libro.processFile();
        System.out.println(libro.toString());
        libro.saveToFile();
        libro2 = new TextFile();
        txtTabla.setText(libro2.toString());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void handleButtonBuscar(ActionEvent event){
        String str;
        str = String.valueOf(libro2.getValue(textToSearch.getText()));
        ocurrencias.setText(str);
    }
    
}
